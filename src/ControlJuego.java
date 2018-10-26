import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Clase gestora del tablero de juego.
 * Guarda una matriz de enteros representado el tablero.
 * Si hay una mina en una posicion guarda el numero -1
 * Si no hay una mina, se guarda cuantas minas hay alrededor.
 * Almacena la puntuacion de la partida
 * 
 * @author jesusredondogarcia
 * @author Marco Antonio Hernandez Valiente
 */
public class ControlJuego {	
	private final static int MINA = -1;
	static final int LADO_TABLERO = pedirDimension();
	static final int MINAS_INICIALES = pedirMinas();

	private int [][] tablero;
	private int puntuacion;	
	
	public ControlJuego() {
		//Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];
		
		//Inicializamos una nueva partida
		inicializarPartida();
	}
	
	// Funciones para pedir las variables
	private static int pedirDimension() {
		int dimension = 0;
		try {
			dimension = Integer.parseInt(JOptionPane.showInputDialog(null, "¿Dimensión del cuadrado?"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Debe introducir números");
			pedirDimension();
		}		
		return dimension;
	}

	private static int pedirMinas() {
		int minas = 0;
		try {
			minas = Integer.parseInt(JOptionPane.showInputDialog(null, "¿Número de minas?"));
			if (minas>=Math.pow(LADO_TABLERO, 2)-1) {
				JOptionPane.showMessageDialog(null, "No puedes asignar tantas minas en estas dimensiones, máximo " 
						+ (Math.floor(Math.pow(LADO_TABLERO, 2)-1)));
				
				pedirMinas();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Debe introducir números");
			pedirMinas();
		}		
		return minas;
	}

	/**
	 * Metodo para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir. 
	 * @post: Al final el tablero se habra inicializado con tantas minas como marque la variable MINAS_INICIALES. 
	 * 			El resto de posiciones que no son minas guardan en el entero cuantas minas hay alrededor de la celda
	 */
	public void inicializarPartida(){
		//TODO: Repartir minas e inicializar puntación. Si hubiese un tablero anterior, lo pongo todo a cero para inicializarlo.
		puntuacion = 0;
		Random rd = new Random();
		int minas;
		
		for (minas = 0; minas < MINAS_INICIALES; minas++) {		
			int ejeX, ejeY;
			ejeX = rd.nextInt(LADO_TABLERO);
			ejeY = rd.nextInt(LADO_TABLERO);
			if (tablero[ejeY][ejeX] != MINA) {
				tablero[ejeY][ejeX] = MINA;
			} else {
				minas--;	
			}			
		}		
		
		//Al final del método hay que guardar el número de minas para las casillas que no son mina:
		for (int ejeY = 0; ejeY < tablero.length; ejeY++) {
			for (int ejeX = 0; ejeX < tablero[ejeY].length; ejeX++) {
				if (tablero[ejeY][ejeX] == MINA){
					calculoMinasAdjuntas(ejeY,ejeX);
				}
			}
		}
		
		depurarTablero();
	}
	
	/**
	 * Calculo de las minas adjuntas: 
	 * Para calcular el numero de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
	 * Por lo tanto, como mucho la i y la j valdran LADO_TABLERO-1.
	 * Por lo tanto, como poco la i y la j valdran 0.
	 * 
	 * @param i: posicion vertical de la casilla a rellenar
	 * @param j: posicion horizontal de la casilla a rellenar
	 * @return : El numero de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j){
		for (int ejeY = Math.max(0, i-1); ejeY <= Math.min(i+1, LADO_TABLERO-1); ejeY++) {
			for (int ejeX = Math.max(0, j-1); ejeX <= Math.min(j+1, LADO_TABLERO-1); ejeX++) {
				if (tablero[ejeY][ejeX] != MINA) {
					tablero[ejeY][ejeX] += 1;
				}
			}
		}
		return tablero[i][j];
	}
	
	/**
	 * Metodo que nos permite 
	 * 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posicion verticalmente de la casilla a abrir
	 * @param j: posicion horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j){
		int ejeY = i, ejeX = j;
		
		if (tablero[ejeY][ejeX] != MINA) {
			java.awt.Toolkit.getDefaultToolkit().beep(); // sonido
			puntuacion++;
			return true;
		} else {
			return false;
		}
	}	
	
	/**
	 * Metodo que checkea si se ha terminado el juego porque se han abierto todas las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
	 **/
	public boolean esFinJuego(){
		return (puntuacion == (Math.pow(LADO_TABLERO, 2) - MINAS_INICIALES) ? true: false);
	}	
	
	/**
	 * Metodo que pinta por pantalla toda la informacion del tablero, se utiliza para depurar
	 */
	public void depurarTablero(){
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuacion: " + puntuacion);
	}

	/**
	 * Metodo que se utiliza para obtener las minas que hay alrededor de una celda
	 * 
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta calcularlo, sÃ­mplemente consultarlo
	 * @param i : posicion vertical de la celda.
	 * @param j : posicion horizontal de la cela.
	 * @return Un entero que representa el numero de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		int ejeY = i, ejeX = j;
		return tablero[ejeY][ejeX];
	}

	/**
	 * Metodo que devuelve la puntuacion actual
	 * 
	 * @return Un entero con la puntuacion actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}	
}
