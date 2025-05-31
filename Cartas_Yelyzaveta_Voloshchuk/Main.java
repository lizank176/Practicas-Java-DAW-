/**
 * Clase principal que implementa un programa de gestión de barajas de cartas.
 * Proporciona opciones como crear, mostrar, barajar, y jugar con barajas.
 */
import java.util.Scanner;    
public class Main {
    private static Baraja baraja;
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int opcion=-1;
        boolean seguir=true;
        while (seguir) {
                try{                
                    System.out.println("MENÚ PRINCIPAL");
                    System.out.println("1. Crear una baraja");
                    System.out.println("2. Mostrar baraja");
                    System.out.println("3. Sacar cartas");
                    System.out.println("4. Barajar");
                    System.out.println("5. Jugar");
                    System.out.println("0. Salir del programa");
                    System.out.print("Selecciona una opción (0-5): ");
                    opcion = sc.nextInt();
                        switch (opcion) {
                            case 1:
                                crearBaraja();
                                break;
                            case 2:
                                mostrarBaraja();
                                break;
                            case 3:
                                sacarCartas();
                                break;
                            case 4:
                                barajar();
                                break;
                            case 5:
                                jugar();
                                break;
                            case 0:
                                System.out.println("¡Hasta luego!");
                                seguir=false;
                                break;
                            default:
                                System.out.println("Opción no válida. Intenta de nuevo.");
                            break;
                        
                        }   
    
                }catch(Exception e){
                    System.out.println("Error, por favor introduce un número entre 0 y 5");
                    sc.nextLine();                    

                }
            
        }  
    }
    /**
     * Método para crear diferentes tipos de barajas.
     * Ofrece un submenú al usuario para seleccionar el tipo de baraja a crear.
     */
    private static int crearBaraja() {
        int tipo_baraja=0;
        int tipo;
        do {
            System.out.println("MENÚ DE BARAJAS");
            System.out.println("1. Crear una baraja de poker");
            System.out.println("2. Crear una baraja española");
            System.out.println("3. Crear una baraja española extendida");
            System.out.println("4. Crear una baraja alemana");
            System.out.println("0. Volver al menú principal");
            System.out.print("Selecciona una opción (0-4): ");
            tipo = sc.nextInt();
            switch (tipo) {
                case 1:
                    baraja = new Baraja(1);
                    baraja.anadirCarta();
                    System.out.println("Baraja de poker creada.");
                    tipo=0;
                    break;
                case 2:
                    baraja = new Baraja(2);
                    baraja.anadirCarta();
                    System.out.println("Baraja española creada.");
                    tipo=0;
                    break;
                case 3:
                    baraja = new Baraja(3);
                    baraja.anadirCarta();
                    System.out.println("Baraja española extendida creada.");
                    tipo=0;
                    break;
                case 4:
                    baraja = new Baraja(4);
                    baraja.anadirCarta();
                    System.out.println("Baraja alemana creada.");
                    tipo=0;
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (tipo != 0);
        return tipo_baraja;
    }
    /**
     * Método para mostrar la baraja actual.
     */
    private static void mostrarBaraja( ) {
        if (baraja == null) {
            System.out.println("No hay ninguna baraja creada.");
        } else {

            System.out.println("Contenido de la baraja:");
            System.out.println(baraja.toString());
        }
    }
    /**
     * Método para sacar cartas de la baraja actual.
     */
    private static void sacarCartas() {
        if (baraja == null) {
            System.out.println("No hay ninguna baraja creada.");
            return;
        }
        System.out.print("¿Cuántas cartas quieres sacar? ");
        int n = sc.nextInt();
        Carta[] cartas = baraja.getBarajaN(n);
        if (cartas == null || cartas.length == 0) {
            System.out.println("No quedan cartas en la baraja.");
        } else {
            System.out.println("Las cartas sacadas son:");
            for (Carta carta : cartas) {
                System.out.println(carta.toString());
            }
            if (cartas.length < n) {
                System.out.println("Ya no quedan más cartas.");
            }
        }
    }
    /**
     * Método para barajar las cartas de la baraja actual.
     */
    private static void barajar() {
        if (baraja == null) {
            System.out.println("No hay ninguna baraja creada.");
        } else {
            baraja.barajar();
            System.out.println("La baraja ha sido barajada.");
        }
    }
    /**
    * Método para iniciar un juego de Siete y Media.
     * Llama a la clase SieteYMedia.
     */
    private static void jugar() {
        if (baraja == null) {
            System.out.println("No hay ninguna baraja creada.");
            return;
        }
        SieteYMedia juego = new SieteYMedia(baraja);
        juego.jugar();
    }
}
