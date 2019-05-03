package com.zhilingsd.collection.common.utils.jsonserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zhilingsd.collection.common.utils.SortUtil;

import java.io.IOException;

public class CamelCaseToUnderLine extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String str = SortUtil.getUnderLineString(s);
        jsonGenerator.writeString(str);
    }
}
