package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HEventTests extends TestCorpusBase
{
    @Test
    public void AmPm() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-event/ampm");
    }

    @Test
    public void Attendees() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-event/attendees");
    }

    @Test
    public void Combining() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-event/combining");
    }

    @Test
    public void Concatenate() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-event/concatenate");
    }

    @Test
    public void Dates() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-event/dates");
    }

    @Test
    public void DtProperty() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-event/dt-property");
    }

    @Test
    public void JustAHyperlink() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-event/justahyperlink");
    }

    @Test
    public void JustAName() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-event/justaname");
    }

    @Test
    public void Time() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-event/time");
    }
}
