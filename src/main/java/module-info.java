module demoFxApp {
    requires javafx.base;
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;
    requires Ensemble8;
    requires reflections;

    requires transitive java.logging;
    requires transitive java.sql;
    
    exports demo.ensemble.app;

}
