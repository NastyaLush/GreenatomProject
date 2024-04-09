package com.runtik.greenatom_test.database;

import com.runtik.greenatom_test.exception.NotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import model.Topic;
import model.UpdateTopic;
import org.springframework.stereotype.Component;
import static com.runtik.greenatom_test.util.MessageContent.TOPIC_NOT_FOUND_MESSAGE;

@Component
public class TopicTable {
    private final Map<UUID, Topic> topics = new HashMap<>();

    public Topic create(Topic topic) {
        topic.id(UUID.randomUUID());
        topics.put(topic.getId(), topic);
        return topic;
    }

    public Topic delete(UUID topicId) {
        return topics.remove(topicId);
    }

    public Topic get(UUID id) {
        if (!topics.containsKey(id)) {
            throw new NotFoundException(TOPIC_NOT_FOUND_MESSAGE);
        }
        return topics.get(id);
    }

    public Collection<Topic> getTopics(long pageSize, long pageNumber) {
        return topics.values()
                     .stream()
                     .skip(pageNumber * pageSize)
                     .limit(pageSize)
                     .toList();
    }

    public Collection<Topic> getTopics() {
        return topics.values();
    }

    public long getCountOfTopics() {
        return topics.size();
    }

    public Topic update(UpdateTopic updateTopic) {
        Topic topic = topics.get(updateTopic.getId());
        if (topic == null) {
            throw new NotFoundException(TOPIC_NOT_FOUND_MESSAGE);
        }
        return topic.name(updateTopic.getTopicName());
    }

}
