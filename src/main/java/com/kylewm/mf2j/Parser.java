package com.kylewm.mf2j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Parser {

	public Parser() {
		
	}
	
	public JsonDict parse(URI resource) throws IOException {
		Document text = Jsoup.connect(resource.toString()).get();
		return parse(text, resource);
	}
	
	public JsonDict parse(String doc, URI baseURI) {
		Document text = Jsoup.parse(doc);
		return parse(text, baseURI);
	}
	
	public JsonDict parse(Document doc, URI baseURI) {
		JsonDict dict = new JsonDict();
		JsonList items = dict.getOrCreateList("items");
		dict.put("rels", new JsonDict());
		parseMicroformats(doc, baseURI, items);
		return dict;
	}

	private void parseMicroformats(Element elem, URI baseURI, JsonList items) {
		if (hasRootClass(elem)) {
			JsonDict itemDict = parseMicroformat(elem, baseURI);
			items.add(itemDict);
		}
		else {
			for (Element child : elem.children()) {
				parseMicroformats(child, baseURI, items);
			}
		}
	}

	private JsonDict parseMicroformat(Element elem, URI baseURI) {
		JsonDict itemDict = new JsonDict();
		itemDict.put("type", getRootClasses(elem));
		itemDict.put("properties", new JsonDict());
		
		for (Element child : elem.children()) {
			parseProperties(child, baseURI, itemDict);
		}
		return itemDict;
	}

	private void parseProperties(Element elem, URI baseURI, JsonDict itemDict) {
		boolean isProperty = false, isMicroformat = false;
		
		JsonDict valueObj = null;
		if (hasRootClass(elem)) {
			valueObj = parseMicroformat(elem, baseURI);
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
				value = parseUrlProperty(elem, baseURI);
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
		
		if (!isProperty && !isMicroformat) {
			for (Element child : elem.children()) {
				parseProperties(child, baseURI, itemDict);
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

	private String parseUrlProperty(Element elem, URI baseURI) {
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
			return baseURI.resolve(url).toString();
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
		return "1982-11-24"; // TODO
	}

	private JsonDict parseHtmlProperty(Element elem) {
		JsonDict dict = new JsonDict();
		dict.put("html", elem.html());
		dict.put("text", elem.text());
		return dict;
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

	public static void main(String args[]) throws IOException, URISyntaxException {
		Parser p = new Parser();
		JsonDict result = p.parse(new URI("https://kylewm.com"));
		System.out.println(result);
	}
	
}
