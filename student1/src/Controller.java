import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    StudentModel model;
    StudentView view;

    public Controller(StudentModel model){
        this.model=model;
        try {
            model.connect();
            model.CreateStatement();
            model.PreparedStmtFindTripsQuert();
        }catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void setView(StudentView view) {
        this.view = view;
        view.exitBtn.setOnAction(e-> Platform.exit());
        EventHandler<ActionEvent> PrintStudentInfo = e->HandlerPrintStudentinfo(view.StudentComB.getValue(),view.InfoText);
        view.StudentInfoBtn.setOnAction(PrintStudentInfo);
        // der skal også være en event halder for course info:
        EventHandler<ActionEvent> PrintCourseInfo = e->HandlerPrintCourseInfo(view.CourseComB.getValue(), view.InfoText);
        view.CourseInfoBtn.setOnAction(PrintCourseInfo);
    }

    public void HandlerPrintStudentinfo(String name,TextArea txtArea){
        txtArea.clear();
        txtArea.appendText("Here are courses and grades for the student: " + name +"\n");
        //ArrayList<Traintrip> trips = model.FindTrainTrips2(From, To, time);
        // modificering af forrige
        ArrayList<GradeRegister> registers = model.FindGradeRegisters(name);
        /*for (int i=0;i<trips.size();i++){
            String deptime= String.format("%.2f", trips.get(i).departureTime);
            String arrtime=String.format("%.2f", trips.get(i).arrivalTime);
            txtArea.appendText(i+";"+ trips.get(i).FromSt + ": "+ deptime + " -> "+ trips.get(i).ToSt +": "+ arrtime + "\n");
        }*/
        // modificering af for-loop
        for (int i=0; i<registers.size(); i++){
            String courseName = String.format("%.2f", registers.get(i).courseName);
            String grade = String.format("%.2f",registers.get(i).grade);
            txtArea.appendText("Course: " + courseName + ". Grade: " + grade + "\n");
        }
    }
    // har prøvet at lave en handler for course info:
    // har tilføjet teacher, fordi vi skal bruge en join funktion
    public void HandlerPrintCourseInfo(String name,TextArea txtArea){
        txtArea.clear();
        txtArea.appendText("Here is information about the course: " + name +"\n");
        // vi laver en klasse i model der står for at få info fra databasen
        CourseInfo courseInfo = new CourseInfo(name);
        String teacher = String.format("%.2f", courseInfo.teacherName);
        String grade = String.format("%.2f",courseInfo.averageGrade);
        String noStudents = String.format("%.2f", courseInfo.noOfStudents);
        // print information about course to text area:
        txtArea.appendText("Teacher: " + teacher +
                            "\nNumber of students: " + noStudents +
                            "\nAverage grade: " + grade + "\n");
    }

    // følgende bruges til at få elev navne som skal indsættes i combobox i view
    public ObservableList<String> getStudent(){
        ArrayList<String> names= model.SQLQueryStudentNames();
        ObservableList<String> StudentNames= FXCollections.observableArrayList(names);
        return  StudentNames;
    }
    // følgende bruges til at få kursusnavne som skal indsættes i combobox i view
    public ObservableList<String> getCourses(){
        ArrayList<String> names = model.SQLQueryCourseNames();
        ObservableList<String> courseNames = FXCollections.observableArrayList(names);
        return courseNames;
    }
    // resterende get metoder herefter kan slettes
    public ObservableList<Integer> getCourse (){
        ArrayList<Integer> hours=new ArrayList<>();
        for(int i=0;i<24;i++)
        {
            hours.add(i);
        }
        ObservableList<Integer> HoursObs=FXCollections.observableArrayList(hours);
        return HoursObs;
    }


    public ObservableList<Integer> getMinutes(){
        ArrayList<Integer> minutes=new ArrayList<>();
        for(int i=0;i<60;i++)
        {
            minutes.add(i);
        }
        ObservableList<Integer> MinutesObs=FXCollections.observableArrayList(minutes);
        return MinutesObs;
    }



}

