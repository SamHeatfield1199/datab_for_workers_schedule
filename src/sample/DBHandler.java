package sample;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;

public class DBHandler {


    //метод хэширования
    public static String doHash(String passwordToHash){
        String salt = "abcd";
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));//Если есть несколько блоков данных для включения в один и тот же дайджест сообщения, вызовите метод update()
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));//завершите вызовом digest(). Вот как выглядит вычисление дайджеста сообщения из нескольких блоков данных:
            StringBuilder sb = new StringBuilder();//для производительности
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }



    //для подключения к бд
    Connection dbConnection;

    public Connection getConnection() throws SQLException, IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("C:\\Users\\asmir\\Desktop\\6 семестр\\Субд\\datab\\src\\sample\\datadata.properties"))){
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
        dbConnection = DriverManager.getConnection(url, username, password);

        return dbConnection;
    }


    public boolean checkLogin(String login){
        ResultSet resultSet = null;
        System.out.println("we are here");
        boolean flag = false;
        String sql2 = "SELECT Login FROM users where Login = ?";
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(sql2);
                preparedStatement.setString(1, login);
                resultSet = preparedStatement.executeQuery();

                //resultSet.last();
                //int rowCount = resultSet.getRow();
               // System.out.println("count" + rowCount);
                int count =0;
                while (resultSet.next()){
                    count++;

                }

                if (count!=0) {
                    flag = true;
                }
            }
        catch (Exception ex) {
            System.out.println("Connection failed...");

            ex.printStackTrace();
        }
        System.out.println(flag);
        return flag;
    }

    public boolean checkPassword(String password){
        ResultSet resultSet = null;
        boolean flag = false;
        System.out.println("we are here");
        String pass = doHash(password);
        System.out.println(pass);
        String sql2 = "SELECT Password_ FROM users where Password_ = ?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql2);
            preparedStatement.setString(1, pass);
            resultSet = preparedStatement.executeQuery();

            //resultSet.last();
            //int rowCount = resultSet.getRow();
            // System.out.println("count" + rowCount);
            int count = 0;
            while (resultSet.next()){
                count++;

            }

            if (count!=0) {
                flag = true;
            }
        }
        catch (Exception ex) {
            System.out.println("Connection failed...");

            ex.printStackTrace();
        }
        System.out.println(flag);
        return flag;
    }
    public boolean checKAdminkPassword(String password, String login){
        ResultSet resultSet = null;
        boolean flag = false;
        System.out.println("we are here");
        String pass = doHash(password);
        System.out.println(pass);
        String sql2 = "SELECT Password_ FROM users where Password_ = ? and Login = ?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql2);
            preparedStatement.setString(1, pass);
            preparedStatement.setString(2, login);
            resultSet = preparedStatement.executeQuery();

            //resultSet.last();
            //int rowCount = resultSet.getRow();
            // System.out.println("count" + rowCount);
            int count = 0;
            while (resultSet.next()){
                count++;

            }

            if (count!=0) {
                flag = true;
            }
        }
        catch (Exception ex) {
            System.out.println("Connection failed...");

            ex.printStackTrace();
        }
        System.out.println(flag);
        return flag;
    }




    //метод для записи данных пользователя в бд
    public void signUpUser(String Login, String Name, String surname, String password ) throws SQLException, IOException, ClassNotFoundException {
        String insert = "INSERT users (Login, FirstName, LastName, Password_) Values (?,?,?,?)";
        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, Login);
            preparedStatement.setString(2, Name);
            preparedStatement.setString(3, surname);
            preparedStatement.setString(4, doHash(password));
            preparedStatement.executeUpdate();

        }
    catch (Exception ex) {
        System.out.println("Connection failed...");
        ex.printStackTrace();
    }
    }
    //Метод получения пользователя
    public ResultSet getUser(String Login, String password){
        ResultSet resSet = null;
        String select = "SELECT * FROM users WHERE Login =? AND Password_ =?";
        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(select);
            preparedStatement.setString(1, Login);
            preparedStatement.setString(2, password);
            resSet = preparedStatement.executeQuery();

        }
        catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
        return resSet;
    }
    public ResultSet getAllUsera(){
        ResultSet resSet = null;
        String select = "SELECT * FROM work_schedule";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(select);
            resSet = preparedStatement.executeQuery();

        }
        catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
        return resSet;
    }
    //коронуем юзера
    public void make_user_a_boss(String login){
        String insert = "UPDATE users SET Admin_ = 1 WHERE Login =?";
        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
    }

    public void appoint_new_worker(String login, String day, String shift){
        String insert_1 = "Update work_schedule SET shift_1 = ? where _day = ?";
        String insert_2 = "Update work_schedule SET shift_2 = ? where _day = ?";
        try {
            if(shift.equals("1")){
                PreparedStatement preparedStatement = getConnection().prepareStatement(insert_1);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, day);
                preparedStatement.executeUpdate();
            }
            if(shift.equals("2")){
                PreparedStatement preparedStatement = getConnection().prepareStatement(insert_2);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, day);
                preparedStatement.executeUpdate();
            }

        }
        catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }

    }
    public void change_user_login(String login, String new_login){
        String insert = "UPDATE users SET Login = ? WHERE Login =?";
        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, new_login);
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
    }
    public void change_user_password(String login, String new_pass){
        String insert = "UPDATE users SET Password_ = ? WHERE Login =?";
        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, doHash(new_pass));
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println("Connection failed...");
            ex.printStackTrace();
        }
    }
    public boolean checkAdmin(String login){
        ResultSet resultSet = null;
        boolean flag = false;
        String sql2 = "SELECT Admin_ FROM users where Login = ?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql2);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            int columns = resultSet.getMetaData().getColumnCount();
            // Перебор строк с данными
            while(resultSet.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.println(resultSet.getString(1));
                    flag = resultSet.getString(1).equals("1");
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Connection failed...");

            ex.printStackTrace();
        }
        System.out.println(flag);
        return flag;
    }
}
