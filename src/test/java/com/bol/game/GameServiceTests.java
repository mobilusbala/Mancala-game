package com.bol.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bol.game.exception.RecordNotFoundException;
import com.bol.game.model.Pit;
import com.bol.game.model.Player;
import com.bol.game.service.GameService;
import com.bol.game.service.PlayerService;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class GameServiceTests {
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayerService playerService;
	
	private List<Pit> list;
	
	private Player player1;
	private Player player2;
	
	@BeforeEach
	public void setUp() throws RecordNotFoundException{
		list = new ArrayList<Pit>();
		
		player1 = playerService.getPlayerById("Palyer1");
		player2 = playerService.getPlayerById("Palyer2");
	
		  list.add(new Pit(0,1,6,player1));
		  list.add(new Pit(1,2,6,player1));
		  list.add(new Pit(2,3,6,player1));
		  list.add(new Pit(3,4,6,player1));
		  list.add(new Pit(4,5,6,player1));
		  list.add(new Pit(5,6,6,player1));
		  list.add(new Pit(6,7,0,player1));
		  list.add(new Pit(7,1,6,player2));
		  list.add(new Pit(8,2,6,player2));
		  list.add(new Pit(9,3,6,player2));
		  list.add(new Pit(10,4,6,player2));
		  list.add(new Pit(11,5,6,player2));
		  list.add(new Pit(12,6,6,player2));
		  list.add(new Pit(13,7,0,player2));
		
	}

	@Test
	public void orderedListByPlayerAndSeqTest() { 
       
        assertEquals(list,gameService.getAllPitsInSeq(player1, player2));
		
	}
	
	@Test
	public void sowSamePlayerTest() { 
		
		List<Pit> expected = new ArrayList<Pit>();
		
		  expected.add(new Pit(0,1,0,player1));
		  expected.add(new Pit(1,2,7,player1));
		  expected.add(new Pit(2,6,7,player1));
		  expected.add(new Pit(3,4,7,player1));
		  expected.add(new Pit(4,3,7,player1));
		  expected.add(new Pit(5,5,7,player1));
		  expected.add(new Pit(6,7,1,player1));
		  expected.add(new Pit(7,1,6, player2));
		  expected.add(new Pit(8,2,6, player2));
		  expected.add(new Pit(9,3,6, player2));
		  expected.add(new Pit(10,4,6,player2));
		  expected.add(new Pit(11,5,6,player2));
		  expected.add(new Pit(12,6,6,player2));
		  expected.add(new Pit(13,7,0,player2));

       
		Pit last = gameService.sowPit(list, player1.getName(), 1);
		
        //assertEquals(list.toArray(),containsInAnyOrder(expected.toArray()));
		assertTrue(list.size() == expected.size() && list.containsAll(expected) && expected.containsAll(list));
		assertEquals(expected.get(6),last);
		
	}
	
	@Test
	public void sowOtherPlayerTest() { 
		
		List<Pit> actual = new ArrayList<Pit>();
		
		  actual.add(new Pit(0,1,6,player1));
		  actual.add(new Pit(1,2,22,player1));
		  actual.add(new Pit(2,6,6,player1));
		  actual.add(new Pit(3,4,6,player1));
		  actual.add(new Pit(4,3,6,player1));
		  actual.add(new Pit(5,5,6,player1));
		  actual.add(new Pit(6,7,0,player1));
		  actual.add(new Pit(7,1,6, player2));
		  actual.add(new Pit(8,2,6, player2));
		  actual.add(new Pit(9,3,6, player2));
		  actual.add(new Pit(10,4,6,player2));
		  actual.add(new Pit(11,5,6,player2));
		  actual.add(new Pit(12,6,6,player2));
		  actual.add(new Pit(13,7,0,player2));
		
		List<Pit> expected = new ArrayList<Pit>();
		
		  expected.add(new Pit(0,1,7,player1));
		  expected.add(new Pit(1,2,1,player1));
		  expected.add(new Pit(2,3,8,player1));
		  expected.add(new Pit(3,4,8,player1));
		  expected.add(new Pit(4,5,8,player1));
		  expected.add(new Pit(5,6,8,player1));
		  expected.add(new Pit(6,7,2,player1));
		  expected.add(new Pit(7,1,8, player2));
		  expected.add(new Pit(8,2,8, player2));
		  expected.add(new Pit(9,3,8, player2));
		  expected.add(new Pit(10,4,8,player2));
		  expected.add(new Pit(11,5,7,player2));
		  expected.add(new Pit(12,6,7,player2));
		  expected.add(new Pit(13,7,0,player2));

       
		Pit last = gameService.sowPit(actual, player1.getName(), 2);
		
        //assertEquals(list.toArray(),containsInAnyOrder(expected.toArray()));
		assertTrue(actual.size() == expected.size() && actual.containsAll(expected) && expected.containsAll(actual));
		assertEquals(expected.get(10),last);
		
	}
	
	@Test
	public void setTurnSamePlayerTest() { 
		
		gameService.setTurn(list.get(6), player1.getName());
		
		assertEquals(gameService.getStatus().getMessage(), "PLAYER1'S TURN");
	}
	
	@Test
	public void setTurnOtherPlayerTest() { 
		
		gameService.setTurn(list.get(2), player1.getName());
		
		assertEquals(gameService.getStatus().getMessage(), "PLAYER2'S TURN");
	}
	
	
	@Test
	public void captureStonesTrueTest() { 
		
		List<Pit> actual = new ArrayList<Pit>();
		int lastSowIndex = 3;
		
		  actual.add(new Pit(0,1,6,player1));
		  actual.add(new Pit(1,2,6,player1));
		  actual.add(new Pit(2,6,6,player1));
		  actual.add(new Pit(3,4,1,player1)); // Stone state 1 because according to the code captureStones run after the sow method
		  actual.add(new Pit(4,3,6,player1));
		  actual.add(new Pit(5,5,6,player1));
		  actual.add(new Pit(6,7,0,player1));
		  actual.add(new Pit(7,1,6, player2));
		  actual.add(new Pit(8,2,6, player2));
		  actual.add(new Pit(9,3,6, player2));
		  actual.add(new Pit(10,4,6,player2));
		  actual.add(new Pit(11,5,6,player2));
		  actual.add(new Pit(12,6,6,player2));
		  actual.add(new Pit(13,7,0,player2));
		  
		  assertTrue(gameService.captureStones(actual, actual.get(lastSowIndex), player1.getName()));
		  assertTrue(actual.get(lastSowIndex).getStones()==7);
		  assertTrue(actual.get(9).getStones()==0);
	}
	
	@Test
	public void captureStonesFalseTest() { 
		
		List<Pit> actual = new ArrayList<Pit>();
		int lastSowIndex = 3;
		
		  actual.add(new Pit(0,1,6,player1));
		  actual.add(new Pit(1,2,6,player1));
		  actual.add(new Pit(2,6,6,player1));
		  actual.add(new Pit(3,4,6,player1)); // Stone state 1 because according to the code captureStones run after the sow method
		  actual.add(new Pit(4,3,6,player1));
		  actual.add(new Pit(5,5,6,player1));
		  actual.add(new Pit(6,7,0,player1));
		  actual.add(new Pit(7,1,6, player2));
		  actual.add(new Pit(8,2,6, player2));
		  actual.add(new Pit(9,3,6, player2));
		  actual.add(new Pit(10,4,6,player2));
		  actual.add(new Pit(11,5,6,player2));
		  actual.add(new Pit(12,6,6,player2));
		  actual.add(new Pit(13,7,0,player2));
		  
		  assertFalse(gameService.captureStones(actual, actual.get(lastSowIndex), player1.getName()));

	}
	
	@Test
	public void checkGameOverTrueTest() { 
		
		 List<Pit> actual = new ArrayList<Pit>();
		
		  actual.add(new Pit(0,1,0,player1));
		  actual.add(new Pit(1,2,0,player1));
		  actual.add(new Pit(2,6,0,player1));
		  actual.add(new Pit(3,4,0,player1)); 
		  actual.add(new Pit(4,3,0,player1));
		  actual.add(new Pit(5,5,0,player1));
		  actual.add(new Pit(6,7,12,player1));
		  actual.add(new Pit(7,1,6, player2));
		  actual.add(new Pit(8,2,6, player2));
		  actual.add(new Pit(9,3,6, player2));
		  actual.add(new Pit(10,4,6,player2));
		  actual.add(new Pit(11,5,6,player2));
		  actual.add(new Pit(12,6,6,player2));
		  actual.add(new Pit(13,7,0,player2));
		  
		  assertTrue(gameService.checkGameOver(actual,player1,player2));
		  assertTrue(gameService.getPitByPlayer(actual, player1.getName(), true).get(0).getStones()==12);
		  assertTrue(gameService.getPitByPlayer(actual, player2.getName(), true).get(0).getStones()==36);
		  assertTrue(gameService.getPitByPlayer(actual, player2.getName(), false).stream().mapToInt(p -> p.getStones()).sum()==0);
		  assertEquals(gameService.getStatus().getMessage(), "PLAYER2 WIN");
		  
		  for (Pit pit : actual) { System.out.println(pit); }
		
	}
	
	@Test
	public void checkGameOverFalseTest() { 
		
		 List<Pit> actual = new ArrayList<Pit>();
		
		  actual.add(new Pit(0,1,0,player1));
		  actual.add(new Pit(1,2,0,player1));
		  actual.add(new Pit(2,6,6,player1));
		  actual.add(new Pit(3,4,0,player1)); 
		  actual.add(new Pit(4,3,0,player1));
		  actual.add(new Pit(5,5,0,player1));
		  actual.add(new Pit(6,7,12,player1));
		  actual.add(new Pit(7,1,6, player2));
		  actual.add(new Pit(8,2,6, player2));
		  actual.add(new Pit(9,3,6, player2));
		  actual.add(new Pit(10,4,6,player2));
		  actual.add(new Pit(11,5,6,player2));
		  actual.add(new Pit(12,6,6,player2));
		  actual.add(new Pit(13,7,0,player2));
		  
		  assertFalse(gameService.checkGameOver(actual,player1,player2));
		  assertTrue(gameService.getPitByPlayer(actual, player1.getName(), true).get(0).getStones()==12);
		  assertTrue(gameService.getPitByPlayer(actual, player2.getName(), true).get(0).getStones()==0);
		  assertTrue(gameService.getPitByPlayer(actual, player2.getName(), false).stream().mapToInt(p -> p.getStones()).sum()==36);
		  
		  //for (Pit pit : actual) { System.out.println(pit); }
		
	}
}
