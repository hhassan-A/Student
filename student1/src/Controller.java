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
        }catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void setView(StudentView view) {
        this.view = view;
        // Set event handlers for and display the four different buttons
        // Each button calls a method and the methods are given values from the combo boxes
        // Exit button:
        view.exitBtn.setOnAction(e-> Platform.exit());
        // Student info button:
        EventHandler<ActionEvent> PrintStudentInfo = e->HandlerPrintStudentinfo(view.StudentComB.getValue(),view.InfoText);
        view.StudentInfoBtn.setOnAction(PrintStudentInfo);
        // Course info button:
        EventHandler<ActionEvent> PrintCourseInfo = e->HandlerPrintCourseInfo(view.CourseComB.getValue(), view.InfoText);
        view.CourseInfoBtn.setOnAction(PrintCourseInfo);
        // Insert new grade button:
        EventHandler<ActionEvent> InsertGrade = e->HandlerInsertGrade(view.GradeComB.getValue(),view.StudentComB.getValue(),view.CourseComB.getValue(), view.InfoText);
        view.GradeBtn.setOnAction(InsertGrade);
    }

    // Method that handles what happens when the get student info button is pressed.
    public void HandlerPrintStudentinfo(String name,TextArea txtArea){
        txtArea.clear();
        txtArea.appendText("Here are courses and grades for the student: " + name +"\n");

        // Retrieve an array list with all grade registers for a given student
        ArrayList<GradeRegister> registers = model.FindGradeRegisters(name);

        // Loop through all grade registers and display the course name and grade
        for (int i=0; i<registers.size(); i++){
            String courseName = String.format("%s", registers.get(i).courseName);
            String grade = String.format("%s",registers.get(i).grade);
            System.out.println("Course: " + courseName + ". Grade: " + grade);
            txtArea.appendText("Course: " + courseName + ". Grade: " + grade + "\n");
        }
        // Retrieve and display the average grade for a student
        // with a SQL query method in the StudentModel class
        double avgGrade = model.SQLQueryGetAvarege(name);
        txtArea.appendText("Grade average: " + avgGrade);
    }

    // Method that handles what happens when the get course info button is pressed.
    public void HandlerPrintCourseInfo(String name,TextArea txtArea){
        txtArea.clear();
        txtArea.appendText("Here is information about the course: " + name +"\n");

        // Retrieves information from a SQL query method in the StudentModel class
        CourseInfo courseInfo = model.SQLQUeryGetCourseInfo(name);
        String teacher = String.format("%s", courseInfo.teacherName);
        String grade = String.format("%s",courseInfo.averageGrade);
        String noStudents = String.format("%s", courseInfo.noOfStudents);

        // Displays the retrieved information
        txtArea.appendText("Teacher: " + teacher +
                            "\nNumber of students: " + noStudents +
                            "\nAverage grade: " + grade + "\n");
    }

    // Method that handles what happens when the insert grade button is pressed.
    public void HandlerInsertGrade(int grade, String studentName, String courseName, TextArea txtArea){
        txtArea.clear();

        // Updates the grade for a given student on a given course
        // This is done with a SQL update method in the Student model class
        // An integer is returned indicating how many rows that has been changed
        int updates = model.SQLUpdateGrade(grade, studentName, courseName);

        // Display whether it was possible to update the grade or not
        if (updates == 0){
            txtArea.appendText("The student has already been graded on this course");
        }
        else {
            txtArea.appendText("The students grade on " +courseName+ " has been updated to " + grade);
        }
    }

        // Observable list is made with all the grade options for the select grade combo box
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

    // Observable list is made with all the student names for the select student combo box
    public ObservableList<String> getStudent(){
        ArrayList<String> names= model.SQLQueryStudentNames();
        ObservableList<String> StudentNames= FXCollections.observableArrayList(names);
        return  StudentNames;
    }

    // Observable list is made with all the course names for the select course combo box
    public ObservableList<String> getCourses(){
        ArrayList<String> names = model.SQLQueryCourseNames();
        ObservableList<String> courseNames = FXCollections.observableArrayList(names);
        return courseNames;
    }

}

