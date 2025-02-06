package com.pozzle.addit.mvp.service;

import com.pozzle.addit.common.exception.ErrorCode;
import com.pozzle.addit.common.exception.RestApiException;
import com.pozzle.addit.mvp.dto.dto.RelayAndTickleDto;
import com.pozzle.addit.mvp.dto.dto.RelayPreviewDto;
import com.pozzle.addit.mvp.dto.dto.TicklePreviewDto;
import com.pozzle.addit.mvp.dto.response.MainResponse;
import com.pozzle.addit.mvp.dto.response.TickleViewResponse;
import com.pozzle.addit.mvp.entity.MvpUser;
import com.pozzle.addit.mvp.repository.MvpUserRepository;
import com.pozzle.addit.relay.dto.response.TickleThumbnail;
import com.pozzle.addit.relay.dto.response.TickleThumbnailsResponse;
import com.pozzle.addit.relay.entity.Relay;
import com.pozzle.addit.relay.entity.RelayTag;
import com.pozzle.addit.relay.entity.Tag;
import com.pozzle.addit.relay.repository.RelayRepository;
import com.pozzle.addit.relay.repository.RelayTagRepository;
import com.pozzle.addit.relay.repository.TagRepository;
import com.pozzle.addit.tickle.entity.Tickle;
import com.pozzle.addit.tickle.repository.TickleRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MvpQueryService {

  private final RelayRepository relayRepository;
  private final TickleRepository tickleRepository;
  private final MvpUserRepository mvpUserRepository;
  private final RelayTagRepository relayTagRepository;
  private final TagRepository tagRepository;

  public MainResponse readMain(int size) {
    //size 만큼 릴레이 가져온다.
    List<Relay> relays = relayRepository.findRelaysByRandom(size);

    List<RelayAndTickleDto> bundles = new ArrayList<>();
    //릴레이 마다 티클&사용자 정보 불러온다.
    for (Relay r : relays) {
      //태그 불러오기
      List<String> tags = getTags(r.getId());

      //처음 3개의 티클의 사용자 프로필 불러오기
      List<String> contributorImages = readContributorImages(r.getId());

      RelayPreviewDto relayPreview = RelayPreviewDto.of(r, tags, contributorImages);

      //최신 티클 5개 정보 불러오기
      List<TicklePreviewDto> ticklePreviews = readTicklePreviews(r.getId());

      bundles.add(RelayAndTickleDto.of(relayPreview, ticklePreviews));
    }

    return MainResponse.of(bundles);
  }

  private List<String> readContributorImages(Long relayId) {
    List<Tickle> tickles = tickleRepository.findTop3ByRelayIdOrderByIdAsc(relayId);

    return tickles.stream()
        .map(Tickle::getAuthorId)
        .map(mvpUserRepository::findById)
        .map(optional -> optional.orElse(null)) // 없는 경우 null 반환
        .filter(Objects::nonNull)
        .map(MvpUser::getImage)
        .toList();
  }

  private List<String> getTags(Long relayId) {
    return relayTagRepository.findAllByRelayId(relayId).stream()
        .map(RelayTag::getTagId)
        .map(tagRepository::findById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(Tag::getName)
        .toList();
  }

  public List<TicklePreviewDto> readTicklePreviews(Long relayId) {
    List<Tickle> tickles = tickleRepository.findTop5ByRelayIdOrderByIdDesc(relayId);

    List<TicklePreviewDto> previews = new ArrayList<>();
    for (Tickle t : tickles) {
      MvpUser user = mvpUserRepository.findById(t.getAuthorId())
          .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

      previews.add(TicklePreviewDto.of(t, user));
    }

    return previews;
  }

  public TickleThumbnailsResponse readTickleThumbnailsFromRelay(String relayId) {
    Relay relay = relayRepository.findByUuid(relayId)
        .orElseThrow(() -> new RestApiException(ErrorCode.RELAY_NOT_FOUND));
    List<Tickle> tickles = tickleRepository.findByRelayIdOrderByIdDesc(relay.getId());

    List<TickleThumbnail> thumbnails = tickles.stream()
        .map(TickleThumbnail::of)
        .toList();

    return TickleThumbnailsResponse.of(thumbnails);
  }

  public TickleViewResponse readTickle(String tickleId) {
    Tickle tickle = tickleRepository.findByUuid(tickleId)
        .orElseThrow(() -> new RestApiException(ErrorCode.TICKLE_NOT_FOUND));

    MvpUser user = mvpUserRepository.findById(tickle.getAuthorId())
        .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

    return TickleViewResponse.of(tickle, user);
  }
}
