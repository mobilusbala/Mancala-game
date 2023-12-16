package com.bol.game.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pit")
public class Pit implements Comparable<Pit>{
	
	@Id 
	
	private int id;
	
	private int seq;
	
	private int stones;
	
	@ManyToOne
    @JoinColumn(name = "player_id")
	private Player player;
	
	
	public Pit() {
		super();
	}

	public Pit(int id, int seq, int stones, Player player) {
		super();
		this.id = id;
		this.seq = seq;
		this.stones = stones;
		this.player = player;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getStones() {
		return stones;
	}

	public void setStones(int stones) {
		this.stones = stones;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
	public void add(int stones){
		this.stones += stones;
	}

	/*seq number 7 considered as big pit*/
	public boolean isBig() {
		return seq==7;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + seq;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pit other = (Pit) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (seq != other.seq)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Pit [id=" + id + ", seq=" + seq + ", stones=" + stones + ", player=" + player + "]";
	}

	@Override
	public int compareTo(Pit o) {
		 int comSeq=o.getSeq();
	        /* For Ascending order*/
	     return this.seq-comSeq;
	}



	
	
}
