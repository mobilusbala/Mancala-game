package com.bol.game.service;

import java.util.List;

import com.bol.game.exception.RecordNotFoundException;
import com.bol.game.model.Player;

public interface PlayerService {

	public List<Player> getAllPlayer();
	public Player getPlayerById(String name) throws RecordNotFoundException ;
	public Player createOrUpdatePlayer(Player player); 
	public void deletePlayerById(String name) throws RecordNotFoundException ;
}
