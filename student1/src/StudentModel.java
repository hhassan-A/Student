import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn = null;
    String url;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    StudentModel(String url){
        this.url = url;
    }
    public void connect() throws SQLException {
        conn=getConnection(url);
    }
    public void close() throws SQLException{
        if (conn != null)
            conn.close();
    }
    public void CreateStatement() throws SQLException{
        this.stmt = conn.createStatement();
    }

    // This method creates and returns an array list with all student names.
    // An SQL query selects all Names in the column Name from the table Student in the database
    // then adds these to the array list
    public ArrayList<String> SQLQueryStudentNames(){
        ArrayList<String> StudentNames = new ArrayList<>();
        String sql = "Select Name From Student";
        try {
            rs = stmt.executeQuery(sql);
            while (rs!= null && rs.next()){
                String name = rs.getString(1);
                StudentNames.add(name);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        rs=null;
        return StudentNames;
    }

    // This method creates and returns an array list with all the course names.
    // An SQL query selects all Names in the column CourseName from the table Course in the database
    // then adds these to the array list
    public ArrayList<String> SQLQueryCourseNames(){
        ArrayList<String> CourseNames = new ArrayList<>();
        String sql = "Select CourseName From Course";
        try {
            rs = stmt.executeQuery(sql);
            while (rs!= null && rs.next()){
                String name = rs.getString(1);
                CourseNames.add(name);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        rs=null;
        return CourseNames;
    }

    // This method gets and returns a double with a given students grade average
    // An SQL query selects/calculates the average from the Grade Column
    // in the Grade_Registration table, but only where the StudentName column matches the given student name.
    public double SQLQueryGetAvarege(String name){
        double avgGrade = 0.0;
        String sql = "SELECT avg(Grade) FROM Grade_Registration WHERE StudentName = \"" + name + "\";";
        try {
            rs = stmt.executeQuery(sql);
            avgGrade = rs.getDouble("avg(Grade)");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        rs=null;
        return avgGrade;
    }

    // This method creates and returns an object of the class CourseInfo
    // the variables for the object is retrieved from the database with a SQL query
    public CourseInfo SQLQUeryGetCourseInfo(String courseName){
        CourseInfo courseInfo = null;

        // This SQL query joins two different tables when they have the same course name
        // to retrieve specific data on a given course from different tables
        String sql = "SELECT avg(Grade), count(StudentName), Course.Teacher FROM Grade_Registration " +
                     "INNER JOIN Course on Grade_Registration.CourseName = Course.CourseName " +
                     "where Course.CourseName= \"" + courseName + "\";";
        try {
            rs = stmt.executeQuery(sql);
            courseInfo = new CourseInfo(courseName,rs.getString("Teacher"),
                         rs.getInt("count(StudentName)"), rs.getDouble("avg(grade)"));
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        rs=null;
        return courseInfo;
    }

    // Creates a prepared statement, that can be used to retrieve information about a given student
    public void PreparedStmtFindStudentInfoQuery(){
        String sql ="SELECT StudentName, CourseName, Grade FROM Grade_Registration WHERE StudentName = ?;";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // An SQl update, that updates the grade for a given student on a given course in the database.
    // This is only possible if the grade is null, so you cant update grades that are already given.
    // The update returns an integer that indicates how many rows that has been updated.
    public int SQLUpdateGrade(int grade, String studentName, String courseName){
        String sql = "update Grade_Registration set Grade = \""+ grade+"\" where StudentName=\""
                     + studentName+"\" and CourseName=\""+courseName+"\" and Grade is null;";
        int updates = 0;
        try {
            updates = stmt.executeUpdate(sql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    return updates;
    }


    // Creates and returns an array list with objects of the GradeRegister class.
    public ArrayList<GradeRegister> FindGradeRegisters(String studentName){
        ArrayList<GradeRegister> gradeRegisters = new ArrayList<>();
        try {
            // Insert the student name into the first parameter index in the prepaed statement
            // and exhicute the prepared statement query
            pstmt.setString(1, studentName);
            rs=pstmt.executeQuery();
            if (rs == null){
                System.out.println("No records fetched.");}

            // Run through each row in the table from the query
            // and create a new object for each row, with its values.
            while(rs != null && rs.next()){
                gradeRegisters.add(new GradeRegister(rs.getString("StudentName"),
                                   rs.getString("CourseName"), rs.getInt("Grade")));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return gradeRegisters;
    }

}

// Class to create instances, where retrieved information can be stored
class GradeRegister{
    String studentName;
    String courseName;
    int grade;

    public GradeRegister(String studentName, String courseName, int grade){
        this.studentName = studentName;
        this.courseName = courseName;
        this.grade = grade;
    }
}

// Class to create instances, where retrieved information can be stored
class CourseInfo{
    String courseName;
    String teacherName;
    int noOfStudents;
    double averageGrade;

    public CourseInfo (String courseName, String teacherName, int noOfStudents, double averageGrade){
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.noOfStudents = noOfStudents;
        this.averageGrade = averageGrade;

    }
}
