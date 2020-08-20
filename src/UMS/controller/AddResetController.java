package UMS.controller;

import UMS.dto.EmployeeDTO;
import UMS.dto.ItemDTO;
import UMS.dto.SiteDTO;
import UMS.service.DBService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;

import static UMS.util.AlertUtil.alertError;
import static UMS.util.AlertUtil.callAlert;

public class AddResetController implements Initializable {

    DBService dbService = new DBService();
    private ToggleGroup addRecord = new ToggleGroup();

    @FXML
    AnchorPane anchorPane;
    @FXML
    private ToggleButton addRemoveToggleEmpID = new ToggleButton();
    @FXML
    private ToggleButton addRemoveToggleSite = new ToggleButton();
    @FXML
    private ToggleButton addRemoveToggleItem = new ToggleButton();
    @FXML
    private TextField addRemoveFieldEmpID = new TextField();
    @FXML
    private Label addRemoveLabelEmpID = new Label();
    @FXML
    private Button addResetButton = new Button();
    @FXML
    private Label addRemoveLabelSite = new Label();
    @FXML
    private TextField addRemoveFieldSite = new TextField();
    @FXML
    private Label addRemoveLabelPhone = new Label();
    @FXML
    private Label addRemoveLabelEmail = new Label();
    @FXML
    private Label addRemoveLabelAddress = new Label();
    @FXML
    private Label addRemoveLabelSiteAddress = new Label();
    @FXML
    private Label addRemoveLabelEmpName = new Label();
    @FXML
    private TextField addRemoveFieldSiteAddress = new TextField();
    @FXML
    private TextField addRemoveFieldPhone = new TextField();
    @FXML
    private TextField addRemoveFieldEmail = new TextField();
    @FXML
    private TextField addRemoveFieldAddress = new TextField();
    @FXML
    private TextField addRemoveFieldEmpName = new TextField();
    @FXML
    private Label addRemoveLabelItemName = new Label();
    @FXML
    private TextField addRemoveFieldCost = new TextField();
    @FXML
    private Label addRemoveLabelMinCount = new Label();
    ;
    @FXML
    private TextField addRemoveFieldItemName = new TextField();
    @FXML
    private TextField addRemoveFieldMinCount = new TextField();
    @FXML
    private Label addRemoveLabelCost = new Label();

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

    private void viewAddRecord() {

        addRemoveToggleEmpID.setSelected(false);
        addRemoveToggleSite.setSelected(false);
        addRemoveFieldSiteAddress.setVisible(false);
        addRemoveFieldEmpName.setVisible(false);
        addRemoveFieldEmpID.setVisible(false);
        addRemoveFieldPhone.setVisible(false);
        addRemoveFieldAddress.setVisible(false);
        addRemoveFieldEmail.setVisible(false);
        addRemoveLabelEmpName.setVisible(false);
        addRemoveFieldEmail.setVisible(false);
        addRemoveLabelAddress.setVisible(false);
        addRemoveFieldSite.setVisible(false);
        addRemoveLabelEmail.setVisible(false);
        addRemoveLabelEmpID.setVisible(false);
        addRemoveLabelPhone.setVisible(false);
        addRemoveFieldSite.setVisible(false);
        addRemoveLabelSite.setVisible(false);
        addRemoveLabelSiteAddress.setVisible(false);
        addResetButton.setVisible(false);
        addRemoveLabelItemName.setVisible(false);
        addRemoveFieldItemName.setVisible(false);
        addRemoveLabelCost.setVisible(false);
        addRemoveFieldCost.setVisible(false);
        addRemoveLabelMinCount.setVisible(false);
        addRemoveFieldMinCount.setVisible(false);

        addRemoveToggleEmpID.setOnAction(event -> {
            if (addRemoveToggleEmpID.isSelected()) {
                addRemoveLabelSite.setVisible(false);
                addRemoveLabelSiteAddress.setVisible(false);
                addRemoveFieldSite.setVisible(false);
                addRemoveFieldSiteAddress.setVisible(false);
                addRemoveFieldSite.setVisible(false);
                addRemoveFieldEmpName.setVisible(true);
                addRemoveFieldEmpID.setVisible(true);
                addRemoveFieldPhone.setVisible(true);
                addRemoveFieldAddress.setVisible(true);
                addRemoveFieldEmail.setVisible(true);
                addRemoveLabelEmpName.setVisible(true);
                addRemoveLabelEmail.setVisible(true);
                addRemoveLabelEmpID.setVisible(true);
                addRemoveLabelPhone.setVisible(true);
                addRemoveLabelAddress.setVisible(true);
                addRemoveLabelItemName.setVisible(false);
                addRemoveLabelCost.setVisible(false);
                addRemoveLabelMinCount.setVisible(false);
                addRemoveFieldItemName.setVisible(false);
                addRemoveFieldCost.setVisible(false);
                addRemoveFieldMinCount.setVisible(false);
                addResetButton.setVisible(true);
            }
        });
        addRemoveToggleSite.setOnAction(event -> {
            if (addRemoveToggleSite.isSelected()) {
                addRemoveLabelSite.setVisible(true);
                addRemoveLabelSiteAddress.setVisible(true);
                addRemoveFieldSite.setVisible(true);
                addRemoveFieldSiteAddress.setVisible(true);
                addRemoveFieldSite.setVisible(true);
                addRemoveFieldEmpName.setVisible(false);
                addRemoveFieldEmpID.setVisible(false);
                addRemoveFieldPhone.setVisible(false);
                addRemoveFieldAddress.setVisible(false);
                addRemoveFieldEmail.setVisible(false);
                addRemoveLabelEmpName.setVisible(false);
                addRemoveLabelEmail.setVisible(false);
                addRemoveLabelEmpID.setVisible(false);
                addRemoveLabelPhone.setVisible(false);
                addRemoveLabelAddress.setVisible(false);
                addRemoveLabelItemName.setVisible(false);
                addRemoveLabelCost.setVisible(false);
                addRemoveLabelMinCount.setVisible(false);
                addRemoveFieldItemName.setVisible(false);
                addRemoveFieldCost.setVisible(false);
                addRemoveFieldMinCount.setVisible(false);
                addResetButton.setVisible(true);
            }
        });
        addRemoveToggleItem.setOnAction(event -> {
            if (addRemoveToggleItem.isSelected()) {
                addRemoveLabelSite.setVisible(false);
                addRemoveLabelSiteAddress.setVisible(false);
                addRemoveFieldSite.setVisible(false);
                addRemoveFieldSiteAddress.setVisible(false);
                addRemoveFieldSite.setVisible(false);
                addRemoveFieldEmpName.setVisible(false);
                addRemoveFieldEmpID.setVisible(false);
                addRemoveFieldPhone.setVisible(false);
                addRemoveFieldAddress.setVisible(false);
                addRemoveFieldEmail.setVisible(false);
                addRemoveLabelEmpName.setVisible(false);
                addRemoveLabelEmail.setVisible(false);
                addRemoveLabelEmpID.setVisible(false);
                addRemoveLabelPhone.setVisible(false);
                addRemoveLabelAddress.setVisible(false);
                addRemoveLabelItemName.setVisible(true);
                addRemoveLabelCost.setVisible(true);
                addRemoveLabelMinCount.setVisible(true);
                addRemoveFieldItemName.setVisible(true);
                addRemoveFieldCost.setVisible(true);
                addRemoveFieldMinCount.setVisible(true);
                addResetButton.setVisible(true);
            }
        });
    }

    public void add() {

        if (addRecord.getSelectedToggle().getUserData() == "arempid") {
            if (addRemoveFieldEmpName.getLength() == 0 || addRemoveFieldEmpID.getLength() == 0 || addRemoveFieldPhone.getLength() == 0 || addRemoveFieldAddress.getLength() == 0
                    || addRemoveFieldEmail.getLength() == 0 || addRemoveFieldSite.getLength() == 0) {
                callAlert(alertError,"Data Field is Empty");
                return;
            }
            EmployeeDTO employeeDTO = new EmployeeDTO(addRemoveFieldEmpName.getText(), addRemoveFieldEmpID.getText(), addRemoveFieldPhone.getText(), addRemoveFieldAddress.getText(), addRemoveFieldEmail.getText(), addRemoveFieldSite.getText());
            dbService.addEmployee(employeeDTO);
        } else if (addRecord.getSelectedToggle().getUserData() == "arsite") {
            if (addRemoveFieldSite.getLength() == 0 || addRemoveFieldSiteAddress.getLength() == 0) {
                callAlert(alertError,"Data Field is Empty");
                return;
            }
            SiteDTO siteDTO = new SiteDTO(addRemoveFieldSite.getText(), addRemoveFieldSiteAddress.getText());
            dbService.addSite(siteDTO);
        } else if (addRecord.getSelectedToggle().getUserData() == "aritem") {
            if (addRemoveFieldItemName.getLength() == 0 || addRemoveFieldMinCount.getLength() == 0 || addRemoveFieldCost.getLength() == 0) {
                callAlert(alertError,"Data Field is Empty");
                return;
            }
            ItemDTO itemDTO = new ItemDTO(addRemoveFieldItemName.getText(), addRemoveFieldCost.getText(), addRemoveFieldMinCount.getText());
            dbService.addItem(itemDTO);
        }
    }

    public void itemReset() {
        ItemDTO itemDTO = new ItemDTO(addRemoveFieldItemName.getText(), addRemoveFieldCost.getText(), addRemoveFieldMinCount.getText());
        dbService.resetItem(itemDTO);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewAddRecord();
        addRemoveToggleEmpID.setUserData("arempid");
        addRemoveToggleSite.setUserData("arsite");
        addRemoveToggleItem.setUserData("aritem");
        addRemoveToggleItem.setToggleGroup(addRecord);
        addRemoveToggleSite.setToggleGroup(addRecord);
        addRemoveToggleEmpID.setToggleGroup(addRecord);

        TextFields.bindAutoCompletion(addRemoveFieldSite, dbService.sitename());
    }
}
