package com.kylewm.mf2j;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public abstract class CorpusTestBase
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
        JsonDict parsed = parser.parse(html, new URI(""));
        JsonDict expected = mapper.readValue(json, JsonDict.class);
        if (expected.containsKey("rel-urls"))
            expected.remove("rel-urls");
        assertTrue(parsed.equals(expected));
    }

    protected void Run(String prefix) throws IOException, URISyntaxException {
        RunTestCase(prefix + ".html", prefix + ".json");
    }
}
