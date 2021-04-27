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

    // denne metode skal bruges i Controller til getStudent,
    // som bruges i view til at indsætte alle elevers navne ind i combobox
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
    // denne metode skal bruges i Controller til getCourse,
    // som bruges i view til at indsætte alle kursusnavne ind i combobox
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
    public CourseInfo SQLQUeryGetCourseInfo(String courseName){
        CourseInfo courseInfo = null;
        String sql = "SELECT avg(Grade), count(StudentName), Course.Teacher FROM Grade_Registration INNER JOIN Course on Grade_Registration.CourseName = Course.CourseName where Course.CourseName= \"" + courseName + "\";";
        try {
            rs = stmt.executeQuery(sql);
            courseInfo = new CourseInfo(courseName,rs.getString("Teacher"), rs.getInt("count(StudentName)"), rs.getDouble("avg(grade)"));
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        rs=null;
        return courseInfo;
    }


    public void PreparedStmtFindStudentInfoQuery(){
        String sql ="SELECT StudentName, CourseName, Grade FROM Grade_Registration WHERE StudentName = ?;";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void SQLQueryUpdateGrade(int grade, String studentName, String courseName){
        String sql = "update Grade_Registration set Grade = \""+ grade+"\" where StudentName=\""+ studentName+" \" and CourseName=\""+courseName+"\";";
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    // Claras bud på en omskrivning af FindTrainTrips2, denne bruger istedet den klasse jeg har lavet i bunden der hedder GradeRegister
    // så der laves her en array-list med grade registers for elev med givent navn
    public ArrayList<GradeRegister> FindGradeRegisters(String studentName){
        ArrayList<GradeRegister> gradeRegisters = new ArrayList<>();
        try {
            pstmt.setString(1, studentName);
            rs=pstmt.executeQuery();
            if (rs == null){
                System.out.println("No records fetched.");}
            while(rs != null && rs.next()){
                gradeRegisters.add(new GradeRegister(rs.getString("StudentName"),rs.getString("CourseName"), rs.getInt("Grade")));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return gradeRegisters;
    }

}

// er ret sikker på at vi skal have følgende klasse istedet for Traintrip
// hver grade register bliver tilføjet til en array-list i FindTrainTrips2 eller vores kunne hede FindGradeRegisters
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
