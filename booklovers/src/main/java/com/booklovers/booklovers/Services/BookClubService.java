package com.booklovers.booklovers.Services;

 
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booklovers.booklovers.DTO.BookClubResponse;
import com.booklovers.booklovers.DTO.CreateBookClubRequest;
import com.booklovers.booklovers.DTO.JoinClubRequest;
import com.booklovers.booklovers.Entity.BookClub;
import com.booklovers.booklovers.Entity.BookClubMember;
import com.booklovers.booklovers.Entity.Users;
import com.booklovers.booklovers.Repository.BookClubMemberRepository;
import com.booklovers.booklovers.Repository.BookClubRepository;
import com.booklovers.booklovers.Repository.UsersRepository;

@Service
public class BookClubService {

    private static final Logger logger =
            LoggerFactory.getLogger(BookClubService.class);

    private final BookClubRepository clubRepository;
    private final BookClubMemberRepository memberRepository;
    private final UsersRepository usersRepository;

    public BookClubService(
            BookClubRepository clubRepository,
            BookClubMemberRepository memberRepository,
            UsersRepository usersRepository) {

        this.clubRepository = clubRepository;
        this.memberRepository = memberRepository;
        this.usersRepository = usersRepository;
    }

    /**
     * Create a new Book Club
     */
    @Transactional
    public BookClubResponse createClub(CreateBookClubRequest request) {

        logger.info("Creating new book club: {}", request.getClubName());

        Users creator = usersRepository.findById(request.getCreatedBy())
                .orElseThrow(() -> {
                    logger.error("User not found with ID {}", request.getCreatedBy());
                    return new RuntimeException("User not found");
                });

        BookClub club = new BookClub();

        club.setClubName(request.getClubName());
        club.setDescription(request.getDescription());
        club.setCreatedBy(creator);

        club = clubRepository.save(club);

        logger.info("Book club created successfully with ID {}", club.getId());

        BookClubMember member = new BookClubMember();

        member.setClub(club);
        member.setUser(creator);
        member.setRole("ADMIN");
        member.setJoinedAt(LocalDateTime.now());

        memberRepository.save(member);

        logger.info("Creator {} added as ADMIN", creator.getId());

        return map(club);
    }

    /**
     * Get all Book Clubs
     */
    public List<BookClubResponse> getAllClubs() {

        logger.info("Fetching all book clubs");

        List<BookClubResponse> clubs = clubRepository.findAll()
                .stream()
                .map(this::map)
                .toList();

        logger.info("{} clubs found", clubs.size());

        return clubs;
    }

    /**
     * Join a Book Club
     */
    @Transactional
    public void joinClub(JoinClubRequest request) {

        logger.info("User {} attempting to join club {}",
                request.getUserId(),
                request.getClubId());

        Users user = usersRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    logger.error("User {} not found", request.getUserId());
                    return new RuntimeException("User not found");
                });

        BookClub club = clubRepository.findById(request.getClubId())
                .orElseThrow(() -> {
                    logger.error("Club {} not found", request.getClubId());
                    return new RuntimeException("Book Club not found");
                });

        boolean alreadyMember = memberRepository
                .findByClubIdAndUserId(
                        request.getClubId(),
                        request.getUserId())
                .isPresent();

        if (alreadyMember) {

            logger.warn("User {} is already a member of club {}",
                    request.getUserId(),
                    request.getClubId());

            throw new RuntimeException("You are already a member of this club.");
        }

        BookClubMember member = new BookClubMember();

        member.setClub(club);
        member.setUser(user);
        member.setRole("MEMBER");
        member.setJoinedAt(LocalDateTime.now());

        memberRepository.save(member);

        logger.info("User {} joined club {} successfully",
                user.getId(),
                club.getId());
    }

    /**
     * Maps Entity -> DTO
     */
    private BookClubResponse map(BookClub club) {

        BookClubResponse dto = new BookClubResponse();

        dto.setId(club.getId());

        dto.setClubName(club.getClubName());

        dto.setDescription(club.getDescription());

        dto.setCreatorId(club.getCreatedBy().getId());

        dto.setCreatorName(club.getCreatedBy().getName());

        dto.setCreatorEmail(club.getCreatedBy().getEmail());

        dto.setMembers(
                memberRepository.countByClubId(club.getId())
        );

        return dto;
    }

}