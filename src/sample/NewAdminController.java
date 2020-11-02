package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;

public class NewAdminController {


    @FXML
    private TextField login_fiel;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button accept_button;

    @FXML
    void initialize() {
        DBHandler dbHandler = new DBHandler();
        accept_button.setOnAction(event ->{
            String login = login_fiel.getText().trim();
            System.out.println(login);
            String loginPassword = password_field.getText().trim();


            if (loginPassword.equals("admin")){
                    boolean flag = dbHandler.checkLogin(login);
                    System.out.println(flag);
                    if(flag){
                        dbHandler.make_user_a_boss(login);
                    }else{
                        mistake("Имя пользователя неверно");
                    }
            }else{
                mistake("Неверный пароль");
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


}
