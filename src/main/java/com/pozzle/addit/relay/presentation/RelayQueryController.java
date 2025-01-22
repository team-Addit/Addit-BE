package com.pozzle.addit.relay.presentation;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.relay.application.RelayQueryService;
import com.pozzle.addit.relay.dto.response.RelayInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relays")
@RequiredArgsConstructor
public class RelayQueryController {

    private final RelayQueryService relayQueryService;

    @GetMapping(value = "/{relayId}")
    @Operation(summary = "릴레이 상세정보",
        description = "릴레이 상세정보를 조회합니다."
    )
    public ResponseEntity<?> readRelayInfo(
        @PathVariable String relayId
    ) {
        RelayInfoResponse response = relayQueryService.readRelayInfo(relayId);
        return Response.ok(response);
    }

}
