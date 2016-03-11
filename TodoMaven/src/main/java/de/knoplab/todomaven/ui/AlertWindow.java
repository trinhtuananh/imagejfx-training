/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.todomaven.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author tuananh
 */
public class AlertWindow {
    public static void display (String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(250);
        Label label = new Label(message);
        Button b = new Button("I understand");
        b.setOnAction(e -> window.close());
        BorderPane root = new BorderPane();
        root.setTop(label);
        root.setCenter(b);        
        Scene s = new Scene(root);
        window.setScene(s);
        window.showAndWait();
        
    }
}
