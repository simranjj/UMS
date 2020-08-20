package UMS.controller;

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

import static UMS.util.AlertUtil.alertError;
import static UMS.util.AlertUtil.callAlert;

public class ReportController implements Initializable {

    DBService dbService = new DBService();
    private ToggleGroup reportToggleGroup = new ToggleGroup();

    @FXML
    AnchorPane anchorPane;
    @FXML
    TextField reportFieldEmpID = new TextField();
    @FXML
    TextField reportFieldSite = new TextField();
    @FXML
    DatePicker reportStartDate = new DatePicker();
    @FXML
    DatePicker reportEndDate = new DatePicker();
    @FXML
    TextField reportFieldEmail = new TextField();
    @FXML
    ToggleButton reportToggleEmpID = new ToggleButton();
    @FXML
    ToggleButton reportToggleDate = new ToggleButton();
    @FXML
    ToggleButton reportToggleSite = new ToggleButton();
    @FXML
    Label reportLabelEmpID = new Label();
    @FXML
    Label reportLabelSite = new Label();
    @FXML
    Label reportLabelStartDate = new Label();
    @FXML
    Label reportLabelEndDate = new Label();
    @FXML
    Button reportButtonGenerate = new Button();

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

    public void report() {

        if (reportToggleGroup.getSelectedToggle().getUserData() == "empid") {
            if (reportFieldEmpID.getLength() == 0) {
                callAlert(alertError,"Data Field is Empty");
                return;
            }
            dbService.report(reportFieldEmpID.getText(), 0, Date.valueOf("2019-09-19"), Date.valueOf("2019-09-19"),
                    reportFieldEmail.getText());
        } else if (reportToggleGroup.getSelectedToggle().getUserData() == "site") {
            if (reportFieldSite.getLength() == 0) {
                callAlert(alertError,"Data Field is Empty");
                return;
            }
            dbService.report(reportFieldSite.getText(), 0, Date.valueOf("2019-09-19"), Date.valueOf("2019-09-19"),
                    reportFieldEmail.getText());
        } else if (reportToggleGroup.getSelectedToggle().getUserData() == "date") {
            LocalDate now = LocalDate.now();
            if (now.isBefore(reportStartDate.getValue())) {
                callAlert(alertError,"Data Field is Empty");
                return;
            } else if (now.isBefore(reportEndDate.getValue())) {
                callAlert(alertError,"Data Field is Empty");
                return;
            }
            dbService.report(reportFieldSite.getText(), 1, Date.valueOf(reportStartDate.getValue()),
                    Date.valueOf(reportEndDate.getValue()),reportFieldEmail.getText());
        }
    }

    //report page-toggle button feature
    public void viewReport() {
        reportToggleEmpID.setSelected(false);
        reportToggleSite.setSelected(false);
        reportToggleDate.setSelected(false);
        reportEndDate.setVisible(false);
        reportStartDate.setVisible(false);
        reportFieldEmpID.setVisible(false);
        reportFieldSite.setVisible(false);
        reportLabelEndDate.setVisible(false);
        reportLabelStartDate.setVisible(false);
        reportLabelEmpID.setVisible(false);
        reportLabelSite.setVisible(false);
        reportButtonGenerate.setVisible(false);

        reportToggleDate.setOnAction(event -> {
            if (reportToggleDate.isSelected()) {
                reportFieldEmpID.setVisible(false);
                reportStartDate.setVisible(true);
                reportEndDate.setVisible(true);
                reportFieldSite.setVisible(false);
                reportLabelEndDate.setVisible(true);
                reportLabelStartDate.setVisible(true);
                reportLabelEmpID.setVisible(false);
                reportLabelSite.setVisible(false);
                reportButtonGenerate.setVisible(true);
            }
        });
        reportToggleEmpID.setOnAction(event -> {
            if (reportToggleEmpID.isSelected()) {
                reportFieldEmpID.setVisible(true);
                reportStartDate.setVisible(false);
                reportEndDate.setVisible(false);
                reportFieldSite.setVisible(false);
                reportLabelEndDate.setVisible(false);
                reportLabelStartDate.setVisible(false);
                reportLabelEmpID.setVisible(true);
                reportLabelSite.setVisible(false);
                reportButtonGenerate.setVisible(true);
            }
        });
        reportToggleSite.setOnAction(event -> {
            if (reportToggleSite.isSelected()) {
                reportFieldEmpID.setVisible(false);
                reportStartDate.setVisible(false);
                reportEndDate.setVisible(false);
                reportFieldSite.setVisible(true);
                reportLabelEndDate.setVisible(false);
                reportLabelStartDate.setVisible(false);
                reportLabelEmpID.setVisible(false);
                reportLabelSite.setVisible(true);
                reportButtonGenerate.setVisible(true);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TextFields.bindAutoCompletion(reportFieldEmpID, dbService.empidList());
        TextFields.bindAutoCompletion(reportFieldSite, dbService.sitename());
        reportToggleEmpID.setToggleGroup(reportToggleGroup);
        reportToggleDate.setToggleGroup(reportToggleGroup);
        reportToggleSite.setToggleGroup(reportToggleGroup);
        reportToggleEmpID.setUserData("empid");
        reportToggleDate.setUserData("date");
        reportToggleSite.setUserData("site");
        viewReport();

    }
}
