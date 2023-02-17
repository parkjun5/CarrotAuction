package com.carrot.auction.domain.account.serailizer;


import com.carrot.auction.domain.account.domain.entity.Account;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AccountSerializer extends JsonSerializer<Account> {
    @Override
    public void serialize(Account account, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", String.valueOf(account.getId()));
        gen.writeStringField("nickname", account.getNickname());
        gen.writeEndObject();
    }
}
