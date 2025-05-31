package com.iescelia;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Controlador para la ventana de mostrar información de un ordenador.
 */
public class MostrarOrdenadorController {

    @FXML
    private TextField marcaField;

    @FXML
    private TextField modeloField;

    @FXML
    private RadioButton estado_noFunciona;

    @FXML
    private RadioButton estado_funciona;

    @FXML
    private TextField ramField;

    @FXML
    private TextField procesadorField;

    @FXML
    private TextField discoField;

    /**
     * Muestra la información de un ordenador en los campos de la ventana.
     *
     * @param ordenador El ordenador cuya información se va a mostrar.
     */
    public void mostrarInformacion(Ordenador ordenador) {
        marcaField.setText(ordenador.getMarca());  // Mostrar la información del dispositivo
        modeloField.setText(ordenador.getModelo());
        procesadorField.setText(ordenador.getProcesador());
        ramField.setText(Integer.toString(ordenador.getRam()));
        discoField.setText(Integer.toString(ordenador.getDisco()));
        if (ordenador.getEstado()) {
            estado_funciona.setSelected(true);
        } else {
            estado_noFunciona.setSelected(true);
        }
    }
}