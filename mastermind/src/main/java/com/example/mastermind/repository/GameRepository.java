package com.example.mastermind.repository;

import com.example.mastermind.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository that holds the created {@link Game} entities
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
}
