module com.example.packet_tracer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    // Include these only if you use these libraries explicitly
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    // Package exports
    exports com.example.packet_tracer;
    exports com.example.packet_tracer.admin to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.example.packet_tracer.expediteur to javafx.fxml;

    // Package opens
    // Open 'com.example.packet_tracer.admin' to both javafx.fxml, javafx.base, and com.fasterxml.jackson.databind
    opens com.example.packet_tracer.admin to javafx.fxml, javafx.base, com.fasterxml.jackson.databind;

    // Open 'com.example.packet_tracer' only to javafx.fxml
    opens com.example.packet_tracer to javafx.fxml;

    // Open 'com.example.packet_tracer.expediteur' only to javafx.fxml
    opens com.example.packet_tracer.expediteur to javafx.fxml;
}
