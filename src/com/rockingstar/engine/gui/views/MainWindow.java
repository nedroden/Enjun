package com.rockingstar.engine.gui.views;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow {

    private Stage _mainStage;
    private BorderPane _layout;
    private Scene _scene;

    public MainWindow(Stage mainStage) {
        _mainStage = mainStage;
        _layout = new BorderPane();
        _scene = new Scene(_layout);
        setup();
    }

    private void setup() {
        _mainStage.setMaximized(true);
        _scene.getStylesheets().add("styles/base.css");
        _mainStage.setScene(_scene);
    }

    public void setCenter(Node node) {
        _layout.setCenter(node);
    }

    public void setTitle(String title) {
        _mainStage.setTitle(title);
    }
}