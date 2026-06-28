package com.booklovers.booklovers.Repository;

 
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booklovers.booklovers.Entity.BookClubMember;

public interface BookClubMemberRepository
        extends JpaRepository<BookClubMember,Long>{

    Optional<BookClubMember> findByClubIdAndUserId(Long clubId,Long userId);

    List<BookClubMember> findByClubId(Long clubId);

    int countByClubId(Long clubId);


    

}