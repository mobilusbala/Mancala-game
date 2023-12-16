package com.bol.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bol.game.exception.RecordNotFoundException;
import com.bol.game.model.Player;
import com.bol.game.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	PlayerRepository repository;

	public List<Player> getAllPlayer() {
		List<Player> result = (List<Player>) repository.findAll();

		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<Player>();
		}
	}

	public Player getPlayerById(String name) throws RecordNotFoundException {
		Optional<Player> player = repository.findById(name);

		if (player.isPresent()) {
			return player.get();
		} else {
			throw new RecordNotFoundException("No Player record exist for given id");
		}
	}

	public Player createOrUpdatePlayer(Player palyer) {
		return repository.save(palyer);
	}

	public void deletePlayerById(String name) throws RecordNotFoundException {
		Optional<Player> Player = repository.findById(name);

		if (Player.isPresent()) {
			repository.deleteById(name);
		} else {
			throw new RecordNotFoundException("No Player record exist for given id");
		}
	}
}