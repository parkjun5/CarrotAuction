package com.carrot.auction.domain.user.serailizer;


import com.carrot.auction.domain.user.domain.entity.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("nickname", user.getNickname());
        gen.writeStringField("email", user.getEmail());
        gen.writeEndObject();
    }
}
