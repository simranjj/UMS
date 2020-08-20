package UMS.controller;

import UMS.dto.UserDTO;
import UMS.service.DBService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static UMS.util.AlertUtil.alertError;
import static UMS.util.AlertUtil.callAlert;

public class RestPasswordController  {

    DBService dbService = new DBService();

    @FXML
    AnchorPane anchorPane;
    @FXML
    TextField userName = new TextField();
    @FXML
    TextField resetPassword = new TextField();

    //change screen
    public void callMenu() {
        NavigationController.callMenu(anchorPane);
    }

    //change screen
    public void callFind() {
        NavigationController.callFind(anchorPane);
    }

    //change screen
    public void callReport() {
        NavigationController.callReport(anchorPane);
    }

    //change screen
    public void callVisual() {
        NavigationController.callVisual(anchorPane);
    }

    //change screen
    public void callStock() {
        NavigationController.callStock(anchorPane);
    }

    //change screen
    public void callIssue() {
        NavigationController.callIssue(anchorPane);
    }


    //change screen
    public void callAddReset() {
        NavigationController.callAddReset(anchorPane);
    }

    //change screen
    public void callResetPass() {
        NavigationController.callResetPass(anchorPane);
    }

    public void reset() {

        if (userName.getLength() == 0 || resetPassword.getLength() == 0) {
            callAlert(alertError,"Data Field is Empty");
            return;
        }
        UserDTO userDTO = new UserDTO(userName.getText(),resetPassword.getText());
        dbService.forgot(userDTO);
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/pillar.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLogin() {

        if (userName.getLength() == 0 || resetPassword.getLength() == 0) {
            callAlert(alertError,"Data Field is Empty");
            return;
        }
        UserDTO userDTO = new UserDTO(userName.getText(),resetPassword.getText());
        if (dbService.addUser(userDTO))
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/pillar.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
