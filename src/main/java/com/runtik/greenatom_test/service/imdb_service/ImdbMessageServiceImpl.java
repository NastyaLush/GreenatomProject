package com.runtik.greenatom_test.service.imdb_service;

import com.runtik.greenatom_test.exception.ForbiddenException;
import com.runtik.greenatom_test.repository.MessageRepository;
import com.runtik.greenatom_test.service.MessageService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import model.Message;
import org.springframework.stereotype.Service;

import static com.runtik.greenatom_test.util.MessageContent.ACCESS_FORBIDDEN_MESSAGE;

@Service
@RequiredArgsConstructor
public class ImdbMessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public void deleteMessage(UUID messageId, String author) {
        Message message = messageRepository.get(messageId);
        if (!message.getAuthor()
                    .equals(author)) {
            throw new ForbiddenException(ACCESS_FORBIDDEN_MESSAGE);
        }
        messageRepository.delete(messageId);
    }

    @Override
    public void deleteMessage(UUID messageId) {
        messageRepository.delete(messageId);
    }
}
