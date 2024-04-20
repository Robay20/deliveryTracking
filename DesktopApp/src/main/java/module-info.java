module com.example.packet_tracer {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.packet_tracer to javafx.fxml;
    opens com.example.packet_tracer.admin to javafx.fxml;
    opens com.example.packet_tracer.expediteur to javafx.fxml;
    exports com.example.packet_tracer;
    exports com.example.packet_tracer.admin to javafx.fxml;
    exports com.example.packet_tracer.expediteur to javafx.fxml;
}