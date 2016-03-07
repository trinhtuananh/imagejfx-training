package de.knoplab.todomaven;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import org.scijava.plugin.Parameter;

public class FXMLController implements Initializable {
    private Effect effectList;
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
        list.setCellFactory((ListView<TodoTask> list1) -> new ListCellcheckbox());
        
    }
    
    @FXML
    private void validTask(){
        refreshList();
        myData.addNewTask(inputTask.getText());
        inputTask.setText("");
        inputTask.setPromptText("Add an other Task");
        refreshList();
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
        refreshList();
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
    
    public void contructmp()
    {
        effectList = list.getEffect();
    }
}
