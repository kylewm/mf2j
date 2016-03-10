package com.kylewm.mf2j;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDict extends HashMap<String, Object> {

    /**
     * 
     */
    private static final long serialVersionUID = 3604372225030827955L;

    public JsonDict() {
        super();
    }

    public JsonDict(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public JsonDict(int initialCapacity) {
        super(initialCapacity);
    }

    public JsonDict(Map<? extends String, ? extends Object> m) {
        super(m);
    }

    public JsonDict getDict(String key) {
        return (JsonDict) get(key);
    }

    public JsonList getList(String key) {
        return (JsonList) get(key);
    }

    public JsonDict getOrCreateDict(String key) {
        JsonDict dict = (JsonDict) get(key);
        if (dict == null) {
            put(key, dict = new JsonDict());
        }
        return dict;
    }

    public JsonList getOrCreateList(String key) {
        JsonList list = (JsonList) get(key);
        if (list == null) {
            put(key, list = new JsonList());
        }
        return list;
    }

    public static String escapeString(String str) {
        return str.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;

        List<String> keys = new ArrayList<String>(keySet());
        Collections.sort(keys);

        for (String key : keys) {
            if (!first) { sb.append(",");}
            first = false;
            sb.append("\"" + escapeString(key) + "\":");
            Object value = get(key);
            if (value instanceof String) {
                sb.append("\"" + escapeString((String) value) + "\"");
            }
            else {
                sb.append(value);
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static JsonDict fromString(String json) throws IOException
    {
        JsonParser parser = new JsonFactory().createParser(json);
        parser.nextToken();
        return parseDict(parser);
    }

    protected static JsonList parseList(JsonParser parser) throws IOException
    {
        JsonList list = new JsonList();
        JsonToken token = parser.getCurrentToken();
        if (token != JsonToken.START_ARRAY)
            throw new IOException("expected START_ARRAY");
        while ((token = parser.nextToken()) != JsonToken.END_ARRAY) {
            switch (token) {
                case START_OBJECT:
                    list.add(parseDict(parser));
                    break;
                case START_ARRAY:
                    list.add(parseList(parser));
                    break;
                case VALUE_STRING:
                    list.add(parser.getText());
                    break;
                default:
                    throw new IOException("unexpected token type " + token);
            }
        }
        return list;
    }

    protected static JsonDict parseDict(JsonParser parser) throws IOException
    {
        JsonDict dict = new JsonDict();
        JsonToken token = parser.getCurrentToken();
        if (token != JsonToken.START_OBJECT)
            throw new IOException("expected START_OBJECT");
        while ((token = parser.nextToken()) != JsonToken.END_OBJECT) {
            if (token != JsonToken.FIELD_NAME)
                throw new IOException("expected FIELD_NAME");
            String key = parser.getText();
            token = parser.nextToken();
            switch (token) {
                case START_OBJECT:
                    dict.put(key, parseDict(parser));
                    break;
                case START_ARRAY:
                    dict.put(key, parseList(parser));
                    break;
                case VALUE_STRING:
                    dict.put(key, parser.getText());
                    break;
                default:
                    throw new IOException("unexpected token type " + token);
            }
        }
        return dict;
    }
}
