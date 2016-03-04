package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HAdrTests extends TestCorpusBase
{
    @Test
    public void Geo() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-adr/geo");
    }

    @Test
    public void GeoUrl() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-adr/geourl");
    }

    @Test
    public void JustAName() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-adr/justaname");
    }

    @Test
    public void SimpleProperties() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-adr/simpleproperties");
    }
}
