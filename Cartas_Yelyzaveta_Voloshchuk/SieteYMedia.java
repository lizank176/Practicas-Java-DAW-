import java.util.Scanner;
public class SieteYMedia {

    /**
     * Objeto Baraja para manejar las cartas.
     */
    private Baraja baraja; 
    /**
     * Array para almacenar los puntos de cada jugador.
     */
    private double [] jugadores;
     /**
     * Objeto Carta que representa la carta actual.
     */
    /**
     * Array para almacenar los resultados finales de los jugadores.
     */
    private int [] resultados;
    /**
     * Constructor vacío.
     */
    public SieteYMedia(){
     
    }
     /**
     * Constructor que inicializa el juego con una baraja específica.
     *
     * @param baraja El objeto Baraja.
     */
    public SieteYMedia(Baraja baraja){ 
        this.baraja = baraja;
    
    }
   /**
    * Método principal del juego "Siete y Media".
    * Configura el juego, permite a los jugadores tomar turnos y muestra los resultados por turno y finales.
    */
    public void jugar(){
        Scanner sc = new Scanner(System.in);
        String respuesta="";
        int numJugadores=2;
        boolean seguir=true;
        // Configuración inicial del juego
        do{
            try {
                do {
                    System.out.print("¿Cuántas personas van a jugar? (mínimo 2): ");
                    numJugadores = sc.nextInt();
                } while (numJugadores<2);
                jugadores = new double[numJugadores];
                resultados = new int [numJugadores];
                seguir=false;
            } catch (Exception e) {
                System.out.println("Error: El número mínimo de jugadores es 2. Por favor, introduce una cantidad válida.");
                sc.nextLine();
            }
        }while(seguir);
        // Turnos
        for (int turno = 1; turno <= 3; turno++) {
            System.out.println("\nTurno " + turno);
            baraja.anadirCarta();
            baraja.barajar();
            for (int i = 0; i < numJugadores; i++) {
                boolean plantado = false;
                while (!plantado) {
                    System.out.println("Jugador " + (i + 1) + ", ¿quieres sacar una carta? (si/no)");
                    respuesta = sc.next();
                    if (respuesta.equalsIgnoreCase("no")) {
                        System.out.println("Jugador " + (i + 1) + " se planta con " + jugadores[i] + " puntos.");
                        plantado = true;
                    } else if(respuesta.equalsIgnoreCase("si")) {
                        Carta carta = baraja.siguiente();
                        if (carta == null) {
                            System.out.println("No quedan cartas.");
                            plantado = true;
                        }

                        double valor = valorCarta(carta);
                        jugadores[i] += valor;

                        System.out.println("Carta: " + carta.toString());
                        System.out.println("Puntos actuales: " + jugadores[i]);

                        if (jugadores[i] > 7.5) {
                            System.out.println("Jugador " + (i + 1) + " se pasó y pierde el turno.");
                            plantado = true;
                        }
                    }
                }
            } mostrarResultados();
            for(int a=0;a<jugadores.length;a++){
                jugadores[a]=0;
            }
        }mostrarResultadosFinales();

    }
        
    
     /**
     * Devuelve el valor de una carta según el tipo de baraja.
     *
     * @param carta Objeto Carta del que se quiere calcular el valor.
     * @return Valor de la carta como un número decimal.
     */
    public double  valorCarta(Carta carta){
        double num=carta.getValor();
        if (num >= 1 && num <= 10) {
            return num;
        } else if (num >= 11 && num <= 14) { // Figuras tienen valor 0.5.
            return 0.5;
        }
        return 0; //Por defecto, devuelve 0 si no coincide
    }
    /**
     * Muestra los resultados finales de los jugadores.
     */
    public void mostrarResultados() {
        double maxPuntos = -1; 
        String ganadores = ""; 
       
            System.out.println("\n--- Resultados del turno ---");
            for (int a = 0; a < jugadores.length; a++) {
                System.out.println("Jugador " + (a + 1) + ": " + jugadores[a] + " puntos.");
            }
            for (int b = 0; b < jugadores.length; b++) {
                if (jugadores[b] <= 7.5 && jugadores[b] > maxPuntos) {
                    maxPuntos = jugadores[b];
                    ganadores = "Jugador " + (b + 1); // Nuevo ganador 
                    resultados[b]+=1;
                } else if (jugadores[b] == maxPuntos && jugadores[b] <= 7.5) {
                    ganadores += ", Jugador " + (b + 1); // Empate
                } else{
                    resultados[b]=0;
                }
               
            }
        
            if (maxPuntos ==-1) {
                System.out.println("Todos los jugadores se han pasado. ¡No hay ganador!");
            } else {
                System.out.println("Ganador(es): " + ganadores + " con " + maxPuntos + " puntos.");
            }
            
            

        }
      
    /**
    * Muestra los resultados finales del juego después de todos los turnos.
    */
    public void mostrarResultadosFinales() {
        System.out.println("\n--- Resultados finales del juego ---");
        for (int i = 0; i < resultados.length; i++) {
            System.out.println("Jugador " + (i + 1) + ": " + resultados[i] + " partidas ganadas.");
        }
        int maxVictorias = -1;
        String ganadoresFinales = "";
        for (int i = 0; i < resultados.length; i++) {
            if (resultados[i] > maxVictorias) {
                maxVictorias = resultados[i];
                ganadoresFinales = "Jugador " + (i + 1);
            } else if (resultados[i] == maxVictorias) {
                ganadoresFinales += ", Jugador " + (i + 1);
            }
        }

        if (maxVictorias == 0) {
            System.out.println("¡No hay ganador definitivo, nadie ha ganado partidas!");
        } else {
            System.out.println("Ganador definitivo: " + ganadoresFinales + " con " + maxVictorias + " partidas ganadas.");
        }
    }

}