/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.knoplab;

import java.awt.GridLayout;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    private Button deleteSelected;
    @Override
    public void start(Stage primaryStage) {
        myData = new Data();
        
        list = new ListView<OneTask>();
        list.setItems(myData.getObservableList());
        list.setCellFactory((ListView<OneTask> list1) -> new ListCellcheckbox());
        
        Button allDone = new Button();
        allDone.setText("All done");
        allDone.setOnAction((ActionEvent e) -> 
        {
            myData.checkAll();
            list.setCellFactory((ListView<OneTask> list1) -> new ListCellcheckbox());
        });
        
        //Head
        head = new HBox();
        head.setPadding(new Insets(15, 12, 15, 12));
        head.getChildren().add(allDone);
        head.setStyle("-fx-background-color: #385f70;");

        
        //Foot
        inputTask = new TextField();
        inputTask.setPromptText("Input task");
        validTask = new Button("Validate Task");
        validTask.setOnAction(e -> 
        {
            myData.addNewTask(inputTask.getText());
            list.setItems(myData.getObservableList());    
            list.setCellFactory((ListView<OneTask> list1) -> new ListCellcheckbox());
        });
        deleteSelected = new Button("Delete All selected");
        deleteSelected.setOnAction(e -> {
            myData.deleteSelected();
            list.setItems(myData.getObservableList());    
            list.setCellFactory((ListView<OneTask> list1) -> new ListCellcheckbox());
        });
        foot = new HBox();
        
        GridPane grid = new GridPane();
        foot.getChildren().addAll(inputTask, validTask, deleteSelected);
        
        
        BorderPane root = new BorderPane();
        root.setTop(head);
        root.setCenter(list);
        root.setBottom(foot);
        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().add("de/knoplab/mystylesheet.css");
        
        primaryStage.setTitle("TODO");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);
        primaryStage.show();
    }
   
}
