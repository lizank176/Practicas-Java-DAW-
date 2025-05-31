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

public class AgregarImpresoraController {

    @FXML
    private TextField marcaField;

    @FXML
    private TextField modeloField;

    @FXML
    private RadioButton estado_funciona;

    @FXML
    private RadioButton estado_noFunciona;
    @FXML private TableView<Impresora> tablaImpresoras;
    
    @FXML
    private TextField colorField;
    @FXML
    private TextField scannerField;
    @FXML
    private RadioButton inyeccion;
    @FXML
    private RadioButton laser;
    @FXML
    private RadioButton otro;
    @FXML
    private TextField discoField;
    @FXML
    private RadioButton color_si;
    @FXML
    private RadioButton color_no;
    @FXML
    private RadioButton scanner_si;
    @FXML
    private RadioButton scanner_no;
    private Dispositivo dispositivo;
    private ImpresoraController controladorPrincipal;
    /**
     * Método para guardar un nuevo dispositivo.
     */
    @FXML
    private void guardarCambios() {
        String marca = marcaField.getText();
        String modelo = modeloField.getText();
        boolean estado = estado_funciona.isSelected();
        int tipo;
        if(laser.isSelected()) tipo=1;
        else if (inyeccion.isSelected()) tipo = 2;
        else tipo = 3;
        boolean scanner;
        if(scanner_si.isSelected()) scanner = true;
        else scanner = false;
        boolean color;
        if(color_si.isSelected()) color = true;
        else color = false;
        if (marca.isEmpty() || modelo.isEmpty()) {
            mostrarMensaje("Error", "Todos los campos son obligatorios.");
            return;
        }
        
        // Crear el nuevo dispositivo
        Impresora impresora = new Impresora(marca, modelo,tipo,color,scanner);
        impresora.save();
        

        // Agregar el dispositivo a la lista global
        ImpresoraController.listaImpresoras.add(impresora);
        SecondaryController.listaDispositivos.add(impresora);
        System.out.println("Dispositivo agregado: " + impresora);

        cancelar();
        actualizarTablaEnVentanaPrincipal();
    }

    public void setControladorPrincipal(ImpresoraController controladorPrincipal){
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
    
        ImpresoraController controller = (ImpresoraController) controllerObj;
        controller.actualizarTabla();
    }
    public void actualizarTabla() {
        tablaImpresoras.setItems(FXCollections.observableArrayList(ImpresoraController.listaImpresoras));
        tablaImpresoras.refresh();
    }
    public void setDispositivo(Dispositivo d) {
        this.dispositivo = d;
    }
}
