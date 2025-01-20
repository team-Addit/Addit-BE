package com.pozzle.addit.relay.presentation;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.relay.application.RelayCommandService;
import com.pozzle.addit.relay.dto.request.RelayCreateRequest;
import com.pozzle.addit.relay.dto.response.RelayCreateResponse;
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
@RequestMapping("/api/relays")
@RequiredArgsConstructor
public class RelayCommandController {

    private final RelayCommandService relayCommandService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "릴레이 생성",
        description = "릴레이를 생성합니다."
    )
    public ResponseEntity<?> createRelay(
        @RequestPart RelayCreateRequest request,
        @RequestPart MultipartFile file
    ) {
        RelayCreateResponse response = relayCommandService.createRelay(request, file);
        return Response.ok("success create relay", response);
    }

}
