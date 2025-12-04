package com.tuempresa.smarthome;

public class MandoTelevision {
	private int volumen;

	// Constructor
	public MandoTelevision() {
		this.volumen = 0;
	}

	// Método para subir el volumen
	public void subirVolumen() {
		this.volumen++;
		System.out.println("Volumen actual: " + this.volumen);
	}

	// Método para bajar el volumen
	public void bajarVolumen() {
		if (this.volumen > 0) {
			this.volumen--;
			System.out.println("Volumen actual: " + this.volumen);
		} else {
			System.out.println("El volumen ya está en 0 y no se puede bajar más.");
		}
	}

	// Método para obtener el volumen actual
	public int obtenerVolumen() {
		return this.volumen;
	}
}
