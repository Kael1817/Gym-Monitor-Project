package com.gymmonitor;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LandingControl implements Initializable {
    //Pages and buttons ------------------------------------------------------------------------------------------------
    @FXML
    private AnchorPane dashboard;
    @FXML
    private AnchorPane member;
    @FXML
    private AnchorPane coaches;
    @FXML
    private AnchorPane payment;
    //Members Textfields------------------------------------------------------------------------------------------------
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private TextField phonenum;
    //Members Table ----------------------------------------------------------------------------------------------------
    @FXML
    private TableView<members> members_table;
    @FXML
    private TableColumn<members, String>memberId_col;
    @FXML
    private TableColumn<members, String> memberName_col;
    @FXML
    private TableColumn<members, String> memberAddress_col;
    @FXML
    private TableColumn<members, String> memberGender_col;
    @FXML
    private TableColumn<members, String> memberPhonenum_col;
    @FXML
    private DatePicker expiration;
    @FXML
    private TableColumn<members, String> memberExpiration_col;
    //Coaches Textfields -----------------------------------------------------------------------------------------------
    @FXML
    private TextField coachId;
    @FXML
    private TextField coachName;
    @FXML
    private TextField coachAddress;
    @FXML
    private ComboBox<String> coachGender;
    @FXML
    private TextField coachPhonenum;
    @FXML
    private ComboBox<String> coachStatus;
    //Coaches Table ----------------------------------------------------------------------------------------------------
    @FXML
    private TableView<coaches> coachTable;
    @FXML
    private TableColumn<coaches, String>coachId_col;
    @FXML
    private TableColumn<coaches, String> coachName_col;
    @FXML
    private TableColumn<coaches, String> coachAddress_col;
    @FXML
    private TableColumn<coaches, String> coachGender_col;
    @FXML
    private TableColumn<coaches, String> coachPhonenum_col;
    @FXML
    private TableColumn<coaches, String> coachStatus_col;

    //Database Tools ---------------------------------------------------------------------------------------------------
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    //Combobox Choices -------------------------------------------------------------------------------------------------
    private String[] genderChoices = {"Male", "Female"};
    private String[] status = {"Active", "In-active"};
    int index = -1;

    //Log out ----------------------------------------------------------------------------------------------------------
    @FXML
    public void logut(ActionEvent event) throws IOException {
        com.gymmonitor.HelloApplication m = new com.gymmonitor.HelloApplication();
        m.changeScene("Login.fxml");
    }

    //Anchor Panes Visibility ------------------------------------------------------------------------------------------
    @FXML
    public void setGoDashboard() {
        dashboard.setVisible(true);
        member.setVisible(false);
        coaches.setVisible(false);
        payment.setVisible(false);
    }
    @FXML
    public void setGoMembers() {
        dashboard.setVisible(false);
        member.setVisible(true);
        coaches.setVisible(false);
        payment.setVisible(false);
    }
    @FXML
    public void setGoCoaches() {
        dashboard.setVisible(false);
        member.setVisible(false);
        coaches.setVisible(true);
        payment.setVisible(false);
    }
    @FXML
    public void setGoPayment() {
        dashboard.setVisible(false);
        member.setVisible(false);
        coaches.setVisible(false);
        payment.setVisible(true);
    }
    //Array list for Column Data ---------------------------------------------------------------------------------------
    ObservableList<members> listview;
    ObservableList<coaches> listviewCoaches;

    //Di ko pa alam function nyan pero kailangan yan HAHSHASASH --------------------------------------------------------
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        updateTable();


        gender.getItems().addAll(genderChoices);
        coachGender.getItems().addAll(genderChoices);
        coachStatus.getItems().addAll(status);
    }

    //Para marefresh yung table ----------------------------------------------------------------------------------------
    public void updateTable(){
        memberId_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        memberName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberAddress_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        memberGender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        memberPhonenum_col.setCellValueFactory(new PropertyValueFactory<>("phonenum"));
        memberExpiration_col.setCellValueFactory(new PropertyValueFactory<>("expiration"));


        coachId_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        coachName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        coachAddress_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        coachGender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        coachPhonenum_col.setCellValueFactory(new PropertyValueFactory<>("phonenum"));
        coachStatus_col.setCellValueFactory(new PropertyValueFactory<>("status"));

        listviewCoaches = database.getDatacoaches();
        coachTable.setItems(listviewCoaches);
        listview = database.getDatamembers();
        members_table.setItems(listview);
    }

    //Members Page Functionality Buttons -------------------------------------------------------------------------------
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = members_table.getSelectionModel().getSelectedIndex();

        if(index <=-1){
            return;
        }
        id.setText(memberId_col.getCellData(index));
        name.setText(memberName_col.getCellData(index));
        address.setText(memberAddress_col.getCellData(index));
        gender.setValue(memberGender_col.getCellData(index));
        phonenum.setText(memberPhonenum_col.getCellData(index));
    }
 
    public void addMember() throws Exception {
        String sql = "INSERT INTO " +database.DB_TABLE_MEMBERS+ " (name, address, gender, phonenum, expiration) VALUES (?,?,?,?,?)";

        connect = database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, name.getText());
            prepare.setString(2, address.getText());
            prepare.setString(3, gender.getValue());
            prepare.setString(4, phonenum.getText());

            LocalDate selectedDate = expiration.getValue();
            if (selectedDate != null) {
                Date expirationDate = Date.valueOf(selectedDate);
                prepare.setDate(5, expirationDate);
            } else {
                prepare.setNull(5, java.sql.Types.DATE);
            }

            if(name.getText().isEmpty() || address.getText().isEmpty() || gender.getValue().isEmpty() || phonenum.getText().isEmpty()){
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the information in the textfields");
                alert.showAndWait();
            }else {
                prepare.executeUpdate();

                Alert alert;
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Member has been successfully added!");
                alert.showAndWait();

                name.clear();
                address.clear();
                gender.getItems().clear();
                phonenum.clear();
                updateTable();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateMembers(){
        String sql = "UPDATE members SET name = ?, address = ?, gender = ?, phonenum = ? WHERE id = ?";

        connect = database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, name.getText());
            prepare.setString(2, address.getText());
            prepare.setString(3, gender.getValue());
            prepare.setString(4, phonenum.getText());
            prepare.setString(5,id.getText());
            prepare.executeUpdate();
            updateTable();

            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("Member has been successfully updated!");
            alert.showAndWait();

            members_table.getSelectionModel().clearSelection();
            id.clear();
            name.clear();
            address.clear();
            gender.getSelectionModel().clearSelection();
            phonenum.clear();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteMember(){
        String sql = "DELETE FROM members WHERE id = ?";

        connect = database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, id.getText());
            prepare.execute();
            updateTable();

            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("Member has been successfully deleted!");
            alert.showAndWait();

            members_table.getSelectionModel().clearSelection();
            id.clear();
            name.clear();
            address.clear();
            gender.getSelectionModel().clearSelection();
            phonenum.clear();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Coaches Page Functionality Buttons -------------------------------------------------------------------------------
    public void getSelected_Coach(){
        index = coachTable.getSelectionModel().getSelectedIndex();

        if(index <=-1){
            return;
        }
        coachId.setText(coachId_col.getCellData(index));
        coachName.setText(coachName_col.getCellData(index));
        coachAddress.setText(coachAddress_col.getCellData(index));
        coachGender.setValue(coachGender_col.getCellData(index));
        coachPhonenum.setText(coachPhonenum_col.getCellData(index));
        coachStatus.setValue(coachStatus_col.getCellData(index));
    }

    public void addCoach(){
        String sql = "INSERT INTO coaches (name, address, gender, phonenum, status) VALUES (?,?,?,?,?)";

        connect = database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, coachName.getText());
            prepare.setString(2, coachAddress.getText());
            prepare.setString(3, coachGender.getValue());
            prepare.setString(4, coachPhonenum.getText());
            prepare.setString(5, coachStatus.getValue());

            if(coachName.getText().isEmpty() || coachAddress.getText().isEmpty() || coachGender.getValue().isEmpty() || coachPhonenum.getText().isEmpty() || coachStatus.getValue().isEmpty()){
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the information in the textfields");
                alert.showAndWait();
            }else {
                prepare.executeUpdate();

                Alert alert;
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION MESSAGE");
                alert.setHeaderText(null);
                alert.setContentText("Member has been successfully added!");
                alert.showAndWait();

                coachName.clear();
                coachAddress.clear();
                coachGender.getItems().clear();
                coachPhonenum.clear();
                coachStatus.getItems().clear();
                updateTable();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateCoach(){
        String sql = "UPDATE coaches SET name = ?, address = ?, gender = ?, phonenum = ?, status = ? WHERE id = ?";

        connect = database.connectDB();

        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, coachName.getText());
            prepare.setString(2, coachAddress.getText());
            prepare.setString(3, coachGender.getValue());
            prepare.setString(4, coachPhonenum.getText());
            prepare.setString(5, coachStatus.getValue());
            prepare.setString(6, coachId.getText());
            prepare.executeUpdate();
            updateTable();

            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("Coach has been successfully updated!");
            alert.showAndWait();

            coachTable.getSelectionModel().clearSelection();
            coachId.clear();
            coachName.clear();
            coachAddress.clear();
            coachGender.getSelectionModel().clearSelection();
            coachPhonenum.clear();
            coachStatus.getSelectionModel().clearSelection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteCoach(){
        String sql = "DELETE FROM coaches WHERE id = ?";

        connect = database.connectDB();
        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, coachId.getText());
            prepare.execute();
            updateTable();

            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION MESSAGE");
            alert.setHeaderText(null);
            alert.setContentText("Coach has been successfully deleted!");
            alert.showAndWait();

            coachTable.getSelectionModel().clearSelection();
            coachId.clear();
            coachName.clear();
            coachAddress.clear();
            coachGender.getSelectionModel().clearSelection();
            coachPhonenum.clear();
            coachStatus.getSelectionModel().clearSelection();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}