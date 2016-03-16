/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab.manageitems;

import de.knoplab.manageitems.ui.WidgetUI;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author tuananh
 */
public class App extends Application
        
{
    public static void main(String[] args) {
        launch(args);
      
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WidgetUI ui = new WidgetUI();
        Scene scene = new Scene(ui);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
      
    }
    
}
