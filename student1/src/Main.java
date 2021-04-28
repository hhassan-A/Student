import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Connect to the database "identifier.sqlite"
        // and initialize classes
        String url = "jdbc:sqlite:student1/identifier.sqlite";
        StudentModel SDB = new StudentModel(url);
        Controller control = new Controller(SDB);
        StudentView view = new StudentView(control);
        control.setView(view);

        // Initialize and show a stage with a new scene
        primaryStage.setTitle("Student- and course information");
        primaryStage.setScene(new Scene(view.asParent(),600,475));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

