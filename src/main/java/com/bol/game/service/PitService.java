package com.bol.game.service;

import java.util.List;

import com.bol.game.exception.RecordNotFoundException;
import com.bol.game.model.Pit;

public interface PitService {
	
	public List<Pit> getAllPits();
	public Pit getPitById(int id) throws RecordNotFoundException ;
	public Pit getPitBySeq(String player,int seq) throws RecordNotFoundException ;
	public Pit createOrUpdatePit(Pit pit); 
	public void deletePitById(int id) throws RecordNotFoundException ;

}
