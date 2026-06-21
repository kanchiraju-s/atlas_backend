package com.atlas.atlas_backend.users.service;


import com.atlas.atlas_backend.users.entity.User;

import java.util.UUID;

public interface UserService {

    User getUser(UUID userId);

    User createUser(User user);

    User getByUsername(String username);
}