package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;


    @FXML
    private PasswordField password_field;

    @FXML
    private Button signin_button;

    @FXML
    private Button authsignup_button;

    @FXML
    void initialize() {
            DBHandler dbHandler = new DBHandler();
            //действия, которые произойдут при нажатии на эту кнопку
            signin_button.setOnAction(event ->{

                String loginText = login_field.getText().trim();
                String loginPassword = password_field.getText().trim();
                System.out.println(loginPassword);

                if(dbHandler.checkAdmin(loginText) ){
                    openNewWindow("/sample/admin.fxml");
                }else {

                    if (!loginText.equals("") && !loginPassword.equals("") && dbHandler.checkLogin(loginText) && dbHandler.checkPassword(loginPassword)) {
                        try {
                            loginUser(loginText, loginPassword);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        openNewWindow("/sample/users_schedule.fxml");
                    } else {
                        signupController s = new signupController();
                        s.mistake("Неправильный логин или пароль");
                    }
                }

            });
        //действия, которые произойдут при нажатии на эту кнопку
            authsignup_button.setOnAction(event ->{
            //переход к окну регистрации
            openNewWindow("/sample/signup.fxml");
            //конец перехода
        });


    }

    private void loginUser(String loginText, String loginPassword) throws SQLException {
        DBHandler dbHandler = new DBHandler();
        ResultSet result = dbHandler.getUser(loginText, loginPassword);

    }

    public void openNewWindow(String window){
        authsignup_button.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}