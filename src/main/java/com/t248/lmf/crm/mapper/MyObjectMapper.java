package com.t248.lmf.crm.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MyObjectMapper extends ObjectMapper {

    private static final long serialVersionUID=1L;

    public MyObjectMapper(){
        super();
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        this.enableDefaultTyping(DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    }

}
