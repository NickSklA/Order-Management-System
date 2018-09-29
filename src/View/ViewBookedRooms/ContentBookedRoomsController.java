package View.ViewBookedRooms;

import DAOImplementation.BookedRoomDAOImplementation;
import DAOImplementation.OrderDAOImplementation;
import DAOImplementation.ViewDAOImplementation;
import Objects.BookedRoom;
import Objects.Order;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ContentBookedRoomsController implements Initializable {
    @FXML
    private TableView tableView;

    @FXML
    private ProgressIndicator progressInd;

    private ViewDAOImplementation viewDAOImplementation = new ViewDAOImplementation();

    private Executor exec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t ;
        });

        TableColumn roomNumberColumn = viewDAOImplementation.getColumnName("ROOM NUMBER", "roomNumber");    // to change...
        TableColumn arrivalDateColumn = viewDAOImplementation.getColumnName("ARRIVAL DATE", "arrivalDate");
        TableColumn departureDateColumn = viewDAOImplementation.getColumnName("DEPARTURE DATE", "departureDate");
        TableColumn actionColumn = viewDAOImplementation.getColumnName("ACTION", "DUMMY");
        actionColumn.setCellFactory(viewDAOImplementation.setupTableCells(new BookedRoom(), tableView));

        BookedRoomDAOImplementation bookedRoomDAOImplementation = new BookedRoomDAOImplementation();
        TableColumn[] list = new TableColumn[] {roomNumberColumn, arrivalDateColumn, departureDateColumn, actionColumn};

        viewDAOImplementation.setTableView(tableView);
        viewDAOImplementation.setProgressInd(progressInd);
        viewDAOImplementation.addColumnNames(list);

        // create a worker
        Task<List<Object>> worker = viewDAOImplementation.createWorker(bookedRoomDAOImplementation);

        // run the task using a thread from the thread pool:
        exec.execute(worker);
    }

    @FXML
    private void onAddRoomClick() {
        viewDAOImplementation.addObject(new BookedRoom());
    }
}