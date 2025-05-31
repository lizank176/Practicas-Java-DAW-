package com.iescelia;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Controlador para la ventana de mostrar información de un dispositivo.
 */
public class MostrarDispositivoController {

    @FXML
    private TextField marcaField;

    @FXML
    private TextField modeloField;

    @FXML
    private RadioButton estado_noFunciona;

    @FXML
    private RadioButton estado_funciona;

    /**
     * Muestra la información de un dispositivo en los campos de la ventana.
     *
     * @param dispositivo El dispositivo cuya información se va a mostrar.
     */
    public void mostrarInformacion(Dispositivo dispositivo) {
        marcaField.setText(dispositivo.getMarca());  // Mostrar la información del dispositivo
        modeloField.setText(dispositivo.getModelo());
        if (dispositivo.getEstado()) {
            estado_funciona.setSelected(true);
        } else {
            estado_noFunciona.setSelected(true);
        }
    }
}