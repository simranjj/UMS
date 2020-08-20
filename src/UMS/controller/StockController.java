package UMS.controller;

import UMS.dto.ItemDTO;
import UMS.util.BarcodeUtil;
import UMS.dto.AddTableDTO;
import UMS.service.DBService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    private ToggleGroup addToggleGroupMaleFemale = new ToggleGroup();
    private ToggleGroup addToggleGroupCloths = new ToggleGroup();
    DBService dbService = new DBService();
    ObservableList<AddTableDTO> obj = FXCollections.observableArrayList();


    @FXML
    AnchorPane anchorPane;
    @FXML
    ImageView doneImage =new ImageView();
    @FXML
    TextField addFieldItemName =new TextField();
    @FXML
    ComboBox<String> addComboItemName = new ComboBox<>();
    @FXML
    private Label addTotalCost =new Label();
    @FXML
    private TableView<AddTableDTO> addTable = new TableView<>();
    @FXML
    public TableColumn<AddTableDTO, String> addColumnItemName = new TableColumn<>();
    @FXML
    public TableColumn<AddTableDTO, String> addColumnItemCount = new TableColumn<>();
    @FXML
    public TableColumn<AddTableDTO, String> addColumnItemCost = new TableColumn<>();
    @FXML
    ToggleButton addToggleMale = new ToggleButton();
    @FXML
    ToggleButton addToggleFemale = new ToggleButton();
    @FXML
    ToggleButton addToggleShirt = new ToggleButton();
    @FXML
    ToggleButton addTogglePants = new ToggleButton();
    @FXML
    ToggleButton addToggleBlazer = new ToggleButton();
    @FXML
    ToggleButton addToggleTie = new ToggleButton();
    @FXML
    TextField addFieldQuantity = new TextField();

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

    @FXML
    private void choose() {
        try {
            String gender = ((addToggleGroupMaleFemale.getSelectedToggle().getUserData() == null) ?
                    "" : addToggleGroupMaleFemale.getSelectedToggle().getUserData() + "");
            String cloth =  ((addToggleGroupCloths.getSelectedToggle().getUserData() == null) ?
                    "" : "" + addToggleGroupCloths.getSelectedToggle().getUserData());
            String code = gender + cloth;
            ObservableList<AddTableDTO> obj;

            switch (code) {
                case "male": {
                    // name.setItems(FXCollections.observableArrayList(utility.male));
                    obj = dbService.view("male", "");
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    addTable.setItems(obj);
                    break;
                }
                case "female": {
                    // name.setItems(FXCollections.observableArrayList(utility.female));
                    obj = dbService.view("female", "");
                    addTable.setItems(obj);
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    break;
                }
                case "maleshirt": {
                    // name.setItems(FXCollections.observableArrayList(utility.male_shirt));
                    obj = dbService.view("male", "shirt");
                    addTable.setItems(obj);
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    break;
                }
                case "malepants": {
                    // name.setItems(FXCollections.observableArrayList(utility.male_pants));
                    obj = dbService.view("male", "pants");
                    addTable.setItems(obj);
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    break;
                }
                case "maleblazer": {
                    // name.setItems(FXCollections.observableArrayList(utility.male_blazer));
                    obj = dbService.view("male", "blazer");
                    addTable.setItems(obj);
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    break;
                }
                case "maletie": {
                    // name.setItems(FXCollections.observableArrayList(utility.male_tie));
                    obj = dbService.view("male", "tie");
                    addTable.setItems(obj);
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    break;
                }
                case "femaleshirt": {
                    // name.setItems(FXCollections.observableArrayList(utility.female_shirt));
                    obj = dbService.view("female", "shirt");
                    addTable.setItems(obj);
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    break;
                }
                case "femalepants": {
                    //  name.setItems(FXCollections.observableArrayList(utility.female_pants));
                    obj = dbService.view("female", "pants");
                    addTable.setItems(obj);
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    break;
                }
                case "femaleblazer": {
                    // name.setItems(FXCollections.observableArrayList(utility.female_blazer));
                    obj = dbService.view("female", "blazer");
                    addTable.setItems(obj);
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    break;
                }
                case "femaletie": {
                    //  name.setItems(FXCollections.observableArrayList(utility.female_tie));
                    obj = dbService.view("female", "tie");
                    addTable.setItems(obj);
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    break;
                }
                default: {
                    // name.setItems(FXCollections.observableArrayList(utility.male_tie));
                    obj = dbService.view("", "");
                    addTable.setItems(obj);
                    addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("caught toggle not selected");
        }
    }
    public void insertRecord() {

        Timeline doneIndicator = new Timeline(new KeyFrame(Duration.seconds(2), e-> {
            doneImage.setVisible(false);
        }));

        Timeline fiveSeconds = new Timeline(new KeyFrame(Duration.seconds(5), e-> {
            doneIndicator.stop();
            if(!addFieldItemName.getText().isEmpty()){
                doneImage.setVisible(true);
                ItemDTO itemDTO = new ItemDTO(addFieldItemName.getText());
                dbService.addStock(itemDTO);
                obj = dbService.view("", "");
                addTable.setItems(obj);
                addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());
                choose();
                addFieldItemName.setText("");
                doneIndicator.play();
            }
            doneIndicator.play();
        }));

        doneIndicator.setCycleCount(Timeline.INDEFINITE);
        fiveSeconds.setCycleCount(Timeline.INDEFINITE);
        doneIndicator.play();
        fiveSeconds.play();
    }

    @FXML
    public void show() {
        addToggleShirt.setVisible(true);
        addTogglePants.setVisible(true);
        addToggleBlazer.setVisible(true);
        addToggleTie.setVisible(true);
    }

    //barcode print
    public void print() throws IOException {
        BarcodeUtil.printCode(addComboItemName.getValue());
    }

    public void hideOptionStock() {
        addToggleShirt.setVisible(false);
        addTogglePants.setVisible(false);
        addToggleBlazer.setVisible(false);
        addToggleTie.setVisible(false);
        addToggleShirt.setSelected(false);
        addTogglePants.setSelected(false);
        addToggleBlazer.setSelected(false);
        addToggleTie.setSelected(false);
        addToggleMale.setSelected(false);
        addToggleFemale.setSelected(false);
    }

    private void link(TableColumn<AddTableDTO, String> name, TableColumn<AddTableDTO, String> count,
                      TableColumn<AddTableDTO,String> totalCost) {
        name.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        count.setCellValueFactory(new PropertyValueFactory<>("itemCount"));
        totalCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addToggleMale.setToggleGroup(addToggleGroupMaleFemale);
        addToggleFemale.setToggleGroup(addToggleGroupMaleFemale);
        addToggleShirt.setToggleGroup(addToggleGroupCloths);
        addTogglePants.setToggleGroup(addToggleGroupCloths);
        addToggleBlazer.setToggleGroup(addToggleGroupCloths);
        addToggleTie.setToggleGroup(addToggleGroupCloths);
        addToggleShirt.setUserData("shirt");
        addTogglePants.setUserData("pants");
        addToggleBlazer.setUserData("blazer");
        addToggleTie.setUserData("tie");
        addToggleMale.setUserData("male");
        addToggleFemale.setUserData("female");

        doneImage.setVisible(false);
        addComboItemName.setItems(dbService.itemNames());
        obj = dbService.view("", "");
        addTable.setItems(obj);
        hideOptionStock();
        TextFields.bindAutoCompletion(addFieldItemName, dbService.itemNames());
        addTotalCost.setText("$ "+ obj.get(obj.size() - 1 ).getGrandTotal());

        link(addColumnItemName, addColumnItemCount, addColumnItemCost);
    }
}
