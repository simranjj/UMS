package UMS.controller;

import UMS.dto.AddTableDTO;
import UMS.service.DBService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<AddTableDTO> table = new TableView<>();
    @FXML
    public TableColumn<AddTableDTO, String> columnItemName = new TableColumn<>();
    @FXML
    public TableColumn<AddTableDTO, String> columnItemCount = new TableColumn<>();

    DBService dbService = new DBService();
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

    private void link(TableColumn<AddTableDTO, String> name, TableColumn<AddTableDTO, String> count) {
        name.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        count.setCellValueFactory(new PropertyValueFactory<>("itemCount"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        link(columnItemName, columnItemCount);
        table.setItems(dbService.view("", ""));

    }
}
