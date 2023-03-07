package com.carrot.auction.domain.post.dto;

import com.carrot.auction.domain.post.domain.entity.Post;
import com.carrot.auction.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "writer", target = "user")
    @Mapping(target = "id", ignore = true)
    Post toEntityByRequest(User writer, PostRequest request);

    PostResponse toResponseByEntity(Post post);
}
