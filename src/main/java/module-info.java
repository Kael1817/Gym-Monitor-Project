module com.gymmonitor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.gymmonitor to javafx.fxml;
    exports com.gymmonitor;
}