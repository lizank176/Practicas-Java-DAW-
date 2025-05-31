/**
 * Esta clase representa una baraja de cartas
 */
public class Baraja {
    /**
     * Matriz sirve para almacenar la baraja organizada
     */
    private Carta  mazo[][]; 
    /**
     *Almacena el tipo de la baraja: 1(poker), 2(española), 3 (española extendida), 4 (alemana).
     */
    private int tipo;
    /**
     * Almacena la baraja barajada
     */
    private Carta[] mazo_aux;
    /**
     * Constructor de la clase Baraja.
     * @param tipo el tipo de baraja a crear
     */
    public Baraja(int tipo) {
        this.tipo=tipo;
        //Poker
        if(tipo==1){
            mazo= new Carta[4][13];
            mazo_aux=new Carta[52];
        //Española 
        }else if(tipo==2){
            mazo= new Carta[4][10];
            mazo_aux=new Carta[40];

        }//Española extendida
        else if(tipo==3){
            mazo= new Carta[4][13];
            mazo_aux=new Carta[52];
        //Alemana 
        } else if(tipo==4){
            mazo= new Carta[4][9];
            mazo_aux=new Carta[36];

        }

    }
    /**
     * Este método añade cartas al mazo según el tipo de baraja.
     */
    public void anadirCarta(){
        int index=0;
        if(tipo==1){
            for(int c=0;c<mazo.length;c++){
                for(int f=0;f<mazo[c].length;f++){
                    mazo[c][f]= new Carta(f+2,c+1,tipo);
                    mazo_aux[index]=mazo[c][f];
                    index++;
                }
            }
        }if(tipo==2){
            for(int c=0;c<mazo.length;c++){
                for(int f=0;f<mazo[c].length;f++){
                    if(f>6){
                        mazo[c][f]= new Carta(f+4,c+1,tipo);
                        mazo_aux[index]=mazo[c][f];
                        index++;
                    }else{
                        mazo[c][f]= new Carta(f+1,c+1,tipo);
                        mazo_aux[index]=mazo[c][f];
                        index++;
                    }
                  
                }
            }
        }if(tipo==3){
            for(int c=0;c<mazo.length;c++){
                for(int f=0;f<mazo[c].length;f++){
                    if(f>8){
                        mazo[c][f]= new Carta(f+2,c+1,tipo);
                        mazo_aux[index]=mazo[c][f];
                        index++;
                    }else{
                        mazo[c][f]= new Carta(f+1,c+1,tipo);
                        mazo_aux[index]=mazo[c][f];
                        index++;
                    }
                    
                }
            }
        }if(tipo==4){
            for(int c=0;c<mazo.length;c++){
                for(int f=0;f<mazo[c].length;f++){
                    mazo[c][f]= new Carta(f+6,c+1,tipo);
                    mazo_aux[index]=mazo[c][f];
                    index++;
                    
                }
            }
        }
        
    }

    
    /**
    * Baraja las cartas aleatoriamente, asegurando que no se repitan posiciones.
    */
    public void barajar(){
        int totalCartas = mazo.length * mazo[0].length;
        mazo_aux = new Carta[totalCartas];
        int index = 0;
        for (int palo = 0; palo < mazo.length; palo++) {
            for (int valor = 0; valor < mazo[palo].length; valor++) {
                mazo_aux[index++] = mazo[palo][valor];
            }
        }
        for (int i = 0; i < mazo_aux.length; i++) {
            int posAleatoria = (int) (Math.random() * mazo_aux.length);
            Carta temp = mazo_aux[i];
            mazo_aux[i] = mazo_aux[posAleatoria];
            mazo_aux[posAleatoria] = temp;
        }
    }
    /**
    *Devuelve la siguiente carta disponible en la baraja.
    *
    * @return la siguiente carta o null si no quedan cartas.
    */
    public Carta siguiente() {
        if (mazo_aux == null || mazo_aux.length == 0) {
            return null;
        }
        for (int i = 0; i < mazo_aux.length; i++) {
            if (mazo_aux[i] != null) {
                Carta carta = mazo_aux[i];  
                mazo_aux[i] = null; // Eliminar la carta del mazo
                return carta;
            }
        }
       return null;  // Si no quedan cartas
    }
     /**
     * Reinicia el mazo, volviendo al estado inicial.
     */
    public void reiniciar(){
        mazo_aux=null; //Reinicia el mazo auxiliar
        anadirCarta(); // Añade las cartas de nuevo

    }
    /**
     *  Calcula el número de cartas que quedan en el mazo barajado
     * @return El número de cartas restantes
    */
    public int numCartas(){
        int num_cartas=0;
        for(int i=0;i<mazo_aux.length;i++){
            if(mazo_aux[i]!=null){
                num_cartas++;
            }
        }return num_cartas;
    }
    /**
     * Calcula el número de cartas que quedan en el mazo barajado.
     *
     * @return el número de cartas restantes.
     */
    public Carta[] getBaraja(){
        int num_cartas=numCartas();
        Carta [] baraja=new Carta[num_cartas];
        int contador=0;
        for(int i=0;i<mazo_aux.length;i++){
            if(mazo_aux[i]!=null){
                baraja[contador]=mazo_aux[i];
                contador++;
            }
        } return baraja;
    }
     /**
     * Genera una lista con las n cartas siguientes que quedan en el mazo.
     *
     * @param n el número de cartas a obtener.
     * @return un array con las n cartas restantes o null si no quedan suficientes.
     */
    public Carta[] getBarajaN(int n){
        Carta []cartas_faltantes= getBaraja();
        if(cartas_faltantes.length==0){
            return null;
        }
        if(n>cartas_faltantes.length){
            n=cartas_faltantes.length;
        }
        Carta [] barajaN=new Carta[n];
        for(int i=0;i<barajaN.length;i++){
            barajaN[i]=cartas_faltantes[i];
        } return barajaN;
    }
     /**
     * Devuelve una representación en cadena de las cartas restantes en el mazo.
     *
     * @return una cadena con las cartas restantes.
     */
    public String toString(){
        String cadena=" ";
        Carta [] restantes=getBaraja();
        for(int i=0;i<restantes.length;i++){
            cadena+=restantes[i].toString()+" ";
        } return cadena;
    }
}


