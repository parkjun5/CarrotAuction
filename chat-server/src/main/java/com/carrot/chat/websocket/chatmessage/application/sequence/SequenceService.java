package com.carrot.chat.websocket.chatmessage.application.sequence;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Profile("mongodb")
@Service
@RequiredArgsConstructor
public class SequenceService {

    private final ReactiveMongoOperations mongoOperations;

    public long generateSeqByName(String seqName) {
        Long seqValue = mongoOperations.findAndModify(
                        query(where("_id").is(seqName)),
                        new Update().inc("seq", 1),
                        options().returnNew(true).upsert(true),
                        ChatMessageSequence.class
                ).map(ChatMessageSequence::getSeq)
                .block();

        return seqValue != null ? seqValue : 1L;
    }
}
