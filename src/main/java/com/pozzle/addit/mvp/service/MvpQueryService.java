package com.pozzle.addit.mvp.service;

import com.pozzle.addit.mvp.dto.dto.RelayPreviewDto;
import com.pozzle.addit.mvp.dto.response.RelayPreviewsResponse;
import com.pozzle.addit.mvp.entity.MvpUser;
import com.pozzle.addit.mvp.repository.MvpUserRepository;
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

  public RelayPreviewsResponse readRelayPreviews(int size) {
    List<Relay> relays = relayRepository.findRelaysByRandom(size);

    List<RelayPreviewDto> previews = new ArrayList<>();
    for (Relay r : relays) {
      List<Tickle> tickles = tickleRepository.findTop3ByRelayIdOrderByIdAtAsc(r.getId());

      List<String> tags = relayTagRepository.findAllByRelayId(r.getId()).stream()
          .map(RelayTag::getTagId)
          .map(tagRepository::findById)
          .filter(Optional::isPresent)
          .map(Optional::get)
          .map(Tag::getName)
          .toList();

      List<String> profileImages = tickles.stream()
          .map(Tickle::getAuthorId)
          .map(mvpUserRepository::findById)
          .map(optional -> optional.orElse(null)) // 없는 경우 null 반환
          .filter(Objects::nonNull)
          .map(MvpUser::getImage)
          .toList();

      previews.add(RelayPreviewDto.of(r, tags, profileImages));
    }

    return RelayPreviewsResponse.of(previews);
  }

}
