package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HRecipeTests extends TestCorpusBase
{
    @Test
    public void All() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-recipe/all");
    }

    @Test
    public void Minimum() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-recipe/minimum");
    }
}
