package com.runtik.greenatom_test.generator;

import com.runtik.greenatom_test.auth.AuthenticationController;
import com.runtik.greenatom_test.auth.dto.RegisterRequest;
import com.runtik.greenatom_test.service.TopicService;
import com.runtik.greenatom_test.user.Role;
import lombok.RequiredArgsConstructor;
import model.NewMessage;
import model.NewTopic;
import model.TopicWithMessages;

@RequiredArgsConstructor
public class Generator {

    private final TopicService topicService;

    private final AuthenticationController authenticationController;

    private static final String AUTHOR_PREFIX = "author";
    private static final String PASSWORD_PREFIX = "password";
    private static final String TOPIC_PREFIX = "topic";
    private static final String MESSAGE_TEXT_PREFIX = "message text";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public void generateData(int topicsCounter, int messageInTopicCounter) {
        for (int i = 0; i < topicsCounter; i++) {
            RegisterRequest request = new RegisterRequest(AUTHOR_PREFIX + i, PASSWORD_PREFIX + i, Role.USER);
            authenticationController.register(request);
            TopicWithMessages topic = topicService.createTopic(new NewTopic(TOPIC_PREFIX + i, new NewMessage(MESSAGE_TEXT_PREFIX + i)), AUTHOR_PREFIX + i);
            for (int j = 0; j < messageInTopicCounter; j++) {
                topicService.createMessage(topic.getId(), MESSAGE_TEXT_PREFIX + i, AUTHOR_PREFIX + i);
            }
        }
        RegisterRequest request = new RegisterRequest(ADMIN_USERNAME, ADMIN_PASSWORD, Role.ADMIN);
        authenticationController.register(request);
    }
}
