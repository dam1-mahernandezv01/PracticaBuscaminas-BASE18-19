import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * <p>Clase ventana principal donde declaramos e instanciamos los componentes de la 
 * ventana principal, en el método {@link #inicializar()} es importante que primero hacemos
 * la ventana visible y luego inicializamos los componentes <br>{@code ventana.setVisible(true);}<br>
 * {@code inicializarComponentes();}<br>{@code inicializarListeners();}</p>
 * 
 * @author Jesus Redondo Garcia
 * @author Marco Antonio Hernandez Valiente
 * @version 1.0
 * @since 1.0
 * @see ActionBoton
 * @see ControlJuego
 * @see Principal
 */
public class VentanaPrincipal {
	//La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;
	
	//Todos los botones se meten en un panel independiente.
	//Hacemos esto para que podamos cambiar despues los componentes por otros
	JPanel [][] panelesJuego;
	JButton [][] botonesJuego;
	
	//Correspondencia de colores para las minas:
	Color correspondenciaColores [] = {Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED};
	
	JButton botonEmpezar;
	JTextField pantallaPuntuacion;	
	
	//LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;	
	
	/**
	 * Constructor, marca el tamaño y el cierre del frame
	 */
	public VentanaPrincipal() {
		ventana = new JFrame("Buscaminas de Marco Antonio Hernández");
		ventana.setBounds(100, 100, ControlJuego.LADO_TABLERO*50+200, ControlJuego.LADO_TABLERO*50+100);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}
	
	/**
	 * Inicializa todos los componentes del frame
	 */
	public void inicializarComponentes(){		
		//Definimos el layout:
		ventana.setLayout(new GridBagLayout());
		
		//Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1,1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1,1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(ControlJuego.LADO_TABLERO, ControlJuego.LADO_TABLERO));		
		
		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));		
			
		//Colocamos los componentes:
		//AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		//VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		//AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		//ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);
		
		//Paneles
		panelesJuego = new JPanel[ControlJuego.LADO_TABLERO][ControlJuego.LADO_TABLERO];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1,1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}
		
		//Botones
		botonesJuego = new JButton[ControlJuego.LADO_TABLERO][ControlJuego.LADO_TABLERO];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}
		
		//BotonEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);		
	}
	
	/**
	 * Metodo que inicializa todos los listeners que necesita inicialmente el programa
	 */
	public void inicializarListeners(){
		botonEmpezar.addActionListener((e) -> {
				ventana.remove(panelJuego);
				ventana.remove(panelEmpezar);
				ventana.remove(panelImagen);
				ventana.remove(panelPuntuacion);				
				juego = new ControlJuego();		
				inicializar();
				refrescarPantalla();
			}
		);
		
		for (int ejeY = 0; ejeY < botonesJuego.length; ejeY++) {
			for (int ejeX = 0; ejeX < panelesJuego[ejeY].length; ejeX++) {
				ActionBoton actionBoton = new ActionBoton(this, ejeY, ejeX);
				botonesJuego[ejeY][ejeX].addActionListener(actionBoton);
			}
		}
	}	
	
	/**
	 * Pinta en la pantalla el numero de minas que hay alrededor de la celda
	 * Saca el boton que haya en la celda determinada y añade un JLabel centrado y no editable con el numero de minas alrededor.
	 * Se pinta el color del texto segÃºn la siguiente correspondecia (consultar la variable correspondeciaColor):
	 * <ul>
	 * 		<li>0 : negro</li>
	 * 		<li>1 : cyan</li>
	 * 		<li>2 : verde</li>
	 * 		<li>3 : naranja</li>
	 * 		<li>o mas : rojo</li>
	 * </ul>
	 * 
	 * @param i: posicion vertical de la celda.
	 * @param j: posicion horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i , int j) {
		int ejeY = i, ejeX = j;

		JLabel numMinas = new JLabel(String.valueOf(juego.getMinasAlrededor(ejeY, ejeX)));
		numMinas.setHorizontalAlignment(SwingConstants.CENTER);	
		
		if (juego.getMinasAlrededor(ejeY, ejeX) > -1) {
			numMinas.setForeground(correspondenciaColores[juego.getMinasAlrededor(ejeY, ejeX)]);		
		} else {
			numMinas.setBackground(Color.RED);
			numMinas.setOpaque(true);
		}
		
		panelesJuego[ejeY][ejeX].removeAll();
		panelesJuego[ejeY][ejeX].add(numMinas);		
		refrescarPantalla();
	}
	
	
	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param porExplosion: Un booleano que indica si es final del juego porque ha explotado una mina (true)
	 * 						 o bien porque hemos desactivado todas (false) 
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {		
		if (porExplosion) {
			JOptionPane.showMessageDialog(ventana, "Lo siento, sigue intentándolo", "PERDISTE", 0);
		} else {
			JOptionPane.showMessageDialog(ventana, "Enhorabuena has ganado", "GANASTE", 1);
		}	
		
		for (int ejeY = 0; ejeY < botonesJuego.length; ejeY++) {
			for (int ejeX = 0; ejeX < botonesJuego[ejeY].length; ejeX++) {
				mostrarNumMinasAlrededor(ejeY, ejeX);
				botonesJuego[ejeY][ejeX].setEnabled(false);
			}
		}
	}

	/**
	 * Metodo que muestra la puntuacion por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(String.valueOf(juego.getPuntuacion()));
	}
	
	/**
	 * Metodo para refrescar la pantalla
	 */
	public void refrescarPantalla(){
		ventana.revalidate(); 
		ventana.repaint();
	}

	/**
	 * Metodo que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Metodo para inicializar el programa
	 */
	public void inicializar(){
		//IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();	
		inicializarListeners();		
	}
}
