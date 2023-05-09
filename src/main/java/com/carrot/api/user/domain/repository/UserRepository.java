package com.carrot.api.user.domain.repository;

import com.carrot.api.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.isDeleted = false")
    Page<User> findAllByDeletedIsFalse(Pageable pageable);

    @Query("select u from User u where u.id = :userId and u.isDeleted = false ")
    Optional<User> findByIdAndDeletedIsFalse(@Param("userId") Long userId);

    @Query("select  u from User u left join fetch u.participatedChatRoom where u.id = :userId and u.isDeleted = false ")
    Optional<User> findByIdFetchChatRoom(@Param("userId") Long userId);
}
