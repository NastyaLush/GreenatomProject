package com.runtik.greenatom_test.service;

import java.util.List;
import java.util.UUID;
import model.Message;
import model.NewTopic;
import model.PageListTopic;
import model.TopicWithMessages;
import model.TopicWithMessagesPage;
import model.UpdateMessage;
import model.UpdateTopic;

public interface TopicService {
    TopicWithMessages createMessage(UUID topicId, String text, String author);

    TopicWithMessages createTopic(NewTopic newTopic, String author);

    TopicWithMessages deleteTopic(UUID topicId);

    PageListTopic listAllTopics(Long pageSize, Long pageNumber);

    TopicWithMessagesPage listTopicMessages(UUID topicId, Long pageSize, Long pageNumber);

    Message updateMessage(UUID topicId, UpdateMessage message);

    List<TopicWithMessages> updateTopic(UpdateTopic topic);

    Message updateMessage(UUID topicId, UpdateMessage message, String author);
}
