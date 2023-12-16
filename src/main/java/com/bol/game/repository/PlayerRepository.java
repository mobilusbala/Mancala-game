package com.bol.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bol.game.model.Player;


public interface PlayerRepository extends JpaRepository<Player, String>  {

}
