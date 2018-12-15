package com.library.libraryapi.Util.Adapter;


import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class ListAdapter implements JsonSerializer<List<?>> {

    @Override
    public JsonElement serialize(List<?> src, Type type, JsonSerializationContext context) {
        if (src == null || src.isEmpty()) {
            return null;
        }

        JsonArray array = new JsonArray();

        for (Object child : src) {

            JsonElement element = context.serialize(child);
            array.add(element);
        }

        return array;
    }
}
