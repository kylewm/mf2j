package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HReviewAggregateTests extends TestCorpusBase
{
    @Test
    public void HEvent() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-review-aggregate/hevent");
    }

    @Test
    public void JustAHyperlink() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-review-aggregate/justahyperlink");
    }

    @Test
    public void SimpleProperties() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-review-aggregate/simpleproperties");
    }
}
