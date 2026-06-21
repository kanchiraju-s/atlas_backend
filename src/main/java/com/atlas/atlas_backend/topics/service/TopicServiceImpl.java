package com.atlas.atlas_backend.topics.service;


import com.atlas.atlas_backend.topics.entity.Topic;
import com.atlas.atlas_backend.topics.repository.TopicRepository;
import com.atlas.atlas_backend.topics.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public List<Topic> searchTopics(String query) {
        return topicRepository
                .findTop20ByTitleContainingIgnoreCase(query);
    }

    @Override
    public Topic getTopic(UUID topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() ->
                        new RuntimeException("Topic not found"));
    }

    @Override
    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public void deleteTopic(UUID topicId, UUID requesterId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
        if (!topic.getCreatedBy().equals(requesterId)) {
            throw new org.springframework.security.access.AccessDeniedException("Not the topic creator");
        }
        topicRepository.delete(topic);
    }
}