package de.knoplab.todomaven.ui;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.xalan.internal.xsltc.compiler.FlowList;
import de.knoplab.todomaven.task.TodoTaskWrapper;
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
import de.knoplab.todomaven.task.TodoTask;

import javafx.event.ActionEvent;
import javafx.scene.layout.FlowPane;

public class TodoUI extends AnchorPane {

    private FlowPane flowPane;
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
    private ListView<TodoTaskWrapper> list;
    @FXML
    private Button confidentielButton;
    @FXML
    private Button saveButton;

    @FXML
    private void selectAll() {
        myData.checkAll();
    }

    @FXML
    private void validTask() {
        myData.addNewTask(new TodoTaskWrapper(new DefaultTodoTask(inputTask.getText(), false)));
        inputTask.setText("");
        inputTask.setPromptText("Add an other Task");
    }

    public TodoUI(Context context) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        context.inject(this);
        flowPane = new FlowPane();
        this.getChildren().add(flowPane);
        pluginService.getPluginsOfType(TodoPlugin.class).forEach(this::addPlugin);
        list.setCellFactory((ListView<TodoTaskWrapper> l) -> new ListCellcheckbox());
        Platform.runLater(() -> eventService.publish(new DataLoadEvent()));
    }

    public void addPlugin(PluginInfo<TodoPlugin> todoPluginInfo) {
        Button button = new Button(todoPluginInfo.getLabel());
        System.out.println("creation d'un plugin " + button.getText());
        button.setOnAction((ActionEvent e) -> {
            applyPlugin(todoPluginInfo);
        });
 
        flowPane.getChildren().add(button);

    }

    public void applyPlugin(PluginInfo<TodoPlugin> todoPluginInfo) {

        TodoPlugin plugin;
        try {
            plugin = todoPluginInfo.createInstance();
            this.pluginService.context().inject(plugin);
            plugin.execute();
        } catch (InstantiableException ex) {
            Logger.getLogger(TodoUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @EventHandler
    public void onDataEventAdded(DataAddedEvent event) {
        Platform.runLater(() -> {
            list.getItems().add(new TodoTaskWrapper(event.getData()));
        });
    }

    @EventHandler
    public void onDataDeleteEvent(DataDeleteEvent event) {
        Platform.runLater(() -> {
            System.out.println("size list" + list.getItems().size());
            list.getItems().remove(getWrapperFromTask(event.getData()));
        });
    }

    private TodoTaskWrapper getWrapperFromTask(TodoTask task) {

        return list.getItems().stream().filter(wrapper -> wrapper.getTask() == task).findFirst().orElse(null);
    }

    @EventHandler
    public void onDataCheckAllEvent(DataCheckAllEvent event) {
        Platform.runLater(() -> {
            list.getItems().forEach(e -> e.setState(true));
            list.setCellFactory((this.list.getCellFactory()));
        });

    }
}
