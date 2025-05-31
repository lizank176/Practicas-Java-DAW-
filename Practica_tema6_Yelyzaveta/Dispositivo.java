import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * La clase Dispositivo representa un dispositivo con varios atributos como marca, modelo, id, estado, tipo e id ajeno.
 * Proporciona métodos para manipular y persistir estos atributos en un archivo.
 */
public class Dispositivo {

    /**
     * La marca del dispositivo.
     */
    protected String marca;

    /**
     * El modelo del dispositivo.
     */
    protected String modelo;

    /**
     * El identificador único del dispositivo.
     */
    protected int id;

    /**
     * El estado del dispositivo (true si funciona, false en caso contrario).
     */
    protected boolean estado;

    /**
     * Indica si el dispositivo ha sido borrado.
     */
    protected boolean borrado;

    /**
     * El tipo del dispositivo.
     */
    protected int tipo;

    /**
     * El id ajeno asociado con el dispositivo.
     */
    protected int idAjeno;

    /**
     * El tamaño fijo de un registro en bytes.
     */
    private static final int TAM_REG = 114;

    /**
     * Construye un nuevo Dispositivo con la marca, modelo y estado especificados.
     * El id se genera automáticamente basado en el último id en el archivo.
     *
     * @param marca  la marca del dispositivo
     * @param modelo el modelo del dispositivo
     * @param estado el estado del dispositivo
     */
    public Dispositivo(String marca, String modelo, boolean estado) {
        this.marca = marca;
        this.modelo = modelo;
        int ultimoId;
        try {
            RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "rw");
            if (raf.length() >= TAM_REG) {
                raf.seek(raf.length() - TAM_REG);
                ultimoId = raf.readInt();
                this.id = ultimoId + 1;
            } else {
                this.id = 0;
            }
            this.estado = estado;
            this.borrado = false; // no está borrado por defecto
            this.tipo = 0; // Valor predeterminado
            this.idAjeno = -1; // No tiene id ajeno por defecto
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
            this.id = 0;
        }
    }

    /**
     * Construye un nuevo Dispositivo con el id especificado.
     * Otros atributos se inicializan con valores predeterminados.
     *
     * @param dispositivo el id del dispositivo
     */
    public Dispositivo(int dispositivo) {
        this.id = dispositivo;
        this.marca = "";
        this.modelo = "";
        this.estado = false; // Valor predeterminado
        this.borrado = false; // no está borrado por defecto
        this.tipo = 0;
        this.idAjeno = -1; // No tiene id ajeno por defecto
    }
    /**
     * El método setMarca establece la marca del dispositivo.
     * @return la marca del dispositivo
     */

    public String getMarca() {
        return this.marca;
    }
    /**
     * El método setMarca establece la marca del dispositivo.
     * @param marca la marca del dispositivo
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }
    /**
     * El método getModelo devuelve el modelo del dispositivo.
     * @return el modelo del dispositivo
     */
    public String getModelo() {
        return this.modelo;
    }
    /**
     * El método setModelo establece el modelo del dispositivo.
     * @param modelo el modelo del dispositivo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    /**
     * El método getId devuelve el id del dispositivo.
     * @return el id del dispositivo
     */
    public int getId() {
        return this.id;
    }
    /**
     * El método getEstado devuelve el estado del dispositivo.
     * @return el estado del dispositivo
     */
    public boolean getEstado() {
        return this.estado;
    }
    /**
     * El método setEstado establece el estado del dispositivo.
     * @param estado el estado del dispositivo
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    /**
     * El método setBorrado establece si el dispositivo ha sido borrado.
     * @param borrado si el dispositivo ha sido borrado
     */
    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }
    /**
     * El método getBorrado devuelve si el dispositivo ha sido borrado.
     * @return si el dispositivo ha sido borrado
     */
    public boolean getBorrado() {
        return this.borrado;
    }
    /**
     * El método getTipo devuelve el tipo del dispositivo.
     * @return el tipo del dispositivo
     */
    public int getTipo() {
        return this.tipo;
    }
    /**
     * El método setTipo establece el tipo del dispositivo.
     * @param tipo el tipo del dispositivo
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    /**
     * El método getNombre devuelve el nombre del dispositivo.
     * @return el nombre del dispositivo
     */
    public int getIdAjeno() {
        return this.idAjeno;
    }
    /**
     * El método setNombre establece el nombre del dispositivo.
     * @param idAjeno el nombre del dispositivo
     */
    public void setIdAjeno(int idAjeno) {
        this.idAjeno = idAjeno;
    }
    /**
     * El método toString devuelve una representación en cadena del dispositivo.
     */
    public String toString() {
        String estadoCadena;
        String borrar;
        String mensaje;
        if (this.estado) estadoCadena = "funciona";
        else estadoCadena = "no funciona";
        if (this.borrado) {
            borrar = "El dispositivo ha sido borrado ";
            mensaje = "ID: " + this.id + "\nMarca: " + this.marca + "\nModelo: " + this.modelo + "\nEstado: " + estadoCadena + "\n" + borrar;
        } else {
            mensaje = "ID: " + this.id + "\nMarca: " + this.marca + "\nModelo: " + this.modelo + "\nEstado: " + estadoCadena;
        }
        return mensaje;
    }
/**
 * El método save guarda el dispositivo en un archivo.
 * @return 0 si se guarda correctamente, 1 en caso contrario
 */
    public int save() {
        try {
            RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "rw");
            raf.seek(raf.length()); // Hacemos un salto aquí porque necesitamos añadir un dispositivo nuevo
            raf.writeInt(this.id);
            longitudFija(raf, this.marca, 50); // Utilizamos el método longitudFija para que la cadena ocupe un tamaño fijo
            longitudFija(raf, this.modelo, 50);
            raf.writeBoolean(this.estado);
            raf.writeBoolean(this.borrado);
            raf.writeInt(this.tipo);
            raf.writeInt(this.idAjeno);
            raf.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
    /**
     * El método longitudFija escribe una cadena en un archivo con una longitud fija.
     * @param raf
     * @param nombre
     * @param longitud
     */
    public void longitudFija(RandomAccessFile raf, String nombre, int longitud) {
        try {
            long posIni;
            long posFin;
            posIni = raf.getFilePointer(); // Posición ANTES de escribir el nombre en el fichero
            raf.writeUTF(nombre);
            posFin = raf.getFilePointer(); // Posición DESPUÉS de escribir el nombre en el fichero
            long bytesEscritos = posFin - posIni; // La variable bytesEscritos sirve para almacenar la longitud original de la cadena
            for (int j = 0; j < longitud - bytesEscritos; j++) {
                raf.writeByte(0); // Si queda algo de espacio pues lo rellenamos con ceros
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * El método leerString lee una cadena de un archivo.
     * @param raf
     * @return
     */
    public String leerString(RandomAccessFile raf) {
        try {
            long pos = raf.getFilePointer();
            String nombre = raf.readUTF();
            raf.seek(pos + 50);
            return nombre;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * El método load carga un dispositivo desde un archivo.
     * @return 0 si se carga correctamente, 1 en caso contrario
     */
    public int load() {
        try {
            RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "r");
            long pos = (long) id * TAM_REG;
            if(pos>=raf.length()){
                System.out.println("El dispositivo no existe");
                return 1;
            }
            raf.seek(pos );
            this.id = raf.readInt();
            this.marca = leerString(raf);
            this.modelo = leerString(raf);
            this.estado = raf.readBoolean();
            this.borrado = raf.readBoolean();
            this.tipo = raf.readInt();
            this.idAjeno = raf.readInt();
            raf.close();
            return 0; // Éxito
        } catch (IOException e) {
            e.printStackTrace();
            return 1; // Error de lectura
        }
    }
    /**
     * El método delete borra un dispositivo del archivo.
     * @return 0 si se borra correctamente, 1 en caso contrario
     */
    public int delete() {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile("dispositivos.dat", "rw");
            raf.seek(this.id * TAM_REG);
            this.marca = "Borrado";
            longitudFija(raf, this.marca, 50);
            this.modelo = "Borrado";
            longitudFija(raf, this.modelo, 50);
            raf.writeBoolean(false);
            raf.writeBoolean(true);
            raf.writeInt(this.tipo); // Añadido para mantener la integridad del registro
            raf.writeInt(this.idAjeno);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("El dispositivo no existe ");
            return 1;
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el archivo: " + e);
                }
            }
        }
    }
}
