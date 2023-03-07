package com.carrot.auction.domain.post.domain.entity;

import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Post extends BaseEntity {

    @Id
    @GeneratedValue @Column(name="post_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String title;
    private String postContent;
    @Embedded private Item item;
    @Enumerated(EnumType.STRING) Category category;
    private int views;
    private int amountOfInterest;
    @Builder.Default
    @Enumerated(EnumType.STRING) private PostStatus postStatus = PostStatus.IN_SALE;
    @Builder.Default
    private boolean isDeleted = false;

}
