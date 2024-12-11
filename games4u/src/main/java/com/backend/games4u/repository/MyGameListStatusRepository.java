package com.backend.games4u.repository;

import com.backend.games4u.models.MyGameListStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyGameListStatusRepository extends JpaRepository<MyGameListStatus, Long> {

    Optional<MyGameListStatus> findByStatus(String status);
}
