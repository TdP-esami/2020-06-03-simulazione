package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;



public class Model {
	
	private Graph<Player, DefaultWeightedEdge> graph;
	private Map<Integer, Player> idMap;
	private PremierLeagueDAO dao;
	
	private List<Player> dreamTeam;
	private Integer bestDegree;
 

	
	public Model() {
		this.dao = new PremierLeagueDAO();
	}

	public void creaGrafo(double x) {
		graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<Integer, Player>();
		
		dao.getVertici(x, idMap);
		Graphs.addAllVertices(graph, idMap.values());
		
		for(Adiacenza adj : dao.getAdiacenze(idMap)) {
			if(graph.containsVertex(adj.getP1()) && graph.containsVertex(adj.getP2())) {
				if(adj.getPeso() < 0) {
					//arco da p2 a p1
					Graphs.addEdgeWithVertices(graph, adj.getP2(), adj.getP1(), ((double) -1)*adj.getPeso());
				} else if(adj.getPeso() > 0){
					//arco da p1 a p2
					Graphs.addEdgeWithVertices(graph, adj.getP1(), adj.getP2(), adj.getPeso());
				}
			}
		}
		
		System.out.println(String.format("Grafo creato con %d vertici e %d archi", graph.vertexSet().size(), graph.edgeSet().size()));
	}
	
	public int nVertici() {
		return graph.vertexSet().size();
	}
	
	public int nArchi() {
		return graph.edgeSet().size();
	}
	
	public Graph<Player, DefaultWeightedEdge> getGrafo(){
		return graph;
	}

	public TopPlayer getTopPlayer() {
		if(graph == null)
			return null;
		
		Player best = null;
		Integer maxDegree = Integer.MIN_VALUE;
		for(Player p : graph.vertexSet()) {
			if(graph.outDegreeOf(p) > maxDegree) {
				maxDegree = graph.outDegreeOf(p);
				best = p;
			}
		}
		
		TopPlayer topPlayer = new TopPlayer();
		topPlayer.setPlayer(best);
		
		List<Opponent> opponents = new ArrayList<>();
		for(DefaultWeightedEdge edge : graph.outgoingEdgesOf(topPlayer.getPlayer())) {
			opponents.add(new Opponent(graph.getEdgeTarget(edge), (int) graph.getEdgeWeight(edge)));
		}
		Collections.sort(opponents);
		topPlayer.setOpponents(opponents);
		return topPlayer;
		
	}
	
	public List<Player> getDreamTeam(int k){
		this.bestDegree = 0;
		this.dreamTeam = new ArrayList<Player>();
		List<Player> partial = new ArrayList<Player>();
		
		this.recursive(partial, new ArrayList<Player>(this.graph.vertexSet()), k);

		return dreamTeam;
	}
	
	public void recursive(List<Player> partial, List<Player> players, int k) {
		if(partial.size() == k) {
			int degree = this.getDegree(partial);
			if(degree > this.bestDegree) {
				dreamTeam = new ArrayList<>(partial);
				bestDegree = k;
			}
			return;
		}
		
		for(Player p : players) {
			if(!dreamTeam.contains(p)) {
				partial.add(p);
				//i "battuti" di p non possono più essere considerati
				List<Player> remainingPlayers = new ArrayList<>(players);
				remainingPlayers.removeAll(Graphs.successorListOf(graph, p));
				recursive(partial, remainingPlayers, k);
				partial.remove(p);
				
			}
		}
	}
	
	private int getDegree(List<Player> team) {
		int degree = 0;
		int in;
		int out;

		for(Player p : team) {
			in = 0;
			out = 0;
			for(DefaultWeightedEdge edge : this.graph.incomingEdgesOf(p))
				in += (int) this.graph.getEdgeWeight(edge);
			
			for(DefaultWeightedEdge edge : graph.outgoingEdgesOf(p))
				out += (int) graph.getEdgeWeight(edge);
		
			degree += (out-in);
		}
		return degree;
	}

	public Integer getBestDegree() {
		return bestDegree;
	}
}
