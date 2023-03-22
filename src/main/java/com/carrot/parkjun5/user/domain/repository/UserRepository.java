package com.carrot.parkjun5.user.domain.repository;

import com.carrot.parkjun5.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
