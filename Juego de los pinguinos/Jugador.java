public class Jugador {
    /*Son los atributos de la clase Jugador */
    String nombre=" ";
    int puntos_de_vida=50;
    String camino="S";
    int decision=0;
    public  boolean lucharHuir(int decision, int pescado){
        boolean resultado=true;
        if (pescado>0){
           resultado=true;
        }else if(pescado==0 && decision==2){
           return resultado=false;
        }return resultado;
    }
    /*Este método nos devuelve un  boolean(si el usuario elije el mismo camino que antes este método nos va a devolver false) */
    public  boolean Camino(String nuevo_camino){
        if(nuevo_camino.equals("N") && camino.equals("S")){
            System.out.println("No puedes volver atrás");
            return false;
        }else if(nuevo_camino.equals("E") && camino.equals("O")){
            System.out.println("No puedes volver atrás");
            return false;
        }else if(nuevo_camino.equals("O") && camino.equals("E")){
            System.out.println("No puedes volver atrás");
            return false;
        }else if(nuevo_camino.equals("S") && camino.equals("N")){
            System.out.println("No puedes volver atrás");
            return false;   
        }else {
    
            return true;
        }
        
    }
    /*Este método me muestra todas las direcciones disponibles para elegir  */
   public String elegirCamino(String nuevo_camino){
        String direccion=" ";
            switch(nuevo_camino){
                case "N":
                    direccion="N = norte,  E = este, O = Oeste";
                break;
                case "S":
                    direccion="S = sur, E = este, O = Oeste";
                break;
                case "E":
                    direccion="N = norte, S = sur, E = este";
                break;
                case "O":
                    direccion="N = norte, S = sur,  O = Oeste";
                break;
            }
        return direccion;
    }
}
