package com.atlas.atlas_backend.drops.service;

import com.atlas.atlas_backend.drops.entity.Drop;
import com.atlas.atlas_backend.drops.repository.DropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DropServiceImpl implements DropService {

    private final DropRepository dropRepository;

    @Override
    public List<Drop> getDropsByTopic(UUID topicId) {
        return dropRepository
                .findByTopicIdOrderByCreatedAtDesc(topicId);
    }

    @Override
    public Drop createDrop(Drop drop) {
        return dropRepository.save(drop);
    }

    @Override
    public Drop getDrop(UUID dropId) {
        return dropRepository.findById(dropId)
                .orElseThrow(() ->
                        new RuntimeException("Drop not found"));
    }
}