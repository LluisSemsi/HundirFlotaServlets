package partidas;

import java.util.Random;
import java.util.Vector;

public class Partida {
	
	private static final int AGUA = -1, TOCADO = -2, HUNDIDO = -3;
	
	/**
	 * El mar se representa mediante una matriz de casillas
	 * En una casilla no tocada con un barco se guarda el indice del barco en el vector de barcos
	 * El resto de valores posibles (AGUA, TOCADO y HUNDIDO) se representan mediante 
	 * constantes enteras negativas.
	 */
	private int mar[][] = null;				// matriz que contendra el mar y los barcos en distintos estados
	private int numFilas, 					// numero de filas del tablero
				numColumnas;				// numero de columnas del tablero
	private Vector<Barco> barcos = null;	// vector dinamico de barcos
	private int numBarcos, 					// numero de barcos de la partida
	            quedan,  					// numero de barcos no hundidos
	            disparos; 					// numero de disparos efectuados
	
	private boolean[][] misDisparos;		// indica sobre qué casillas hemos disparado
	
	/**
	 * Contructor por defecto. No hace nada
	 */
	public Partida() {}
	
	/**
	 * Constructor de una partida
	 * @param	nf	numero de filas del tablero
	 * @param   nf  numero de columnas del tablero
	 * @param   nc  numero de barcos
	 */
	public Partida(int nf, int nc, int nb) {
       //Inicializamos las variables
	   numBarcos = nb;
	   quedan = nb;
	   disparos = 0;
       numFilas = nf;
       numColumnas = nc;
       
       
       //Inicializamos el mar con agua
       iniciaMar(nf, nc);
       
       //Inicializamos la matriz de booleanos misDisparos
       iniciaDisparos(nf, nc);
       
       //Inicializamos los barcos
       barcos = new Vector<Barco>();
       ponBarcos();
       
	}
	
	/**
	 * Dispara sobre una casilla y devuelve el resultado
	 * @param	f	fila de la casilla
	 * @param   c   columna de la casilla
	 * @return		resultado de marcar la casilla: AGUA, ya TOCADO, ya HUNDIDO, identidad del barco recien hundido
	 */	
    public int pruebaCasilla(int f, int c) {
    	
    	int casilla = mar[f][c];
    	disparos++;
    	misDisparos[f][c] = true;
    	//Comprobamos si es necesario actualizar el contenido de la matriz (en caso de acertar un barco)
    	if (casilla >= 0) {
    		Barco b = barcos.get(casilla);
    		b.tocado();
    		//Si hundimos el barco...
    		if (b.getTamanyo() == b.getTocadas()) {    	
    			int fi = b.getFilaInicial();
    			int ci = b.getColumnaInicial();
    			int tam= b.getTamanyo();
    			char ori= b.getOrientacion();
    			
    			if (ori == 'H') {						
    				for(int i=0;i<tam;i++) {
    					mar[fi][ci+i] = HUNDIDO;
    				}
    			} else {
    				for(int i=0;i<tam;i++) {
    					mar[fi+i][ci] = HUNDIDO;
    				}
    			}
    			quedan--;
    			return casilla;
    			
    		} else
    			return mar[f][c] = TOCADO;
    	}
    	
        //De lo contrario, devolvemos la casilla (no pasa nada)
        return AGUA;
    }
    
    
	/**
	 * Devuelve una cadena con los datos de un barco dado: filIni, colIni, orientacion, tamanyo
	 * Los datos se separan con el caracter especial '#'
	 * @param	idBarco	indice del barco en el vector de barcos
	 * @return	        cadena con los datos del barco
	 */	
	public String getBarco(int idBarco) {
        return barcos.get(idBarco).toString();
	}
	
	/**
	 * Devuelve un vector de cadenas con los datos de todos los barcos
	 * @return	vector de cadenas, una por barco con la informacion de getBarco
	 */	
	public String[] getSolucion() {
        String[] solucion = new String[barcos.size()];
        int i=0;
        for(Barco b:barcos)
        	solucion[i++] = b.toString();
        
        return solucion;
	}
	
	
	public boolean casillaDisparada(int f, int c) {
		return misDisparos[f][c];
	}
	
	public int getCasilla(int f, int c) {
		return mar[f][c];
	}
	
	public int getDisparos() {
		return disparos;
	}
	
	public int getNumBarcos() {
		return numBarcos;
	}
	
	public int getQuedan() {
		return quedan;
	}
	
	public int getFilas() {
		return numFilas;
	}
	
	public int getColumnas() {
		return numColumnas;
	}
    

	/********************************    METODOS PRIVADOS  ********************************************/
    
	private void iniciaMar(int nf, int nc) {
		mar = new int[nf][nc];
		for(int f=0;f<nf;f++) {
			for(int c=0;c<nc;c++) {
				mar[f][c] = AGUA;
			}
	    }
	}
	
	// Inicializamos la matriz de booleanos a false
	private void iniciaDisparos(int nf, int nc) {
		misDisparos = new boolean[nf][nc];
		for(int f=0;f<nf;f++) {
			for(int c=0;c<nc;c++) {
				misDisparos[f][c] = false;
			}
		}
	}
	
	/**
	 * Coloca los barcos en el tablero
	 */	
	private void ponBarcos() {
		/* Por defecto colocamos un barco de tamaño 4, uno de tamaño 3, otro de tamaño 2 y tres barcos de tamaño 1 */
		
		/*barcos.add( ponBarco(0, 4) );
		barcos.add( ponBarco(1, 3) );
		barcos.add( ponBarco(2, 2) );
		barcos.add( ponBarco(3, 1) );
		barcos.add( ponBarco(4, 1) );
		barcos.add( ponBarco(5, 1) );*/
		
		barcos.add( ponBarco(0, 5) );
		barcos.add( ponBarco(1, 4) );
		barcos.add( ponBarco(2, 3) );
		barcos.add( ponBarco(3, 3) );
		barcos.add( ponBarco(4, 2) );
		barcos.add( ponBarco(5, 2) );
	}
	
	
	/**
	 * Busca hueco para un barco y lo coloca en el tablero.
	 * @param  id   indice del barco en el vector de barcos
	 * @param  tam  tamanyo del barco
	 * @return      un barco guardado como un objeto Barco
	 */	
	private Barco ponBarco(int id, int tam) {
        char orientacion=' ';
        boolean ok = false;
        int fila = 0, col = 0;
        Random random = new Random(); // Para generar aleatoriamente la orientacion y posicion de los barcos
        
        // Itera hasta que encuentra hueco para colocar el barco cumpliendo las restricciones
        while (!ok) {
        	// Primero genera aleatoriamente la orientacion del barco
            if (random.nextInt(2) == 0) { // Se dispone horizontalmente
            	// Ahora genera aleatoriamente la posicion del barco
                col = random.nextInt(numColumnas + 1 - tam); // resta tam para asegurar que cabe
                fila = random.nextInt(numFilas);

                // Comprueba si cabe a partir de la posicion generada con mar o borde alrededor
                if (librePosiciones(fila, col, tam+1, 'H')) {
                	// Coloca el barco en el mar
                    for (int i = 0; i < tam; i++) {
                        mar[fila][col+i] = id;
                    }
                    ok = true;
                    orientacion = 'H';
                }
            }
            else { //Se dispone verticalmente
                fila = random.nextInt(numFilas + 1 - tam);
                col = random.nextInt(numColumnas);
                if (librePosiciones(fila, col, tam+1, 'V')) {
                    for (int i = 0; i < tam; i++) {
                        mar[fila + i][col] = id;
                    }
                    ok = true;
                    orientacion = 'V';
                }
            } // end if H o V
        } // end while
        return new Barco(fila, col, orientacion, tam);
	}
	
	/**
	 * Comprueba si hay hueco para un barco a partir de una casilla inicial.
	 * Los barcos se colocan dejando una casilla de hueco con los otros.
	 * Pueden pegarse a los bordes.
	 * @param  fila   fila de la casilla inicial
	 * @param  col    columna de la casilla inicial
	 * @param  tam    tamanyo del barco + 1 para dejar hueco alrededor
	 * @param  ori    orientacion del barco: 'H' o 'V'
	 * @return        true si se encuentra hueco, false si no.
	 */	
    private boolean librePosiciones(int fila, int col, int tam, char ori) {
    	int i;
        if (ori == 'H') {
        	i = ( (col > 0) ? -1 : 0 );
        	// Comprueba que "cabe" horizontalmente a partir de la columna dada.
        	// Esto implica que:
        	// haya 'tam' casillas vacias (con mar) en la fila 'fila'
        	// y que quede rodeado por el mar o por un borde
        	while ( (col+i < numColumnas) && (i<tam) && (mar[fila][col+i] == AGUA) && 
        			( (fila == 0) || (mar[fila-1][col+i] == AGUA) )  &&
        			( (fila == numFilas-1) || (mar[fila+1][col+i] == AGUA) )         			
        		  ) i++;
        }
        else { // ori == 'V'
        	i = ( (fila > 0) ? -1 : 0 );
        	while ( (fila+i < numFilas) &&  (i<tam) && (mar[fila+i][col] == AGUA) &&
        			( (col == 0) || (mar[fila+i][col-1] == AGUA) )  &&
        			( (col == numColumnas-1) || (mar[fila+i][col+1] == AGUA) )  
        		  ) i++;
        }
        // Ha encontrado un hueco cuando ha generado el barco totalmente rodeado de agua o
        boolean resultado = (i == tam);
        // lo ha generado horizontal pegado al borde derecho o
        resultado = resultado || ( (ori == 'H') && (col+i == numColumnas) );
        // lo ha generado vertical pegado al borde inferior.
        resultado = resultado || ( (ori == 'V') && (fila+i == numFilas) );
        return resultado;
    }
    
    
} // end class Partida
