package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class RelTests extends CorpusTestBase
{
    @Test
    public void DuplicateRels() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/rel/duplicate-rels");
    }

    @Test
    public void License() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/rel/license");
    }

    @Test
    public void Nofollow() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/rel/nofollow");
    }

    @Test
    public void RelUrls() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/rel/rel-urls");
    }

    @Test
    public void VaryingTextDuplicateRels() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/rel/varying-text-duplicate-rels");
    }

    @Test
    public void XfnAll() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/rel/xfn-all");
    }

    @Test
    public void XfnElsewhere() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/rel/xfn-elsewhere");
    }
}
