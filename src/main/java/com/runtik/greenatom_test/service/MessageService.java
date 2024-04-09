package com.runtik.greenatom_test.service;

import java.util.UUID;


public interface MessageService {
    void deleteMessage(UUID messageId, String author);

    void deleteMessage(UUID messageId);
}
