package com.backend.games4u.repository;

import com.backend.games4u.models.MyGameListEntry;
import com.backend.games4u.models.MyGameListStatus;
import com.backend.games4u.models.User;
import com.backend.games4u.models.VideoGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyGameListEntryRepository extends JpaRepository<MyGameListEntry, Long> {

    Optional<List<MyGameListEntry>> findAllByUser(User user);

    Optional<List<MyGameListEntry>> findAllByUserAndMyGameListStatus(User user, MyGameListStatus status);

    Optional<MyGameListEntry> findMyGameListEntryByVideoGameAndUser(VideoGame videoGame, User user);

    Page<MyGameListEntry> findMyGameListEntryByUser(User user, Pageable pageable);

    Page<MyGameListEntry> findMyGameListEntryByUserAndAndMyGameListStatus(
            User user,
            MyGameListStatus status,
            Pageable pageable);
}
