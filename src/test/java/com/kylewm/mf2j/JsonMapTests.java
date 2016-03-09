package com.kylewm.mf2j;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JsonMapTests
{
    @Test
    public void EscapeQuoteTest() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("key", "\"");
        ArrayList<Object> arr = new ArrayList<Object>();
        arr.add("\"");
        map.put("arr", arr);
        JsonMap jsonMap = new JsonMap(map);
        Assert.assertEquals("{\"key\": \"\\\"\", \"arr\": [\"\\\"\"]}", jsonMap.toString());
    }

    @Test
    public void EscapeNLTest() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("key", "\n");
        ArrayList<Object> arr = new ArrayList<Object>();
        arr.add("\n");
        map.put("arr", arr);
        JsonMap jsonMap = new JsonMap(map);
        Assert.assertEquals("{\"key\": \"\\n\", \"arr\": [\"\\n\"]}", jsonMap.toString());
    }

    @Test
    public void EscapeCRTest() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("key", "\r");
        ArrayList<Object> arr = new ArrayList<Object>();
        arr.add("\r");
        map.put("arr", arr);
        JsonMap jsonMap = new JsonMap(map);
        Assert.assertEquals("{\"key\": \"\\r\", \"arr\": [\"\\r\"]}", jsonMap.toString());
    }
}
