package UMS.controller;

import UMS.service.DBService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class VisualController implements Initializable {

    DBService dbService = new DBService();

    @FXML
    AnchorPane anchorPane;
    @FXML
    DatePicker visualStartDate =new DatePicker();
    @FXML
    DatePicker visualEndDate =new DatePicker();
    @FXML
    ComboBox<String> visualItemGroupName = new ComboBox<>();
    @FXML
    CategoryAxis xAxis    = new CategoryAxis();
    @FXML
    NumberAxis yAxis = new NumberAxis();
    @FXML
    AreaChart areaChart =new AreaChart(xAxis,yAxis);
    @FXML
    Label visualLabel =new Label();
    @FXML
    ToggleButton visualItemIn = new ToggleButton();
    @FXML
    ToggleButton visualItemOut = new ToggleButton();
    @FXML
    ComboBox<String> visualComboItemName = new ComboBox<>();

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

    public void visual() {
        try {
            visualItemOut.setOnAction(event -> {
                if (visualItemOut.isSelected()) {
                    visualLabel.setText("Items Out");
                    areaChart.getData().clear();
                    areaChart.setTitle(visualComboItemName.getValue());
                    if(visualItemGroupName.getValue()!=null && visualItemGroupName.getValue().contains("Count"))
                        yAxis.setLabel("Count");
                    else if (visualItemGroupName.getValue()!=null && visualItemGroupName.getValue().contains("Cost"))
                        yAxis.setLabel("Cost");
                    else
                        yAxis.setLabel("Count");
                    if(visualComboItemName.getValue()!=null){
                        areaChart = dbService.view("out", Date.valueOf(visualStartDate.getValue())
                                ,Date.valueOf(visualEndDate.getValue()), visualComboItemName.getValue(), areaChart);
                        visualComboItemName.setValue(null);
                    }
                    else if(visualItemGroupName.getValue()!=null){
                        areaChart = dbService.view("out",Date.valueOf(visualStartDate.getValue())
                                ,Date.valueOf(visualEndDate.getValue()), visualItemGroupName.getValue(), areaChart);
                        visualItemGroupName.setValue(null);
                    }
                }
            });

            visualItemIn.setOnAction(event2 -> {
                if (visualItemIn.isSelected()) {
                    visualLabel.setText("Items In");
                    areaChart.getData().clear();
                    areaChart.setTitle(visualComboItemName.getValue());
                    xAxis.setLabel("Date");
                    if(visualItemGroupName.getValue()!=null && visualItemGroupName.getValue().contains("Count"))
                        yAxis.setLabel("Count");
                    else if (visualItemGroupName.getValue()!=null && visualItemGroupName.getValue().contains("Cost"))
                        yAxis.setLabel("Cost");
                    else
                        yAxis.setLabel("Count");
                    if(visualComboItemName.getValue()!=null){
                        areaChart = dbService.view("in",Date.valueOf(visualStartDate.getValue())
                                ,Date.valueOf(visualEndDate.getValue()), visualComboItemName.getValue(), areaChart);
                        visualComboItemName.setValue(null);
                    }
                    else if(visualItemGroupName.getValue()!=null){
                        areaChart = dbService.view("in",Date.valueOf(visualStartDate.getValue())
                                ,Date.valueOf(visualEndDate.getValue()), visualItemGroupName.getValue(), areaChart);
                        visualItemGroupName.setValue(null);
                    }
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        visual();
        areaChart.setLegendVisible(false);
        ObservableList<String> groupItemName = FXCollections.observableArrayList();
        groupItemName.addAll("Male Shirts Count","Male Pants Count","Male Tie Count","Male Blazer Count",
                "Female Shirts Count","Female Pants Count","Female Tie Count",
                "Female Blazer Count","Male Shirts Cost","Male Pants Cost","Male Tie Cost","Male Blazer Cost",
                "Female Shirts Cost","Female Pants Cost","Female Tie Cost", "Female Blazer Cost");
        visualItemGroupName.setItems(groupItemName);
        visualComboItemName.setItems(dbService.itemNames());
    }
}
