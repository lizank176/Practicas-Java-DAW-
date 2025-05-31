/**
 * La clase Ordenador representa un dispositivo informático con atributos como RAM, procesador,
 * tamaño y tipo de disco. Hereda de la clase Dispositivo e incluye funcionalidades para
 * persistencia en archivos.
 */
import java.io.RandomAccessFile;

public class Ordenador extends Dispositivo {
    /** Cantidad de memoria RAM en GB. */
    protected int ram;
    /** Modelo del procesador. */
    protected String procesador;
    /** Tamaño del disco en GB. */
    protected int tamDisco;
    /** Tipo de disco (1: Mecánico, 2: SSD, 3: NVMe, 4: Otro). */
    protected int tipoDisco;
    /** Identificador único del ordenador. */
    protected int idOrdenador;
    /** Tamaño fijo del registro en el archivo. */
    private static final int TAM_REG = 66;

    /**
     * Constructor de la clase Ordenador.
     * 
     * @param marca Marca del ordenador.
     * @param modelo Modelo del ordenador.
     * @param estado Estado del ordenador (encendido/apagado).
     * @param ram Cantidad de memoria RAM en GB.
     * @param procesador Modelo del procesador.
     * @param tamDisco Tamaño del disco en GB.
     * @param tipoDisco Tipo de disco (1: Mecánico, 2: SSD, 3: NVMe, 4: Otro).
     */
    public Ordenador(String marca, String modelo, boolean estado, int ram, String procesador, int tamDisco, int tipoDisco) {
        super(marca, modelo, estado);
        this.tipo = 1;
        this.ram = ram;
        this.procesador = procesador;
        this.tamDisco = tamDisco;
        this.tipoDisco = tipoDisco;
        int ultimoId = -1;
        try {
            RandomAccessFile raf = new RandomAccessFile("ordenadores.dat", "rw");
            if (raf.length() >= TAM_REG) {
                raf.seek(raf.length() - TAM_REG);
                ultimoId = raf.readInt();
                this.idOrdenador = ultimoId + 1;
            } else {
                this.idOrdenador = 0;
            }
            idAjeno = idOrdenador;
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor de la clase Ordenador para cargar un ordenador por su ID.
     * 
     * @param id Identificador único del ordenador.
     */
    public Ordenador(int id) {
        super(id);
        this.ram = 0;
        this.procesador = "";
        this.tamDisco = 0;
        this.tipoDisco = 0;
    }

    /**
     * Obtiene la cantidad de RAM.
     * 
     * @return Memoria RAM en GB.
     */
    public int getRam() {
        return this.ram;
    }

    /**
     * Establece la cantidad de RAM.
     * 
     * @param ram Nueva cantidad de RAM en GB.
     */
    public void setRam(int ram) {
        this.ram = ram;
    }

    /**
     * Obtiene el modelo del procesador.
     * 
     * @return Modelo del procesador.
     */
    public String getProcesador() {
        return this.procesador;
    }

    /**
     * Establece el modelo del procesador.
     * 
     * @param procesador Nuevo modelo del procesador.
     */
    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    /**
     * Obtiene el tamaño del disco en GB.
     * 
     * @return Tamaño del disco en GB.
     */
    public int getTamDisco() {
        return this.tamDisco;
    }

    /**
     * Establece el tamaño del disco en GB.
     * 
     * @param tamDisco Nuevo tamaño del disco en GB.
     */
    public void setTamDisco(int tamDisco) {
        this.tamDisco = tamDisco;
    }

    /**
     * Convierte la información del ordenador a una cadena de texto.
     * 
     * @return Representación en texto del ordenador.
     */
    @Override
    public String toString() {
        String disco;
        switch (tipoDisco) {
            case 1: disco = "Mecánico"; break;
            case 2: disco = "SSD"; break;
            case 3: disco = "NVMe"; break;
            case 4: disco = "Otro"; break;
            default: disco = "Desconocido"; break;
        }
        return "ID: " + this.idOrdenador + "\nMarca: " + getMarca() + "\nModelo: " + getModelo() +
                "\nRAM: " + this.ram + " GB\nProcesador: " + this.procesador +
                "\nTamaño del disco: " + this.tamDisco + " GB\nTipo de disco: " + disco;
    }

    /**
     * Guarda la información del ordenador en el archivo.
     * 
     * @return 0 si el guardado fue exitoso, 1 si hubo un error.
     */
    @Override
    public int save() {
        super.save();
        try {
            RandomAccessFile raf = new RandomAccessFile("ordenadores.dat", "rw");
            raf.seek(raf.length());
            raf.writeInt(this.idOrdenador);
            longitudFija(raf, this.procesador, 50);
            raf.writeInt(this.ram);
            raf.writeInt(this.tamDisco);
            raf.writeInt(this.tipoDisco);
            raf.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * Carga la información del ordenador desde el archivo.
     * 
     * @return 0 si la carga fue exitosa, 1 si hubo un error.
     */
    @Override
    public int load() {
        super.load();
        try {
            RandomAccessFile raf = new RandomAccessFile("ordenadores.dat", "rw");
            long pos = (long) this.idOrdenador * TAM_REG;
            raf.seek(pos);
            this.idOrdenador = raf.readInt();
            this.procesador = leerString(raf);
            this.ram = raf.readInt();
            this.tamDisco = raf.readInt();
            this.tipoDisco = raf.readInt();
            raf.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
