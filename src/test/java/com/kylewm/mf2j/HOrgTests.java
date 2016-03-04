package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HOrgTests extends TestCorpusBase
{
    @Test
    public void Hyperlink() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-org/hyperlink");
    }

    @Test
    public void Simple() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-org/simple");
    }

    @Test
    public void SimpleProperties() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-org/simpleproperties");
    }
}
