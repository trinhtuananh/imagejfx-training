/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author tuananh
 */
public class View extends Application{

    private Data myData;
    private ListView<OneTask> list;
    private HBox head;
    private HBox foot;
    private TextField inputTask;
    private Button validTask;
    @Override
    public void start(Stage primaryStage) {
        myData = new Data();
        
        list = new ListView<OneTask>();
        final ObservableList<OneTask> olist = myData.getObservableList();     
        list.setItems(olist);
        list.setCellFactory(new Callback<ListView<OneTask>, ListCell<OneTask>>() {
                @Override 
                public ListCell<OneTask> call(ListView<OneTask> list) {
                    return new ListCellcheckbox();
                }
            }
        );
        
        Button allDone = new Button();
        allDone.setText("All done");
        allDone.setOnAction((ActionEvent e) -> 
        {
            myData.checkAll();
            olist.stream().forEach(ee -> ee.setState(true));
            list.setCellFactory(new Callback<ListView<OneTask>, ListCell<OneTask>>() {
                @Override 
                public ListCell<OneTask> call(ListView<OneTask> list) {
                    return new ListCellcheckbox();
                }
            }
        );
       
      
            
        });
        
        //Head
        head = new HBox();
        head.setPadding(new Insets(15, 12, 15, 12));
        head.getChildren().add(allDone);
        
        
        //Foot
        inputTask = new TextField();
        inputTask.setPromptText("Input task");
        validTask = new Button("Validate Task");
        validTask.setOnAction(e -> 
        {
            //myData.addNewTask(inputTask.getText());
            olist.add(new OneTask((inputTask.getText()), false)); 

        });
        
        foot = new HBox();
        foot.getChildren().addAll(inputTask, validTask);
        
        
        BorderPane root = new BorderPane();
        root.setTop(head);
        root.setCenter(list);
        root.setBottom(foot);
        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().add("de/knoplab/mystylesheet.css");
        
        primaryStage.setTitle("TODO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
   
}
