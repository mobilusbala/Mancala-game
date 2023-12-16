package com.bol.game.model;

public class GameStatus {  
	
	private static final int PLAYER1_TURN = 0;
	private static final int PLAYER2_TURN = 1;
	private static final int PLAYER1_WIN = 2;
	private static final int PLAYER2_WIN = 3;
	
	public static final int PLAYER_PIT_COUNT = 7;
	
	private int status;
	private String player;
	
	public  GameStatus() {
		
	}
	
	public  GameStatus(int status,String player) {
		this.status = status;
		this.player = player;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	
	public void setPlayerTurn(String player) {
		   setPlayer(player);
		   setStatus(player.equals("Palyer1") ? GameStatus.PLAYER1_TURN : GameStatus.PLAYER2_TURN );
	}

	public void setPlayerWin(String player) {
		   setPlayer(player);
		   setStatus(player.equals("Palyer1") ? GameStatus.PLAYER1_WIN : GameStatus.PLAYER2_WIN );
	}



	public String getMessage() {
		
		String res="";
		
		if (status == GameStatus.PLAYER1_WIN) {
			res="PLAYER1 WIN";
		}else if (status == GameStatus.PLAYER2_WIN) {
			res="PLAYER2 WIN";
		}else if (status == GameStatus.PLAYER1_TURN) {
			res="PLAYER1'S TURN";
		}else if (status == GameStatus.PLAYER2_TURN) {
			res="PLAYER2'S TURN";
		}
		
		return res;
	}

}
