import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class studentView {
    private StudentController control;
    private GridPane Startview;
    Button exitBtn = new Button("Exit");
    Button FindTrainsBtn = new Button("Find Trains");
    Label StartstationLbl = new Label("Select start station:");
    Label EndstationLbl = new Label("Select destination:");
    Label TimeLbl = new Label("Select earliest time:");
    TextArea TrainText = new TextArea();
    ComboBox<String> StartStationComB = new ComboBox<>();
    ComboBox<String> EndStationComB = new ComboBox<>();
    ComboBox<Integer> HourComB = new ComboBox<>();
    ComboBox<Integer> MinuteComB = new ComboBox<>();

    public studentViewt(StudentController control) {
        this.control = control;
        createAndConfigure();
    }
}
