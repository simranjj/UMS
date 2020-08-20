package UMS.util;

import javafx.scene.control.Alert;

public class AlertUtil {

    public static Alert alertError = new Alert(Alert.AlertType.ERROR);
    public static Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);

    public static void callAlert(Alert alert, String e){
        alert.setContentText("Task Unsuccessfully\n" + e);
        alert.show();
    }

}
