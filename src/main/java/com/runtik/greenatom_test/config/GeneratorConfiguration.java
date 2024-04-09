package com.runtik.greenatom_test.config;

import com.runtik.greenatom_test.auth.AuthenticationController;
import com.runtik.greenatom_test.generator.Generator;
import com.runtik.greenatom_test.service.TopicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneratorConfiguration {
    @Value("${app.generateTestData: false}")
    private boolean generateTestData;
    @Value("${app.countOfTopics:10}")
    private Integer countOfTopics;
    @Value("${app.countOfMessagesInTopic:10}")
    private Integer countOfMessagesInTopic;

    @Bean
    public Generator generator(TopicService topicService, AuthenticationController authenticationController) {
        Generator gen = new Generator(topicService, authenticationController);
        if (generateTestData) {
            gen.generateData(countOfTopics, countOfMessagesInTopic);
        }
        return gen;
    }
}
