package com.booklovers.booklovers.Repository;


import com.booklovers.booklovers.Entity.UserProfile;
import com.booklovers.booklovers.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(Users user);

    Optional<UserProfile> findByUserId(Long userId);
}
