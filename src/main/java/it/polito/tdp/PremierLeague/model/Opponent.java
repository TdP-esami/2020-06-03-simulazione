package it.polito.tdp.PremierLeague.model;


public class Opponent implements Comparable<Opponent> {

		private Player player;
		private Integer weight;
		
		public Opponent(Player player, Integer weight) {
			super();
			this.player = player;
			this.weight = weight;
		}

		public Player getPlayer() {
			return player;
		}

		public void setPlayer(Player player) {
			this.player = player;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		@Override
		public int compareTo(Opponent o) {
			// TODO Auto-generated method stub
			return (-1) * this.weight.compareTo(o.getWeight());
		}

		@Override
		public String toString() {
			return player + " | " + weight;
		}
		
		
		
	}