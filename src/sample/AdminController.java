package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private Button open_admin_schel_button;

    @FXML
    private Button make_admin_button;

    @FXML
    void initialize() {
        DBHandler dbHandler = new DBHandler();
        //действия, которые произойдут при нажатии на эту кнопку
        open_admin_schel_button.setOnAction(event ->{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/schedule.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

        make_admin_button.setOnAction(event ->{
            //переход к окну регистрации

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/make_admin.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            //конец перехода
        });

    }

}
