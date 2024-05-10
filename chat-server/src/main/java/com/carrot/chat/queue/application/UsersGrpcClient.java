package com.carrot.chat.queue.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import users.UserFinderGrpc;
import users.Users;

@Slf4j
@Component
public class UsersGrpcClient {
    private final UserFinderGrpc.UserFinderBlockingStub blockingStub;

    public UsersGrpcClient(UserFinderGrpc.UserFinderBlockingStub blockingStub
    ) {
        this.blockingStub = blockingStub;
    }
    public Users.UserNameResponse findWriterById(Long writerId) {
        var request = Users.UserRequest.newBuilder().setWriterId(writerId).build();
        return blockingStub.findUserNameById(request);
    }
}
