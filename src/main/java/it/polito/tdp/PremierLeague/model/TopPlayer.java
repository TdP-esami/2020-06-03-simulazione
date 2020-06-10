package it.polito.tdp.PremierLeague.model;

import java.util.List;

public class TopPlayer {
	
	private Player player;
	private List<Opponent> opponents;
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public List<Opponent> getOpponents() {
		return opponents;
	}
	public void setOpponents(List<Opponent> opponents) {
		this.opponents = opponents;
	}
	
	
	
	
}
