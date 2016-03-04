package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HFeedTests extends TestCorpusBase
{
    @Test
    public void ImpliedTitle() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-feed/implied-title");
    }

    @Test
    public void Simple() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-feed/simple");
    }
}
