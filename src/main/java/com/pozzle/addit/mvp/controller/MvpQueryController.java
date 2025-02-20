package com.pozzle.addit.mvp.controller;

import com.pozzle.addit.common.payload.Response;
import com.pozzle.addit.mvp.dto.response.MainResponse;
import com.pozzle.addit.mvp.dto.response.TickleViewResponse;
import com.pozzle.addit.mvp.service.MvpMetricService;
import com.pozzle.addit.mvp.service.MvpQueryService;
import com.pozzle.addit.relay.dto.response.TickleThumbnailsResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mvp")
@RequiredArgsConstructor
public class MvpQueryController {

    private final MvpQueryService mvpQueryService;
    private final MvpMetricService mvpMetricService;

    @GetMapping(value = "/main")
    @Operation(summary = "메인페이지 조회",
        description = "메인페이지에서 릴레이를 랜덤으로 size개 만큼 조회합니다.\n"
            + "각 릴레이마다 최신순의 티클을 5개씩 제공합니다."
    )
    public ResponseEntity<?> readMain(
        @RequestParam int size
    ) {
        MainResponse response = mvpQueryService.readMain(size);
        return Response.ok(response);
    }

    @GetMapping(value = "/relays/{relayId}/tickles/thumbnail")
    @Operation(summary = "특정 릴레이의 티클 썸네일 목록",
        description = "릴레이에서 최신순으로 전체 티클들의 썸네일 목록을 불러옵니다."
    )
    public ResponseEntity<?> readTickleThumbnailsFromRelay(
        @PathVariable String relayId
    ) {
        TickleThumbnailsResponse response = mvpQueryService.readTickleThumbnailsFromRelay(relayId);
        return Response.ok(response);
    }

    @GetMapping(value = "/tickles/{tickleId}")
    @Operation(summary = "티클 조회",
        description = "티클을 조회합니다."
    )
    public ResponseEntity<?> readTickle(
        HttpServletRequest request,
        @PathVariable String tickleId
    ) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        mvpMetricService.update(ip);
        TickleViewResponse response = mvpQueryService.readTickle(tickleId);
        return Response.ok(response);
    }

}
