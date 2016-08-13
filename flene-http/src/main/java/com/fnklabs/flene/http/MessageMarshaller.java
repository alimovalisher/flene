package com.fnklabs.flene.http;

public interface MessageMarshaller {

    String serialize(Object object);

    <T> T deserialize(String text, Class<T> clazz);
}
