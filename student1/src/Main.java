import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
//hej

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception { //throws Exception
        String url = "jdbc:sqlite:student1/identifier.sqlite";
        StudentModel SDB = new StudentModel(url);
        Controller control = new Controller(SDB);
        StudentView view = new StudentView(control);
        control.setView(view);

        primaryStage.setTitle("Student- and course information");
        primaryStage.setScene(new Scene(view.asParent(),600,475));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

