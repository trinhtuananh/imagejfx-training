package de.knoplab.todomaven;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import org.scijava.event.EventHandler;
import org.scijava.plugin.Parameter;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

public class FXMLController implements Initializable {
    private Effect effectList;
    private ObservableList<TodoTask> obsList;
    
    @Parameter
    public IDataTaskService myData;
    
    @FXML
    private TextField inputTask;
    @FXML
    private Button valid;
    @FXML
    private Button selectAll;
    @FXML
    private Button delete;
    @FXML
    private ListView<TodoTask> list;
    @FXML
    private Button confidentiel;
    @FXML
    private void selectAll() {
        myData.checkAll();
    }
    
    @FXML
    private void validTask(){
        myData.addNewTask(inputTask.getText());
        inputTask.setText("");
        inputTask.setPromptText("Add an other Task");
        //refreshList();
    }
    @FXML
    private void confidentiel()
    {
        if (list.getEffect() != null)
        {
            list.setEffect(null);   
        }
        else
        {
            list.setEffect(new GaussianBlur());     
        }
    }
    
    @FXML
    private void deleteSelected(){
        myData.deleteSelected();
    }
    
    public void refreshList(){
        list.setItems(myData.getObservableList());    
        list.setCellFactory((ListView<TodoTask> list1) -> new ListCellcheckbox());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //myData = new DataTask();
        //refreshList();
        effectList = list.getEffect();
        
    }  
    @EventHandler
    public void onDataEventAdded(DataAddedEvent event) {        
        Platform.runLater(() -> list.getItems().add(event.getData()));
    }
    
    @EventHandler
    public void onDataDeleteEvent (DataDeleteEvent event) {  
        Platform.runLater(() -> list.getItems().removeAll(event.getData()));
    }
   
        @EventHandler
        public void onDataCheckAllEvent(DataCheckAllEvent event){
        list.setCellFactory(new Callback<ListView<TodoTask>, ListCell<TodoTask>>() {
            @Override
            public ListCell<TodoTask> call(ListView<TodoTask> l) {
                return new ListCellcheckbox();
            }
        });
        }
   
    }
    
   
    
   

