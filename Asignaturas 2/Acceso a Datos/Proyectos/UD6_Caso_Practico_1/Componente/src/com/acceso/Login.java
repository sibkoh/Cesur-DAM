package com.acceso;

// Importamos AWT (Abstract Window Toolkit). Aunque usamos Swing para los botones y cajas, 
// AWT sigue siendo el motor subyacente para manejar colores (Color), dimensiones (Dimension), 
// fuentes tipográficas (Font) y alineaciones (Component).
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
// Importamos los escuchadores de eventos de acción. Sin esto, nuestros botones 
// serían simples dibujos en pantalla que no harían nada al hacer clic sobre ellos.
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// ------------------------------------

// Importamos la librería Swing al completo. Swing es la evolución de AWT y nos 
// proporciona componentes gráficos "ligeros" (lightweight) dibujados completamente en Java.
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
// Importamos EmptyBorder para poder aplicar márgenes invisibles (padding) a nuestros paneles.
// Esto es vital para que la interfaz "respire" y no se vea agobiante.
import javax.swing.border.EmptyBorder;
// Estos eventos específicos de documentos (DocumentEvent y DocumentListener) son necesarios 
// para nuestro medidor de seguridad. Nos permiten "escuchar" cada pulsación de tecla 
// en tiempo real dentro del campo de la contraseña.
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Componente visual reutilizable para el Acceso al Sistema. Decidimos extender
 * de la clase JFrame para que este componente actúe como una ventana
 * independiente y autosuficiente. De este modo, cualquier compañero del equipo
 * de desarrollo solo tendrá que instanciar la clase para usarla.
 */
public class Login extends JFrame {

	// --- VARIABLES DE CLASE (COMPONENTES VISUALES) ---
	// Agrupamos los elementos estándar solicitados por la rúbrica.
	private JTextField txtUsuario;
	private JPasswordField txtPassword; // Usamos JPasswordField para ocultar los caracteres por seguridad
	private JButton btnEntrar;
	private JButton btnRegistrar;

	// Agrupamos nuestros 3 componentes adicionales que aportan el valor añadido
	// (Innovación).
	private JComboBox<String> comboRol; // 1. Selector de perfil de acceso
	private JCheckBox chkMostrarPass; // 2. Control interactivo de visibilidad de contraseña
	private JProgressBar progressSeguridad; // 3. Medidor visual de la fuerza de la contraseña

	/**
	 * CONSTRUCTOR DEL COMPONENTE Aquí inicializamos y ensamblamos toda la interfaz
	 * gráfica en el momento en que se hace el 'new Login()'.
	 */
	public Login() {
		// 1. Configuración del marco principal (Ventana)
		setTitle("Acceso al Sistema - Corporativo");
		setSize(380, 420); // Dimensiones probadas para un diseño vertical equilibrado
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Finaliza el proceso en memoria al cerrar
		setLocationRelativeTo(null); // Centra la ventana automáticamente en el monitor
		setResizable(false); // Bloqueamos el redimensionamiento para evitar que se rompa el diseño

		// 2. Creación del panel principal (El lienzo)
		JPanel panelPrincipal = new JPanel();
		// Usamos BoxLayout en el eje Y (vertical) para apilar los elementos de arriba a
		// abajo
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		// Aplicamos el padding (arriba, izquierda, abajo, derecha) para evitar que los
		// elementos toquen los bordes
		panelPrincipal.setBorder(new EmptyBorder(20, 25, 20, 25));
		panelPrincipal.setBackground(new Color(245, 245, 250)); // Un gris muy claro, más moderno que el blanco puro

		// 3. Título de bienvenida
		JLabel lblTitulo = new JLabel("Bienvenido al Sistema");
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Tipografía corporativa y limpia
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitulo.setForeground(new Color(40, 40, 60));

		panelPrincipal.add(lblTitulo);
		// Usamos RigidArea para crear separaciones físicas (márgenes) entre componentes
		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

		// --- SECCIÓN 1: NOMBRE DE USUARIO ---
		JLabel lblUsuario = new JLabel("Nombre de Usuario:");
		lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT); // Alineación a la izquierda

		txtUsuario = new JTextField();
		// Limitamos el alto de la caja de texto, pero dejamos que ocupe todo el ancho
		// disponible
		txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		panelPrincipal.add(lblUsuario);
		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
		panelPrincipal.add(txtUsuario);
		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

		// --- SECCIÓN 2: CONTRASEÑA ---
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

		txtPassword = new JPasswordField();
		txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		panelPrincipal.add(lblPassword);
		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
		panelPrincipal.add(txtPassword);

		// --- SECCIÓN 3: NUESTROS COMPONENTES ADICIONALES DE INNOVACIÓN ---

		// [Componente Extra 1]: CheckBox para mostrar/ocultar contraseña
		// Añade un control de accesibilidad muy valorado por los usuarios hoy en día.
		chkMostrarPass = new JCheckBox("Mostrar contraseña");
		chkMostrarPass.setBackground(new Color(245, 245, 250)); // Mismo fondo que el panel para que se integre
		chkMostrarPass.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		// Añadimos lógica dinámica al CheckBox mediante una función Lambda
		chkMostrarPass.addActionListener(e -> {
			if (chkMostrarPass.isSelected()) {
				txtPassword.setEchoChar((char) 0); // Al pasar el carácter 0 (nulo), mostramos el texto real
			} else {
				txtPassword.setEchoChar('•'); // Al desmarcarlo, volvemos a poner el punto de ocultación
			}
		});
		panelPrincipal.add(chkMostrarPass);

		// [Componente Extra 2]: Barra de progreso interactiva (Fuerza de contraseña)
		progressSeguridad = new JProgressBar(0, 100);
		progressSeguridad.setStringPainted(true); // Permitimos que la barra muestre texto superpuesto
		progressSeguridad.setString("Fuerza de la contraseña");
		progressSeguridad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 15));

		// Añadimos un "Listener" al documento interno del campo de contraseña.
		// Así podemos reaccionar a cada tecla que pulsa o borra el usuario en tiempo
		// real.
		txtPassword.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				evaluarPassword();
			}

			public void removeUpdate(DocumentEvent e) {
				evaluarPassword();
			}

			public void changedUpdate(DocumentEvent e) {
				evaluarPassword();
			}
		});

		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
		panelPrincipal.add(progressSeguridad);
		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

		// [Componente Extra 3]: JComboBox (Lista desplegable de Roles)
		JLabel lblRol = new JLabel("Perfil de Acceso:");
		// Inicializamos el desplegable con un array de Strings predefinido
		comboRol = new JComboBox<>(new String[] { "Administrador", "Empleado (Estándar)", "Invitado / Temporal" });
		comboRol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		panelPrincipal.add(lblRol);
		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
		panelPrincipal.add(comboRol);
		panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

		// --- SECCIÓN 4: BOTONERA ---
		// Usamos un nuevo panel secundario con GridLayout (1 fila, 2 columnas)
		// para que ambos botones midan exactamente lo mismo.
		JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
		panelBotones.setBackground(new Color(245, 245, 250));

		btnEntrar = new JButton("Iniciar Sesión");
		btnEntrar.setBackground(new Color(60, 120, 216)); // Azul corporativo
		btnEntrar.setForeground(Color.WHITE);
		btnEntrar.setFocusPainted(false); // Eliminamos el feo borde punteado nativo de Java al hacer clic

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBackground(new Color(40, 167, 69)); // Verde para indicar creación/alta
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setFocusPainted(false);

		// --- REQUISITO ESPECÍFICO DEL CASO PRÁCTICO ---
		// El botón Registrar debe mostrar un mensaje concreto. Lo implementamos aquí.
		btnRegistrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Invocamos el cuadro de diálogo estándar de información
				JOptionPane.showMessageDialog(Login.this, "Ha pulsado el botón Registrar", // Mensaje exacto requerido
						"Información de Registro", // Título de la ventanita
						JOptionPane.INFORMATION_MESSAGE); // Icono de información (la 'i' azul)
			}
		});

		// Ensamblamos los botones en su panel secundario
		panelBotones.add(btnEntrar);
		panelBotones.add(btnRegistrar);

		// Añadimos la botonera al panel vertical principal
		panelPrincipal.add(panelBotones);

		// Finalmente, añadimos todo el lienzo ensamblado a nuestra ventana JFrame y la
		// hacemos visible
		add(panelPrincipal);
		setVisible(true);
	}

	/**
	 * Método lógico auxiliar. Lee la longitud de la contraseña actual y actualiza
	 * el color y valor de la barra de progreso. Decidimos separarlo en un método
	 * privado para mantener el código limpio e instanciarlo fácilmente.
	 */
	private void evaluarPassword() {
		String pass = new String(txtPassword.getPassword()); // Extraemos la clave de forma segura

		if (pass.length() == 0) {
			progressSeguridad.setValue(0);
			progressSeguridad.setForeground(Color.GRAY);
		} else if (pass.length() < 5) {
			progressSeguridad.setValue(30);
			progressSeguridad.setForeground(Color.RED); // Insegura
		} else if (pass.length() < 8) {
			progressSeguridad.setValue(60);
			progressSeguridad.setForeground(Color.ORANGE); // Nivel medio
		} else {
			progressSeguridad.setValue(100);
			progressSeguridad.setForeground(new Color(40, 167, 69)); // Segura (Verde)
		}
	}
}