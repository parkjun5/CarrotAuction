package com.carrot.api.chat.domain.repository;

import com.carrot.api.chat.domain.ChatRoomParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomParticipationRepository extends JpaRepository<ChatRoomParticipation, Long> {
}
