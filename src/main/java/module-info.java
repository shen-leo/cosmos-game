module ca.bcit.comp2522.termprojec.olu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.java;

    opens ca.bcit.comp2522.termprojec.olu to javafx.fxml;
    exports ca.bcit.comp2522.termprojec.olu;
}