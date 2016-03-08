package com.kylewm.mf2j;

import java.util.*;

public class JsonMap implements Map<String, Object>
{
    private Map<String,Object> _m;

    public JsonMap(Map<String,Object> m) {
        _m = m;
    }

    public static String toString(ArrayList<Object> arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Boolean first = true;
        for (Object val: arr) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            if (val instanceof Map) {
                sb.append(toString((Map) val));
            } else if (val instanceof ArrayList) {
                sb.append(toString((ArrayList) val));
            } else if (val instanceof String) {
                sb.append("\"" + val + "\"");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static String toString(Map<String,Object> map) {
        TreeSet<String> keys = new TreeSet<String>(map.keySet());
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Boolean first = true;
        for (String key: keys) {
            Object val = map.get(key);
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append("\"" + key + "\": ");
            if (val instanceof Map) {
                sb.append(toString((Map) val));
            } else if (val instanceof ArrayList) {
                sb.append(toString((ArrayList) val));
            } else if (val instanceof String) {
                sb.append("\"" + val + "\"");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public String toString() {
        return toString(_m);
    }

    public int size()
    {
        return _m.size();
    }

    public boolean isEmpty()
    {
        return _m.isEmpty();
    }

    public boolean containsKey(Object key)
    {
        return _m.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return _m.containsValue(value);
    }

    public Object get(Object key)
    {
        return _m.get(key);
    }

    public Object put(String key, Object value)
    {
        return _m.put(key, value);
    }

    public Object remove(Object key)
    {
        return _m.remove(key);
    }

    public void putAll(Map<? extends String, ?> m)
    {
        _m.putAll(m);
    }

    public void clear()
    {
        _m.clear();
    }

    public Set<String> keySet()
    {
        return _m.keySet();
    }

    public Collection<Object> values()
    {
        return _m.values();
    }

    public Set<Entry<String, Object>> entrySet()
    {
        return _m.entrySet();
    }

    public boolean equals(Object o)
    {
        return _m.equals(o);
    }

    public int hashCode()
    {
        return _m.hashCode();
    }
}
