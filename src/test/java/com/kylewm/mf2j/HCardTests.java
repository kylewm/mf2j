package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HCardTests extends TestCorpusBase
{
    @Test
    public void BaseUrl() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/baseurl");
    }

    @Test
    public void ChildImplied() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/childimplied");
    }

    @Test
    public void ExtendedDescription() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/extendeddescription");
    }

    @Test
    public void HCard() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/hcard");
    }

    @Test
    public void HOrgHCard() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/horghcard");
    }

    @Test
    public void HyperlinkedPhoto() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/hyperlinkedphoto");
    }

    @Test
    public void ImpliedName() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/impliedname");
    }

    @Test
    public void ImpliedPhoto() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/impliedphoto");
    }

    @Test
    public void ImpliedUrl() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/impliedurl");
    }

    @Test
    public void JustAHyperlink() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/justahyperlink");
    }

    @Test
    public void JustAName() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/justaname");
    }

    @Test
    public void Nested() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/nested");
    }

    @Test
    public void PProperty() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/p-property");
    }

    @Test
    public void RelativeUrls() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-card/relativeurls");
    }
}