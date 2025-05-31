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

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ParticipanteController {
    @FXML private TableView<Participante> tableParticipante;
    @FXML private TableColumn<Participante, String> nombre;
    @FXML private TableColumn<Participante, String> apellido1;
    @FXML private TableColumn<Participante, String> apellido2;
    @FXML private TableColumn<Participante, String> email;
    @FXML private TableColumn<Participante, String> evento;
    @FXML private TableColumn<Participante, Void> actionColumn; // Columna de Acciones (Botón de eliminar)
    
    @FXML private Tab tabParticipante;

    public void initialize() {
        System.out.println("Inicializando...");
        Participante.getAll(); // Suponiendo que este método carga los eventos en listaEventos
        tableParticipante.setItems(Participante.listaParticipantes); // Asignar la lista de eventos a la tabla
        tableParticipante.setEditable(true); // Habilitar la edición de la tabla
        // Configurar las columnas de la tabla
        nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        apellido1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido1()));
        apellido2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido2()));
        email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
         // Configurar la columna de eventos
        evento.setCellValueFactory(cellData -> {
            Participante participante = cellData.getValue();
            return new SimpleStringProperty(
                participante.getEventos().stream()
                    .map(Evento::getNombre)
                    .collect(Collectors.joining(", "))
            );
        });
        switchToModificar();
        clickToAdd();
        addDeleteButtonColumn();
        addExportButtonColumn();
    }
    private void addExportButtonColumn() {
        TableColumn<Participante, Void> exportColumn = new TableColumn<>("Exportar");
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
    
        tableParticipante.getColumns().add(exportColumn);
    }
    
    public void clickToAdd(){
        tableParticipante.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                // Crear un nuevo evento
                Participante participante = new Participante(Persona.getLastId()+1, "Nuevo Artista", "Apellido1", "Apellido2", "tuemail@host.com");
                participante.save(); // Guardar el nuevo artista en la base de datos
                tableParticipante.getItems().add(participante); // Agregar el nuevo artista a la tabla
                tableParticipante.refresh(); // Actualizar la tabla
                mostrarAlert("Participante Guardado", "Se ha creado un nuevo participante.");

                }
            });
             // Manejar el doble clic en la celda de eventos
        tableParticipante.setRowFactory(tv -> {
            TableRow<Participante> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    TableColumn<Participante, ?> col = getTableColumn(row, event.getX());
                    if (col == evento) {
                        mostrarDialogoSeleccionEventos(row.getItem());
                    }
                }
            });
            return row;
        });
    }
    private TableColumn<Participante, ?> getTableColumn(TableRow<Participante> row, double x) {
        double totalWidth = 0;
        for (TableColumn<Participante, ?> column : row.getTableView().getColumns()) {
            totalWidth += column.getWidth();
            if (totalWidth > x) {
                return column;
            }
        }
        return null;
    }

    private void mostrarDialogoSeleccionEventos(Participante participante) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Selección de Eventos");
        dialog.setHeaderText("Selecciona los eventos para " + participante.getNombre());

        // Cargar todos los eventos disponibles
        Evento.getAll();

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(15));
        List<CheckBox> checkBoxes = new ArrayList<>();

        // Obtener los IDs de los eventos actuales del participante
        Set<Integer> eventosActualesIds = participante.getEventos().stream()
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
            participante.participa(FXCollections.observableArrayList(eventosSeleccionados));
            
            // Refrescar la tabla para mostrar los cambios
            tableParticipante.refresh();
        }
    }

   

    public void switchToModificar() {
        nombre.setCellFactory(TextFieldTableCell.forTableColumn());
        nombre.setOnEditCommit(event -> {
            Participante participante = event.getRowValue();
            participante.setNombre(event.getNewValue());
            participante.save(); // Guardar los cambios en la base de datos
            mostrarAlert("Participante Modificado", "Se ha modificado el nombre del participante.");
        });
        apellido1.setCellFactory(TextFieldTableCell.forTableColumn());
        apellido1.setOnEditCommit(event -> {
            Participante participante = event.getRowValue();
            participante.setApellido1(event.getNewValue());
            participante.save(); // Guardar los cambios en la base de datos
            mostrarAlert("Artista Modificado", "Se ha modificado el apellido1 del artista.");
        });
        apellido2.setCellFactory(TextFieldTableCell.forTableColumn());
        apellido2.setOnEditCommit(event -> {
            Participante participante = event.getRowValue();
            participante.setApellido2(event.getNewValue());
            participante.save(); // Guardar los cambios en la base de datos
            mostrarAlert("Artista Modificado", "Se ha modificado el apellido2 del artista.");
        });
        email.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setOnEditCommit(event -> {
            Participante participante = event.getRowValue();
            participante.setEmail(event.getNewValue());
            participante.save(); // Guardar los cambios en la base de datos
            mostrarAlert("Artista Modificado", "Se ha modificado el email del artista.");
        });
        // Hacer que la celda sea editable al doble clic
        evento.setCellFactory(column -> new TableCell<Participante, String>() {
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
                    Participante participante = getTableView().getItems().get(getIndex()); // Obtener el evento de la fila
                    eliminarParticipante(participante); // Llamar al método para eliminar
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
          tableParticipante.getColumns().add(actionColumn);
    }
    // Método para eliminar un evento de la base de datos y de la tabla
    private void eliminarParticipante(Participante participante) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");

            String sql = "DELETE FROM persona INNER JOIN persona.id = participante.id_persona WHERE id = ? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, participante.getId());  // Suponiendo que 'evento.getId()' devuelve el ID del evento

            ps.executeUpdate();  // Ejecutar la eliminación

            Participante.listaParticipantes.remove(participante);  // Eliminar de la lista local
            tableParticipante.refresh();  // Refrescar la tabla
            mostrarAlert("Participante Eliminado", "Se ha eliminado el participante.");
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
    private void mostrarAlert(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.show();
    }
    public  void exportToText() {
        try (FileWriter writer = new FileWriter("participante_export.txt")) {
            writer.write("ID\tNombre\tApellido1\tApellido2\tEmail\tEventos\n");
            for (Participante participante : tableParticipante.getItems()) {
                String eventos = participante.getEventos().stream()
                    .map(Evento::getNombre)
                    .collect(Collectors.joining(", "));
                writer.write(
                    participante.getId() + "\t" +
                    participante.getNombre() + "\t" +
                    participante.getApellido1() + "\t" +
                    participante.getApellido2() + "\t" +
                    participante.getEmail() + "\t" +
                    eventos + "\n"
                );
            }
          
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    
    }
}