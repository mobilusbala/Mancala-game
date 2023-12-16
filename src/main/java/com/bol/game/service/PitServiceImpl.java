package com.bol.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bol.game.exception.RecordNotFoundException;
import com.bol.game.model.Pit;
import com.bol.game.model.Player;
import com.bol.game.repository.PitRepository;

public class PitServiceImpl implements PitService {

	@Autowired
	private PitRepository pitRepository;

	@Override
	public List<Pit> getAllPits() {
		List<Pit> result = (List<Pit>) pitRepository.findAll();

		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<Pit>();
		}
	}

	@Override
	public Pit getPitById(int id) throws RecordNotFoundException {
		Optional<Pit> pit = pitRepository.findById(id);

		if (pit.isPresent()) {
			return pit.get();
		} else {
			throw new RecordNotFoundException("No Pit record exist for given id");
		}
	}

	@Override
	public Pit getPitBySeq(String player,int seq) throws RecordNotFoundException {
		List<Pit> result = (List<Pit>) pitRepository.findByPlayerAndSeq(player, seq);

		if (result.size() > 0) {
			return result.get(0);
		} else {
			throw new RecordNotFoundException("No Pit record exist for given seq for "+player);
		}
	}

	@Override
	public Pit createOrUpdatePit(Pit pit) {
		return pitRepository.save(pit);
	}

	@Override
	public void deletePitById(int id) throws RecordNotFoundException {
		Optional<Pit> pit = pitRepository.findById(id);

		if (pit.isPresent()) {
			pitRepository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No Pit record exist for given id");
		}

	}

}
