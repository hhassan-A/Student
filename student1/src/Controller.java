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
            model.PreparedStmtFindStudentInfoQuery();
            //model.PreparedStmtFindCourseInfoQuery();
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
        EventHandler<ActionEvent> InsertGrade = e->HandlerInsertGrade(view.GradeComB.getValue(),view.StudentComB.getValue(),view.CourseComB.getValue(), view.InfoText);
        view.GradeBtn.setOnAction(InsertGrade);
    }

    public void HandlerPrintStudentinfo(String name,TextArea txtArea){
        txtArea.clear();
        txtArea.appendText("Here are courses and grades for the student: " + name +"\n");
        ArrayList<GradeRegister> registers = model.FindGradeRegisters(name);

        for (int i=0; i<registers.size(); i++){
            String courseName = String.format("%s", registers.get(i).courseName);
            String grade = String.format("%s",registers.get(i).grade);

            System.out.println("Course: " + courseName + ". Grade: " + grade);
            txtArea.appendText("Course: " + courseName + ". Grade: " + grade + "\n");
        }
        double avgGrade = model.SQLQueryGetAvarege(name);
        txtArea.appendText("Grade average: " + avgGrade);
    }
    // har prøvet at lave en handler for course info:
    // har tilføjet teacher, fordi vi skal bruge en join funktion
    public void HandlerPrintCourseInfo(String name,TextArea txtArea){
        txtArea.clear();
        txtArea.appendText("Here is information about the course: " + name +"\n");

        // vi laver en klasse i model der står for at få info fra databasen
        CourseInfo courseInfo = model.SQLQUeryGetCourseInfo(name);
        String teacher = String.format("%s", courseInfo.teacherName);
        String grade = String.format("%s",courseInfo.averageGrade);
        String noStudents = String.format("%s", courseInfo.noOfStudents);

        // print information about course to text area:
        txtArea.appendText("Teacher: " + teacher +
                            "\nNumber of students: " + noStudents +
                            "\nAverage grade: " + grade + "\n");
    }

    public void HandlerInsertGrade(int grade, String studentName, String courseName, TextArea txtArea){
        txtArea.clear();
        model.SQLQueryUpdateGrade(grade, studentName, courseName);
    }


        public ObservableList<Integer> getGrades(){
        ArrayList<Integer> grades=new ArrayList<>();

        grades.add(-3);
        grades.add(00);
        grades.add(02);
        grades.add(4);
        grades.add(7);
        grades.add(10);
        grades.add(12);

        ObservableList<Integer> GradesObs=FXCollections.observableArrayList(grades);
        return GradesObs;
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

}

