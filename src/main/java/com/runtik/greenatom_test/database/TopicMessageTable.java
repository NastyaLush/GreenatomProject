package com.runtik.greenatom_test.database;

import com.runtik.greenatom_test.exception.NotFoundException;
import com.runtik.greenatom_test.exception.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;
import static com.runtik.greenatom_test.util.MessageContent.TOPIC_EXIST_MESSAGE;
import static com.runtik.greenatom_test.util.MessageContent.TOPIC_NOT_FOUND_MESSAGE;
import static com.runtik.greenatom_test.util.MessageContent.TOPIC_WITHOUT_CONTENT_MESSAGE;

@Component
public class TopicMessageTable {
    private final Map<UUID, List<UUID>> topicMessages = new HashMap<>();
    private final Map<UUID, UUID> messageTopics = new HashMap<>();

    public void createTopic(UUID topicId, UUID messageId) {
        if(existsTopic(topicId)){
            throw new ValidationException(TOPIC_EXIST_MESSAGE);
        }
        topicMessages.put(topicId, new ArrayList<>(List.of(messageId)));
        messageTopics.put(topicId, messageId);

    }

    public void createMessage(UUID topicId, UUID messageId) {
        if (!existsTopic(topicId) || topicMessages.get(topicId)
                                                               .isEmpty()) {
            throw new ValidationException(TOPIC_WITHOUT_CONTENT_MESSAGE);
        }
        topicMessages.get(topicId)
                     .add(messageId);
        messageTopics.put(messageId, topicId);
    }

    public List<UUID> getMessagesByTopic(UUID topicId, long pageSize, long pageNumber) {
        if (!existsTopic(topicId)) {
            throw new NotFoundException(TOPIC_NOT_FOUND_MESSAGE);
        }
        return topicMessages.get(topicId)
                            .stream()
                            .skip(pageSize * pageNumber)
                            .limit(pageSize)
                            .toList();
    }

    public List<UUID> getMessagesByTopic(UUID topicId) {
        if (!existsTopic(topicId)) {
            throw new NotFoundException(TOPIC_NOT_FOUND_MESSAGE);
        }
        return topicMessages.get(topicId);
    }

    public void deleteTopic(UUID topicId) {
        topicMessages.remove(topicId);
    }

    public void deleteMessage(UUID messageId) {
        UUID uuid = messageTopics.get(messageId);
        if (uuid == null) return;
        if (topicMessages.get(uuid)
                         .size() == 1) {
            throw new ValidationException(TOPIC_WITHOUT_CONTENT_MESSAGE);
        }
        UUID topicUuid = messageTopics.remove(messageId);
        topicMessages.get(topicUuid)
                     .remove(messageId);
    }

    public boolean existsTopic(UUID topicId) {
        return topicMessages.containsKey(topicId);
    }

    public long getCountOfMessagesInTopic(UUID topicId) {
        return topicMessages.get(topicId)
                            .size();
    }

}
