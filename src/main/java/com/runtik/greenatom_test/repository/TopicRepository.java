package com.runtik.greenatom_test.repository;

import com.runtik.greenatom_test.database.MessageTable;
import com.runtik.greenatom_test.database.TopicMessageTable;
import com.runtik.greenatom_test.database.TopicTable;
import java.util.Collection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import model.Message;
import model.Topic;
import model.UpdateTopic;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TopicRepository {
    private final MessageTable messageTable;
    private final TopicMessageTable topicMessageTable;
    private final TopicTable topicTable;

    public void create(Topic topic, Message message) {
        Topic newTopic = topicTable.create(topic);
        Message newMessage = messageTable.create(message);
        topicMessageTable.createTopic(newTopic.getId(), newMessage.getId());
    }

    public Topic delete(UUID topicId) {
        Topic topic = topicTable.delete(topicId);
        topicMessageTable.deleteTopic(topicId);
        return topic;
    }

    public void update(UpdateTopic topic) {
        topicTable.update(topic);
    }

    public Topic get(UUID topicId) {
        return topicTable.get(topicId);
    }

    public Collection<Topic> getTopics(long pageSize, long pageNumber) {
        return topicTable.getTopics(pageSize, pageNumber);
    }

    public Collection<Topic> getTopics() {
        return topicTable.getTopics();
    }

    public long countOfTopics() {
        return topicTable.getCountOfTopics();
    }

    public Collection<Message> getMessages(UUID topicId, long pageSize, long pageNumber) {
        return topicMessageTable.getMessagesByTopic(topicId, pageSize, pageNumber)
                                .stream()
                                .map(messageTable::getMessage)
                                .toList();
    }

    public Collection<Message> getMessages(UUID topicId) {
        return topicMessageTable.getMessagesByTopic(topicId)
                                .stream()
                                .map(messageTable::getMessage)
                                .toList();
    }

    public long countOfMessages(UUID topicId) {
        return topicMessageTable.getCountOfMessagesInTopic(topicId);
    }
}
