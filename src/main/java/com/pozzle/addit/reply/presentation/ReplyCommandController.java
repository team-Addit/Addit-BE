package com.pozzle.addit.reply.presentation;

import com.pozzle.addit.relay.application.RelayCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class ReplyCommandController {

    private final RelayCommandService relayCommandService;

}
