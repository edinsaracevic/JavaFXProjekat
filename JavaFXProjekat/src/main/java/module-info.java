module org.edin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens org.edin to javafx.fxml;
    opens org.edin.model to javafx.base;
    exports org.edin;
}
