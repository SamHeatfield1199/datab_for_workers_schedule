package sample;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.awt.*;

public class changeController {
    @FXML
    private TextField login_field;

    @FXML
    private TextField day_field;

    @FXML
    private Button appoint_button;

    @FXML
    private TextField change_login_field;

    @FXML
    private TextField shift_field;

    @FXML
    private TextField change_new_login_field;

    @FXML
    private TextField change_pass_login_field;

    @FXML
    private PasswordField change_new_pass_field;

    @FXML
    private Button change_button;

    @FXML
    void initialize() {
        DBHandler dbHandler = new DBHandler();
        appoint_button.setOnAction(event ->{

            String login = login_field.getText().trim();
            System.out.println(login);
            boolean flag = dbHandler.checkLogin(login);
            System.out.println(flag);
            if(flag){
                System.out.println("day" + day_field.getText() + "shift " + shift_field.getText() );
                if (Integer.parseInt(day_field.getText()) <= 31 && (Integer.parseInt(shift_field.getText()) == 1 || Integer.parseInt(shift_field.getText()) ==2)){
                    dbHandler.appoint_new_worker(login, day_field.getText(), shift_field.getText() );
                }else{
                    mistake("Вы ввели неправильные данные");
                }
            }else{
                    mistake("Имя пользователя неверно");
            }

        });

        change_button.setOnAction(event ->{
            String login = change_login_field.getText().trim();
            String new_login = change_new_login_field.getText();
            String new_password= change_new_pass_field.getText().trim();
            String login_pass = change_pass_login_field.getText().trim();
            if ((!login.isEmpty() &&!new_login.isEmpty()) || (!login_pass.isEmpty() && !new_password.isEmpty() ) && (dbHandler.checkLogin(login) || dbHandler.checkLogin(login_pass)) ) {
                if (!new_login.isEmpty() ){
                    dbHandler.change_user_login(login, new_login);
                }
                if(!new_password.isEmpty()){
                    dbHandler.change_user_password(login_pass, new_password);
                }

            } else {
                signupController s = new signupController();
                s.mistake("Вы не заполнили все поля");
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
