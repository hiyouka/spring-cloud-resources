package com.hiyouka.sources.pojo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;


public class JsonSerializer<T> implements Serializer<T> {

    public JsonSerializer() {

    }

    public void configure(Map<String, ?> topic, boolean isKey) {

    }

    public byte[] serialize(String topic, T data) {
        try {
            byte[] result = null;
            if (data != null) {

                result = JSONObject.toJSONBytes(data,
                        SerializerFeature.UseISO8601DateFormat,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteClassName);
            }
            return result;
        } catch (Exception ex) {
            throw new SerializationException("Can't serialize data [" + data + "] for topic [" + topic + "]", ex);
        }
    }

    public void close() {

    }
}
