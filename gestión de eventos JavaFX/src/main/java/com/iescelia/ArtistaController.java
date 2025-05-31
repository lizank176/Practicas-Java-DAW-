package com.iescelia;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.control.Tab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class ArtistaController {
    
    @FXML private TableView<Artista> tableArtista;
    @FXML private TableColumn<Artista, String> nombre;
    @FXML private TableColumn<Artista, String> apellido1;
    @FXML private TableColumn<Artista, String> apellido2;
    @FXML private TableColumn<Artista, String> fotografia;
    @FXML private TableColumn<Artista, String> obra_destacada;
    @FXML private TableColumn<Artista, String> evento; 
    @FXML private TableColumn<Artista, Void> actionColumn; // Columna de Acciones (Botón de eliminar)

    @FXML private Tab tabArtista;

    /**
     * Inicializa el controlador y configura la tabla con los eventos cargados.
     */
    public void initialize() throws Exception {
        System.out.println("Inicializando...");
        Artista.getAll(); // Suponiendo que este método carga los eventos en listaEventos
        
        // Configuración de las columnas
        nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        apellido1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido1()));
        apellido2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido2()));
        obra_destacada.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObra_destacada()));
        tableArtista.setEditable(true); // Habilitar la edición de las celdas
        fotografia();
        // Configurar la columna de eventos
        evento.setCellValueFactory(cellData -> {
            Artista artista = cellData.getValue();
            return new SimpleStringProperty(
                artista.getEventos().stream()
                    .map(Evento::getNombre)
                    .collect(Collectors.joining(", "))
            );
        });
        // Llamar a la función que maneja la edición de las celdas
        switchToModificar();
        addDeleteButtonColumn(); // Agregar la columna de eliminar
        tableArtista.setItems(Artista.listaArtistas);
        clickToCrear(); // Llamar a la función que maneja el doble clic para crear un nuevo evento
        addExportButtonColumn(); // Agregar la columna de exportar
        // Detectar doble clic para crear un nuevo evento
    }
    public void clickToCrear() throws Exception {
        tableArtista.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                // Crear un nuevo evento
                Artista artistaVacio = new Artista(Persona.getLastId()+1, "Nuevo Artista", "Apellido1", "Apellido2", "Fotografia", "Obra destacada");
                artistaVacio.save(); // Guardar el nuevo artista en la base de datos
                tableArtista.getItems().add(artistaVacio);
                tableArtista.refresh(); // Actualizar la tabla
                mostrarAlert("Artista Guardado", "Se ha creado un nuevo artista.");
                }
            });
                 // Manejar el doble clic en la celda de eventos
        tableArtista.setRowFactory(tv -> {
            TableRow<Artista> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    TableColumn<Artista, ?> col = getTableColumn(row, event.getX());
                    if (col == evento) {
                        mostrarDialogoSeleccionEventos(row.getItem());
                    }
                }
            });
            return row;
        });
    }
    private void addExportButtonColumn() {
        TableColumn<Artista, Void> exportColumn = new TableColumn<>("Exportar");
        exportColumn.setPrefWidth(80);
    
        exportColumn.setCellFactory(param -> new TableCell<>() {
            private final Button exportButton = new Button("📝 Exportar");
    
            {
                exportButton.setOnAction(event -> exportToText());
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : exportButton);
            }
        });
    
        tableArtista.getColumns().add(exportColumn);
    }
    private TableColumn<Artista, ?> getTableColumn(TableRow<Artista> row, double x) {
        double totalWidth = 0;
        for (TableColumn<Artista, ?> column : row.getTableView().getColumns()) {
            totalWidth += column.getWidth();
            if (totalWidth > x) {
                return column;
            }
        }
        return null;
    }
    /**
     * Maneja la edición de las celdas de la tabla.
     */
    @FXML
    private void switchToModificar() {
        // Configurar las columnas como editables
        nombre.setCellFactory(TextFieldTableCell.forTableColumn());
        apellido1.setCellFactory(TextFieldTableCell.forTableColumn());
        apellido2.setCellFactory(TextFieldTableCell.forTableColumn());
        fotografia.setCellFactory(TextFieldTableCell.forTableColumn());
        obra_destacada.setCellFactory(TextFieldTableCell.forTableColumn());
        // Manejar la edición de las celdas
        nombre.setOnEditCommit(event -> {
            Artista e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            e.setNombre(event.getNewValue());
            e.save(); // Guardar cambios en la base de datos
            tableArtista.refresh();
            mostrarAlert("Artista Modificado", "Se ha modificado el nombre del artista.");
        });

        apellido1.setOnEditCommit(event -> {
            Artista e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            e.setApellido1(event.getNewValue());
            e.save(); // Guardar cambios en la base de datos
            tableArtista.refresh();
            mostrarAlert("Artista Modificado", "Se ha modificado el apellido1 del artista.");

        });
        // Manejar la edición de las celdas
        apellido2.setOnEditCommit(event -> {
            Artista e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            e.setApellido2(event.getNewValue());
            e.save(); // Guardar cambios en la base de datos
            tableArtista.refresh();
            mostrarAlert("Artista Modificado", "Se ha modificado el apellido2 del artista.");
        });
        
        // Manejar la edición de las celdas
        obra_destacada.setOnEditCommit(event -> {
            Artista e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            e.setObra_destacada(event.getNewValue());
            e.save(); // Guardar cambios en la base de datos
            tableArtista.refresh();
            mostrarAlert("Artista Modificado", "Se ha modificado la obra destacada del artista.");
        });
        fotografia.setOnEditCommit(event -> {
            Artista e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            e.setFotografia(event.getNewValue());
            e.save(); // Guardar cambios en la base de datos
            tableArtista.refresh();
            mostrarAlert("Artista Modificado", "Se ha modificado la fotografía del artista.");
        });
         // Hacer que la celda sea editable al doble clic
         evento.setCellFactory(column -> new TableCell<Artista, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
        // Agregar los eventos a la tabla
        tableArtista.setEditable(true); // Habilitar la edición de la tabla
        tableArtista.setItems(Artista.listaArtistas); // Agregar los eventos a la tabla
        
        }
     /**
    * Método para agregar la columna de eliminar con el botón.
    */
    private void addDeleteButtonColumn() {
        actionColumn = new TableColumn<>("Borrar");
        actionColumn.setPrefWidth(50);  // Ajusta el ancho de la columna
        actionColumn.getStyleClass().add("actionColumn");
        // Configura la celda de la columna para agregar un botón
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("❌"); // Botón de eliminar

            {
            
                deleteButton.getStyleClass().add("delete-button"); // Agregar la clase CSS al botón
                deleteButton.setOnAction(event -> {
                    Artista artista = getTableView().getItems().get(getIndex()); // Obtener el evento de la fila
                    eliminarArtista(artista); // Llamar al método para eliminar
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);  // Si la celda está vacía, no mostrar el botón
                } else {
                    setGraphic(deleteButton);  // Mostrar el botón de eliminar
                }
            }
        });

        // Añadir la columna de acciones a la tabla
        tableArtista.getColumns().add(actionColumn);
    }
     // Método para eliminar un evento de la base de datos y de la tabla
     private void eliminarArtista(Persona artista) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");

            String sql = "DELETE FROM persona WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, artista.getId());  // Suponiendo que 'evento.getId()' devuelve el ID del evento

            ps.executeUpdate();  // Ejecutar la eliminación
            Persona.listaPersonas.remove(artista);  // Eliminar de la lista local
            Artista.listaArtistas.remove(artista);  // Eliminar de la lista local
            tableArtista.refresh();  // Refrescar la tabla
            mostrarAlert("Artista Eliminado", "Se ha eliminado el artista."); // Mostrar alerta de eliminación
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
       
    }
  
    /**
    *  Método para mostrar una alerta informativa.
    * @param titulo Título de la alerta
    * @param contenido Contenido de la alerta
    */
    private static void mostrarAlert(String titulo, String contenido) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(contenido);
    alert.show();
    }
    
    public void fotografia() {
        fotografia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFotografia()));
        fotografia.setCellFactory(col -> new TableCell<Artista, String>() {
            private final Button fotoButton = new Button("📷 Ver");
    
            {
                fotoButton.setOnAction(event -> {
                    Artista artista = getTableView().getItems().get(getIndex());
                    String relativePath = artista.getFotografia();
    
                    if (relativePath != null && !relativePath.isEmpty()) {
                        try {
                            File file = new File(relativePath); // Ruta relativa
                            if (!file.exists()) {
                                mostrarAlert("Error", "Archivo no encontrado: " + relativePath);
                                return;
                            }
    
                            javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
                            javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
                            imageView.setFitWidth(400);
                            imageView.setPreserveRatio(true);
    
                            javafx.scene.Scene scene = new javafx.scene.Scene(new javafx.scene.layout.StackPane(imageView), 420, 420);
                            javafx.stage.Stage stage = new javafx.stage.Stage();
                            stage.setTitle("Foto del Artista");
                            stage.setScene(scene);
                            stage.show();
                        } catch (Exception e) {
                            mostrarAlert("Error", "No se pudo mostrar la imagen.");
                            e.printStackTrace();
                        }
                    } else {
                        mostrarAlert("Sin imagen", "Este artista no tiene una ruta de imagen.");
                    }
                });
            }
    
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic((empty || item == null || item.isEmpty()) ? null : fotoButton);
            }
        });
    }
    private void mostrarDialogoSeleccionEventos(Artista artista) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Selección de Eventos");
        dialog.setHeaderText("Selecciona los eventos para " + artista.getNombre());

        // Cargar todos los eventos disponibles
        Evento.getAll();

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        List<CheckBox> checkBoxes = new ArrayList<>();

        // Obtener los IDs de los eventos actuales del participante
        Set<Integer> eventosActualesIds = artista.getEventos().stream()
            .map(Evento::getId)
            .collect(Collectors.toSet());

        // Crear CheckBox para cada evento
        for (Evento evento : Evento.listaEventos) {
            CheckBox checkBox = new CheckBox(evento.getNombre() + " (" + evento.getFechaInicio() + ")");
            checkBox.setSelected(eventosActualesIds.contains(evento.getId()));
            checkBoxes.add(checkBox);
            vbox.getChildren().add(checkBox);
        }

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        dialog.getDialogPane().setContent(scrollPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Filtrar solo los eventos seleccionados
            List<Evento> eventosSeleccionados = new ArrayList<>();
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    eventosSeleccionados.add(Evento.listaEventos.get(i));
                }
            }

            // Actualizar los eventos del participante
            artista.participa(FXCollections.observableArrayList(eventosSeleccionados));
            
            // Refrescar la tabla para mostrar los cambios
            tableArtista.refresh();
        }
    }
    public  void exportToText() {
        try (FileWriter writer = new FileWriter("artistas_export.txt")) {
            writer.write("ID\tNombre\tApellido1\tApellido2\tFotografía\tObra destacada\tEventos\n");
            for (Artista artista : tableArtista.getItems()) {
                String eventos = artista.getEventos().stream()
                    .map(Evento::getNombre)
                    .collect(Collectors.joining(", "));
                writer.write(
                    artista.getId() + "\t" +
                    artista.getNombre() + "\t" +
                    artista.getApellido1() + "\t" +
                    artista.getApellido2() + "\t" +
                    artista.getFotografia() + "\t" +
                    artista.getObra_destacada() + "\t" +
                    eventos + "\n"
                );
            }
          
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    
    }
}
