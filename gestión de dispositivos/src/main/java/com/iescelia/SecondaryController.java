package com.iescelia;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controlador secundario para la aplicación, maneja la lógica de la lista de dispositivos.
 */
public class SecondaryController {

    /**
     * Lista observable que contiene todos los dispositivos cargados.
     */
    public static ObservableList<Dispositivo> listaDispositivos = FXCollections.observableArrayList();

    /**
     * Carga los datos de los dispositivos desde la base de datos (o persistencia) y los añade a la lista observable.
     * Detecta el tipo de dispositivo y carga los datos específicos según corresponda.
     */
    public static void cargarDatos() {
        listaDispositivos.clear(); // Asegurarse de que la lista esté vacía antes de llenarla
        System.out.println("Cargando dispositivos...");

        int id = 0;
        boolean terminar = false;
        do {
            Dispositivo d = new Dispositivo(id);
            if (d.load() == 0) {
                switch (d.getTipo()) {
                    case 1: // Ordenador
                        Ordenador o = new Ordenador(id);
                        o.load();
                        listaDispositivos.add(o);
                        break;
                    case 2: // Impresora
                        Impresora i = new Impresora(id);
                        i.load();
                        listaDispositivos.add(i);
                        break;
                    default:
                        listaDispositivos.add(d);
                        break;
                }
            } else {
                terminar = true;
            }
            id++;
        } while (!terminar);

        System.out.println("Dispositivos cargados: " + listaDispositivos.size());
        for (Dispositivo dispositivo : listaDispositivos) {
            System.out.println(dispositivo);
        }
    }

    /**
     * Cambia la vista de la aplicación a la vista primaria.
     *
     * @throws IOException Si ocurre un error al cambiar la vista.
     */
    @FXML
    private void switchTo() throws IOException {
        App.setRoot("primary");
    }

    /**
     * Muestra un mensaje de alerta con el título y contenido especificados.
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
     * Vuelve a la vista de selección de tipo de dispositivo.
     *
     * @throws IOException Si ocurre un error al cambiar la vista.
     */
    @FXML
    private void volverInicio() throws IOException {
        App.setRoot("tipo");
    }
}