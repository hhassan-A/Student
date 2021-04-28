
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

    // Creates all the different buttons, labels, combo boxes and the text area
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


    public StudentView(Controller control){
        this.control=control;
        createAndConfigure();
    }

    private void createAndConfigure(){
        // Creates a new GridPane and sets the layout
        Startview=new GridPane();
        Startview.setMinSize(300, 200);
        Startview.setPadding(new Insets(10,10,10,10));
        Startview.setVgap(5);
        Startview.setHgap(1);

        // Inserts all the elements at specified locations in the GridPane StartView
        Startview.add(exitBtn,20,20);
        Startview.add(StudentInfoBtn,1,7);
        Startview.add(CourseInfoBtn,1,8);
        Startview.add(GradeBtn,1,9);

        Startview.add(StudentLbl,1,1);
        Startview.add(CourseLbl,1,3);
        Startview.add(GradeLbl, 1, 5);

        Startview.add(InfoText,1,10,15,10);

        Startview.add(StudentComB, 2,1 );
        Startview.add(CourseComB,2,3);
        Startview.add(GradeComB,2,5);

        // Set items for the three different combo boxes
        // and sets the first shown/selected item
        StudentComB.setItems(control.getStudent());
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