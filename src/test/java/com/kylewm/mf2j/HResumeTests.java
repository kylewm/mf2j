package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HResumeTests extends TestCorpusBase
{
    @Test
    public void Affiliation() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-resume/affiliation");
    }

    @Test
    public void Contact() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-resume/contact");
    }

    @Test
    public void Education() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-resume/education");
    }

    @Test
    public void JustAName() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-resume/justaname");
    }

    @Test
    public void Skill() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-resume/skill");
    }

    @Test
    public void Work() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-resume/work");
    }
}
