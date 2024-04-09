package com.runtik.greenatom_test.repository;

import com.runtik.greenatom_test.database.MessageTable;
import com.runtik.greenatom_test.database.TopicMessageTable;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import model.Message;
import model.UpdateMessage;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final MessageTable messageTable;
    private final TopicMessageTable topicMessageTable;

    public void delete(UUID messageId) {
        messageTable.delete(messageId);
        topicMessageTable.deleteMessage(messageId);
    }

    public void create(UUID topicId, Message message) {
        Message newMessage = messageTable.create(message);
        topicMessageTable.createMessage(topicId, newMessage.getId());
    }

    public Message get(UUID messageId) {
        return messageTable.getMessage(messageId);
    }

    public Message update(UpdateMessage message) {
        return messageTable.update(message);
    }
}
