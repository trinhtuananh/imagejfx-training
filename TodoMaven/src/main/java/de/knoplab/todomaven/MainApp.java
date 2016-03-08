package de.knoplab.todomaven;

import de.knoplab.todomaven.ui.TodoUI;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.scijava.SciJava;
import org.scijava.event.EventHandler;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;

public class MainApp extends Application {

    @EventHandler

    @Override
    public void start(Stage stage) throws Exception {

        TodoUI ui = new TodoUI();
        SciJava scijava = new SciJava();
        scijava.context().inject(ui);
        Scene scene = new Scene(ui);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Todo");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
