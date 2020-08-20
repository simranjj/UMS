package UMS.controller;

import UMS.dto.FindTableDTO;
import UMS.dto.EmployeeDTO;
import UMS.service.DBService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class FindController implements Initializable {

    @FXML
    AnchorPane anchorPane;
    @FXML
    TextField findFieldEmpID = new TextField();
    @FXML
    public TableColumn<FindTableDTO, String> findColumnEmpID = new TableColumn<>();
    @FXML
    public TableColumn<FindTableDTO, String> findColumnItemCount = new TableColumn<>();
    @FXML
    public TableColumn<FindTableDTO, String> findColumnItemName = new TableColumn<>();
    @FXML
    public TableColumn<FindTableDTO, String> findColumnQuantity = new TableColumn<>();
    @FXML
    public TableColumn<FindTableDTO, Date> findColumnDate = new TableColumn<>();
    @FXML
    private TableView<FindTableDTO> findTable = new TableView<>();

    DBService dbService = new DBService();
    private Alert alert = new Alert(Alert.AlertType.NONE);

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

    public void findRecord() {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpID(findFieldEmpID.getText());

        if (findFieldEmpID.getLength() == 0) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Data Field is Empty");
            alert.show();
            return;
        }

        ObservableList<FindTableDTO> obj = dbService.find(employeeDTO);
        if (obj != null)
            findTable.setItems(obj);

    }

    private void link(TableColumn<FindTableDTO, String> empID,
                      TableColumn<FindTableDTO, String> itemCount, TableColumn<FindTableDTO, String> itemName,
                      TableColumn<FindTableDTO, String> quantity, TableColumn<FindTableDTO, Date> date) {
        empID.setCellValueFactory(new PropertyValueFactory<>("empId"));
        itemCount.setCellValueFactory(new PropertyValueFactory<>("itemCount"));
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        date.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        link(findColumnEmpID, findColumnItemCount, findColumnItemName, findColumnQuantity, findColumnDate);
        ObservableList<String> names = dbService.sitename();
        names.addAll(dbService.empidList());
        TextFields.bindAutoCompletion(findFieldEmpID, names);
    }
}
