package com.carrot.core.user.application;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import users.UserFinderGrpc;
import users.Users;

@Service
public class UserFinderImpl extends UserFinderGrpc.UserFinderImplBase {

    private final UserService userService;

    public UserFinderImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void findUserNameById(Users.UserRequest request, StreamObserver<Users.UserNameResponse> responseObserver) {
        try {
            long writerId = request.getWriterId();
            var user = userService.findUserById(writerId);  // 이 메소드가 null을 반환하거나 예외를 던질 수 있음
            if (user != null && user.getNickname() != null) {
                var response = Users.UserNameResponse.newBuilder().setWriter(user.getNickname()).build();
                responseObserver.onNext(response);
            } else {
                responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND.withDescription("User not found")));
            }
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL.withDescription("Internal server error")));
        } finally {
            responseObserver.onCompleted();
        }
    }
}
