package com.atlas.atlas_backend.drops.service;


import com.atlas.atlas_backend.drops.entity.Drop;

import java.util.List;
import java.util.UUID;

public interface DropService {

    List<Drop> getDropsByTopic(UUID topicId);

    Drop createDrop(Drop drop);

    Drop getDrop(UUID dropId);
}