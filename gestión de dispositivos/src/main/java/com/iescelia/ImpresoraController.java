package com.iescelia;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controlador para la vista de impresoras, maneja la tabla y operaciones relacionadas con impresoras.
 */
public class ImpresoraController {
    @FXML private TableView<Impresora> tablaImpresoras;
    @FXML private TableColumn<Impresora, String> columnId;
    @FXML private TableColumn<Impresora, String> columnMarca;
    @FXML private TableColumn<Impresora, String> columnModelo;
    @FXML private TableColumn<Impresora, String> columnEstado;
    @FXML private TableColumn<Impresora, String> columnTipo;
    @FXML private TableColumn<Impresora, String> columnScanner;
    @FXML private TableColumn<Impresora, String> columnColor;
    @FXML private TextField textoField;
    @FXML private TextArea info;

    static ObservableList<Impresora> listaImpresoras = FXCollections.observableArrayList();
/**
 * Inicializa el controlador, carga las impresoras y configura las columnas de la tabla.
*/
    @FXML
    public void initialize() {
        cargarImpresora();
        configurarColumnas();
    }
/**
 * Carga las impresoras desde la lista global de dispositivos y las muestra en la tabla.
 */
    private void cargarImpresora() {
        listaImpresoras.clear();
        for (Dispositivo d : SecondaryController.listaDispositivos) {
            if (d instanceof Impresora) {
                listaImpresoras.add((Impresora) d);
            }
        }
        tablaImpresoras.setItems(listaImpresoras);
        tablaImpresoras.refresh();
    }

/**
 * Configura las columnas de la tabla con los datos de las impresoras y las hace editables.
 */
    private void configurarColumnas() {
        columnId.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getIdImpresora())));
        columnMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
        columnModelo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        columnEstado.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().getEstado())));
        columnColor.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().getColor())));
        columnTipo.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getTipo())));
        columnScanner.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().getScanner())));
        tablaImpresoras.setEditable(true);
        hacerColumnasEditables();
    }
/**
 * Hace las columnas de marca, modelo, tipo, scanner y color editables.
 */
    private void hacerColumnasEditables() {
        columnMarca.setCellFactory(TextFieldTableCell.forTableColumn());
        columnMarca.setOnEditCommit(event -> {
            Impresora o = event.getRowValue();
            o.setMarca(event.getNewValue());
            o.save();
            tablaImpresoras.refresh();
        });

        columnModelo.setCellFactory(TextFieldTableCell.forTableColumn());
        columnModelo.setOnEditCommit(event -> {
            Impresora o = event.getRowValue();
            o.setModelo(event.getNewValue());
            o.save();
            tablaImpresoras.refresh();
        
        });

        columnTipo.setCellFactory(TextFieldTableCell.forTableColumn());
        columnTipo.setOnEditCommit(event -> {
            Impresora o = event.getRowValue();
            try {
                int tiponew = Integer.parseInt(event.getNewValue());
                o.setTipo(tiponew);
                o.save();
            } catch (NumberFormatException e) {
                mostrarMensaje("Error", "La RAM debe ser un número.");
            }
            tablaImpresoras.refresh();
        });

        columnScanner.setCellFactory(TextFieldTableCell.forTableColumn());
        columnScanner.setOnEditCommit(event -> {
            Impresora o = event.getRowValue();
            try {
                boolean scanner = Boolean.parseBoolean(event.getNewValue());
                o.setScanner(scanner);
                o.save();
            } catch (NumberFormatException e) {
                mostrarMensaje("Error", "La RAM debe ser un número.");
            }
            tablaImpresoras.refresh();
        });
        columnColor.setCellFactory(TextFieldTableCell.forTableColumn());
        columnColor.setOnEditCommit(event -> {
            Impresora o = event.getRowValue();
            try {
                boolean color = Boolean.parseBoolean(event.getNewValue());
                o.setColor(color);
                o.save();
            } catch (NumberFormatException e) {
                mostrarMensaje("Error", "La RAM debe ser un número.");
            }
            tablaImpresoras.refresh();
        });
    }
/**
 * Abre la ventana para agregar una nueva impresora.
 *
 * @throws IOException Si ocurre un error al cargar el FXML.
 */
    @FXML
    private void agregarImpresora() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("agregar_impresora.fxml"));
        Parent root = loader.load();
        
        AgregarImpresoraController controller = loader.getController();
        controller.setControladorPrincipal(this);

        Stage stage = new Stage();
        stage.setTitle("Añadir nueva impresora");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(tablaImpresoras.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.show();
    }
/**
 * Elimina una impresora según el ID ingresado.
 */
    @FXML
    private void eliminarImpresora() {
        try {
            int id = Integer.parseInt(textoField.getText());
            boolean encontrado = false;

            for (Impresora o : listaImpresoras) {
                if (o.getIdImpresora() == id) {
                    o.delete();
                    listaImpresoras.remove(o);
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
                tablaImpresoras.refresh();
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Introduce un ID numérico válido.");
        }
    }
/**
 * Busca una impresora por su ID y muestra su información en una nueva ventana.
 */
    @FXML
    private void buscarImpresora() {
        try {
            int id = Integer.parseInt(textoField.getText());
            Impresora impresoraEncontrado = null;

            for (Impresora o : listaImpresoras) {
                if (o.getIdImpresora() == id) {
                    impresoraEncontrado = o;
                    break;
                }
            }

            if (impresoraEncontrado == null) {
                mostrarMensaje("Error", "Ordenador no encontrado.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("mostrar_ordenador.fxml"));
            Parent root = loader.load();

            MostrarImpresoraController controller = loader.getController();
            controller.mostrarInformacion(impresoraEncontrado);

            Stage stage = new Stage();
            stage.setTitle("Mostrar Ordenador");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tablaImpresoras.getScene().getWindow());
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
/**
 * Actualiza la tabla de impresoras con los datos de la lista de impresoras.
 */
    public void actualizarTabla() {
        tablaImpresoras.setItems(FXCollections.observableArrayList(listaImpresoras));
        tablaImpresoras.refresh();
    }
}


