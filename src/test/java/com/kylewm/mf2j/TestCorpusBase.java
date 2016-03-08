package com.kylewm.mf2j;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public abstract class TestCorpusBase
{
    protected String LoadResource(String resourcePath) throws URISyntaxException, IOException {
        URL url = this.getClass().getResource(resourcePath);
        Path path = Paths.get(url.toURI());
        return new String(Files.readAllBytes(path), "UTF8");
    }

    protected static String CollapseWhitespace(String s) {
        return s.replaceAll("\\s+", " ");
    }

    protected static void CollapseWhitespace(ArrayList<Object> a) {
        for (int i = 0; i < a.size(); i++) {
            Object entry = a.get(i);
            if (entry instanceof Map) {
                CollapseWhitespace((Map) entry);
            } else if (entry instanceof String) {
                a.set(i, CollapseWhitespace((String) entry));
            }
        }
    }

    protected static void CollapseWhitespace(Map<String,Object> mf) {
        for (Map.Entry<String,Object> entry: mf.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                CollapseWhitespace((Map) value);
            } else if (value instanceof ArrayList) {
                CollapseWhitespace((ArrayList) value);
            } else if (value instanceof String) {
                mf.put(entry.getKey(), CollapseWhitespace((String) value));
            }
        }
    }

    protected void RunTestCase(String htmlPath, String jsonPath) throws IOException, URISyntaxException {
        ObjectMapper mapper = new ObjectMapper();
        Mf2Parser parser = new Mf2Parser();
        String html = LoadResource(htmlPath);
        String json = LoadResource(jsonPath);
        JsonDict parsed = parser.parse(html, new URI("http://example.com"));
        SortedJsonMap actual = new SortedJsonMap(mapper.readValue(parsed.toString(), Map.class));
        SortedJsonMap expected = new SortedJsonMap(mapper.readValue(json, Map.class));
        //TODO: remove when whitespace issues are sorted out
        CollapseWhitespace(actual);
        CollapseWhitespace(expected);
        //TODO: remove when parser supports rel-urls
        if (expected.containsKey("rel-urls"))
            expected.remove("rel-urls");
        Assert.assertEquals(expected, actual);
    }

    protected void Run(String prefix) throws IOException, URISyntaxException {
        RunTestCase(prefix + ".html", prefix + ".json");
    }
}
