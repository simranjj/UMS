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

public class LoginController  {

    @FXML
    AnchorPane anchorPane;
    @FXML
    TextField password =new TextField();
    @FXML
    TextField username = new TextField();

    DBService dbService = new DBService();

    public void login() {

        if (username.getLength() == 0 || password.getLength() == 0) {
            callAlert(alertError,"Data Field is Empty");
            return;
        }

        UserDTO userDTO = new UserDTO(username.getText(),password.getText());

        if (dbService.match(userDTO)) {

            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/Dashboard.fxml"));
                anchorPane.getChildren().setAll(pane);
            } catch (IOException e) {
                callAlert(alertError,"Task Unsuccessfully\n" + e);
            }
        } else {
            callAlert(alertError,"Wrong Password");
        }
    }

}
