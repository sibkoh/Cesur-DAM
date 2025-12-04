package com.tuempresa.smarthome;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

//@SuppressWarnings("resource")
public class DeteccionMovimientoAR {

	public static void main(String[] args) throws FrameGrabber.Exception {
		MandoTelevision mando = new MandoTelevision();
		OpenCVFrameConverter.ToMat convertidor = new OpenCVFrameConverter.ToMat();
		VideoCapture camara = new VideoCapture(0);

		if (!camara.isOpened()) {
			System.out.println("No se puede abrir la cámara");
			return;
		}

		Mat frame = new Mat(), anterior = new Mat(), diferencia = new Mat();
		camara.read(anterior);
		opencv_imgproc.cvtColor(anterior, anterior, opencv_imgproc.COLOR_BGR2GRAY);

		CanvasFrame lienzo = new CanvasFrame("Detección de Movimiento AR");
		lienzo.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

		// Contadores para filtrado temporal
		int framesBrazoIzq = 0;
		int framesBrazoDer = 0;
		int minFramesParaAccion = 3;

		try {
			while (lienzo.isVisible() && camara.read(frame)) {
				opencv_imgproc.cvtColor(frame, frame, opencv_imgproc.COLOR_BGR2GRAY);
				opencv_core.absdiff(anterior, frame, diferencia);
				opencv_imgproc.threshold(diferencia, diferencia, 25, 255, opencv_imgproc.THRESH_BINARY);

				MatVector contornos = new MatVector();
				opencv_imgproc.findContours(diferencia, contornos, new Mat(), opencv_imgproc.RETR_EXTERNAL,
						opencv_imgproc.CHAIN_APPROX_SIMPLE);

				boolean brazoIzq = false, brazoDer = false;

				int ancho = frame.cols();
				int alto = frame.rows();
				int limiteY = alto / 2; // Solo mitad superior (brazo levantado)

				for (int i = 0; i < contornos.size(); i++) {
					Rect rect = opencv_imgproc.boundingRect(contornos.get(i));
					int centroX = rect.x() + rect.width() / 2;
					int centroY = rect.y() + rect.height() / 2;

					// Solo contornos grandes en la mitad superior
					if (centroY < limiteY && rect.height() > 50 && rect.width() > 20) {
						if (centroX < ancho / 2)
							brazoIzq = true; // brazo izquierdo levantado
						else
							brazoDer = true; // brazo derecho levantado
					}
				}

				// Filtrado temporal
				if (brazoIzq)
					framesBrazoIzq++;
				else
					framesBrazoIzq = 0;
				if (brazoDer)
					framesBrazoDer++;
				else
					framesBrazoDer = 0;

				if (framesBrazoIzq >= minFramesParaAccion) {
					mando.subirVolumen();
					framesBrazoIzq = 0;
				}

				if (framesBrazoDer >= minFramesParaAccion) {
					mando.bajarVolumen();
					framesBrazoDer = 0;
				}

				lienzo.showImage(convertidor.convert(frame));
				anterior = frame.clone();
			}
		} finally {
			camara.release();
			lienzo.dispose();
		}
	}
}
