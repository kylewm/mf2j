package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HGeoTests extends CorpusTestBase
{
    @Test
    public void AbbrPattern() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-geo/abbrpattern");
    }

    @Test
    public void Altitude() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-geo/altitude");
    }

    @Test
    public void Hidden() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-geo/hidden");
    }

    @Test
    public void JustAName() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-geo/justaname");
    }

    @Test
    public void SimpleProperties() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-geo/simpleproperties");
    }

    @Test
    public void ValueTitleClass() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-geo/valuetitleclass");
    }
}
