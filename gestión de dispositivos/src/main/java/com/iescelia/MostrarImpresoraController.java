package com.iescelia;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Controlador para la ventana de mostrar información de una impresora.
 */
public class MostrarImpresoraController {

    @FXML
    private TextField marcaField;
    @FXML
    private TextField modeloField;
    @FXML
    private RadioButton estado_noFunciona;
    @FXML
    private RadioButton estado_funciona;

    @FXML
    private TextField colorField;
    @FXML
    private TextField tipoField;
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

    /**
     * Muestra la información de una impresora en los campos de la ventana.
     *
     * @param impresora La impresora cuya información se va a mostrar.
     */
    public void mostrarInformacion(Impresora impresora) {
        marcaField.setText(impresora.getMarca());  // Mostrar la información del dispositivo
        modeloField.setText(impresora.getModelo());
        if (impresora.getColor()) {
            color_si.setSelected(true);
        } else {
            color_no.setSelected(true);
        }
        if (impresora.getTipo() == 1) {
            laser.setSelected(true);
        } else if (impresora.getTipo() == 2) {
            inyeccion.setSelected(true);
        } else {
            otro.setSelected(true);
        }
        if (impresora.getScanner()) {
            scanner_si.setSelected(true);
        } else {
            scanner_no.setSelected(true);
        }
        if (impresora.getEstado()) {
            estado_funciona.setSelected(true);
        } else {
            estado_noFunciona.setSelected(true);
        }
    }
}