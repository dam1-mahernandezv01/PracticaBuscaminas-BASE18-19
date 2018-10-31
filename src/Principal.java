import java.awt.EventQueue;

/**
 * Clase principal
 * 
 * @author Jesus Redondo Garcia
 * @version 1.0
 * @since 1.0
 * @see ActionBoton
 * @see ControlJuego
 * @see VentanaPrincipal
 */
public class Principal {

	/**
	 * Metodo main
	 * @param args : Cadenas de parametros del main
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal ventana = new VentanaPrincipal();
					ventana.inicializar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
