/* La alerta InputMismatchException  */
import java.util.InputMismatchException;
import java.util.Scanner;
public class Juego {
    /*Aquí he declarado las variables y puse static ya que las vamos a utilizar en esta misma clase */
    static String nombre=" ";
    static Pinguino pin ;
    static Jugador explorador;
    static int nuevo_camino;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        /*Se crean instancias del jugador (explorador) y los enemigos (pin). */
        explorador=new Jugador();
        pin=new Pinguino();
        System.out.println("**********************************************************");
        System.out.println("*                                                        *");
        System.out.println("*             ¡Bienvenido a PINGÜINO ISLAND!             *");
        System.out.println("*                                                        *");
        System.out.println("*      Una aventura épica en tierras heladas te espera.  *");
        System.out.println("*                                                        *");
        System.out.println("*   Explora, lucha y sobrevive contra pingüinos mutantes.*");
        System.out.println("*         ¡Demuestra tu valentía en este frío mundo!     *");
        System.out.println("*                                                        *");
        System.out.println("**********************************************************");
        System.out.println("¿Cuál es tu nombre, valiente explorador/a?");
        nombre = sc.nextLine();
        System.out.println("¡Un gran saludo para ti, " + nombre + "!");
        System.out.println("Tienes 50 puntos de vida y te diriges hacia el SUR.");
        System.out.println("¡Para poder huir del pingüino mutante, utiliza el pescado!");
        System.out.print("Tienes ");
        pin.dibujoPescado(pin.pescado);
        /*El juego continúa mientras el jugador tenga puntos de vida y no haya vencido a 5 pingüinos*/
        while(explorador.puntos_de_vida>0  && pin.pin_muertos<5){
            String direccion="S";
            System.out.println("");
            System.out.println("---------------------------------------------");
            System.out.println("Has llegado a una encrucijada. ¿Qué dirección quieres seguir ahora? \n "+explorador.elegirCamino(explorador.camino));
            /*Cada iteración, el jugador elige una dirección, que se procesa para continuar avanzando. */
            direccion=sc.nextLine().toUpperCase();
            boolean camino=explorador.Camino(direccion);
            explorador.camino=direccion;
            /*Mediante la utilización de if controlo que el usuario no pueda seguir avanzando mientras que el camino no sea el mismo que el jugador ha elegido antes */
            if (camino){
                /*Si el jugador elige un camino válido, se encuentra con un pingüino de tamaño aleatorio. */
                int pinguino_tamanyo=pin.Tamanyo();
                explorador.camino=direccion;
                System.out.println("¡Cuidado! Frente a ti aparece un pingüino mutante");
                pin.mostrarTamanyoPinguino(pinguino_tamanyo);
                do{
                    try{
                        /*  Si el pingüino tiene pescado disponible, se muestra al jugador cuántos pescados le quedan.*/
                        if(pin.pescado>0){
                        System.out.print("Pescado: ");  
                        // Se muestra un dibujo del pescado y se actualiza la cantidad restante.  
                        pin.dibujoPescado( pin.pescadoCantidad(explorador.decision));
                        System.out.println("");
                        }
                        System.out.println("");
                        System.out.println("¿Qué deseas hacer?");
                        System.out.println("Luchar contra el pingüno-> 1");
                        // Si aún hay pescado, el jugador tiene la opción de huir.
                        if(pin.pescado>0){
                            System.out.println("Huir-> 2");
                        }
                        explorador.decision=sc.nextInt();
                        // En caso de que el jugador ingrese un valor no válido, se informa y se repite la entrada
                    }catch (InputMismatchException ex){
                        System.out.println("Debes ingresar  un número entero (entre 1 y 2).");
                        explorador.decision=0;
                        sc.nextLine();
                    }if (explorador.decision==2 && pin.pescado==0){
                        while (explorador.decision!=1){
                            System.out.println("No tienes  suficiente pescado para huir");
                            System.out.println("Luchar contra el pingüno->1");
                            explorador.decision=sc.nextInt();
                        }
                    }
                }while(explorador.decision==0);
                explorador=Puntos(explorador.decision,pinguino_tamanyo, explorador );
                System.out.println("Tus puntos de vida ahora: "+explorador.puntos_de_vida);
                System.out.println("Has vencido a "+pin.pin_muertos+"/5 pingüinos.");
                sc.nextLine();
            }
            /*Llamo el método de otra clase para que se imprima un dibujo dependiendo del resultado*/
            if (pin.pin_muertos==5){
                System.out.println("");
                pin.mostrarVictoria();
            }else if( explorador.puntos_de_vida<=0){
                System.out.println("");
                pin.mostrarDerrota();
            }
        }
    }
    /*El método "Puntos" calcula los puntos que gana o pierde el usuario   */
    public static Jugador Puntos(int decision, int pinguino, Jugador explorador){
        /*Dependiendo del tamaño de cada pingüno se va a generar un número aleatorio */
        int num_aleatorio=0;
        if (pinguino==1)  num_aleatorio = 1 + (int)(Math.random() * (10));  
        if(pinguino==2) num_aleatorio = 10 + (int)(Math.random() * (11 ));  
        if (pinguino==3)num_aleatorio = 20 + (int)(Math.random() * (11)); 
        if (pinguino==4)num_aleatorio = 30 + (int)(Math.random() * (11)); 
        if(pinguino==5) num_aleatorio = 40 + (int)(Math.random() * (11 ));  
        /*En caso de que elija huir se le va a quitar un punto */
        if (decision==2 && pin.pescado!=0){
            explorador.puntos_de_vida-=1; 
            System.out.println("¡Has huido!");
        /*En caso de que elija luchar se simula el lanzamiento de un dado de 6 caras, que determina si el jugador gana o pierde la batalla contra el pingüino. */
        }else if (decision==1){
            int dado_explorador;
            dado_explorador=Dado();
            System.out.println("El dado ha caído en "+dado_explorador);
            if(pinguino<dado_explorador ){
                    explorador.puntos_de_vida+=num_aleatorio;
                    pin.pin_muertos+=1;
                    System.out.println("Has ganado "+num_aleatorio+" "+(num_aleatorio==1? " punto":" puntos")+ " de vida");
            }else{
                    explorador.puntos_de_vida-=num_aleatorio;
                    System.out.println("¡Oh no! El Pingüino "+pin.mostrarTamanyo(pinguino)+" te ha vencido, te ha quitado "+num_aleatorio+(num_aleatorio==1? " punto":" puntos")+ " de vida");
            }
            
        }return explorador;
    }
    /*El método "Dado" nos genera una cara aleatoria */
    public static int Dado(){
        int dado_aleatorio=(int)(Math.random()*6)+1;
        return dado_aleatorio;
    }
    
    
}

