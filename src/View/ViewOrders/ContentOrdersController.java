package View.ViewOrders;

import DAOImplementation.OrderDAOImplementation;
import Objects.Order;
import DAOImplementation.ViewDAOImplementation;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javafx.fxml.FXML;

public class ContentOrdersController implements Initializable {
	@FXML
    private TableView tableView;

    @FXML
    private ProgressIndicator progressInd;

    @FXML
    private JFXComboBox<String> statusComboBox;

    @FXML
    private JFXComboBox<String> categoryComboBox;

    @FXML
    private JFXComboBox<String> dateComboBox;

    @FXML
    private JFXCheckBox checkBox;

    private ViewDAOImplementation viewDAOImplementation = new ViewDAOImplementation();

    private Executor exec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t ;
        });

        // initialize status combo box values
        statusComboBox.setValue("Unpaid");
        statusComboBox.getItems().addAll("All", "Paid", "Unpaid");

        // initialize category combo box values
        categoryComboBox.setValue("Bar Category");
        categoryComboBox.getItems().addAll("All Categories", "Bar Category", "Extra Category");

        // initialize date combo box values
        dateComboBox.setValue("All");
        dateComboBox.getItems().addAll( "All", "Today", "Yesterday", "Last 7 days", "Last month");

        TableColumn roomIDColumn = viewDAOImplementation.getColumnName("ROOM NUMBER", "roomNumber");
        TableColumn itemListColumn = viewDAOImplementation.getColumnName("ITEMS", "itemListToPrint");
        TableColumn statusColumn = viewDAOImplementation.getColumnName("STATUS", "vboxToPrint");
        TableColumn actionColumn = viewDAOImplementation.getColumnName("ACTION", "DUMMY");
        actionColumn.setCellFactory(viewDAOImplementation.setupTableCells(new Order(), tableView));

        // set column width
        roomIDColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width
        itemListColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 50 ); // 50% width
        statusColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width
        actionColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 10 ); // 10% width

        TableColumn[] list = new TableColumn[] {statusColumn, roomIDColumn, itemListColumn, actionColumn};
		
		viewDAOImplementation.setTableView(tableView);
		viewDAOImplementation.setProgressInd(progressInd);
		viewDAOImplementation.addColumnNames(list);

        // create a worker
        Task<List<Order>> worker = createWorker();

        // run the task using a thread from the thread pool:
        exec.execute(worker);
    }

    @FXML
    public void onAddOrderClick() {
        viewDAOImplementation.addObject(new Order());
    }

    @FXML
    public void onComboBoxClick() {
        Task<List<Order>> worker = createWorker();
        exec.execute(worker);
    }

    @FXML
    public void onCheckBoxClick() {
        List<Order> orderList = tableView.getItems();
        if (orderList.size() == 0)
            return;

        if (checkBox.isSelected()) {
            List<Order> newList = getUnpaidOrderList(orderList);
            tableView.getItems().clear();
            tableView.getItems().setAll(newList);
        }
        else {
            Task<List<Order>> worker = createWorker();
            exec.execute(worker);
        }
    }

    private List<Order> getUnpaidOrderList(List<Order> orderList) {
        List<Order> newList = new ArrayList<>();
        for (Order order : orderList) {
            if (!order.isStatus()) {
                newList.add(order);
            }
        }
        return newList;
    }

    private Task<List<Order>> createWorker() {
        OrderDAOImplementation orderDAOImplementation = new OrderDAOImplementation();

        // create the task
        Task<List<Order>> task = new Task<List<Order>>() {
            @Override
            public List<Order> call() throws Exception {
                return orderDAOImplementation.get(categoryComboBox.getValue(), dateComboBox.getValue(), statusComboBox.getValue());
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
            tableView.getItems().clear();
            tableView.getItems().setAll(task.getValue());
        });

        return task;
    }
}
