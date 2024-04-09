package com.runtik.greenatom_test.controller;

import api.TopicApi;
import com.runtik.greenatom_test.service.TopicService;
import java.util.List;
import java.util.UUID;

import com.runtik.greenatom_test.util.Util;
import lombok.RequiredArgsConstructor;
import model.Message;
import model.NewMessage;
import model.NewTopic;
import model.PageListTopic;
import model.TopicWithMessages;
import model.TopicWithMessagesPage;
import model.UpdateMessage;
import model.UpdateTopic;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TopicApiController implements TopicApi {
    private final TopicService topicService;

    @Override
    @PreAuthorize("hasAnyAuthority('MESSAGE_CREATOR')")
    public ResponseEntity<TopicWithMessages> createMessage(UUID topicId, NewMessage newMessage) {
        TopicWithMessages topicWithMessages = topicService.createMessage(topicId, newMessage.getText(), Util.getUsernameFromToken());
        return ResponseEntity.ok(topicWithMessages);
    }


    @Override
    @PreAuthorize("hasAnyAuthority('TOPIC_CREATOR')")
    public ResponseEntity<TopicWithMessages> createTopic(NewTopic newTopic) {
        TopicWithMessages topic = topicService.createTopic(newTopic, Util.getUsernameFromToken());
        return ResponseEntity.ok(topic);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('TOPIC_DELETER')")
    public ResponseEntity<TopicWithMessages> deleteTopic(UUID topicId) {
        TopicWithMessages topicWithMessages = topicService.deleteTopic(topicId);
        return ResponseEntity.ok(topicWithMessages);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('TOPIC_VIEWER')")
    public ResponseEntity<PageListTopic> listAllTopics(Long pageSize, Long pageNumber) {
        PageListTopic pageListTopic = topicService.listAllTopics(pageSize, pageNumber);
        return ResponseEntity.ok(pageListTopic);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('MESSAGE_VIEWER')")
    public ResponseEntity<TopicWithMessagesPage> listTopicMessages(UUID topicId, Long pageSize, Long pageNumber) {
        TopicWithMessagesPage topicWithMessagesPage = topicService.listTopicMessages(topicId, pageSize, pageNumber);
        return ResponseEntity.ok(topicWithMessagesPage);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('MESSAGE_UPDATER')")
    public ResponseEntity<Object> updateMessage(UUID topicId, UpdateMessage updateMessage) {
        Message message1;
        if (Util.isAdmin()) {
            message1 = topicService.updateMessage(topicId, updateMessage);
        } else {
            message1 = topicService.updateMessage(topicId, updateMessage, Util.getUsernameFromToken());
        }
        return ResponseEntity.ok(message1);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('TOPIC_UPDATER')")
    public ResponseEntity<List<TopicWithMessages>> updateTopic(UpdateTopic topic) {
        List<TopicWithMessages> topicWithMessages = topicService.updateTopic(topic);
        return ResponseEntity.ok(topicWithMessages);
    }


}
