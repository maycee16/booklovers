package com.booklovers.booklovers.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.booklovers.booklovers.DTO.NotificationRequest;
import com.booklovers.booklovers.DTO.NotificationResponse;
import com.booklovers.booklovers.Entity.BookClub;
import com.booklovers.booklovers.Entity.BookClubMember;
import com.booklovers.booklovers.Entity.Notification;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.BookClubMemberRepository;
import com.booklovers.booklovers.Repository.BookClubRepository;
import com.booklovers.booklovers.Repository.NotificationRepository;
import com.booklovers.booklovers.Repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
private final NotificationRepository notificationRepository;
private final BookClubRepository clubRepository;
private final BookClubMemberRepository memberRepository;
private final UsersRepository usersRepository;


public NotificationService(
        NotificationRepository notificationRepository,
        BookClubRepository clubRepository,
        BookClubMemberRepository memberRepository,
        UsersRepository usersRepository) {

    this.notificationRepository = notificationRepository;
    this.clubRepository = clubRepository;
    this.memberRepository = memberRepository;
    this.usersRepository = usersRepository;
}

  @Transactional
public NotificationResponse createNotification(NotificationRequest request) {

    logger.info("Creating notification for club {}", request.getClubId());

    BookClub club = clubRepository.findById(request.getClubId())
            .orElseThrow(() -> {
                logger.error("Club not found {}", request.getClubId());
                return new RuntimeException("Book club not found.");
            });

    Users sender = usersRepository.findById(request.getSenderId())
            .orElseThrow(() -> {
                logger.error("User not found {}", request.getSenderId());
                return new RuntimeException("User not found.");
            });

    boolean member = memberRepository
            .findByClubIdAndUserId(
                    request.getClubId(),
                    request.getSenderId())
            .isPresent();

    if (!member) {
        logger.error("User {} is not a member of club {}",
                request.getSenderId(),
                request.getClubId());

        throw new RuntimeException("You are not a member of this club.");
    }

    Notification notification = new Notification();

    notification.setClub(club);
    notification.setCreatedBy(sender);
    notification.setTitle(request.getTitle());
    notification.setMessage(request.getMessage());
    notification.setType(request.getType());
    notification.setDeleted(false);
    notification.setCreatedAt(LocalDateTime.now());

    notification = notificationRepository.save(notification);

    logger.info("Notification {} created successfully",
            notification.getId());

    return map(notification);
}


public List<NotificationResponse> getClubNotifications(
        Long clubId,
        Long userId) {

    logger.info("Fetching notifications for club {}", clubId);

    boolean member = memberRepository
            .findByClubIdAndUserId(clubId, userId)
            .isPresent();

    if (!member) {

        logger.error("User {} attempted to access club {}",
                userId,
                clubId);

        throw new RuntimeException(
                "You are not a member of this club.");
    }

    List<Notification> notifications =
            notificationRepository
            .findByClubIdAndDeletedFalseOrderByCreatedAtDesc(clubId);

    logger.info("{} notifications found",
            notifications.size());

    return notifications
            .stream()
            .map(this::map)
            .toList();
}



  


 private NotificationResponse map(Notification notification) {

    NotificationResponse dto = new NotificationResponse();

    dto.setNotificationId(notification.getId());

    dto.setTitle(notification.getTitle());

    dto.setMessage(notification.getMessage());

    dto.setType(notification.getType());

    dto.setCreatedAt(notification.getCreatedAt());

    dto.setSenderId(notification.getCreatedBy().getId());

    dto.setSenderName(notification.getCreatedBy().getName());

    dto.setSenderEmail(notification.getCreatedBy().getEmail());

    return dto;
}
}