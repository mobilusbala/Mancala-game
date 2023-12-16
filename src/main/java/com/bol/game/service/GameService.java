package com.bol.game.service;

import java.util.List;

import com.bol.game.exception.RecordNotFoundException;
import com.bol.game.model.GameStatus;
import com.bol.game.model.Pit;
import com.bol.game.model.Player;

public interface GameService {

	void gameProcess(String index) throws RecordNotFoundException ;
	
	List<Pit> getAllPitsInSeq(Player player);
	
	List<Pit> getAllPitsInSeq(Player player1, Player player2);
	
	Pit findByPlayerAndSeq(List<Pit> pits,String player,int seq);
	
	Pit sowPit(List<Pit> pits, String player, int start);
	
	void setTurn(Pit last, String player);
	
	boolean captureStones(List<Pit> pits, Pit lastPit, String player);
	
	boolean checkGameOver(List<Pit> pits,Player player1, Player player2);
	
	List<Pit> getPitByPlayer(List<Pit> pits, String player,boolean isBig);
	
	GameStatus getStatus() ;

}
