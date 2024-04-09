package com.runtik.greenatom_test.database;

import com.runtik.greenatom_test.exception.NotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import model.Message;
import model.UpdateMessage;
import org.springframework.stereotype.Component;

import static com.runtik.greenatom_test.util.MessageContent.MESSAGE_NOT_FOUND;

@Component
public class MessageTable {
    private final Map<UUID, Message> messages = new HashMap<>();

    public Message create(Message message) {
        message.id(UUID.randomUUID());
        messages.put(message.getId(), message);
        return message;
    }

    public void delete(UUID messageUuid) {
        messages.remove(messageUuid);
    }

    public Message update(UpdateMessage updateMessage) {
        Message message = messages.get(updateMessage.getId());
        if (message == null) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
        message.text(updateMessage.getText());
        return message;
    }

    public Message getMessage(UUID uuid) {
        Message message = messages.get(uuid);
        if (message == null) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
        return message;
    }
}
