package com.runtik.greenatom_test.service.imdb_service;

import com.runtik.greenatom_test.exception.ForbiddenException;
import com.runtik.greenatom_test.repository.MessageRepository;
import com.runtik.greenatom_test.repository.TopicRepository;
import com.runtik.greenatom_test.service.TopicService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import model.Message;
import model.NewTopic;
import model.PageListTopic;
import model.PageMessage;
import model.Topic;
import model.TopicWithMessages;
import model.TopicWithMessagesPage;
import model.UpdateMessage;
import model.UpdateTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static com.runtik.greenatom_test.util.MessageContent.ACCESS_FORBIDDEN_MESSAGE;

@Service
@RequiredArgsConstructor
public class ImdbTopicServiceImpl implements TopicService {
    private static final int PAGE_NUMBER = 0;
    private final MessageRepository messageRepository;
    private final TopicRepository topicRepository;
    @Value("${app.countOfMessagesToReturn:10}")
    private Integer countOfMessagesToReturn;


    @Override
    public TopicWithMessages createMessage(UUID topicId, String text, String author) {
        Message message = toMessage(text, author);
        messageRepository.create(topicId, message);
        return listTopicMessages(topicId);
    }

    private Message toMessage(String text, String author) {
        return new Message().created(LocalDateTime.now()
                                                  .toString())
                            .text(text)
                            .author(author);
    }

    @Override
    public TopicWithMessages createTopic(NewTopic newTopic, String author) {
        Topic topic = toTopic(newTopic);
        Message message = toMessage(newTopic.getMessage()
                                            .getText(), author);
        topicRepository.create(topic, message);
        return listTopicMessages(topic.getId());
    }

    private Topic toTopic(NewTopic newTopic) {
        return new Topic()
                .created(LocalDateTime.now()
                                      .toString())
                .name(newTopic.getTopicName());
    }


    @Override
    public PageListTopic listAllTopics(Long pageSize, Long pageNumber) {
        List<Topic> list = topicRepository.getTopics(pageSize, pageNumber)
                                          .stream()
                                          .toList();
        Long size = topicRepository.countOfTopics();
        return new PageListTopic().content(list)
                                  .totalElements(size);
    }

    @Override
    public TopicWithMessagesPage listTopicMessages(UUID topicId, Long pageSize, Long pageNumber) {
        List<Message> list = topicRepository.getMessages(topicId, pageSize, pageNumber)
                                            .stream()
                                            .toList();
        Long size = topicRepository.countOfMessages(topicId);
        PageMessage pageMessage = new PageMessage().content(list)
                                                   .totalElements(size);
        Topic topic = topicRepository.get(topicId);
        return new TopicWithMessagesPage().id(topic.getId())
                                          .name(topic.getName())
                                          .created(topic.getCreated())
                                          .messages(pageMessage);
    }

    private TopicWithMessages listTopicMessages(UUID topicId) {
        List<Message> list = topicRepository.getMessages(topicId, countOfMessagesToReturn, PAGE_NUMBER)
                                            .stream()
                                            .toList();
        Topic topic = topicRepository.get(topicId);
        return new TopicWithMessages().id(topic.getId())
                                      .name(topic.getName())
                                      .created(topic.getCreated())
                                      .messages(list);

    }

    @Override
    public Message updateMessage(UUID topicId, UpdateMessage updateMessage, String author) {
        Message message = messageRepository.get(updateMessage.getId());
        if (!message.getAuthor()
                    .equals(author)) {
            throw new ForbiddenException(ACCESS_FORBIDDEN_MESSAGE);
        }
        return messageRepository.update(updateMessage);
    }

    @Override
    public Message updateMessage(UUID topicId, UpdateMessage message) {
        return messageRepository.update(message);
    }

    @Override
    public List<TopicWithMessages> updateTopic(UpdateTopic topic) {
        topicRepository.update(topic);
        return topicRepository.getTopics()
                              .stream()
                              .map(topic1 -> listTopicMessages(topic.getId()))
                              .toList();
    }

    @Override
    public TopicWithMessages deleteTopic(UUID topicId) {
        List<Message> list = topicRepository.getMessages(topicId)
                                            .stream()
                                            .toList();
        Topic topic = topicRepository.delete(topicId);
        return new TopicWithMessages().id(topic.getId())
                                      .name(topic.getName())
                                      .created(topic.getCreated())
                                      .messages(list);
    }

}
