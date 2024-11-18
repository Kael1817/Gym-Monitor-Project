package com.gymmonitor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class database {
    //Ito nalang babaguhin nyo guys!
    static String DB_URL = "jdbc:mysql://localhost:3307/";
    static String DB_NAME = "gym_database";
    static String DB_USERNAME = "root";
    static String DB_PASSWORD = "";
    static String DB_TABLE_ADMIN = "admin"; //ito yung table name ng db para sa login/signup
    static String DB_TABLE_MEMBERS = "members"; // ito yung name ng db para sa mga members
    static String DB_TABLE_COACHES = "coaches";

    public static Connection connectDB() {
        try {
            Connection connect = DriverManager.getConnection(DB_URL + DB_NAME, DB_USERNAME, DB_PASSWORD);
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<members> getDatamembers(){
        Connection connect = database.connectDB();
        ObservableList<members> listview = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM members";
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while(result.next()){
               listview.add(new members(result.getString("expiration"),
                       result.getString("id"),
                       result.getString("name"),
                       result.getString("address"),
                       result.getString("gender"),
                       result.getString("phonenum")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return listview;
    }

    public static ObservableList<coaches> getDatacoaches() {
        Connection connect = database.connectDB();
        ObservableList<coaches> listview = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM coaches";
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                listview.add(new coaches(result.getString("id"),
                        result.getString("name"),
                        result.getString("address"),
                        result.getString("gender"),
                        result.getString("phonenum"),
                        result.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listview;
    }
}
