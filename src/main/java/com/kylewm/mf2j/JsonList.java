package com.kylewm.mf2j;

import java.util.ArrayList;
import java.util.Collection;

public class JsonList extends ArrayList<Object> {

    /**
     * 
     */
    private static final long serialVersionUID = 1280821270688138705L;

    public JsonList() {
        super();
    }

    public JsonList(Collection<? extends Object> c) {
        super(c);
    }

    public JsonList(int initialCapacity) {
        super(initialCapacity);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Object value : this) {
            if (!first) { sb.append(",");}
            first = false;
            if (value instanceof String) {
                sb.append("\"" + JsonDict.escapeString((String) value) + "\"");
            }
            else {
                sb.append(value);
            }
        }
        sb.append("]");
        return sb.toString();
    }


}
