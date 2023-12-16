package com.bol.game.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bol.game.model.Pit;

public interface PitRepository extends JpaRepository<Pit, Integer> {
	
	 List<Pit> findByPlayerAndSeq(String player,int seq);

}
