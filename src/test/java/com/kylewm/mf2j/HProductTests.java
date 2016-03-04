package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HProductTests extends TestCorpusBase
{
    @Test
    public void Aggregate() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-product/aggregate");
    }

    @Test
    public void JustAHyperlink() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-product/justahyperlink");
    }

    @Test
    public void JustAName() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-product/justaname");
    }

    @Test
    public void SimpleProperties() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-product/simpleproperties");
    }
}
