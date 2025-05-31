/**
 * Esta clase sirve para modelar una carta individual de cualquier baraja
 */
public class Carta {
private int valor;
private int palo;
private int tipo;
/**
 * Constructor de la clase Carta.
 * 
 * @param valor el valor de la carta.
 * @param palo el palo de la carta.
 * @param tipo el tipo de baraja al que pertenece la carta.
 */
public Carta(int valor, int palo, int tipo) {
    this.valor = valor;
    this.palo = palo;
    this.tipo = tipo;
}
/**
 * Establece el valor de la carta solo si es válida 
 * @param valor el valor a asignar
 */
    public void  setValor(int valor){
        boolean esValido=false;
        switch (tipo) {
            case 1:
                int[]valores_correctos_poker={2,3,4,5,6,7,8,9,10,11,12,13,14};
                for(int v:valores_correctos_poker){
                    if(v==valor){
                        esValido=true;
                        break;
                    }
                }
                break;
            case 2:
                int []valores_correctos_espanyola={1,2,3,4,5,6,7,11,12,13};
                for(int v:valores_correctos_espanyola){
                    if(v==valor){
                        esValido=true;
                        break;
                    }
                }
                break;
            case 3:
                int []valores_correctos_espanyola_extendida={1,2,3,4,5,6,7,8,9,11,12,13};
                for(int v:valores_correctos_espanyola_extendida){
                    if(v==valor){
                        esValido=true;
                        break;
                    }
                }
                break;
            case 4:
                int []valores_correctos_alemana={6,7,8,9,10,11,12,13,14};
                for(int v:valores_correctos_alemana){
                    if(v==valor){
                        esValido=true;
                        break;
                    }
                }
                break;
        }
        if(esValido){
            this.valor=valor;
        } 
    }
    /**
     * Establece el palo de la carta se está dentro de los valores válidos 
     * @param palo el palo a asignar
     */
    public void setPalo(int palo){
        if(palo>=1 && palo<=4) this.palo = palo;

    }
    /**
     * Establece el tipo de la carta si está dentro de los valores válidos
     * @param tipo el tipo a asignar
     */
    public void setTipo(int tipo){
        if(tipo>=1 && tipo<=4) this.tipo = tipo;
    }
    /**
     * Obtiene el valor de la carta.
     * 
     * @return el valor de la carta.
     */
    public int getValor() {
        return valor;
    }
    /**
     * Obtiene el palo de la carta.
     * 
     * @return el palo de la carta.
     */
    public int getPalo(){
        return palo;
    }
     /**
     * Obtiene el tipo de la carta.
     * 
     * @return el tipo de la carta.
     */
    public int getTipo(){
        return tipo;
    }
    /**
     * Representa la carta como una cadena de texto indicando su valor y palo.
     * 
     * @return una cadena que describe la carta.
     */
    public String toString(){
        String palo_nombre="";
        String valor_nombre="";
        if(tipo==1){
            switch (palo) {
                case 1:
                    palo_nombre="Tréboles";
                    break;
                case 2:
                    palo_nombre="Diamantes";
                    break;
                case 3:
                    palo_nombre="Picas";
                    break;
                case 4:
                    palo_nombre="Corazones";
                    break;
            }
    
            switch(valor){
                case 11:
                    valor_nombre= "Jack";
                    break;
                case 12:
                    valor_nombre="Queen";
                    break;
                case 13:
                    valor_nombre="King";
                    break;
                case 14:
                    valor_nombre="As";
                    break;
                default:
                    valor_nombre+=String.valueOf(valor);
                    break;
            }
        }if (tipo==2 || tipo==3){
                switch (palo) {
                    case 1:
                        palo_nombre="Oros";
                        break;
                    case 2:
                        palo_nombre="Bastos";
                        break;
                    case 3:
                        palo_nombre="Espadas";
                        break;
                    case 4:
                        palo_nombre="Copas";
                        break;
                }
                switch(valor){
                    case 11:
                        valor_nombre= "Sota";
                        break;
                    case 12:
                        valor_nombre="Caballo";
                        break;
                    case 13:
                        valor_nombre="Rey";
                        break;
                    default:
                        valor_nombre=String.valueOf(valor);
                        break;
                }
            }if(tipo==4){
                switch(palo){
                    case 1:
                     palo_nombre="Campanas";
                     break;
                 case 2:
                     palo_nombre="Pastos";
                     break;
                 case 3:
                     palo_nombre="Corazones";
                     break;
                 case 4:
                     palo_nombre="Bellotas";
                     break; 
                 }
                 switch(valor){
                     case 11:
                         valor_nombre= "Campesion";
                         break;
                     case 12:
                         valor_nombre="Dama";
                         break;
                     case 13:
                         valor_nombre="Rey";
                         break;
                     case 14:
                         valor_nombre="As";
                         break;
                     default:
                        valor_nombre=String.valueOf(valor);
                        break;
                 }
            } return valor_nombre+" de "+palo_nombre;

        }
    
    /**
     * El métod equals compara el valor de la carta actual con la carta que le pasamos como párametro
     * @param c La carta a comparar con la carta actual
     * @return true en caso si tienen el mismo valor, palo
     */
    public boolean equals(Carta c){
        c = new Carta(valor,palo,tipo);
        if( valor == c.valor && palo == c.palo && tipo == c.tipo){
            return true;
        }return false;
    }
    /**
    * Crea y devuelve una copia de la carta actual.
    *
    * @return Una nueva instancia de Carta con los mismos atributos que la carta actual.
    */
    public Carta clone(Carta c){
        c = new Carta(valor,palo,tipo);
        c.setValor(this.valor);
        c.setPalo(this.palo);
        c.setTipo(this.tipo);
        return c;
    }
    /**
    * Compara si el número (valor) de la carta actual es igual al de otra carta.
    *
    * @param c La carta a comparar.
    * @return true si ambas cartas tienen el mismo valor; false en caso contrario.
    */
    public boolean compararNumero(Carta c){
        c = new Carta(valor,palo,tipo);
        if (this.valor == c.valor){
            return true;
        }return false;
    }
    /**
    * Compara si el palo de la carta actual es igual al de otra carta.
    *
    * @param c La carta a comparar.
    * @return true si ambas cartas tienen el mismo palo; false en caso contrario.
    */
    public boolean  compararPalo(Carta c){
        c = new Carta(valor,palo,tipo);
        if (this.palo == c.palo){
            return true;
        } return false;
    }
    /**
    * Compara si la carta actual es mayor que otra carta. 
    * La comparación se basa primero en el valor y luego, en caso de igualdad, en el palo.
    *
    * @param c La carta a comparar.
    * @return true si la carta actual es mayor que la otra; false en caso contrario.
    */
    public boolean mayorQue(Carta c){
        c = new Carta(valor,palo,tipo);
        boolean resultado=false;
        if(valor<this.valor){
            resultado= true;
        }else if(valor>this.valor){
            resultado= false;
        }else if(valor==this.valor){
            if(palo<this.palo){
                resultado= true;
            }else if(palo>this.palo){
                resultado= false;
            }
        }
        return resultado;
        
    }
    
}