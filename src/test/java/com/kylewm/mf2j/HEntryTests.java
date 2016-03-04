package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HEntryTests extends TestCorpusBase
{
    @Test
    public void ImpliedValueNested() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-entry/impliedvalue-nested");
    }

    @Test
    public void JustAHyperlink() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-entry/justahyperlink");
    }

    @Test
    public void JustAName() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-entry/justaname");
    }

    @Test
    public void SummaryContent() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-entry/summarycontent");
    }

    @Test
    public void UProperty() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-entry/u-property");
    }

    @Test
    public void UrlInContent() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-entry/urlincontent");
    }
}
