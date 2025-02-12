package com.pozzle.addit.mvp.service;

import com.pozzle.addit.common.exception.ErrorCode;
import com.pozzle.addit.common.exception.RestApiException;
import com.pozzle.addit.common.util.GcsMediaManager;
import com.pozzle.addit.mvp.dto.request.SessionRequest;
import com.pozzle.addit.mvp.entity.MvpUser;
import com.pozzle.addit.mvp.repository.MvpUserRepository;
import com.pozzle.addit.reaction.entity.ReactionType;
import com.pozzle.addit.relay.dto.request.RelayCreateRequest;
import com.pozzle.addit.relay.dto.response.RelayCreateResponse;
import com.pozzle.addit.relay.entity.Relay;
import com.pozzle.addit.relay.entity.RelayStatus;
import com.pozzle.addit.relay.entity.RelayTag;
import com.pozzle.addit.relay.entity.Tag;
import com.pozzle.addit.relay.repository.RelayRepository;
import com.pozzle.addit.relay.repository.RelayTagRepository;
import com.pozzle.addit.relay.repository.TagRepository;
import com.pozzle.addit.tickle.dto.request.TickleAddRequest;
import com.pozzle.addit.tickle.dto.response.TickleAddResponse;
import com.pozzle.addit.tickle.entity.Tickle;
import com.pozzle.addit.tickle.repository.TickleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MvpCommandService {

    private final MvpUserRepository mvpUserRepository;
    private final RelayRepository relayRepository;
    private final TickleRepository tickleRepository;
    private final RelayTagRepository relayTagRepository;
    private final TagRepository tagRepository;
    private final GcsMediaManager mediaManager;

    public RelayCreateResponse createRelay(
        RelayCreateRequest request,
        MultipartFile file) {

        MvpUser user = createUser(request.userName(), request.userImage());

        String fileUrl = mediaManager.saveMediaFile(file);

        Relay relay;
        try {
            relay = Relay.builder()
                .authorId(user.getId())
                .uuid(UUID.randomUUID().toString())
                .title(request.title())
                .description(request.relayDescription())
                .status(RelayStatus.ACTIVE)
                .build();
            relayRepository.save(relay);
        } catch (Exception e) {
            throw new RestApiException(ErrorCode.VALIDATE_FAILED, e.getMessage());
        }

        Tickle tickle = Tickle.builder()
            .relayId(relay.getId())
            .authorId(user.getId())
            .uuid(UUID.randomUUID().toString())
            .description(request.tickleDescription())
            .file(fileUrl)
            .build();
        tickleRepository.save(tickle);

        assignTagWithRelay(relay, request.tags());

        return new RelayCreateResponse(relay.getUuid(), tickle.getUuid());
    }

    private void assignTagWithRelay(Relay relay, List<String> tags) {
        if(tags.isEmpty()) {
            throw new RestApiException(ErrorCode.EMPTY_TAG);
        }
        tags.forEach(t -> {
            Tag tag = tagRepository.findByName(t)
                .orElseGet(() -> tagRepository.save(
                        Tag.builder()
                            .name(t)
                            .build()
                    )
                );
            relayTagRepository.save(
                RelayTag.builder()
                    .relayId(relay.getId())
                    .tagId(tag.getId())
                    .build()
            );
        });
    }

    public TickleAddResponse addTickle(TickleAddRequest request, MultipartFile file) {

        MvpUser user = createUser(request.userName(), request.userImage());

        String fileUrl = mediaManager.saveMediaFile(file);

        Relay relay = relayRepository.findByUuid(request.relayId())
            .orElseThrow(() -> new RestApiException(ErrorCode.RELAY_NOT_FOUND));

        relay.addTickle();

        Tickle tickle = Tickle.builder()
            .relayId(relay.getId())
            .authorId(user.getId())
            .uuid(UUID.randomUUID().toString())
            .description(request.tickleDescription())
            .file(fileUrl)
            .build();
        tickleRepository.save(tickle);

        return new TickleAddResponse(relay.getUuid(), tickle.getUuid());
    }

    private MvpUser createUser(String name, String image) {
        MvpUser user = MvpUser.builder()
            .uuid(UUID.randomUUID().toString())
            .nickname(name)
            .image(image)
            .build();
        mvpUserRepository.save(user);
        return user;
    }

    public void addLike(String tickleId) {
        Tickle tickle = tickleRepository.findByUuid(tickleId)
            .orElseThrow(() -> new RestApiException(ErrorCode.TICKLE_NOT_FOUND));
        tickle.addReaction(ReactionType.LIKE);
    }
}
