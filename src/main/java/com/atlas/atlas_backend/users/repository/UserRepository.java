package com.atlas.atlas_backend.users.repository;


import com.atlas.atlas_backend.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByGoogleId(String googleId);

    boolean existsByUsername(String username);

    long countByStatus(String status);
}