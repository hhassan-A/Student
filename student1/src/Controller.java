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

    public StudentController(StudentModel model){
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

    public void Controller(StudentView view) {
        this.view = view;
        view.exitBtn.setOnAction(e-> Platform.exit());
        EventHandler<ActionEvent> PrintStudentinfo = e->HandlerPrintTrainRoutes(view.StudentComB.getValue(),view.CourseInfoBtn.getValue(),
              ,view.InfoText);
        view.StudentInfoBtn.setOnAction(PrintStudentinfo);
    }
    public void HandlerPrintTrainRoutes(String , String To, Integer Hour, Integer Minutes, TextArea txtArea){
        txtArea.clear();
        txtArea.appendText(" Train, From Station: Departure -> To station: arrival \n");
        double time=(double) Hour +((double) Minutes/100);
        ArrayList<Traintrip> trips= model.FindTrainTrips2(From,To,time);
        for (int i=0;i<trips.size();i++){
            String deptime= String.format("%.2f", trips.get(i).departureTime);
            String arrtime=String.format("%.2f", trips.get(i).arrivalTime);
            txtArea.appendText(i+";"+ trips.get(i).FromSt + ": "+ deptime + " -> "+ trips.get(i).ToSt +": "+ arrtime + "\n");
        }
    }


    public ObservableList<String> getStudent(){
        ArrayList<String> names= model.SQLQueryStationNames();
        ObservableList<String> stationNames= FXCollections.observableArrayList(names);
        return  stationNames;
    }

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

