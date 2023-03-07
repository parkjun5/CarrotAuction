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
@EqualsAndHashCode(callSuper=false)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue @Column(name="post_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String postTitle;
    private String postContent;
    @Embedded private Item item;
    @Enumerated(EnumType.STRING) Category category;
    private int views;
    private int amountOfInterest;
    @Builder.Default
    @Enumerated(EnumType.STRING) private PostStatus postStatus = PostStatus.IN_SALE;
    @Builder.Default
    private boolean isDeleted = false;

    public void changeContent(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
    }

    public void changeItem(String title, int price, String content, Category category) {
        this.item.changeInfo(title, price, content);
        this.category = category;
    }

}
