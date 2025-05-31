package com.iescelia;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class Main {
    @FXML private TabPane tabPanePrincipal;
    @FXML private Tab tabEvento;
    @FXML private Tab tabCategoria;
    @FXML private Tab tabArtista;
    @FXML private Tab tabParticipante;
    @FXML private TextField barraBusquedaPrincipal;

 

    
    // Método para conectar el botón de búsqueda global
    @FXML
    public void buscarVistaActual() {
        Tab tabSeleccionado = tabPanePrincipal.getSelectionModel().getSelectedItem();
        String textoBusqueda = barraBusquedaPrincipal.getText();

        if (tabSeleccionado == tabEvento) {
            Evento.getByNombre(textoBusqueda);
        } else if (tabSeleccionado == tabCategoria) {
            Categoria.getByNombre(textoBusqueda);
        } else if (tabSeleccionado == tabArtista) {
            Artista.getByNombre(textoBusqueda);
        } else if (tabSeleccionado == tabParticipante) {
            Participante.getByNombre(textoBusqueda);
        }
    }

    
}

