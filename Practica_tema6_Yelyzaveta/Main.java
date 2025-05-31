import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.RandomAccessFile;

/**
 * Clase principal que gestiona una lista de dispositivos y proporciona un menú para la interacción del usuario.
 * Permite agregar, mostrar, buscar, borrar, cambiar el estado y modificar dispositivos.
 */
public class Main {
  /**
   * La lista de dispositivos.
   */
  static ArrayList<Dispositivo> listaDispositivos = new ArrayList<Dispositivo>();
 /**
   * El objeto Scanner para leer la entrada del usuario.
   */
  private static Scanner sc = new Scanner(System.in);
  public static void main(String[] args) { 
    cargarDatos();
    menuPrincipal(); 
  }
/**
 * Muestra el menú principal y gestiona la interacción con el usuario.
 */
 public static void menuPrincipal() {
        boolean salir = false;
        int opcion;
        while (!salir) {
            System.out.println("1. Agregar dispositivo\n2. Mostrar Dispositivo\n3. Buscar dispositivo\n4. Borrar dispositivo\n5. Cambiar estado dispositivo\n6. Modificar dispositivo\n0. Salir");
            try {
                opcion = sc.nextInt();
                sc.nextLine(); // Limpiar el buffer del scanner
                switch (opcion) {
                    case 1:
                        anadirDispositivo();
                        break;
                    case 2:
                        mostrarDispositivos();
                        break;
                    case 3:
                        buscarDispositivos();
                        break;
                    case 4:
                        borrarDispositivo();
                        break;
                    case 5:
                        cambiarEstadoDispositivo();
                        break;
                    case 6:
                        modificarDispositivo();
                        break;
                    case 0:
                        System.out.println("¡Hasta luego!");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            }catch (InputMismatchException e) {
              System.out.println("Error: Debes ingresar un número entero.");
              sc.nextLine(); // Limpiar el buffer del scanner
            } catch (Exception e) {
              System.out.println("Error inesperado: " + e.getMessage());
              e.printStackTrace(); // Imprimir la traza del error para depuración
            }
        }
    }
/**
 * Carga los datos de los dispositivos desde el archivo "dispositivos.dat".
 */
  public static void cargarDatos() {
    try {
      int id = 0;
      boolean terminar = false;
      while (!terminar) {
        Dispositivo d = new Dispositivo(id);
        if(d.load() == 0 ){
          switch(d.getTipo()){
            case 1:
            Ordenador ordenador = new Ordenador(id);
            ordenador.load();
            listaDispositivos.add(ordenador);
            break;
          case 2:
            Impresora impresora = new Impresora(id);
            impresora.load();
            listaDispositivos.add(impresora);
            break;
          default: 
            listaDispositivos.add(d);
            break;
          }
        }else{
            terminar = true;
        }
        id++;
      }
      System.out.println("Datos cargados con éxito");
    } catch (Exception e) {
      System.out.println("Error al cargar datos: " + e.getMessage());
    }
  }
/**
 * Añade un dispositivo a la lista de dispositivos.
 */
  public static void anadirDispositivo() {
    boolean estado = false;
    System.out.println("Ingrese el nombre del fabricante: ");
    String marca = sc.nextLine();
    System.out.println("Ingrese el modelo del dispositivo: ");
    String modelo = sc.nextLine();
    System.out.println("Ingrese el estado del dispositivo(funciona o no funciona): ");
    String respuesta = sc.nextLine();
    if (respuesta.equals("funciona"))
      estado = true;
    else if (respuesta.equals("no funciona"))
      estado = false;
    System.out.println("Ingrese el tipo de dispositivo\n1.Ordenador\n2.Impresora\n3.Otro");
    int tipo = sc.nextInt();
    switch (tipo) {
      case 1:
        System.out.println("Ingrese el procesador: ");
        String procesador = sc.nextLine();
        System.out.println("Ingrese la ram(Ej.: 16, 32) : ");
        int ram = sc.nextInt();
        System.out.println("Ingrese el tipo del disco(0 = mecánico, 1 = SSD, 2 = NVMe, 3 = otros): ");
        int tipoDisco = sc.nextInt();
        System.out.println("Ingrese el tamaño del disco en GB ");
        int tamanoDisco = sc.nextInt();
        listaDispositivos.add(new Ordenador(marca, modelo, estado, ram, procesador, tamanoDisco, tipoDisco));
        Ordenador o = new Ordenador(marca, modelo, estado, ram, procesador, tamanoDisco, tipoDisco);
        o.save();
        System.out.println("Dispositivo agregado exitosamente.");
        break;
      case 2:
        System.out.println("Ingrese el tipo de impresora: 0 = laser, 1 = inyección de tinta, 2 = otro");
        int tipoImpresora = sc.nextInt();
        System.out.println("¿Tiene impresión a color? (si/no): ");
        String color = sc.nextLine();
        boolean color2;
        if(color.equals("si"))color2 = true;
        else color2 = false;
        System.out.println("¿Tiene Scanner?(si/no): ");
        String scanner = sc.nextLine();
        boolean scanner2;
        if(scanner.equals("si"))scanner2 = true;
        else scanner2 = false;
        listaDispositivos.add(new Impresora(marca, modelo, estado, tipoImpresora, color2, scanner2));
        Impresora i = new Impresora(marca, modelo, estado,tipoImpresora, color2, scanner2);
        i.save();
        System.out.println("Dispositivo agregado exitosamente.");
        break;
      case 3:
        listaDispositivos.add(new Dispositivo(marca, modelo, estado));
        Dispositivo d = new Dispositivo(marca, modelo, estado);
        d.save();
        System.out.println("Dispositivo agregado exitosamente.");
        break;
      default:
        System.out.println("Opción no válida");
    }
  }
/**
 * Muestra los dispositivos en la lista.
 */
  public static void mostrarDispositivos() {
    System.out.printf("%-10s %-15s %-20s %-20s\n", "ID", "Marca", "Modelo", "Estado");
    System.out.println("------------------------------------------------------------------");
    for (Dispositivo dispositivo : listaDispositivos) {
        System.out.printf("%-10d %-15s %-20s %-20s\n",
        dispositivo.getId(),
        dispositivo.getMarca(),
        dispositivo.getModelo(),
        dispositivo.getEstado() ? "Funciona" : "No funciona");
    }
  }

  public static void buscarDispositivos() {
    System.out.println("Ingrese el id del dispositivo que quieres localizar: ");
    int id = sc.nextInt();
    boolean encontrado = false;
    for (Dispositivo dispositivo : listaDispositivos) {
      if (dispositivo.getId() == id) {
        encontrado = true;
        System.out.println("Dispositivo encontrado: " + dispositivo.toString());
      }
    }
    if (!encontrado) System.out.println("Dispositivo no encontrado");
  }
/**
 * Elimina un dispositivo de la lista.
 */
  public static void borrarDispositivo() {
    try {
      RandomAccessFile raf = new RandomAccessFile("dispositivo.dat", "rw");
      System.out.println("Ingrese el id del dispositivo que quieres eliminar: ");
      int id = sc.nextInt();
      Dispositivo d = new Dispositivo(id);
      d.delete();
      boolean encontrado = false;
      for (int i = 0; i < listaDispositivos.size(); i++) {
        if (listaDispositivos.get(i).getId() == id) {
          listaDispositivos.get(i).delete();
          listaDispositivos.remove(i);
          encontrado = true;
          System.out.println("Dispositivo eliminado exitosamente");
        }
      }if(!encontrado) System.out.println("Dispositivo no encontrado");
      raf.close();
    }catch (Exception e) {
      System.out.println("Error al borrar dispositivo");
    }
  }
  /**
   * Cambia el estado de un dispositivo en la lista.
   */
  public static void cambiarEstadoDispositivo() {
    System.out.println("Ingrese el ID del dispositivo cuyo estado desea cambiar: ");
    int id = sc.nextInt();
    boolean cambiado = false;
    boolean encontrado = false;
    // Buscar el dispositivo en la lista
    for (Dispositivo dispositivo : listaDispositivos) {
      if (dispositivo.getId() == id) {
          encontrado = true;
          System.out.println("Estado actual del dispositivo: " + (dispositivo.getEstado() ? "Funciona" : "No funciona"));
          System.out.println("Ingrese el nuevo estado (1 = funciona/ 2 = no funciona): ");
          int nuevoEstado = sc.nextInt();
          if (nuevoEstado == 1) {
              dispositivo.setEstado(true);
              cambiado = true;
          } else if (nuevoEstado == 2) {
              dispositivo.setEstado(false);
              cambiado = true;
          } else {
              System.out.println("Estado no válido.");
          }
          if (!cambiado) {
              System.out.println("No se ha podido cambiar el estado del dispositivo.");
          } else {
              System.out.println("Estado del dispositivo modificado exitosamente.");
          }
          break;
      }
    
      if (!encontrado) {
          System.out.println("Dispositivo no encontrado.");
      }
    }
  }
    
  
/**
 * Modifica un dispositivo en la lista.
*/
  public static void modificarDispositivo() {
    boolean salir = false;
    System.out.println("Ingrese el id del dispositivo que quieres modificar: ");
    int id = sc.nextInt();
    boolean encontrado = false;
    try {
      RandomAccessFile raf = new RandomAccessFile("dispositivo.dat", "rw");
      for (Dispositivo dispositivo : listaDispositivos) {
        if (dispositivo.getId() == id) {
          encontrado = true;
          dispositivo.toString();
          System.out.println("Ingrese el nuevo nombre del dispositivo: ");
          String nombre = sc.next();
          System.out.println("Ingrese el nuevo modelo del dispositivo: ");
          String modelo = sc.next();
          System.out.println("Ingrese el nuevo estado del dispositivo (funciona o no funciona): ");
          String estado = sc.nextLine();
          System.out.println("Dispositivo nuevo: " + dispositivo.toString());
          dispositivo.setMarca(nombre);
          dispositivo.setModelo(modelo);
          if (estado == "funciona")
            dispositivo.setEstado(true);
          else
            dispositivo.setEstado(false);
            raf.seek(id * 114);
            raf.writeUTF(dispositivo.getMarca());
            raf.writeUTF(dispositivo.getModelo());
            raf.writeBoolean(dispositivo.getEstado());
            encontrado = true;
            System.out.println("Dispositivo modificado exitosamente");
        }
      }
      if (encontrado = false) {
        System.out.println("Dispositivo no encontrado");
      }
      raf.close();
    }catch (Exception e) {
      System.out.println("Error al modificar dispositivo");
    }
  }
}