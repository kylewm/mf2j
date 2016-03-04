package com.kylewm.mf2j;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class Mf2ParserTest
{
    protected String LoadResource(String resourcePath) throws URISyntaxException, IOException
    {
        URL url = this.getClass().getResource(resourcePath);
        Path path = Paths.get(url.toURI());
        return new String(Files.readAllBytes(path), "UTF8");
    }

    protected void ParseSample(String prefix) throws IOException, URISyntaxException
    {
        ObjectMapper mapper = new ObjectMapper();
        Mf2Parser parser = new Mf2Parser();
        String html = LoadResource(prefix + ".html");
        String json = LoadResource(prefix + ".json");
        JsonDict parsed = parser.parse(html, new URI(""));
        JsonDict expected = mapper.readValue(json, JsonDict.class);
        if (expected.containsKey("rel-urls"))
            expected.remove("rel-urls");
        assertTrue(parsed.equals(expected));
    }

    @Test
    public void BaseUrl() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/baseurl");
    }

    @Test
    public void ChildImplied() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/childimplied");
    }

    @Test
    public void ExtendedDescription() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/extendeddescription");
    }

    @Test
    public void HCard() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/hcard");
    }

    @Test
    public void HOrgHCard() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/horghcard");
    }

    @Test
    public void HyperlinkedPhoto() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/hyperlinkedphoto");
    }

    @Test
    public void ImpliedName() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/impliedname");
    }

    @Test
    public void ImpliedPhoto() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/impliedphoto");
    }

    @Test
    public void ImpliedUrl() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/impliedurl");
    }

    @Test
    public void JustAHyperlink() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/justahyperlink");
    }

    @Test
    public void JustAName() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/justaname");
    }

    @Test
    public void Nested() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/nested");
    }

    @Test
    public void PProperty() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/p-property");
    }

    @Test
    public void RelativeUrls() throws IOException, URISyntaxException
    {
        ParseSample("/microformats-v2/h-card/relativeurls");
    }
}