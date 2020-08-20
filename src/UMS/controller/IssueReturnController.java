package UMS.controller;

import UMS.dto.UpdateItemDTO;
import UMS.service.DBService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static UMS.util.AlertUtil.*;

public class IssueReturnController implements Initializable {

    DBService dbService = new DBService();

    @FXML
    AnchorPane anchorPane;
    @FXML
    ToggleButton returnToggleSite = new ToggleButton();
    @FXML
    Label issueLabelSite = new Label();
    @FXML
    Label issueLabelEmpID = new Label();
    @FXML
    Label issueReturnLabel = new Label();
    @FXML
    Label issueLabelDate = new Label();
    @FXML
    Label returnLabelDate = new Label();
    @FXML
    TextField issueFieldProviderName = new TextField();
    @FXML
    TextField issueFieldQuantity = new TextField();
    @FXML
    DatePicker issueReturnDate = new DatePicker();
    @FXML
    TextField issueFieldEmpID = new TextField();
    @FXML
    TextField issueReturnItemName =new TextField();
    @FXML
    ComboBox<String> issueReturnComboBoxSite = new ComboBox<>();
    @FXML
    ToggleButton returnToggle = new ToggleButton();
    @FXML
    ToggleButton issueToggleSite = new ToggleButton();

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

    public void updateRecord() {

        if (issueFieldEmpID.getText().isEmpty() && issueReturnComboBoxSite.getValue().isEmpty()) {
            callAlert(alertError,"Data Field is Empty");
            return;
        }



        if (issueFieldProviderName.getLength() == 0) {
            callAlert(alertError,"Data Field is Empty");
            return;
        }

        if (issueReturnItemName.getText() == null ) {
            callAlert(alertError,"Data Field is Empty");
            return;
        }

        Pattern p = Pattern.compile("^[-]?\\d*$");
        Matcher m = p.matcher(issueFieldQuantity.getText());
        if (!(m.find() && m.group().equals(issueFieldQuantity.getText()))) {
            callAlert(alertError,"Quantity should be digits");
            return;
        }

        LocalDate now = LocalDate.now();

        if (now.isBefore(issueReturnDate.getValue())) {
            callAlert(alertError,"Date Can't be in future");
            return;
        }

        UpdateItemDTO updateItemDTO = new UpdateItemDTO(Integer.parseInt(issueFieldQuantity.getText()),
                Date.valueOf(issueReturnDate.getValue()), issueFieldEmpID.getText()
                , issueFieldProviderName.getText(), issueReturnItemName.getText(), issueReturnComboBoxSite.getValue());

        dbService.update(updateItemDTO);
        callAlert(alertInformation,"Issue/Return Successfully\n"+updateItemDTO.getItemName());
    }

    //issue/return  toggle button feature
    public void hideOptionIR() {
        issueLabelSite.setVisible(false);
        issueReturnComboBoxSite.setVisible(false);
        returnToggle.setOnAction(event -> {
            if (returnToggle.isSelected()) {
                issueFieldQuantity.setText("-" + issueFieldQuantity.getText());
                issueReturnLabel.setText("Return Item");
                returnLabelDate.setVisible(true);
                issueLabelDate.setVisible(false);
                returnToggleSite.setVisible(true);
                issueToggleSite.setVisible(false);
                issueToggleSite.setSelected(false);
                issueLabelSite.setVisible(false);
                issueReturnComboBoxSite.setVisible(false);
                issueLabelEmpID.setVisible(true);
                issueFieldEmpID.setVisible(true);

                returnToggleSite.setOnAction(event2 -> {
                    if (returnToggleSite.isSelected()) {
                        issueLabelSite.setVisible(true);
                        issueReturnComboBoxSite.setVisible(true);
                        issueLabelEmpID.setVisible(false);
                        issueFieldEmpID.setVisible(false);
                    } else if (!returnToggleSite.isSelected()) {
                        issueLabelSite.setVisible(false);
                        issueReturnComboBoxSite.setVisible(false);
                        issueLabelEmpID.setVisible(true);
                        issueFieldEmpID.setVisible(true);
                        issueFieldEmpID.setText("");
                    }
                });

            } else if (!returnToggle.isSelected()) {
                issueFieldQuantity.setText("");
                issueReturnLabel.setText("Issue Item");
                returnLabelDate.setVisible(false);
                issueLabelDate.setVisible(true);
                returnToggleSite.setVisible(false);
                issueToggleSite.setVisible(true);
                issueLabelSite.setVisible(false);
                issueReturnComboBoxSite.setVisible(false);
                issueLabelEmpID.setVisible(true);
                issueFieldEmpID.setVisible(true);
                returnToggleSite.setSelected(false);
            }
        });

        issueToggleSite.setOnAction(event -> {
            if (issueToggleSite.isSelected()) {
                issueLabelSite.setVisible(true);
                issueReturnComboBoxSite.setVisible(true);
                issueLabelEmpID.setVisible(false);
                issueFieldEmpID.setVisible(false);
            } else if (!issueToggleSite.isSelected()) {
                issueLabelSite.setVisible(false);
                issueReturnComboBoxSite.setVisible(false);
                issueLabelEmpID.setVisible(true);
                issueFieldEmpID.setVisible(true);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideOptionIR();
        returnToggleSite.setVisible(false);
        issueLabelSite.setVisible(false);
        issueReturnComboBoxSite.setVisible(false);
        returnLabelDate.setVisible(false);

        issueReturnComboBoxSite.setItems(dbService.sitename());
        TextFields.bindAutoCompletion(issueReturnItemName, dbService.itemNames());
    }
}
