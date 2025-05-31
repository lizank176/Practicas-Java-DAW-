import java.io.RandomAccessFile;
/**
 * Clase Impresora que hereda de Dispositivo y que tiene como atributos tipo, color y scanner 
 */
public class Impresora extends Dispositivo{
    protected int tipo; // 4
    protected boolean color; //1
    protected boolean scanner; // 1
    protected int idImpresora; //4
    private static final int TAM_REG = 10;
/**
 * Constructor de la clase Impresora que recibe como parámetros marca, modelo, estado, tipo, color y scanner
 * @param marca 
 * @param modelo 
 * @param estado
 * @param tipo
 * @param color
 * @param scanner
 */
    public Impresora(String marca, String modelo , boolean estado, int tipo, boolean color, boolean scanner){
        super(marca, modelo, estado);
        this.tipo = tipo;
        this.color = color;
        this.scanner = scanner;
        int ultimoId = -1;
        try{
            RandomAccessFile raf = new RandomAccessFile("Impresoras.dat","rw");
            //Aquí leo el último id para asignar el siguiente
            if(raf.length()>=TAM_REG){
                raf.seek(raf.length()-TAM_REG);
                ultimoId = raf.readInt();
                this.idImpresora = ultimoId + 1; 
            }else{//Si no hay registros, el id es 0
                this.idImpresora = 0;  
            } idAjeno = idImpresora;
            raf.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Constructor que asigna valores vacíos a todos los atributos menos al id
     * @param id 
     */
    public Impresora(int id){
        super(id);
        this.tipo = 0;
        this.color = false;
        this.scanner = false;
    } 
    /**
     * Método get y set para los atributos tipo, color y scanner
     */
    public int getTipo(){
        return this.tipo;
    }
    public void setTipo( int tipo){
        this.tipo = tipo;
    }
    
    public boolean getColor(){
        return this.color;
    }
    public void setColor(boolean color){
        this.color = color;
    }
    public boolean getScanner(){
        return this.scanner;
    }
    public void setScanner(boolean scanner){
        this.scanner =  scanner; 
    }
    /**
     * Método toString que devuelve un String con los atributos de la clase
     */
    public String toString(){
        String color ;
        String impresora;
        if(this.color==true){
            color = "sí";
        }else color = "no";
        if(this.scanner == true)  impresora = "tiene escáner";
        else impresora = "no tiene escáner";
        return "ID: "+this.idImpresora+ "\nMarca: "+getMarca()+"\nModelo: "+ getModelo() + "\nTipo: " + this.tipo + "\nColor: " + color + "\nScanner: " + impresora;
       }
    /**
     * Método save que guarda los atributos de la clase en un archivo
     */
    @Override
    public int save(){
        super.save();
        try{
            RandomAccessFile raf = new RandomAccessFile("Impresoras.dat", "rw");
            raf.seek(raf.length());
            raf.writeInt(this.idImpresora);
            raf.writeInt(this.tipo);
            raf.writeBoolean(this.color);
            raf.writeBoolean(this.scanner);
            raf.close();
            return 0;
        }catch(Exception e){
            e.printStackTrace();
            return 1;
           
        }
    }
    /**
     * Método load que carga los atributos de la clase desde un archivo 
     */
    @Override
    public int load(){
        super.load();
        try{
            RandomAccessFile raf = new RandomAccessFile("Impresoras.dat", "rw");
            long pos = (long)idImpresora*TAM_REG;
            raf.seek(pos);
            this.idImpresora = raf.readInt(); 
            this.tipo = raf.readInt();
            this.color = raf.readBoolean();
            this.scanner = raf.readBoolean();   
            raf.close();
            return 0;
        }catch(Exception e){
            e.printStackTrace();
            return 1;
        }
    }

}
