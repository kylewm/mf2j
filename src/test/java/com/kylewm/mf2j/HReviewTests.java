package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HReviewTests extends CorpusTestBase
{
    @Test
    public void Hyperlink() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-review/hyperlink");
    }

    @Test
    public void ImpliedItem() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-review/implieditem");
    }

    @Test
    public void Item() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-review/item");
    }

    @Test
    public void JustAName() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-review/justaname");
    }

    @Test
    public void Photo() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-review/photo");
    }

    @Test
    public void VCard() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-review/vcard");
    }
}
