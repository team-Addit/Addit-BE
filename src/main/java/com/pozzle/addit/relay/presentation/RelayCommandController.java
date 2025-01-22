package com.pozzle.addit.relay.presentation;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.relay.application.RelayCommandService;
import com.pozzle.addit.relay.dto.request.RelayCreateRequest;
import com.pozzle.addit.relay.dto.request.RelayUpdateRequest;
import com.pozzle.addit.relay.dto.response.RelayCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PutMapping(value = "/")
    @Operation(summary = "릴레이 수정",
        description = "릴레이를 수정합니다."
    )
    public ResponseEntity<?> updateRelay(
        @RequestBody RelayUpdateRequest request
    ) {
        String result = relayCommandService.updateRelay(request);
        return Response.ok("success update relay", result);
    }

    //릴레이를 삭제한다
    @DeleteMapping(value = "/{relayId}")
    @Operation(summary = "릴레이 삭제",
        description = "릴레이를 삭제합니다."
    )
    public ResponseEntity<?> deleteRelay(
        @PathVariable String relayId
    ) {
        relayCommandService.deleteRelay(relayId);
        return Response.ok("success delete relay", null);
    }

}
