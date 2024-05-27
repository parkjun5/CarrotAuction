package com.carrot.core.auctionroom.application;

import com.carrot.core.auctionroom.application.dto.AuctionRoomRequest;
import com.carrot.core.auctionroom.application.dto.AuctionRoomResponse;
import com.carrot.core.auctionroom.domain.AuctionParticipation;
import com.carrot.core.auctionroom.domain.AuctionRoom;
import com.carrot.core.auctionroom.domain.repository.AuctionParticipationRepository;
import com.carrot.core.auctionroom.domain.repository.AuctionRoomRepository;
import com.carrot.core.auctionroom.exception.AlreadyFullEnrollmentException;
import com.carrot.core.user.application.UserService;
import com.carrot.core.user.application.dto.UserResponse;
import com.carrot.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionRoomService {

    private final AuctionRoomRepository auctionRepository;
    private final AuctionParticipationRepository auctionParticipationRepository;
    private final UserService userService;
    private static final String AUCTION_NOT_FOUND = "의 경매장을 찾지 못했습니다.";

    public AuctionRoomResponse findAuctionResponseById(final Long roomId) {
        AuctionRoom auctionRoom = findAuctionRoomById(roomId);
        return toResponseByAuctionRoom(auctionRoom);
    }

    @Transactional
    public AuctionRoomResponse createAuctionRoom(AuctionRoomRequest request) {
        User hostUser = userService.findUserById(request.userId());
        AuctionRoom auctionRoom = AuctionRoom.of(hostUser, request);
        AuctionParticipation auctionParticipation = AuctionParticipation.createAuctionParticipation(hostUser, auctionRoom);

        auctionParticipationRepository.save(auctionParticipation);
        return toResponseByAuctionRoom(auctionRepository.save(auctionRoom));
    }

    @Transactional
    public AuctionRoomResponse updateAuctionRoom(final Long roomId, AuctionRoomRequest request) {
        AuctionRoom auctionRoom = findAuctionRoomById(roomId);
        auctionRoom.changeAuctionRoom(request.name(), request.password(), request.limitOfEnrollment());
        return toResponseByAuctionRoom(auctionRoom);
    }

    @Transactional
    public Long deleteAuctionRoom(final Long roomId) {
        AuctionRoom auctionRoom = findAuctionRoomById(roomId);
        AuctionParticipation auctionParticipation = auctionParticipationRepository.findOneByUserIdAndAuctionRoomId(auctionRoom.getHostUser().getId(), roomId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 경매장입니다."));

        auctionParticipationRepository.delete(auctionParticipation);
        auctionRepository.delete(auctionRoom);
        return roomId;
    }

    @Transactional
    public AuctionRoomResponse addParticipateAuctionRoom(Long roomId, Long userId) {
        AuctionRoom auctionRoom = findAuctionRoomById(roomId);
        isFullEnrollment(auctionRoom.getAuctionParticipation().size(), auctionRoom.getLimitOfEnrollment());

        User user = userService.findUserById(userId);
        AuctionParticipation auctionParticipation = AuctionParticipation.createAuctionParticipation(user, auctionRoom);

        auctionParticipationRepository.save(auctionParticipation);
        return toResponseByAuctionRoom(auctionRoom);
    }

    public Page<AuctionRoomResponse> getAuctionRoomsByPageable(Pageable pageable) {
        List<AuctionRoomResponse> auctionRoomResponseList = auctionRepository.findAll(pageable).stream().map(this::toResponseByAuctionRoom).toList();
        return new PageImpl<>(auctionRoomResponseList);
    }

    public AuctionRoom findAuctionRoomById(Long roomId) {
        return auctionRepository.findById(roomId).orElseThrow(() -> new NoSuchElementException("ID: " + roomId + AUCTION_NOT_FOUND));
    }

    private void isFullEnrollment(int numberOfCurrentEnrollment, int limitOfEnrollment) {
        if (limitOfEnrollment <= numberOfCurrentEnrollment) {
            throw new AlreadyFullEnrollmentException("경매장이 꽉찼어요.");
        }
    }

    private AuctionRoomResponse toResponseByAuctionRoom(AuctionRoom auctionRoom) {
        Set<String> nameOfParticipants = auctionRoom.getParticipantsNicknames();
        UserResponse userResponse = UserResponse.from(auctionRoom.getHostUser());
        return AuctionRoomResponse.from(auctionRoom, userResponse, nameOfParticipants);
    }

    public Set<Long> findAuctionParticipationByAuctionId(long chatRoomId, long senderId) {
        return auctionParticipationRepository.findAuctionParticipationByAuctionId(chatRoomId, senderId);
    }
}
