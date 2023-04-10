package com.carrot.web_mvc.post.application.dto;

import com.carrot.web_mvc.post.domain.Post;
import com.carrot.web_mvc.user.domain.User;
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
