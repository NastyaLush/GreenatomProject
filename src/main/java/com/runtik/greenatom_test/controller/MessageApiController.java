package com.runtik.greenatom_test.controller;

import api.MessageApi;
import com.runtik.greenatom_test.service.MessageService;
import java.util.UUID;

import com.runtik.greenatom_test.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageApiController implements MessageApi {
    private final MessageService messageService;

    @Override
    @PreAuthorize("hasAnyAuthority('MESSAGE_DELETER')")
    public ResponseEntity<Void> deleteMessage(UUID messageId) {
        if (Util.isAdmin()) {
            messageService.deleteMessage(messageId);
        } else {
            messageService.deleteMessage(messageId, Util.getUsernameFromToken());
        }
        return ResponseEntity.ok().build();
    }

}
