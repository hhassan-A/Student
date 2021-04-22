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

    public ArrayList<String> SQLQueryStationNames(){
        ArrayList<String> Names= new ArrayList<>();
        String sql="Select Name From Station";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()){
                String name = rs.getString(1);
                Names.add(name);
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        rs=null;
        return Names;
    }

    public void StationInfoQuery(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the name of the station you wish to get information on:");
        String station =scanner.nextLine();
        String sql = " SELECT Name, Tracks From Station Where Name='"+station+"';";
        try{
            rs = stmt.executeQuery(sql);
            if (rs==null){
                System.out.println("No station with that name");}
            while(rs != null && rs.next()){
                String stname = rs.getString(1);
                Integer stTracks = rs.getInt(2);
                System.out.println("Station: "+ stname+" has "+ stTracks +" tracks");
            }
            rs = null;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void PreparedStmtFindTripsQuert(){
        String sql = "SELECT D1.StationName,D1.Time,D2.StationName,D2.Time " +
                "From Departure as D1 " +
                "JOIN Departure as D2 ON D1.TrainID=D2.TrainID " +
                "WHERE  D1.StationName= ? AND D2.StationName= ? AND D1.Time> ? AND D1.Time<D2.Time;";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void FindTrainTrips(String fromst, String tost, double time){
        try {
            pstmt.setString(1, fromst);
            pstmt.setString(2, tost);
            pstmt.setDouble(3, time);
            rs=pstmt.executeQuery();
            if (rs == null){
                System.out.println("No records fetched.");}
            while(rs != null && rs.next()){
                System.out.println(" From Station: " + rs.getString(1) + " To Station: " + rs.getString(3));
                System.out.println(" Departure time: " + rs.getDouble(2) + " Arrival time: " + rs.getDouble(4));
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public ArrayList<Traintrip> FindTrainTrips2(String fromst, String tost, double time){
        ArrayList<Traintrip> traintrips = new ArrayList<>();
        try {
            pstmt.setString(1, fromst);
            pstmt.setString(2, tost);
            pstmt.setDouble(3, time);
            rs=pstmt.executeQuery();
            if (rs == null){
                System.out.println("No records fetched.");}
            while(rs != null && rs.next()){
                traintrips.add(new Traintrip(rs.getString(1),rs.getDouble(2), rs.getString(3),rs.getDouble(4)));
                System.out.println(" From Station: " + rs.getString(1) + " To Station: " + rs.getString(3));
                System.out.println(" Departure time: " + rs.getDouble(2) + " Arrival time: " + rs.getDouble(4));
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return traintrips;
    }

    public void PrintStations(ArrayList<String> Stations){
        for (int i = 0; i<Stations.size(); i++){
            System.out.println(Stations.get(i));
        }
    }
}

class Traintrip{
    String Fromst;
    double departureTime;
    String ToSt;
    double arrivalTime;
    public Traintrip(String fromst, double departureTime, String toSt, double arrivalTime){
        this.Fromst = fromst;
        this.ToSt = toSt;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;

    }
}
