package com.iescelia;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador para la vista de ordenadores, maneja la tabla y operaciones relacionadas con ordenadores.
 */
public class OrdenadorController {

    @FXML
    private TableView<Ordenador> tablaOrdenadores;
    @FXML
    private TableColumn<Ordenador, String> columnId;
    @FXML
    private TableColumn<Ordenador, String> columnMarca;
    @FXML
    private TableColumn<Ordenador, String> columnModelo;
    @FXML
    private TableColumn<Ordenador, String> columnEstado;
    @FXML
    private TableColumn<Ordenador, String> columnRam;
    @FXML
    private TableColumn<Ordenador, String> columnProcesador;
    @FXML
    private TableColumn<Ordenador, String> columnDisco;
    @FXML
    private TextField textoField;
    @FXML
    private TextArea info;

    static ObservableList<Ordenador> listaOrdenadores = FXCollections.observableArrayList();

    /**
     * Inicializa el controlador, carga los ordenadores y configura las columnas de la tabla.
     */
    @FXML
    public void initialize() {
        cargarOrdenadores();
        configurarColumnas();
    }

    /**
     * Carga los ordenadores desde la lista global de dispositivos y los muestra en la tabla.
     */
    private void cargarOrdenadores() {
        listaOrdenadores.clear();
        for (Dispositivo d : SecondaryController.listaDispositivos) {
            if (d instanceof Ordenador) {
                listaOrdenadores.add((Ordenador) d);
            }
        }
        tablaOrdenadores.setItems(listaOrdenadores);
        tablaOrdenadores.refresh();
    }

    /**
     * Configura las columnas de la tabla con los datos de los ordenadores y las hace editables.
     */
    private void configurarColumnas() {
        columnId.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getIdOrdenador())));
        columnMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
        columnModelo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        columnEstado.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().getEstado())));
        columnRam.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getRam())));
        columnProcesador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProcesador()));
        columnDisco.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getRam())));
        tablaOrdenadores.setEditable(true);
        hacerColumnasEditables();
    }

    /**
     * Hace las columnas de marca, modelo, RAM y procesador editables.
     */
    private void hacerColumnasEditables() {
        columnMarca.setCellFactory(TextFieldTableCell.forTableColumn());
        columnMarca.setOnEditCommit(event -> {
            Ordenador o = event.getRowValue();
            o.setMarca(event.getNewValue());
            o.save();
            tablaOrdenadores.refresh();
        });

        columnModelo.setCellFactory(TextFieldTableCell.forTableColumn());
        columnModelo.setOnEditCommit(event -> {
            Ordenador o = event.getRowValue();
            o.setModelo(event.getNewValue());
            o.save();
            tablaOrdenadores.refresh();
        });

        columnRam.setCellFactory(TextFieldTableCell.forTableColumn());
        columnRam.setOnEditCommit(event -> {
            Ordenador o = event.getRowValue();
            try {
                int nuevaRam = Integer.parseInt(event.getNewValue());
                o.setRam(nuevaRam);
                o.save();
            } catch (NumberFormatException e) {
                mostrarMensaje("Error", "La RAM debe ser un número.");
            }
            tablaOrdenadores.refresh();
        });

        columnProcesador.setCellFactory(TextFieldTableCell.forTableColumn());
        columnProcesador.setOnEditCommit(event -> {
            Ordenador o = event.getRowValue();
            o.setProcesador(event.getNewValue());
            o.save();
            tablaOrdenadores.refresh();
        });
    }

    /**
     * Abre la ventana para agregar un nuevo ordenador.
     *
     * @throws IOException Si ocurre un error al cargar el FXML.
     */
    @FXML
    private void agregarOrdenador() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("agregar_ordenador.fxml"));
        Parent root = loader.load();

        AgregarOrdenadorController controller = loader.getController();
        controller.setControladorPrincipal(this);

        Stage stage = new Stage();
        stage.setTitle("Añadir nuevo ordenador");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(tablaOrdenadores.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Elimina un ordenador según el ID ingresado.
     */
    @FXML
    private void eliminarOrdenador() {
        try {
            int id = Integer.parseInt(textoField.getText());
            boolean encontrado = false;

            for (Ordenador o : listaOrdenadores) {
                if (o.getIdOrdenador() == id) {
                    o.delete();
                    listaOrdenadores.remove(o);
                    encontrado = true;
                    break;
                }
            }
            for (Dispositivo o : SecondaryController.listaDispositivos) {
                if (o.getIdAjeno() == id) {
                    o.delete();
                    SecondaryController.listaDispositivos.remove(o);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                mostrarMensaje("Error", "Ordenador no encontrado.");
            } else {
                tablaOrdenadores.refresh();
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Introduce un ID numérico válido.");
        }
    }

    /**
     * Busca un ordenador por su ID y muestra su información en una nueva ventana.
     */
    @FXML
    private void buscarOrdenador() {
        try {
            int id = Integer.parseInt(textoField.getText());
            Ordenador ordenadorEncontrado = null;

            for (Ordenador o : listaOrdenadores) {
                if (o.getIdOrdenador() == id) {
                    ordenadorEncontrado = o;
                    break;
                }
            }

            if (ordenadorEncontrado == null) {
                mostrarMensaje("Error", "Ordenador no encontrado.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("mostrar_ordenador.fxml"));
            Parent root = loader.load();

            MostrarOrdenadorController controller = loader.getController();
            controller.mostrarInformacion(ordenadorEncontrado);

            Stage stage = new Stage();
            stage.setTitle("Mostrar Ordenador");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tablaOrdenadores.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Introduce un ID numérico válido.");
        } catch (IOException e) {
            mostrarMensaje("Error", "No se pudo cargar la ventana de detalles.");
        }
    }

    /**
     * Muestra un mensaje de alerta con el título y contenido especificados.
     *
     * @param titulo    El título del mensaje.
     * @param contenido El contenido del mensaje.
     */
    private void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR, contenido, ButtonType.OK);
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