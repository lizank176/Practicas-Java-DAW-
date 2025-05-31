package com.iescelia;

import javafx.beans.property.SimpleStringProperty;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.control.Tab;

import javafx.scene.control.Alert;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;


public class EventoController {
    @FXML private TableView<Evento> tableEvento;
    @FXML private TableColumn<Evento, String> name;
    @FXML private TableColumn<Evento, String> place;
    @FXML private TableColumn<Evento, String> description;
    @FXML private TableColumn<Evento, String> start_date;
    @FXML private TableColumn<Evento, String> finish_date;
    @FXML private TableColumn<Evento, String> categoria;
    @FXML private TableColumn<Evento, Void> actionColumn; // Columna de Acciones (Botón de eliminar)

    @FXML private Tab tabEvento;

    /**
    * Inicializa el controlador y configura la tabla con los eventos cargados.
    */
    public void initialize() {
        System.out.println("Inicializando...");
        Evento.getAll(); // Suponiendo que este método carga los eventos en listaEventos
        tableEvento.setItems(Evento.listaEventos);

        // Configuración de las columnas
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        place.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLugar()));
        description.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcionEvento()));
        start_date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaInicio()));
        finish_date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaFin()));
        categoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreCategoria()));
        categorias(); // Llamar al método para cargar categorías
        tableEvento.setEditable(true); // Habilitar la edición de las celdas

        // Llamar a la función que maneja la edición de las celdas
        switchToModificar();

        // Detectar doble clic para crear un nuevo evento
        clickToAdd();
        // Añadir la columna de acciones (eliminar)
        addDeleteButtonColumn();
        addExportButtonColumn();

    }
    public void clickToAdd(){
        tableEvento.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Detectar doble clic
                Evento eventoVacio = new Evento(Evento.getLastId()+1, "Nuevo Evento", "Lugar", "2020-12-01", "2020-12-01", "Descripción", 1);
                eventoVacio.save();
                Evento.listaEventos.add(eventoVacio);
                tableEvento.refresh();
                mostrarAlert("Evento Guardado", "Se ha creado un nuevo evento.");
            }
            
        });
    }
    private void addExportButtonColumn() {
        TableColumn<Evento, Void> exportColumn = new TableColumn<>("Exportar");
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
    
        tableEvento.getColumns().add(exportColumn);
    }
    /**
    * Configura la edición de las celdas de la tabla.
    */
    @FXML
    private void switchToModificar() {
        // Configurar las celdas como editables
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> {
            Evento e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            e.setNombre(event.getNewValue());
            e.save(); 
            tableEvento.refresh();
            mostrarAlert("Evento Modificado", "Se ha modificado el nombre del evento.");

        });

        // Repetir para las demás columnas (place, description, etc.)
        place.setCellFactory(TextFieldTableCell.forTableColumn());
        place.setOnEditCommit(event -> {
            Evento e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            e.setLugar(event.getNewValue());
            e.save();
            tableEvento.refresh();
            mostrarAlert("Evento Modificado", "Se ha modificado el lugar del evento.");

        });

        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit(event -> {
            Evento e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            e.setDescripcionEvento(event.getNewValue());
            e.save();
            tableEvento.refresh();
            mostrarAlert("Evento Modificado", "Se ha modificado la descripción del evento.");

        });

        start_date.setCellFactory(TextFieldTableCell.forTableColumn());
        start_date.setOnEditCommit(event -> {
            Evento e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            e.setFechaInicio(event.getNewValue());
            e.save();
            tableEvento.refresh();
            mostrarAlert("Evento Modificado", "Se ha modificado la fecha inicio del evento.");

        });

        finish_date.setCellFactory(TextFieldTableCell.forTableColumn());
        finish_date.setOnEditCommit(event -> {
            Evento e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            e.setHora(event.getNewValue());
            e.save();
            tableEvento.refresh();
            mostrarAlert("Evento Modificado", "Se ha modificado la fecha final del evento.");

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
                    Evento evento = getTableView().getItems().get(getIndex()); // Obtener el evento de la fila
                    eliminarEvento(evento); // Llamar al método para eliminar
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
        tableEvento.getColumns().add(actionColumn);
    }

    // Método auxiliar para obtener el ID de la categoría a partir de su nombre
    private int obtenerIdCategoriaPorNombre(String nombreCategoria) {
        int idCategoria = -1;
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id FROM categoria WHERE nombre = '" + nombreCategoria + "'");

            if (rs.next()) {
                idCategoria = rs.getInt("id");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error al obtener ID de la categoría: " + e.getMessage());
        }
        return idCategoria;
    }

    // Método para eliminar un evento de la base de datos y de la tabla
    private void eliminarEvento(Evento evento) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");

            String sql = "DELETE FROM evento WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, evento.getId());  // Suponiendo que 'evento.getId()' devuelve el ID del evento

            ps.executeUpdate();  // Ejecutar la eliminación

            Evento.listaEventos.remove(evento);  // Eliminar de la lista local
            tableEvento.refresh();  // Refrescar la tabla

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
        mostrarAlert("Evento Eliminado", "Se ha eliminado el evento."); // Mostrar alerta de eliminación

    }
    /**
     * Método para cargar las categorías en la columna de la tabla.
     * Se utiliza un ComboBoxTableCell para permitir la selección de categorías.
     */
    public void categorias(){
        tableEvento.refresh();
        categoria.setCellFactory(ComboBoxTableCell.forTableColumn(Evento.cargarCategorias()));
        categoria.setOnEditCommit(event -> {
            Evento e = event.getTableView().getItems().get(event.getTablePosition().getRow());
            String nombreCategoria = event.getNewValue();
            int idCategoria = obtenerIdCategoriaPorNombre(nombreCategoria); // Obtener el ID de la categoría por su nombre
            e.setId_categoria(idCategoria); // Establecer el ID de la categoría en el evento
            e.save(); // Guardar los cambios en la base de datos
            tableEvento.refresh(); // Refrescar la tabla
            mostrarAlert("Evento Modificado", "Se ha modificado la categoría del evento."); // Mostrar alerta de modificación
        });
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
    public void exportToText() {
        try (FileWriter writer = new FileWriter("evento_export.txt")) {
            writer.write("ID\tNombre\tDescripcion\tLugar\tFecha_Inicio\tFecha_fin\tCategoría\n");
            for (Evento evento : Evento.listaEventos) {
                // Escribir los datos de cada categoría en el archivo       
                writer.write(
                    evento.getId() + "\t" +
                    evento.getNombre() + "\t" +
                    evento.getDescripcionEvento() + "\t" +
                    evento.getLugar()+ "\t" +
                    evento.getFechaInicio()+"\t"+
                    evento.getFechaFin() + "\t" +
                    evento.getNombreCategoria() + "\n"

                   
                );
            }
            mostrarAlert("Exportación completa", "Las categorías  han sido exportadas a evento_export.txt");
        } catch (IOException e) {
            mostrarAlert("Error de exportación", "No se pudo escribir el archivo: " + e.getMessage());
        }
    
    }
    
}
