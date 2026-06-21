package com.atlas.atlas_backend.topics.service;


import com.atlas.atlas_backend.topics.entity.Topic;

import java.util.List;
import java.util.UUID;

public interface TopicService {

    List<Topic> searchTopics(String query);

    Topic getTopic(UUID topicId);

    Topic createTopic(Topic topic);

    void deleteTopic(UUID topicId, UUID requesterId);
}