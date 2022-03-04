package com.femass.resourceserver.repositories;

import com.femass.resourceserver.domain.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserFeedbackRepository extends JpaRepository< UserFeedback, UUID > {
}