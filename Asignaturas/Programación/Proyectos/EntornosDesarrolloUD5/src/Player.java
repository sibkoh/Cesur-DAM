package main;

public class Player {
	private String name;
	private int speed;
	private int power;

	public Player(String name, int speed, int power) {
		this.name = name;
		this.speed = speed;
		this.power = power;
	}

	public String getName() {
		return name;
	}

	public int getSpeed() {
		return speed;
	}

	public int getPower() {
		return power;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Player player = (Player) obj;
		return name.equals(player.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public Player clone() {
		return new Player(this.name, this.speed, this.power);
	}

	@Override
	public String toString() {
		return "Nombre: " + name + ", Velocidad: " + speed + ", Potencia: " + power;
	}
}
