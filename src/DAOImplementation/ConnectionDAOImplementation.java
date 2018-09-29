package DAOImplementation;

import DAO.ConnectionDAO;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class ConnectionDAOImplementation implements ConnectionDAO {
    private Connection connection = null;

    public ConnectionDAOImplementation() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            printConnectionError(e.toString());
        } finally {
            return connection;
        }
    }

    @Override
    public final void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            printConnectionError(e.toString());
        }
    }

    @Override
    public void printConnectionError(String exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(exception);
        alert.showAndWait();
    }

}
