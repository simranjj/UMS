package UMS.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static UMS.util.AlertUtil.alertError;
import static UMS.util.AlertUtil.callAlert;

public class NavigationController {

    public static void callFind(AnchorPane anchorPane) {
        try {
            AnchorPane pane = FXMLLoader.load(NavigationController.class.getResource("../view/Find.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (Exception e) {
            callAlert(alertError, e.getMessage());
        }
    }

    public static void callStock(AnchorPane anchorPane) {
        try {
            AnchorPane pane = FXMLLoader.load(NavigationController.class.getResource("../view/Stock.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (Exception e) {
            callAlert(alertError,e.getMessage());
        }
    }

    public static void callIssue(AnchorPane anchorPane) {
        try {
            AnchorPane pane = FXMLLoader.load(NavigationController.class.getResource("../view/Issue.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (Exception e) {
            callAlert(alertError,e.getMessage());
        }
    }

    public static void callAddReset(AnchorPane anchorPane) {
        try {
            AnchorPane pane = FXMLLoader.load(NavigationController.class.getResource("../view/AddReset.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            callAlert(alertError,e.getMessage());
        }
    }

    public static void callResetPass(AnchorPane anchorPane) {
        try {
            AnchorPane pane = FXMLLoader.load(NavigationController.class.getResource("../view/ResetPass.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            callAlert(alertError,e.getMessage());
        }
    }

    public static void callVisual(AnchorPane anchorPane) {
        try {
            AnchorPane pane = FXMLLoader.load(NavigationController.class.getResource("../view/Visual.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (Exception e) {
            callAlert(alertError,e.getMessage());
        }
    }

    public static void callReport(AnchorPane anchorPane) {
        try {
            AnchorPane pane = FXMLLoader.load(NavigationController.class.getResource("../view/Report.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (Exception e) {
            callAlert(alertError,e.getMessage());
        }
    }

    public static void callMenu(AnchorPane anchorPane) {
        try {
            AnchorPane pane = FXMLLoader.load(NavigationController.class.getResource("../view/Dashboard.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (Exception e) {
            callAlert(alertError,e.getMessage());
        }
    }
}
