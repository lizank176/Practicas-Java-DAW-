public class Pinguino {
    /*La variable pin_muertos nos va a almacenar  todos los pingünos muertos */
    int pin_muertos=0;
    int pescado=3;
    /*El método Tamanyo te saca un tamaño aleatorio */
    public  int Tamanyo(){
        int ping_aleatorio=(int)(Math.random()*5 +1);
        return ping_aleatorio;   
    }
    /* Este método almacena la cantidad de pescado que te quede */
    public int pescadoCantidad(int decision){
        if(decision==2){
            pescado=pescado-1;
        }
        return pescado;
    }
    public void dibujoPescado(int pescado){
        int contador=0;
        while(contador!=pescado){
            System.out.print(" ><_> ");
            contador=contador+1;
        }
    }
    /*Si el usuario gana se nos va a mostrar un dibujo */
    public void mostrarVictoria(){
        System.out.println("*****************************************************************");
        System.out.println("  _   _                                         _       _ ");
        System.out.println(" | | | | __ _ ___    __ _  __ _ _ __   __ _  __| | ___ | |");
        System.out.println(" | |_| |/ _` / __|  / _` |/ _` | '_ \\ / _` |/ _` |/ _ \\| |");
        System.out.println(" |  _  | (_| \\__ \\ | (_| | (_| | | | | (_| | (_| | (_) |_|");
        System.out.println(" |_| |_|\\__,_|___/  \\__, |\\__,_|_| |_|\\__,_|\\__,_|\\___/(_)");
        System.out.println("                    |___/                                 ");
        System.out.println("*****************************************************************");
        }
    /*Si el usuario pierde se nos va a mostrar un dibujo */

    public static void mostrarDerrota() {
        System.out.println("***************************");
        System.out.println("       .-\"\"\"\"\"-.");
        System.out.println("     .'          '.");
        System.out.println("    /   O      O   \\");
        System.out.println("   :           `    :");
        System.out.println("   |                |");
        System.out.println("   :    .------.    :");
        System.out.println("    \\  '        '  /");
        System.out.println("     '.          .'");
        System.out.println("       '-......-'");
        System.out.println("      ¡Has perdido!");
        System.out.println("***************************");
    }
    public String  mostrarTamanyo(int tamanyo_pin){
        String tamanyo=" ";
        switch (tamanyo_pin){
            case 1:
            tamanyo="XS";
            break;
            case 2:
            tamanyo="S";
            break;
            case 3:
            tamanyo="M";
            break;
            case 4:
            tamanyo="L";
            break;
            case 5:
            tamanyo="XL";
            break;
        }return tamanyo;
    }
    /* Este método imprime el pingüino según el tamaño que ha salido */
    public void mostrarTamanyoPinguino(int ping_aleatorio){
        if (ping_aleatorio==1){
            System.out.println("PINGUINO XS");
            System.out.println("    ^   ");
            System.out.println("  (o o) ");
            System.out.println("  /(v)\\");
            System.out.println("   \" \"  ");
            
          
        }if(ping_aleatorio==2){
        System.out.println("PINGUINO S");
        System.out.println("    ^    ");
        System.out.println("  (o o)  ");
        System.out.println(" /( v )\\ ");
        System.out.println("/  ---  \\");
        System.out.println("|   -   |");
        System.out.println("   \" \"   ");
        }if(ping_aleatorio==3){
            System.out.println("PINGUINO M");
            System.out.println("      .___.\r\n" + //
                                "     /     \\\r\n" + //
                                "    | O _ O |\r\n" + //
                                "    /  \\_/  \\\r\n" + //
                                "  .' /     \\ `.\r\n" + //
                                " / _|       |_ \\\r\n" + //
                                "(_/ |       | \\_)\r\n" + //
                                "    \\       /\r\n" + //
                                "   __\\_>-<_/__\r\n" + //
                                "   ~;/     \\;~");
        }if(ping_aleatorio==4){
            System.out.println("PINGUINO L");
            System.out.println("         /~~~~~~\\\n" +
            "       /`    -s- ~~~~\\\n" +
            "      /`::::      ~~~~\n" +
            "     /`:::::     :\n" +
            "    /` :::::...::::\n" +
            "   /`   `:::::::::::\n" +
            "  /`      `:::::::::\n" +
            " /`        :::::::::\n" +
            " :        ::::::::::\n" +
            " :       :::::::::::\n" +
            " :       :::::::::::\n" +
            " :   .    ::::::::::\n" +
            " :   :.   ::::::::'\n" +
            " :   ::  .:::::::'\n" +
            " :   ::..:::::::'\n" +
            " :    :::::::::'\n" +
            "  :    :::::::::\n" +
            "   :..::......::\n" );
        }if(ping_aleatorio==5){
            System.out.println("PINGUINO XL");
            System.out.println( "                        ooo\n" +
            "                   ooo$$$$$$$oo\n" +
            "                o$$$$$$$$$$$$$$$ooo\n" +
            "              o$$$$$$$$$$$$$$$\"$$$$$oo\n" +
            "           o$$$$$$$$$$$$$$$$$  o $$$$$$$$oooo\n" +
            "          o$$$\"\"\"$$$$$$$$$$$ooo$$$$$$$$$$$\"\"\n" +
            "        oo$\"      \"$$$$$$$$$$$$$$$$$$$$\"\n" +
            "       o$$          $$$$$$$$$$$$$$$$$$\"\n" +
            "      o$$           $$$$$$$$$$$$$$$$$$\n" +
            "    o$$$$            $$$$$$$$$$$$$$$\"\n" +
            "   o$$$$$$oooooo \"               $\"\n" +
            "  $$$$$$$$$$$$$\"\n" +
            " $$$$$$$$$$$$$$\n" +
            " $$$$$$$$$$$$                        o\n" +
            " $$$$$$$$$$$$                         o\n" +
            " $$$$$$$$$$$$$                         \"o\n" +
            " $$$$$$$$$$$$$$                         \"o\n" +
            " $$$$$$$$$$$$$$                          $\n" +
            " $$$$$$$$$$$$$\"                          \"\n" +
            " $$$$$$$$$$$$$                            $\n" +
            " $$$$$$$$$$$$$                            $\n" +
            " $$$$$$$$$$$$$$                           \"\n" +
            " $$$$$$$$$$$$$                            $\n" +
            " $$$$$$$$$$$$$                            $\n" +
            " $$$$$$$$$$$$$$                           $\n" +
            " $$$$$$$$$$$$$$                          o$\n" +
            " $$$$$$$$$$$$$$$o                       o$$\n" +
            " $$$$$$$$$$$$$$$$$o                     \"$$\n" +
            " $$$$$$$$$$$$$$$$$$$o                   o$$$\n" +
            " $$$$$$$$$$$$$$$$$$$$$$                o$$$$\n" +
            "  $$$$$$$$$$$$$$$$$$$$$$$o             o$$$$$\n" +
            "  $$$$$$$$$$$$$$$$$$$$$$$$$$          o$$$$$$\n" +
            "   $$$$$$$$$$$$$$$$$$$$$$$$$$$$ooo  o$$$$$$$$\n" +
            "   $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
            "    \"$\"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
            "      \"$\"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
            "        \"$\"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
            "           \"\"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
            "               \"$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n" +
            "                   \"\"$$$$$$$$$$$$$$$$$$\"\n"
        );
        } 
    }
}


