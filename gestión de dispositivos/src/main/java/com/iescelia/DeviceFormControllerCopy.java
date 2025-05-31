
package com.iescelia;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Controlador para la gestión de dispositivos en la interfaz gráfica.
 * Permite añadir, modificar, eliminar y buscar dispositivos en una tabla.
 * 
 */
public class DeviceFormControllerCopy {
    @FXML private TextField marcaField;
    @FXML private TextField modeloField;
    @FXML private RadioButton estado_noFunciona;
    @FXML private RadioButton estado_funciona;
    @FXML private TextField textoField;
    private Dispositivo dispositivo;
    @FXML private TableView<Dispositivo> tablaDispositivos;
    @FXML private TableColumn<Dispositivo, String> column_id;
    @FXML private TableColumn<Dispositivo, String> column_marca;
    @FXML private TableColumn<Dispositivo, String> column_modelo;
    @FXML private TableColumn<Dispositivo, String> column_estado;
    @FXML private TableColumn<Dispositivo, String> column_tipo;
    @FXML private TableColumn<Dispositivo, String> column_id_ajeno;
    @FXML private TableColumn<Dispositivo, String> column_borrado;
    @FXML private TextArea info;
/**
* Inicializa el controlador y configura la tabla con los dispositivos cargados.
*/
    @FXML
    public void initialize() {
        System.out.println("Inicializando DeviceFormControllerCopy...");
        tablaDispositivos.setItems(SecondaryController.listaDispositivos);
        SecondaryController.cargarDatos();
        column_id.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
        column_marca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
        column_modelo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        column_estado.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().getEstado())));
        column_tipo.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getTipo())));
        column_id_ajeno.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getIdAjeno()))); 
        column_borrado.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().getBorrado())));
        tablaDispositivos.setEditable(true);
        switchToModificar();
        tablaDispositivos.refresh();  // Asegura que la tabla se actualice con los nuevos datos.
        System.out.println("Datos cargados en la tabla: " + tablaDispositivos.getItems().size());
    }

/**
* Obtiene el dispositivo actualmente seleccionado.
* @return Dispositivo seleccionado
*/
    @FXML
    public Dispositivo getDispositivo() {
        return dispositivo;
    }
/**
 * Muestra un mensaje de alerta en pantalla.
 * @param titulo   Título de la alerta
 * @param contenido Mensaje de la alerta
 */
    @FXML
    public void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(AlertType.ERROR, contenido, ButtonType.OK);
        alert.setTitle(titulo);
        alert.showAndWait();
    }

/**
 * Regresa a la pantalla principal.
 * @throws IOException Si hay un error al cambiar de pantalla
 */
    @FXML
    private void volverInicio() throws IOException {
        App.setRoot("tipo");
    }

/**
 * Habilita la edición de las columnas marca y modelo de la tabla.
 */
    @FXML
    private void switchToAnadir() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("agregar_dispositivo.fxml"));
        Parent root = loader.load();

        // Obtener el controlador
        AgregarDispositivoController controller = loader.getController();
        controller.setControladorPrincipal(this);
        
        // Mostrar la nueva ventana
        Stage stage = new Stage();
        stage.setTitle("Añadir nuevo dispositivo");
        stage.initModality(Modality.WINDOW_MODAL); // modal
        stage.initOwner(tablaDispositivos.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.show();
    }
   
  /**
 * Cancela la operación actual y vuelve a la vista de la tabla de dispositivos.
 *
 * @throws Exception Si ocurre un error al cambiar la vista.
 */
public void cancelar() throws Exception {
    App.setRoot("tabla_dispositivo_copy");
}

/**
 * Limpia los campos de entrada de marca, modelo y estado del dispositivo.
 */
public void limpiarCampos() {
    marcaField.clear();
    modeloField.clear();
    estado_funciona.setSelected(false);
    estado_noFunciona.setSelected(false);
}

/**
 * Actualiza la tabla de dispositivos con los datos de la lista de dispositivos.
 */
public void actualizarTabla() {
    tablaDispositivos.setItems(FXCollections.observableArrayList(SecondaryController.listaDispositivos));
    tablaDispositivos.refresh();
}

/**
 * Elimina un dispositivo de la lista y la base de datos según el ID ingresado.
 *
 * @throws NullPointerException Si el campo de ID está vacío o no inicializado.
 * @throws NumberFormatException Si el ID ingresado no es un número válido.
 */
public void deleteDevice() {
    try {
        String id_cadena = textoField.getText();
        int id = Integer.parseInt(id_cadena);
        boolean encontrado = false;
        for (Dispositivo d : SecondaryController.listaDispositivos) {
            if (d.getId() == id) {
                d.delete();
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            System.out.println("Dispositivo borrado con éxito.");
        } else {
            System.out.println("Dispositivo no encontrado.");
        }
    } catch (NullPointerException e) {
        System.out.println("El campo de ID está vacío o no inicializado.");
    } catch (NumberFormatException e) {
        System.out.println("Introduce un ID numérico válido.");
    }
    actualizarTabla();
}

/**
 * Cambia el estado de un dispositivo (funciona/no funciona) según el ID ingresado.
 *
 * @throws NumberFormatException Si el ID ingresado no es un número válido.
 */
public void changeDeviceStatus() {
    String id_cadena = textoField.getText();
    int id = Integer.parseInt(id_cadena);
    boolean encontrado = false;
    for (Dispositivo d : SecondaryController.listaDispositivos) {
        if (d.getId() == id) {
            d.setEstado(!d.getEstado());
            d.save();
            System.out.println(d.toString());
            encontrado = true;
            break;
        }
    }
    if (encontrado) {
        System.out.println("Estado del dispositivo cambiado con éxito.");
    } else {
        System.out.println("Dispositivo no encontrado.");
    }
    actualizarTabla();
}

/**
 * Permite la edición de las columnas "marca" y "modelo" de la tabla de dispositivos.
 */
@FXML
private void switchToModificar() {
    // Configurar la columna "marca" como editable
    column_marca.setCellFactory(TextFieldTableCell.<Dispositivo>forTableColumn());
    column_marca.setOnEditCommit(event -> {
        Dispositivo d = event.getTableView().getItems().get(event.getTablePosition().getRow());
        d.setMarca(event.getNewValue());
        d.save(); // Guardar los cambios en la base de datos o lista
        actualizarTabla(); // Actualizar la tabla para reflejar los cambios
    });

    // Configurar la columna "modelo" como editable
    column_modelo.setCellFactory(TextFieldTableCell.<Dispositivo>forTableColumn());
    column_modelo.setOnEditCommit(event -> {
        Dispositivo d = event.getTableView().getItems().get(event.getTablePosition().getRow());
        d.setModelo(event.getNewValue());
        d.save(); // Guardar los cambios en la base de datos o lista
        actualizarTabla(); // Actualizar la tabla para reflejar los cambios
    });
}

/**
 * Busca un dispositivo por su ID y muestra su información en una nueva ventana.
 *
 * @throws NumberFormatException Si el ID ingresado no es un número válido.
 * @throws IOException Si ocurre un error al cargar la ventana de detalles.
 */
@FXML
public void searchDevice() {
    try {
        String id_cadena = textoField.getText();
        int id = Integer.parseInt(id_cadena);
        Dispositivo dispositivoEncontrado = null;

        for (Dispositivo d : SecondaryController.listaDispositivos) {
            if (d.getId() == id) {
                dispositivoEncontrado = d;
                break;
            }
        }

        if (dispositivoEncontrado == null) {
            mostrarMensaje("Error", "Dispositivo no encontrado.");
            return;
        }

        // Cargar el FXML de la ventana mostrar_dispos.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mostrar_dispos.fxml"));
        Parent root = loader.load();

        // Obtener el controlador correcto
        MostrarDispositivoController controller = loader.getController();

        // Pasar el dispositivo encontrado al controlador para que muestre la información
        controller.mostrarInformacion(dispositivoEncontrado);

        // Mostrar la nueva ventana
        Stage stage = new Stage();
        stage.setTitle("Mostrar Dispositivo");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(tablaDispositivos.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.show();

    } catch (NumberFormatException e) {
        mostrarMensaje("Error", "Introduce un ID numérico válido.");
    } catch (IOException e) {
        mostrarMensaje("Error", "No se pudo cargar la ventana de detalles.");
        e.printStackTrace();  // Para ver el error en la consola
    }
}

}