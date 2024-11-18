package com.gymmonitor;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginControl {

    @FXML
    private Button button;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text incorrectWarning;
    @FXML
    private Text textFieldWarning;
    @FXML
    private TextField username1;
    @FXML
    private TextField password1;
    @FXML
    private TextField password2;
    @FXML
    private Text matchWarning;
    @FXML
    private Text existWarning;
    @FXML
    private Text textfieldWarning;

    //DATABASE TOOLS
    private Connection connect;
    private ResultSet result;

    public void login (){
        PreparedStatement prepare;

        String sql = "SELECT * FROM " + database.DB_TABLE_ADMIN + " WHERE username = ? and password = ?";

        connect = database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            result = prepare.executeQuery();

            if(username.getText().isEmpty() || password.getText().isEmpty()){
                incorrectWarning.setVisible(true);
                textfieldWarning.setVisible(false);
            }else if(result.next()){
                HelloApplication m = new HelloApplication();
                m.changeScene("Landing.fxml");
            }else {
                incorrectWarning.setVisible(false);
                textfieldWarning.setVisible(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createAccount()throws IOException{
        HelloApplication m = new HelloApplication();
        m.changeScene("Signup.fxml");
    }

    public void backToLogin()throws IOException{
        HelloApplication m = new HelloApplication();
        m.changeScene("Login.fxml");
    }

    public void signup(){
        String checkExist = "SELECT * FROM " + database.DB_TABLE_ADMIN + " WHERE username = ?";
        String addAdmin = "INSERT INTO " + database.DB_TABLE_ADMIN + " (username, password) VALUES (?, ?)";

        connect = database.connectDB();

        try {
            PreparedStatement prepare;
            PreparedStatement prepare2;
            Alert alert;

            //Check if the username already exist
            prepare = connect.prepareStatement(checkExist);
            prepare.setString(1, username1.getText());
            result = prepare.executeQuery();

            //Addition of new Admin
            prepare2 = connect.prepareStatement(addAdmin);
            prepare2.setString(1, username1.getText());
            prepare2.setString(2, password2.getText());

            if(username1.getText().isEmpty() || password1.getText().isEmpty() || password2.getText().isEmpty()){
                existWarning.setVisible(false);
                matchWarning.setVisible(false);
                textfieldWarning.setVisible(true);
            }else if(result.next()){
                existWarning.setVisible(true);
                matchWarning.setVisible(false);
                textfieldWarning.setVisible(false);
            }else if (!password1.getText().equals(password2.getText())){
                existWarning.setVisible(false);
                matchWarning.setVisible(true);
                textfieldWarning.setVisible(false);
            }else {
                prepare2.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Congratulations!");
                alert.setHeaderText(null);
                alert.setContentText("New Account has been added!");
                alert.showAndWait();

                HelloApplication m = new HelloApplication();
                m.changeScene("Login.fxml");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }





}
