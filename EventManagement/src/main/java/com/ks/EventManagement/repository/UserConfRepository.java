package com.ks.EventManagement.repository;

import com.ks.EventManagement.entity.UserConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConfRepository extends JpaRepository<UserConfirmation, Long> {
    Optional<UserConfirmation> findUserConfirmationByUserId(Long id);
    Optional<UserConfirmation> findUserConfirmationByConfirmationToken(String conf);
}
