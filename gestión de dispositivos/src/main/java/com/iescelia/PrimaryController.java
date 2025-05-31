package com.iescelia;

import java.io.*;
import java.util.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controlador principal para la aplicación, maneja la navegación entre diferentes vistas.
 */
public class PrimaryController {

    @FXML
    private TextArea tabla;

    /**
     * Cambia la vista de la aplicación a la vista de la tabla de ordenadores.
     *
     * @throws IOException Si ocurre un error al cambiar la vista.
     */
    @FXML
    private void switchToComputer() throws IOException {
        App.setRoot("tabla_ordenador");
        //cargar datos
    }

    /**
     * Cambia la vista de la aplicación a la vista de la tabla de impresoras.
     *
     * @throws IOException Si ocurre un error al cambiar la vista.
     */
    @FXML
    private void switchToImpresora() throws IOException {
        App.setRoot("tabla_impresora");
        //cargar datos
    }

    /**
     * Vuelve a la vista de selección de tipo de dispositivo.
     *
     * @throws IOException Si ocurre un error al cambiar la vista.
     */
    @FXML
    private void volverInicio() throws IOException {
        App.setRoot("tipo");
    }

    /**
     * Cambia la vista de la aplicación a la vista de la tabla de dispositivos genéricos.
     *
     * @throws IOException Si ocurre un error al cambiar la vista.
     */
    @FXML
    private void switchToDevice() throws IOException {
        App.setRoot("tabla_dispositivo_copy");
    }
}