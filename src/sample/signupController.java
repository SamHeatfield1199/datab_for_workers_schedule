package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class signupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField signup_password;

    @FXML
    private Button signup_button;

    @FXML
    private TextField signup_name;

    @FXML
    private TextField signup_login;

    @FXML
    private TextField signup_surname;

    @FXML
    void initialize() {
        DBHandler dbHandler = new DBHandler();
        //действия, которые произойдут при нажатии на эту кнопку
            signup_button.setOnAction(event -> {

                String login = signup_login.getText();
                try {
                    if (login.isEmpty() || signup_password.getText().isEmpty()|| signup_name.getText().isEmpty() || signup_surname.getText().isEmpty()) {
                        mistake("Вы что-то пропустили");
                    }else {
                        boolean flag = dbHandler.checkLogin(login);
                        if (flag) {
                            mistake("Имя пользователя недоступно");
                        } else {
                            dbHandler.signUpUser(login, signup_name.getText(),
                                    signup_surname.getText(), signup_password.getText());
                            openNewWindow("/sample/users_schedule.fxml");
                        }
                    }

                    } catch(SQLException | IOException | ClassNotFoundException e){
                        e.printStackTrace();
                    }

            });

    }

    void mistake(String error) {
        JFrame frame = new JFrame();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel Panel = new JPanel();
        JLabel label = new JLabel(error);
        frame.getContentPane().add(BorderLayout.CENTER, label);
        frame.setBounds(860,465,200, 150);
        frame.setVisible(true);
    }
    public void openNewWindow(String window){
        signup_button.getScene().getWindow().hide();

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
        stage.show();
    }
}
