package main;

import java.util.ArrayList;
import java.util.List;

public class FortniteClass {
	private List<Player> players;

	public FortniteClass() {
		players = new ArrayList<>();
	}

	public boolean addPlayer(Player player) {
		if (players.size() >= 5) {
			return false;
		}

		if (players.contains(player)) {
			return false;
		}

		players.add(player);
		return true;
	}

	public boolean removePlayer(Player player) {
		if (players.size() <= 1) {
			return false;
		}

		if (players.contains(player)) {
			players.remove(player);
			return true;
		} else {			
			return false;
		}
	}

	public void printPlayerList() {
		if (players.isEmpty()) {
			System.out.println("No hay jugadores en la lista.");
		} else {
			System.out.println("Lista de jugadores:");
			for (Player player : players) {
				System.out.println(player.toString());
			}
		}
	}

	public int getPlayerCount() {
		return players.size();
	}

}
