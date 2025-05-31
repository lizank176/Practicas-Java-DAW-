package com.iescelia;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * Controlador para la ventana de agregar dispositivos.
 */
public class AgregarDispositivoController {

    @FXML
    private TextField marcaField;

    @FXML
    private TextField modeloField;

    @FXML
    private RadioButton estado_funciona;

    @FXML
    private RadioButton estado_noFunciona;
    @FXML private TableView<Dispositivo> tablaDispositivos;

    private Dispositivo dispositivo;
    private DeviceFormControllerCopy controladorPrincipal;
    /**
     * Método para guardar un nuevo dispositivo.
     */
    @FXML
    private void guardarDispositivo() {
        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        boolean estado = estado_funciona.isSelected();

        if (marca.isEmpty() || modelo.isEmpty()) {
            mostrarMensaje("Error", "Todos los campos son obligatorios.");
            return;
        }
        
        // Crear el nuevo dispositivo
        Dispositivo dispositivo = new Dispositivo(marca, modelo, estado);
        dispositivo.save();

        // Agregar el dispositivo a la lista global
        SecondaryController.listaDispositivos.add(dispositivo);
        System.out.println("Dispositivo agregado: " + dispositivo);

        cancelar();
        actualizarTablaEnVentanaPrincipal();
    }

    public void setControladorPrincipal(DeviceFormControllerCopy controladorPrincipal){
        this.controladorPrincipal = controladorPrincipal;
    }
    /**
     * Método para limpiar los campos del formulario.
     */
    public void limpiarCampos() {
        marcaField.clear();
        modeloField.clear();
        estado_funciona.setSelected(false);
        estado_noFunciona.setSelected(false);
    }

    /**
     * Método para mostrar un mensaje de alerta.
     *
     * @param titulo   El título del mensaje.
     * @param contenido El contenido del mensaje.
     */
    private void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, contenido, ButtonType.OK);
        alert.setTitle(titulo);
        alert.showAndWait();
    }

    /**
     * Método para cancelar y cerrar la ventana.
     */
    @FXML
    private void cancelar() {
        Stage stage = (Stage) marcaField.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void actualizarTablaEnVentanaPrincipal() {
        Stage stage = (Stage) marcaField.getScene().getWindow();
    
        if (stage == null) {
            System.out.println("Error: la ventana principal es nula.");
            return;
        }
    
        Scene scene = stage.getScene();
        if (scene == null) {
            System.out.println("Error: la escena principal es nula.");
            return;
        }
    
        Object controllerObj = scene.getUserData();
        if (!(controllerObj instanceof DeviceFormControllerCopy)) {
            System.out.println("Error: No se encontró el controlador principal.");
            return;
        }
    
        DeviceFormControllerCopy controller = (DeviceFormControllerCopy) controllerObj;
        controller.actualizarTabla();
    }
   /**
 * Actualiza la tabla de dispositivos con los datos de la lista de dispositivos.
 */
public void actualizarTabla() {
    tablaDispositivos.setItems(FXCollections.observableArrayList(SecondaryController.listaDispositivos));
    tablaDispositivos.refresh();
}

/**
 * Establece el dispositivo actual para la clase.
 *
 * @param d El dispositivo a establecer.
 */
public void setDispositivo(Dispositivo d) {
    this.dispositivo = d;
}
}
