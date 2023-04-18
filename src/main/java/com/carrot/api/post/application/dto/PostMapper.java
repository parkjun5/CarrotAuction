package com.carrot.api.post.application.dto;

import com.carrot.api.post.domain.Post;
import com.carrot.api.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "writer", target = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "amountOfInterest", ignore = true)
    @Mapping(target = "postStatus", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Post toEntityByRequest(User writer, PostRequest request);

    PostResponse toResponseByEntity(Post post);
}
