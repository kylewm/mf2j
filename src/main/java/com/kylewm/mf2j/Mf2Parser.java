package com.kylewm.mf2j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Parse a Microformats2 formatted HTML document.
 * @author kmahan
 *
 */
public class Mf2Parser {

    private boolean includeAlternates;
    private boolean includeRelUrls;

    /**
     * Constructor
     */
    public Mf2Parser() {
        this.includeAlternates = true;
        this.includeRelUrls = false;
    }

    /**
     * If true, include the "alternates" key in the parsed output.
     * @param includeAlts
     * @return this, for method chaining
     */
    public Mf2Parser setIncludeAlternates(boolean includeAlts) {
        this.includeAlternates = includeAlts;
        return this;
    }

    /**
     * If true, include the experimental "rel-urls" hash in the parsed output.
     * @param includeRelUrls
     * @return this, for method chaining
     */
    public Mf2Parser setIncludeRelUrls(boolean includeRelUrls) {
        this.includeRelUrls = includeRelUrls;
        return this;
    }

    /**
     * Fetch a remote resource and parse it for microformats2.
     * @param resource the URI of the resource to fetch.
     * @return a well-defined JSON structure containing the parsed microformats2 data.
     * @throws IOException
     */
    public JsonDict parse(URI resource) throws IOException {
        Document text = Jsoup.connect(resource.toString()).get();
        return parse(text, resource);
    }

    /**
     * Parse an existing document for microformats2.
     * @param html the contents of the document to parse
     * @param baseUri the URI where the document exists, used for normalization
     * @return a well-defined JSON structure containing the parsed microformats2 data.
     */
    public JsonDict parse(String html, URI baseUri) {
        Document doc = Jsoup.parse(html);
        return parse(doc, baseUri);
    }

    private URI findBaseUri(Document doc, URI baseUri) {
        Element base = doc.getElementsByTag("base").first();
        if (base != null && base.hasAttr("href")) {
            baseUri = baseUri.resolve(base.attr("href"));
        }
        // normalize URIs with missing path
        String path = baseUri.getPath();
        if (path == null || path.isEmpty())
        {
            try
            {
                baseUri = new URI(baseUri.getScheme(), baseUri.getAuthority(), "/", null, null);
            } catch (URISyntaxException e) {}
        }
        return baseUri;
    }

    /**
     * Parse an existing document for microformats2.
     * @param doc the Jsoup document to parse
     * @param baseUri the URI where the document exists, used for normalization
     * @return a well-defined JSON structure containing the parsed microformats2 data.
     */    
    public JsonDict parse(Document doc, URI baseUri) {
        baseUri = findBaseUri(doc, baseUri);

        JsonDict dict = new JsonDict();
        JsonList items = dict.getOrCreateList("items");
        parseMicroformats(doc, baseUri, items);

        parseRels(doc, baseUri, dict);
        return dict;
    }

    private void parseRels(Document doc, URI baseUri, JsonDict dict) {
        dict.getOrCreateDict("rels");
        if (includeRelUrls) {
            dict.getOrCreateDict("rel-urls");
        }

        for (Element link : doc.select("a[rel][href],link[rel][href]")) {
            String relStr = link.attr("rel");
            String href = link.attr("href");
            href = baseUri.resolve(href).toString();

            JsonList rels = new JsonList();
            for (String rel : relStr.split(" ")) {
                rel = rel.trim();
                if (!rel.isEmpty()) {
                    rels.add(rel);
                }
            }

            if (includeAlternates && rels.contains("alternate")) {
                String relNoAlt = Pattern.compile("(^|\\s+)alternate(\\s+|$)").matcher(relStr).replaceAll(" ").trim();
                JsonDict altDict = new JsonDict();
                altDict.put("url", href);
                altDict.put("rel", relNoAlt);
                for (String attr : Arrays.asList("hreflang", "media", "type", "title")) {
                    if (link.hasAttr(attr)) {
                        altDict.put(attr, link.attr(attr));
                    }
                }
                String textContent = link.text().trim();
                if (!textContent.isEmpty()) {
                    altDict.put("text", textContent);
                }
                dict.getOrCreateList("alternates").add(altDict);
            }
            else {
                for (Object rel : rels) {
                    dict.getDict("rels").getOrCreateList((String) rel).add(href);
                }

                if (includeRelUrls) {
                    JsonDict urlDict = dict.getDict("rel-urls").getOrCreateDict(href);
                    urlDict.put("rels", rels);
                    for (String attr : Arrays.asList("hreflang", "media", "type", "title")) {
                        if (link.hasAttr(attr)) {
                            urlDict.put(attr, link.attr(attr));
                        }
                    }
                    String textContent = link.text().trim();
                    if (!textContent.isEmpty()) {
                        urlDict.put("text", textContent);
                    }
                }
            }
        }
    }

    private void parseMicroformats(Element elem, URI baseUri, JsonList items) {
        if (hasRootClass(elem)) {
            JsonDict itemDict = parseMicroformat(elem, baseUri);
            items.add(itemDict);
        }
        else {
            for (Element child : elem.children()) {
                parseMicroformats(child, baseUri, items);
            }
        }
    }

    private JsonDict parseMicroformat(Element elem, URI baseUri) {
        JsonDict itemDict = new JsonDict();
        itemDict.put("type", getRootClasses(elem));
        JsonDict properties = itemDict.getOrCreateDict("properties");

        for (Element child : elem.children()) {
            parseProperties(child, baseUri, itemDict);
        }

        if (!properties.containsKey("name")) {
            String impliedName = parseImpliedName(elem);
            if (impliedName != null) {
                JsonList implNameList = new JsonList();
                implNameList.add(impliedName);
                properties.put("name", implNameList);
            }
        }
        if (!properties.containsKey("url")) {
            String impliedUrl = parseImpliedUrl(elem, baseUri);
            if (impliedUrl != null) {
                JsonList implUrlList = new JsonList();
                implUrlList.add(impliedUrl);
                properties.put("url", implUrlList);
            }
        }
        if (!properties.containsKey("photo")) {
            String impliedPhoto = parseImpliedPhoto(elem, baseUri);
            if (impliedPhoto != null) {
                JsonList implPhotoList = new JsonList();
                implPhotoList.add(impliedPhoto);
                properties.put("photo", implPhotoList);
            }
        }

        return itemDict;
    }

    private void parseProperties(Element elem, URI baseUri, JsonDict itemDict) {
        boolean isProperty = false, isMicroformat = false;

        JsonDict valueObj = null;
        if (hasRootClass(elem)) {
            valueObj = parseMicroformat(elem, baseUri);
            isMicroformat = true;
        }

        for (String className : elem.classNames()) {
            String propName = null;
            Object value = null;
            if (className.startsWith("p-")) {
                propName = className.substring(2);
                value = parseTextProperty(elem);
                isProperty = true;
            }
            else if (className.startsWith("u-")) {
                propName = className.substring(2);
                value = parseUrlProperty(elem, baseUri);
                isProperty = true;
            }
            else if (className.startsWith("dt-")) {
                propName = className.substring(3);
                value = parseDateTimeProperty(elem);
                isProperty = true;
            }
            else if (className.startsWith("e-")) {
                propName = className.substring(2);
                value = parseHtmlProperty(elem);
                isProperty = true;
            }

            if (propName != null) {
                if (isMicroformat && valueObj != null) {
                    JsonDict copy = new JsonDict(valueObj);
                    copy.put("value", value);
                    value = copy;
                }
                itemDict.getDict("properties").getOrCreateList(propName).add(value);
            }
        }

        if (!isProperty && isMicroformat) {
            itemDict.getOrCreateList("children").add(valueObj);
        }

        if (!isMicroformat) {
            for (Element child : elem.children()) {
                parseProperties(child, baseUri, itemDict);
            }
        }
    }

    private String parseTextProperty(Element elem) {
        /// TODO value-class-pattern
        if ("abbr".equals(elem.tagName()) && elem.hasAttr("title")) {
            return elem.attr("title");
        }
        if (("data".equals(elem.tagName()) || "input".equals(elem.tagName())) && elem.hasAttr("value")) {
            return elem.attr("value");
        }
        if (("img".equals(elem.tagName()) || "area".equals(elem.tagName())) && elem.hasAttr("alt")) {
            return elem.attr("alt");
        }
        // TODO replace nested <img> with alt or src
        return elem.text().trim();
    }

    private String parseUrlProperty(Element elem, URI baseUri) {
        String url = null;
        if (("a".equals(elem.tagName()) || "area".equals(elem.tagName())) && elem.hasAttr("href")) {
            url = elem.attr("href");
        }
        if (("img".equals(elem.tagName()) || "audio".equals(elem.tagName())
                || "video".equals(elem.tagName()) || "source".equals(elem.tagName())) && elem.hasAttr("src")) {
            url = elem.attr("src");
        }
        if ("object".equals(elem.tagName()) && elem.hasAttr("data")) {
            url = elem.attr("data");
        }
        if (url != null) {
            return baseUri.resolve(url).toString();
        }
        // TODO value-class-pattern
        if ("abbr".equals(elem.tagName()) && elem.hasAttr("title")) {
            return elem.attr("title");
        }
        if (("data".equals(elem.tagName()) || "input".equals(elem.tagName())) && elem.hasAttr("value")) {
            return elem.attr("value");
        }
        return elem.text().trim();
    }

    private String parseDateTimeProperty(Element elem) {
        // TODO value-class-pattern
        //if time.dt-x[datetime] or ins.dt-x[datetime] or del.dt-x[datetime], then return the datetime attribute
        if (("time".equals(elem.tagName()) || "ins".equals(elem.tagName()) || "del".equals(elem.tagName()))
                && elem.hasAttr("datetime")) {
            return elem.attr("datetime");
        }
        if ("abbr".equals(elem.tagName()) && elem.hasAttr("title")) {
            return elem.attr("title");
        }
        if (("data".equals(elem.tagName()) || "input".equals(elem.tagName())) && elem.hasAttr("value")) {
            return elem.attr("value");
        }
        return elem.text().trim();


    }

    private JsonDict parseHtmlProperty(Element elem) {
        JsonDict dict = new JsonDict();
        dict.put("html", elem.html());
        dict.put("text", elem.text());
        return dict;
    }

    private String parseImpliedPhoto(Element elem, URI baseUri) {
        String href = parseImpliedPhotoRelative(elem);
        if (href != null) {
            return baseUri.resolve(href).toString();
        }
        return null;
    }

    private String parseImpliedPhotoRelative(Element elem) {
        String[][] tagAttrs = {
                {"img", "src"},
                {"object", "data"},
        };

        for (String[] tagAttr : tagAttrs) {
            String tag = tagAttr[0], attr = tagAttr[1];
            if (tag.equals(elem.tagName()) && elem.hasAttr(attr)) {
                return elem.attr(attr);
            }
        }

        for (String[] tagAttr : tagAttrs) {
            String tag = tagAttr[0], attr = tagAttr[1];
            Elements children = filterByTag(elem.children(), tag);
            if (children.size() == 1) {
                Element child = children.first();
                if (!hasRootClass(child) && child.hasAttr(attr)) {
                    return child.attr(attr);
                }
            }
        }

        Elements children = elem.children();
        if (children.size() == 1) {
            Element child = children.first();
            for (String[] tagAttr : tagAttrs) {
                String tag = tagAttr[0], attr = tagAttr[1];
                Elements grandChildren = filterByTag(child.children(), tag);
                if (grandChildren.size() == 1) {
                    Element grandChild = grandChildren.first();
                    if (!hasRootClass(grandChild) && grandChild.hasAttr(attr)) {
                        return grandChild.attr(attr);
                    }
                }
            }
        }

        return null;
    }

    private String parseImpliedUrl(Element elem, URI baseUri) {
        String href = parseImpliedUrlRelative(elem);
        if (href != null) {
            return baseUri.resolve(href).toString();
        }
        return null;
    }

    private String parseImpliedUrlRelative(Element elem) {
        //     if a.h-x[href] or area.h-x[href] then use that [href] for url
        if (("a".equals(elem.tagName()) || "area".equals(elem.tagName()))
                && elem.hasAttr("href")) {
            return elem.attr("href");
        }
        //else if .h-x>a[href]:only-of-type:not[.h-*] then use that [href] for url
        //else if .h-x>area[href]:only-of-type:not[.h-*] then use that [href] for url
        for (String childTag : Arrays.asList("a", "area")) {
            Elements children = filterByTag(elem.children(), childTag);
            if(children.size() == 1) {
                Element child = children.first();
                if (!hasRootClass(child) && child.hasAttr("href")) {
                    return child.attr("href");
                }
            }
        }

        return null;
    }


    private String parseImpliedName(Element elem) {
        if (("img".equals(elem.tagName()) || ("area".equals(elem.tagName())) && elem.hasAttr("alt"))) {
            return elem.attr("alt");
        }
        if ("abbr".equals(elem.tagName()) && elem.hasAttr("title")) {
            return elem.attr("title");
        }

        Elements children = elem.children();
        if (children.size() == 1) {
            Element child = children.first();
            // else if .h-x>img:only-child[alt]:not[.h-*] then use that img alt for name
            // else if .h-x>area:only-child[alt]:not[.h-*] then use that area alt for name
            if (!hasRootClass(child)
                    && ("img".equals(child.tagName()) || "area".equals(child.tagName()))
                    && child.hasAttr("alt")) {
                return child.attr("alt");
            }
            // else if .h-x>abbr:only-child[title] then use that abbr title for name
            if ("abbr".equals(child.tagName()) && child.hasAttr("title")) {
                return child.attr("title");
            }

            Elements grandChildren = child.children();
            if (grandChildren.size() == 1) {
                Element grandChild = grandChildren.first();
                // else if .h-x>:only-child>img:only-child[alt]:not[.h-*] then use that img alt for name
                // else if .h-x>:only-child>area:only-child[alt]:not[.h-*] then use that area alt for name
                if (!hasRootClass(grandChild)
                        && ("img".equals(grandChild.tagName()) || "area".equals(grandChild.tagName()))
                        && grandChild.hasAttr("alt")) {
                    return grandChild.attr("alt");
                }
                // else if .h-x>:only-child>abbr:only-child[title] use that abbr title for name
                if ("abbr".equals(grandChild.tagName()) && grandChild.hasAttr("c")) {
                    return grandChild.attr("title");
                }
            }
        }

        // else use the textContent of the .h-x for name
        // drop leading & trailing white-space from name, including nbsp
        return elem.text().trim();
    }



    private Elements filterByTag(Elements elems, String tag) {
        Elements filtered = new Elements();
        for (Element child : elems) {
            if (tag.equals(child.tagName())) {
                filtered.add(child);
            }
        }
        return filtered;
    }

    private JsonList getRootClasses(Element elem) {
        JsonList rootClasses = null;
        for (String className : elem.classNames()) {
            if (isRootClass(className)) {
                if (rootClasses == null) {
                    rootClasses = new JsonList();
                }
                rootClasses.add(className);
            }
        }
        return rootClasses;
    }

    private boolean hasRootClass(Element elem) {
        for (String className : elem.classNames()) {
            if (isRootClass(className)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRootClass(String className) {
        return className.startsWith("h-");
    }

}
