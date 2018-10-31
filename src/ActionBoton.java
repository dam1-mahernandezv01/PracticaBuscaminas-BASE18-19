import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Clase que implementa el listener de los botones del Buscaminas.</p>
 * <p>De alguna manera tendra que poder acceder a la ventana principal.</p>
 * <p>Se puede lograr pasando en el constructor la referencia a la ventana.</p>
 * <p>Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego</p>
 * 
 * @author Jesus Redondo Garcia
 * @author Marco Antonio Hernandez Valiente
 * @version 1.0
 * @since 1.0
 * @see ControlJuego
 * @see Principal
 * @see VentanaPrincipal
 */
public class ActionBoton implements ActionListener{
	private VentanaPrincipal ventanaPrincipal;
	private int ejeY;
	private int ejeX;

	public ActionBoton() {}

	public ActionBoton(VentanaPrincipal ventanaPrincipal, int ejeY, int ejeX) {
		this.ventanaPrincipal = ventanaPrincipal;
		this.ejeY = ejeY;
		this.ejeX = ejeX;
	}
	
	/**
	 *Accion que ocurrira cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ventanaPrincipal.mostrarNumMinasAlrededor(ejeY, ejeX);		
		if (!ventanaPrincipal.getJuego().abrirCasilla(ejeY, ejeX)) {			
			ventanaPrincipal.mostrarFinJuego(true);
		} 		
		if (ventanaPrincipal.getJuego().esFinJuego()) {			
			ventanaPrincipal.mostrarFinJuego(false);
		} 
		ventanaPrincipal.actualizarPuntuacion();
	}
}
