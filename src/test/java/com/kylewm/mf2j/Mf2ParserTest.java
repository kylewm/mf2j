package com.kylewm.mf2j;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class Mf2ParserTest
{
    @Test
    public void SmokeTest() {
        Mf2Parser mf2Parser = new Mf2Parser();
        try
        {
            mf2Parser.parse("", new URI(""));
        } catch (URISyntaxException e)
        {}
    }
}