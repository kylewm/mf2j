package com.kylewm.mf2j;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HAsNoteTests extends CorpusTestBase
{
    @Test
    public void Note() throws IOException, URISyntaxException
    {
        Run("/microformats-v2/h-as-note/note");
    }
}
