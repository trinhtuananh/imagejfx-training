package de.knoplab.todomaven.ui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.knoplab.todomaven.task.TodoTask;
import de.knoplab.todomaven.event.DataDeleteEvent;
import de.knoplab.todomaven.event.DataCheckAllEvent;
import de.knoplab.todomaven.event.DataAddedEvent;
import de.knoplab.todomaven.event.DataLoadEvent;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import org.scijava.event.EventHandler;
import org.scijava.event.EventService;
import org.scijava.plugin.Parameter;
import de.knoplab.todomaven.task.DataTaskService;
import org.scijava.Context;
import org.scijava.InstantiableException;
import org.scijava.plugin.PluginInfo;
import org.scijava.plugin.PluginService;
import de.knoplab.todomaven.plugins.TodoPlugin;
import de.knoplab.todomaven.task.DefaultTodoTask;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;

public class TodoUI extends AnchorPane {

    private Effect effectList;
    private ObservableList<TodoTask> obsList;
    


    @Parameter
    public DataTaskService myData;

    @Parameter
    public EventService eventService;
    @Parameter
    public PluginService pluginService;
    @FXML
    private TextField inputTask;
    @FXML
    private Button validButton;
    @FXML
    private Button selectAllButton;
    @FXML
    private Button deleteButton;
    @FXML
    private ListView<TodoTask> list;
    @FXML
    private Button confidentielButton;
    @FXML
    private Button saveButton;
    private double x=0;
    @FXML
    private void selectAll() {
        myData.checkAll();
    }

    @FXML
    private void validTask() {
        myData.addNewTask(new DefaultTodoTask(inputTask.getText(), false));
        inputTask.setText("");
        inputTask.setPromptText("Add an other Task");
    }

    @FXML
    private void confidentiel() {
        if (list.getEffect() != null) {
            list.setEffect(null);
        } else {
            list.setEffect(new GaussianBlur());
        }
    }

    @FXML
    private void deleteSelected() {
        myData.deleteSelected();
    }

    public TodoUI(Context context) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        context.inject(this);
        pluginService.getPluginsOfType(TodoPlugin.class).forEach(this::addPlugin);
        List<PluginInfo<TodoPlugin>> lll = pluginService.getPluginsOfType(TodoPlugin.class);
        list.setCellFactory((ListView<TodoTask> l) -> new ListCellcheckbox());
        Platform.runLater(() -> eventService.publish(new DataLoadEvent()));
    }

    public void addPlugin(PluginInfo<TodoPlugin> todoPluginInfo) {
        x= x+100;
        Button button = new Button(todoPluginInfo.getLabel());
        System.out.println("creation d'un plugin "+ button.getText());
       
        button.setOnAction((ActionEvent e) -> {
            applyPlugin(() -> {
                try {
                    TodoPlugin plugin2 =todoPluginInfo.createInstance();
                    this.pluginService.context().inject(plugin2);
                    plugin2.execute();

                } catch (InstantiableException ex) {
                    Logger.getLogger(TodoUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });
        button.setLayoutX(x);
        button.setLayoutY(10);
        this.getChildren().add(button);
        
        

    }

    public void applyPlugin(TodoPlugin p)  {
        try {
            p.execute();
        } catch (IOException ex) {
            Logger.getLogger(TodoUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @EventHandler
    public void onDataEventAdded(DataAddedEvent event) {

        Platform.runLater(() -> {
            list.getItems().add(event.getData());

        }
        );
    }

    @EventHandler
    public void onDataDeleteEvent(DataDeleteEvent event) {
        Platform.runLater(() -> {
            list.getItems().removeAll(event.getData());
        });
    }

    @EventHandler
    public void onDataCheckAllEvent(DataCheckAllEvent event) {
        Platform.runLater(() -> {
            list.getItems().setAll(event.getData());
            list.setCellFactory((this.list.getCellFactory()));
        });

    }

}
