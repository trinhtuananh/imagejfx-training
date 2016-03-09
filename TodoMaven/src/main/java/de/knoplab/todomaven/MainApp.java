package de.knoplab.todomaven;

import de.knoplab.todomaven.ui.TodoUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.scijava.SciJava;
import static javafx.application.Application.launch;
import org.scijava.Context;
import static javafx.application.Application.launch;

public class MainApp extends Application {

    public Context context;
    @Override
    public void start(Stage stage) throws Exception {
        SciJava scijava = new SciJava();
        context = scijava.context();
        TodoUI ui = new TodoUI(context);
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
