package com.bol.game;

import java.util.ArrayList;
import java.util.List;

import com.bol.game.exception.RecordNotFoundException;
import com.bol.game.model.Pit;
import com.bol.game.model.Player;
import com.bol.game.service.PlayerServiceImpl;

public class Test {

	
   public static void main(String[] args) throws RecordNotFoundException {
	   Player player1 = new Player();
		List<Pit> expected = new ArrayList<Pit>();
		
		  expected.add(new Pit(0,1,0,player1));
		  expected.add(new Pit(1,2,7,player1));
		  expected.add(new Pit(2,6,7,player1));
		  expected.add(new Pit(3,4,7,player1));
		  expected.add(new Pit(4,3,7,player1));
		  expected.add(new Pit(5,5,7,player1));
		  expected.add(new Pit(6,7,1,player1));

		  
		  for (Pit pit : expected) { System.out.println(pit); }
	   
	       System.out.println("------------------------------");
		  expected.get(3).setStones(0);
		  
		  for (Pit pit : expected) { System.out.println(pit); }
    }
	
}
