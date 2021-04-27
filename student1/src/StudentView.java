
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;


public class StudentView {
    private Controller control;
    private GridPane Startview;
    Button exitBtn=new Button("Exit");
    Button StudentInfoBtn= new Button("Get student's info");
    Button CourseInfoBtn= new Button("Get course's info");
    Button GradeBtn= new Button("Insert selected grade for selected student and course");
    Label  StudentLbl=new Label("Select student:");
    Label CourseLbl=new Label("Select course:");
    Label GradeLbl=new Label("Select grade:");
    TextArea InfoText = new TextArea();
    ComboBox<String> StudentComB=new ComboBox<>();
    ComboBox<String> CourseComB=new ComboBox<>();
    ComboBox<Integer> GradeComB=new ComboBox<>();


    public StudentView(Controller control){ // StudentView(StudentController control)
        this.control=control;
        createAndConfigure();
    }

    private void createAndConfigure(){
        Startview=new GridPane();
        Startview.setMinSize(300, 200);
        Startview.setPadding(new Insets(10,10,10,10));
        Startview.setVgap(5);
        Startview.setHgap(1);
        Startview.add(exitBtn,20,20);
        Startview.add(StudentInfoBtn,1,6);
        Startview.add(CourseInfoBtn,1,7);
        Startview.add(GradeBtn,1,8);
        Startview.add(StudentLbl,1,1);
        Startview.add(CourseLbl,1,3);
        Startview.add(GradeLbl, 1, 5);
        Startview.add(InfoText,1,9,15,9);

        Startview.add(StudentComB, 15,1 );
        Startview.add(CourseComB,15,3);
        Startview.add(GradeComB,15,5);


        /*ObservableList<String> stations=control.getStations();
        StartStationComB.setItems(stations);
        EndStationComB.setItems(stations);
        StartStationComB.getSelectionModel().selectFirst();
        EndStationComB.getSelectionModel().selectFirst();
        HourComB.setItems(control.getHours());
        HourComB.getSelectionModel().selectFirst();
        MinuteComB.setItems(control.getMinutes());
        MinuteComB.getSelectionModel().selectFirst();*/
        ObservableList<String> students=control.getStudent();
        StudentComB.setItems(students);
        StudentComB.getSelectionModel().selectFirst();
        CourseComB.setItems(control.getCourses());
        CourseComB.getSelectionModel().selectFirst();
        GradeComB.setItems(control.getGrades());
        GradeComB.getSelectionModel().selectFirst();
    }
    public Parent asParent() {
        return Startview;
    }


}