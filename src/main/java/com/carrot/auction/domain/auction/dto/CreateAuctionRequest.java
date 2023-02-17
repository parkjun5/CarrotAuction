package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.auction.domain.entity.AuctionStatus;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class CreateAuctionRequest {
    @NotEmpty
    @ApiModelProperty(example = "주최자 아이디")
    private String userId;

    @NotEmpty
    @ApiModelProperty(example = "경매 방 이름")
    private String name;

    @ApiModelProperty(example = "비밀번호")
    private String password;

    @NotEmpty
    @ApiModelProperty(example = "5")
    private int limitOfEnrollment;

    private int basePrice;
    @Enumerated(EnumType.STRING)
    private AuctionStatus auctionStatus = AuctionStatus.DRAFT;

    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;


}
