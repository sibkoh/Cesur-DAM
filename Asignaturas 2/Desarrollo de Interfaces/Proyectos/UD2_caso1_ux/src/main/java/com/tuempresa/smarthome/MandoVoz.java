package com.tuempresa.smarthome;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

import org.vosk.Model;
import org.vosk.Recognizer;

public class MandoVoz {

	public static void main(String[] args) throws Exception {
		System.out.println("Comenzando a procesar audio...");
		MandoTelevision mando = new MandoTelevision();

		// LibVosk.setLogLevel(0);
		Model model = new Model("src/main/resources/modelos_vosk/es"); // modelo español
		Recognizer recognizer = new Recognizer(model, 16000);

		AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
		TargetDataLine microphone = AudioSystem.getTargetDataLine(format);
		microphone.open(format);
		microphone.start();

		byte[] buffer = new byte[4096];
		while (true) {
			int bytesRead = microphone.read(buffer, 0, buffer.length);
			if (bytesRead < 0)
				break;

			if (recognizer.acceptWaveForm(buffer, bytesRead)) {
				String result = recognizer.getResult();
				if (result.contains("subir volumen")) {
					mando.subirVolumen();
				} else if (result.contains("bajar volumen")) {
					mando.bajarVolumen();
				}
			}
		}
		microphone.close();
		recognizer.close();
		model.close();
	}
}
