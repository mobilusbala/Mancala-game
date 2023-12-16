package com.bol.game.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bol.game.exception.RecordNotFoundException;
import com.bol.game.model.GameStatus;
import com.bol.game.model.Pit;
import com.bol.game.model.Player;
import com.bol.game.repository.PitRepository;
import com.bol.game.repository.PlayerRepository;

public class GameServiceImpl implements GameService {

	private static final Logger logger = LogManager.getLogger(GameServiceImpl.class);

	@Autowired
	private PitService pitService;

	@Autowired
	private PlayerService playerService;

	private GameStatus board = new GameStatus(0, "Palyer1");

	private Player player1;
	private Player player2;
	
	@Override
	public void gameProcess(String index) throws RecordNotFoundException{
		
		player1 = playerService.getPlayerById("Palyer1");
		player2 = playerService.getPlayerById("Palyer2");

		int start = Integer.parseInt(index);
		
		List<Pit> pitsOrdered =getAllPitsInSeq(player1, player2);

		String player = start < GameStatus.PLAYER_PIT_COUNT ? "Palyer1" : "Palyer2";
		
		Pit last =sowPit(pitsOrdered, player, start);

		setTurn(last, player);
		
		captureStones(pitsOrdered, last, player);
		
		checkGameOver(pitsOrdered,player1,player2);
		
		saveAllPits(pitsOrdered);
	}


	@Override
	public GameStatus getStatus() {
		return board;
	}
	
	@Override
	public List<Pit> getAllPitsInSeq(Player player) {

		List<Pit> pitSorted = player.getPits();

		Collections.sort(pitSorted);
		
		
		// for (Pit pit : pitSorted) { System.out.println(pit); }
		 

		return pitSorted;
	}

	/*All pits are ordered with player and seq*/
	@Override
	public List<Pit> getAllPitsInSeq(Player player1, Player player2) {
		List<Pit> pits = new ArrayList<>();

		pits.addAll(getAllPitsInSeq(player1));
		pits.addAll(getAllPitsInSeq(player2));

	
		return pits;
	}


    /*sow stones to all following pits anti clockwise except the other player's big pit */
	@Override
	public Pit sowPit(List<Pit> pits, String player, int start) {
		List<Pit> pitsRes = pits;
		int sowStones = 0;
		Pit ref = null;
		int refIndex = 0;

		Pit pitSow = findByPlayerAndSeq(pitsRes, player, start % GameStatus.PLAYER_PIT_COUNT);
		sowStones = pitSow.getStones();
		pitSow.setStones(0);
		pitsRes.set(pitsRes.indexOf(pitSow), pitSow);

		for (int i = start; i < sowStones + start; i++) {
			refIndex = i % (GameStatus.PLAYER_PIT_COUNT*2);
			ref = pitsRes.get(refIndex);

			if (!ref.getPlayer().getName().equals(player) && ref.isBig()) { // (refIndex+1)%7==0
				sowStones++;
				//System.out.println(ref.getPlayer().getName() + " " + refIndex % GameStatus.PLAYER_PIT_COUNT);
			} else {
				ref.add(1);
				pitsRes.set(refIndex, ref);
			}

		}

		return ref;
	}

	/*Set player  turn*/
	@Override
	public void setTurn(Pit last, String player) {

		if (last.getPlayer().getName().equals(player) && last.isBig()) {
			board.setPlayerTurn(player);
		} else {
			board.setPlayerTurn(getNextPlayer(player));
		}
	}

	/* capture stones are assigned to little pit. (not big pit) */
	@Override
	public boolean captureStones(List<Pit> pits, Pit lastPit, String player) {

		boolean res = false;
		
		if(lastPit.getPlayer().getName().equals(player) && lastPit.getStones()==1 && !lastPit.isBig() ) {  
			
			Pit oppositePit = findByPlayerAndSeq(pits, getNextPlayer(player), GameStatus.PLAYER_PIT_COUNT - lastPit.getSeq());
			Pit last = lastPit;
			last.add(oppositePit.getStones());
			oppositePit.setStones(0);

			UpdatePit(pits, oppositePit);
			UpdatePit(pits, last);
			res = true;
    	}

		return res;
	}

	@Override
	public boolean checkGameOver(List<Pit> pits,Player player1, Player player2) {

		boolean gameOverTag = false;	
		
		if (!findAnyNonEmptyPit(getPitByPlayer(pits, player1.getName(), false))) {
			updateAllPitsGameOver(pits, player2.getName());
			gameOverTag = true;
		} else if (!findAnyNonEmptyPit(getPitByPlayer(pits, player2.getName(), false))) {
			updateAllPitsGameOver(pits, player1.getName());
			gameOverTag = true;
		}

		if (gameOverTag) {
			
			if (getPitByPlayer(pits, player1.getName(),true).get(0).getStones() > getPitByPlayer(pits, player1.getName(),true).get(0).getStones()  ) {
				board.setPlayerWin(player1.getName());
			} else {
				board.setPlayerWin(player2.getName());
			}
			
		}

		
		return gameOverTag;
	}
	
	/*Take a small pits stones from the player and put to big pit */
	public void updateAllPitsGameOver(List<Pit> pits,String player) {
		
		 Pit pitBig = getPitByPlayer(pits, player,true).get(0);
		 pitBig.add(getAllStones(pits, player));
		 UpdatePit(pits, pitBig);
		 
		 System.out.println("pitBig - "+pitBig);
		
	     for (Pit pit :  getPitByPlayer( pits, player,false)) {
	    	 pit.setStones(0);
			UpdatePit(pits, pit);
		 }
	}
	
	

	public String getNextPlayer(String player) {
		return player.equals("Palyer1") ? "Palyer2" : "Palyer1";
	}

	public void UpdatePit(List<Pit> pits, Pit pit) {
		pits.set(pits.indexOf(pit), pit);
	}

	public void saveAllPits(List<Pit> pits) {

		for (Pit pit : pits) {
			pitService.createOrUpdatePit(pit);
		}
	}

	@Override
	public Pit findByPlayerAndSeq(List<Pit> pits, String player, int seq) {
		return pits.stream().filter(p -> (p.getPlayer().getName().equals(player) && p.getSeq() == seq))
				.collect(Collectors.toList()).get(0);
	}

	public boolean findAnyNonEmptyPit(List<Pit> pits) {

		return pits.stream().filter(p -> (p.getStones() != 0 && !p.isBig())).findAny().isPresent();
	}
	
	public int getAllStones(List<Pit> pits,String player) {

		return pits.stream().filter(p -> (p.getStones() != 0 && !p.isBig())).mapToInt(p -> p.getStones()).sum();
	}
	
	@Override
	public List<Pit> getPitByPlayer(List<Pit> pits, String player,boolean isBig) {
		
		return pits.stream().filter(p -> (p.getPlayer().getName().equals(player) && p.isBig()==isBig)).collect(Collectors.toList());
	}


}
