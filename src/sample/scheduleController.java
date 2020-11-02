package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class scheduleController {

    private ObservableList<User> usersData = FXCollections.observableArrayList();
    @FXML
    private TableView<User> table_users;

    @FXML
    private TableColumn<User, Integer> day_column;

    @FXML
    private TableColumn<User, String> first_shift_column;

    @FXML
    private TableColumn<User, String> second_shift_column;

    @FXML
    private Button change_button;

    @FXML
    private Button return_button;

    @FXML
    void initialize() throws SQLException {
        System.out.println("hello");
        System.out.println(usersData);
        initData();
        DBHandler dbHandler = new DBHandler();

        day_column.setCellValueFactory(new PropertyValueFactory<User, Integer>("day"));
        first_shift_column.setCellValueFactory(new PropertyValueFactory<User, String>("first"));
        second_shift_column.setCellValueFactory(new PropertyValueFactory<User, String>("second"));

        table_users.setItems(usersData);

        change_button.setOnAction(event ->{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/change.fxml"));

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
        return_button.setOnAction(event ->{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/lol.fxml"));

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

    }
    private void initData() throws SQLException {
        DBHandler dbHandler = new DBHandler();
        ResultSet rs = dbHandler.getAllUsera();
        // Количество колонок в результирующем запросе
        int columns = rs.getMetaData().getColumnCount();
        // Перебор строк с данными
        while(rs.next()) {
            for (int i = 1; i <= columns; i++) {
                System.out.println(i);
                if (i == 1) {
                    usersData.add(new User(rs.getString(2), rs.getString(3), rs.getString(4)));
                }
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
    }
}
