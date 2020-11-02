package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class users_scheduleController {
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
    void initialize() throws SQLException {
        System.out.println("hello");
        System.out.println(usersData);
        initData();

        day_column.setCellValueFactory(new PropertyValueFactory<User, Integer>("day"));
        first_shift_column.setCellValueFactory(new PropertyValueFactory<User, String>("first"));
        second_shift_column.setCellValueFactory(new PropertyValueFactory<User, String>("second"));

        table_users.setItems(usersData);

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