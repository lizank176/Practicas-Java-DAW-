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
 * Controlador para la ventana de agregar ordenadores.
 */
public class AgregarOrdenadorController {

    @FXML
    private TextField marcaField;

    @FXML
    private TextField modeloField;

    @FXML
    private RadioButton estado_funciona;

    @FXML
    private RadioButton estado_noFunciona;

    @FXML
    private TableView<Dispositivo> tablaDispositivos;

    @FXML
    private TextField ramField;

    @FXML
    private TextField procesadorField;

    @FXML
    private TextField discoField;

    private Dispositivo dispositivo;
    private OrdenadorController controladorPrincipal;

    /**
     * Método para guardar un nuevo ordenador.
     */
    @FXML
    private void guardarCambios() {
        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        boolean estado = estado_funciona.isSelected();
        String procesador = procesadorField.getText();
        int ram = Integer.parseInt(ramField.getText());
        int disco = Integer.parseInt(discoField.getText());

        if (marca.isEmpty() || modelo.isEmpty()) {
            mostrarMensaje("Error", "Todos los campos son obligatorios.");
            return;
        }

        // Crear el nuevo dispositivo
        Ordenador ordenador = new Ordenador(marca, modelo, procesador, ram, disco);
        ordenador.save();

        // Agregar el dispositivo a la lista global
        OrdenadorController.listaOrdenadores.add(ordenador);
        SecondaryController.listaDispositivos.add(ordenador);
        System.out.println("Dispositivo agregado: " + dispositivo);

        cancelar();
        actualizarTablaEnVentanaPrincipal();
    }

    /**
     * Establece el controlador principal para la comunicación entre ventanas.
     *
     * @param controladorPrincipal El controlador principal a establecer.
     */
    public void setControladorPrincipal(OrdenadorController controladorPrincipal) {
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
     * @param titulo    El título del mensaje.
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

    /**
     * Actualiza la tabla de dispositivos en la ventana principal después de agregar un nuevo ordenador.
     */
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