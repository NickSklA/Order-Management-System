package View.ViewDashboard;


import DAOImplementation.ConnectionDAOImplementation;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static jdk.nashorn.internal.objects.NativeMath.round;

public class ContentDashboardController implements Initializable{

    @FXML
    private ProgressIndicator progressInd;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    private Executor exec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        XYChart.Series series = new XYChart.Series();

        lineChart.setLegendVisible(false);

        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t ;
        });

        // create the task
        Task<Map<String, Double>> task = new Task<Map<String, Double>>() {
            @Override
            public Map<String, Double> call() throws Exception {
                return new TreeMap<>(getSalesPerDay());
            }
        };

        progressInd.progressProperty().bind(task.progressProperty());

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            // inform user of error...
        });

        task.setOnSucceeded(e -> {
            progressInd.progressProperty().unbind();
            progressInd.setProgress(1); // mark as complete...
            // Task.getValue() gives the value returned from call()...
            for (Map.Entry<String, Double> entry : task.getValue().entrySet()) {
                series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
            }
            lineChart.getData().addAll(series);
        });

        exec.execute(task);
    }

    // get sales for the last 30 days
    private Map<String, Double> getSalesPerDay () {
        ConnectionDAOImplementation connectionDAOImplementation = new ConnectionDAOImplementation();
        Map<String, Double> salesDayMap = new HashMap<>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection con = connectionDAOImplementation.getConnection();
            for (int day = 0; day < 30; day++) {
                String query = "SELECT CURDATE() - INTERVAL '"+ day +"' day, SUM(items.price) " +
                        "FROM orders, order_item, items " +
                        "WHERE orders.date = CURDATE() - INTERVAL '"+ day +"' DAY " +
                        "AND orders.orderID = order_item.orderID " +
                        "AND order_item.itemID = items.itemID " +
                        "AND items.itemCategory <> 'Extra'";
                statement = con.createStatement();
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    String[] date = resultSet.getString(1).split("-");
                    Double income = round("%.2f",resultSet.getDouble(2));
                    salesDayMap.put(date[1] + "/" + date[2], income);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            connectionDAOImplementation.closeConnection();
            return salesDayMap;
        }
    }
}
