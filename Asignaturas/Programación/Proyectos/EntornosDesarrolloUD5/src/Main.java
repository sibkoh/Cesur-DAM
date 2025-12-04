package main;

public class Main {

	public static void main(String[] args) {
		FortniteClass fortnite = new FortniteClass();

        Player player1 = new Player("John", 10, 5);
        Player player2 = new Player("Jane", 12, 7);
        Player player3 = new Player("Bob", 8, 4);
        Player player4 = new Player("John", 9, 6);  // Jugador duplicado
        Player player5 = new Player("Mike", 11, 8);

        fortnite.addPlayer(player1);
        fortnite.addPlayer(player2);
        fortnite.addPlayer(player3);
        fortnite.addPlayer(player4);
        fortnite.addPlayer(player5);  // Intentar agregar más allá del límite
        fortnite.printPlayerList();

        fortnite.removePlayer(player2);
        fortnite.removePlayer(player4);  // Intentar eliminar un jugador que no está en la lista
        fortnite.printPlayerList();

        int playerCount = fortnite.getPlayerCount();
        System.out.println("Cantidad de jugadores: " + playerCount);

	}

}
