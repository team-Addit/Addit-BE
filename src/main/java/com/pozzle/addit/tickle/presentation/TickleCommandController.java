package com.pozzle.addit.tickle.presentation;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.relay.dto.response.RelayCreateResponse;
import com.pozzle.addit.tickle.application.TickleCommandService;
import com.pozzle.addit.tickle.dto.request.TickleAddRequest;
import com.pozzle.addit.tickle.dto.response.TickleAddResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/tickles")
@RequiredArgsConstructor
public class TickleCommandController {

    private final TickleCommandService tickleCommandService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "티클 추가",
        description = "릴레이에 티클을 추가합니다."
    )
    public ResponseEntity<?> addTickle(
        @RequestPart TickleAddRequest request,
        @RequestPart MultipartFile file
    ) {
        TickleAddResponse response = tickleCommandService.addTickle(request, file);
        return Response.ok("success add tickle", response);
    }
}
