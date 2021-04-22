package sample;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;


public class StudentView {
    //private StudentController control;
    private GridPane Startview;
    Button exitBtn=new Button("Exit");
    Button StudentInfoBtn= new Button("Get student's info");
    Button CourseInfoBtn= new Button("Get course's info");
    Label  StudentLbl=new Label("Select student:");
    Label CourseLbl=new Label("Select course:");
    TextArea InfoText = new TextArea();
    ComboBox<String> StudentComB=new ComboBox<>();
    ComboBox<String> CourseComB=new ComboBox<>();

    public StudentView(){ // StudentView(StudentController control)
        //this.control=control;
        createAndConfigure();
    }

    private void createAndConfigure(){
        Startview=new GridPane();
        Startview.setMinSize(300, 200);
        Startview.setPadding(new Insets(10,10,10,10));
        Startview.setVgap(5);
        Startview.setHgap(1);
        Startview.add(exitBtn,20,15);
        Startview.add(StudentInfoBtn,15,6);
        Startview.add(CourseInfoBtn,15,10);
        Startview.add(StudentLbl,1,1);
        Startview.add(CourseLbl,1,3);
        Startview.add(InfoText,1,7,15,7);

        Startview.add(StudentComB, 15,1 );
        Startview.add(CourseComB,15,3);


       /* ObservableList<String> stations=control.getStations();
        StartStationComB.setItems(stations);
        EndStationComB.setItems(stations);
        StartStationComB.getSelectionModel().selectFirst();
        EndStationComB.getSelectionModel().selectFirst();
        HourComB.setItems(control.getHours());
        HourComB.getSelectionModel().selectFirst();
        MinuteComB.setItems(control.getMinutes());
        MinuteComB.getSelectionModel().selectFirst();*/

    }
    public Parent asParent() {
        return Startview;
    }


}