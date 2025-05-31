module com.iescelia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires javafx.base;
    opens com.iescelia to javafx.fxml;
    exports com.iescelia;
    // Ensure ControlsFX is added to your project dependencies before requiring it
   
    requires java.desktop;
    requires javafx.graphics; 
}
