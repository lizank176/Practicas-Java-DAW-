package com.iescelia;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.TextFieldTableCell;

public class CategoriaController {
    @FXML
    private TableView<Categoria> tableCategoria;
    @FXML
    private TableColumn<Categoria, String> nombre;
    @FXML
    private TableColumn<Categoria, String> descripcion;
    @FXML
    private TableColumn<Categoria, Void> actionColumn; // Columna para el botón de eliminar
    @FXML
    private Tab tabCategoria;
    /**
     * Inicializa el controlador y configura la tabla con las categorias cargadas.
     */
    @FXML
    public void initialize() throws Exception {
        Categoria.getAll();
        tableCategoria.setItems(Categoria.listaCategoria);
        nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        descripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        tableCategoria.setEditable(true);
        switchToModificar();
        agregarCategoria();
         // Añadir la columna de acciones (eliminar)
         addDeleteButtonColumn();
         addExportButtonColumn(); // Columna para exportar
    }
    private void addExportButtonColumn() {
        TableColumn<Categoria, Void> exportColumn = new TableColumn<>("Exportar");
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
    
        tableCategoria.getColumns().add(exportColumn);
    }
    /**
     * Método para agregar una nueva categoría.
     * @throws Exception si ocurre un error al agregar la categoría.
     * */
    @FXML
    public void agregarCategoria() throws Exception {
        // Crear una nueva categoría con ID 0 (nuevo registro)
        tableCategoria.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Categoria categoria = new Categoria(0, "Categoria", "Descripción"); // ID 0 para nuevo registro
                categoria.save();
                Categoria.getAll();
                tableCategoria.setItems(Categoria.listaCategoria);
                tableCategoria.refresh();
                mostrarAlert("Categoría Guardada", "Se ha creado una nueva categoría.");

            }
        });
    }

        @FXML
        private void switchToModificar() {
            // Hacer la tabla editable
            tableCategoria.setEditable(true);
    
            // Configurar columna "Nombre" como editable
            nombre.setCellFactory(TextFieldTableCell.forTableColumn());
            nombre.setOnEditCommit(event -> {
                Categoria e = event.getTableView().getItems().get(event.getTablePosition().getRow());
                e.setNombre(event.getNewValue());
                e.save(); // Guardar cambios en la base de datos
                tableCategoria.refresh();
                mostrarAlert("Categoría Modificada", "Se ha modificado el nombre de la categoría.");

            });
            // Configurar columna "Descripción" como editable
            descripcion.setCellFactory(TextFieldTableCell.forTableColumn());
            descripcion.setOnEditCommit(event -> {
                Categoria e = event.getTableView().getItems().get(event.getTablePosition().getRow());
                e.setDescripcion(event.getNewValue());
                e.save(); // Guardar cambios en la base de datos
                tableCategoria.refresh();
                mostrarAlert("Categoría Modificada", "Se ha modificado la descripción de la categoría.");

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
                    Categoria categoria = getTableView().getItems().get(getIndex()); // Obtener el evento de la fila
                    eliminarCategoria(categoria); // Llamar al método para eliminar
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
        tableCategoria.getColumns().add(actionColumn);
    }
    // Método para eliminar un evento de la base de datos y de la tabla
    private void eliminarCategoria(Categoria categoria) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/evento", "root", "root");

            String sql = "DELETE FROM categoria WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, categoria.getId());  // Suponiendo que 'evento.getId()' devuelve el ID del evento

            ps.executeUpdate();  // Ejecutar la eliminación

            Categoria.listaCategoria.remove(categoria);  // Eliminar de la lista local
            tableCategoria.refresh();  // Refrescar la tabla

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
        mostrarAlert("Categoría Eliminada", "Se ha eliminado la categoría."); // Mostrar alerta de eliminación

    }
    public void exportToText() {
        try (FileWriter writer = new FileWriter("categoria_export.txt")) {
            writer.write("ID\tNombre\tDescripción\n");
            for (Categoria categoria : Categoria.listaCategoria) {
                // Escribir los datos de cada categoría en el archivo       
                writer.write(
                    categoria.getId() + "\t" +
                    categoria.getNombre() + "\t" +
                    categoria.getDescripcion() + "\t" + "\n"
                );
            }
            mostrarAlert("Exportación completa", "Las categorías  han sido exportadas a categoria_export.txt");
        } catch (IOException e) {
            mostrarAlert("Error de exportación", "No se pudo escribir el archivo: " + e.getMessage());
        }
    
    }
}
