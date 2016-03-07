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
import java.util.HashMap;

public abstract class TestCorpusBase
{
    protected String LoadResource(String resourcePath) throws URISyntaxException, IOException
    {
        URL url = this.getClass().getResource(resourcePath);
        Path path = Paths.get(url.toURI());
        return new String(Files.readAllBytes(path), "UTF8");
    }

    protected void RunTestCase(String htmlPath, String jsonPath) throws IOException, URISyntaxException
    {
        ObjectMapper mapper = new ObjectMapper();
        Mf2Parser parser = new Mf2Parser();
        String html = LoadResource(htmlPath);
        String json = LoadResource(jsonPath);
        HashMap actual = mapper.readValue(parser.parse(html, new URI("http://example.com")).toString(), HashMap.class);
        HashMap expected = mapper.readValue(json, HashMap.class);
        //TODO: remove when parser supports rel-urls
        if (expected.containsKey("rel-urls"))
            expected.remove("rel-urls");
        Assert.assertEquals(expected, actual);
    }

    protected void Run(String prefix) throws IOException, URISyntaxException {
        RunTestCase(prefix + ".html", prefix + ".json");
    }
}
