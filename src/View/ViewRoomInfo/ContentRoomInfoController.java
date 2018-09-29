package View.ViewRoomInfo;


import DAO.EditDAO;
import DAOImplementation.RoomInfoDAOImplementation;
import DAOImplementation.ViewDAOImplementation;
import Objects.BookedRoom;
import Objects.Order;
import Objects.RoomInfo;
import View.ViewRoomInfo.EditRoomInfo.EditRoomInfo;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ContentRoomInfoController implements Initializable{

    @FXML
    private TableView tableView;

    @FXML
    private  ProgressIndicator progressInd;

    private ViewDAOImplementation viewDAOImplementation = new ViewDAOImplementation();

    private Executor exec;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t ;
        });

        TableColumn roomNumberColumn = viewDAOImplementation.getColumnName("ROOM NUMBER", "roomNumberToPrint");
        TableColumn roomOrdersColumn = viewDAOImplementation.getColumnName("LAST ORDER", "orderToPrint");
        TableColumn statusColumn = viewDAOImplementation.getColumnName("STATUS", "statusAndTotalCostToPrint");

        // set column width
        roomNumberColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width
        roomOrdersColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 60 ); // 60% width
        statusColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 20 ); // 20% width

        RoomInfoDAOImplementation roomInfoDAOImplementation = new RoomInfoDAOImplementation();
        TableColumn[] list = new TableColumn[] {roomNumberColumn, roomOrdersColumn, statusColumn};

        initializeContextMenuForTableView();

        viewDAOImplementation.setTableView(tableView);
        viewDAOImplementation.setProgressInd(progressInd);
        viewDAOImplementation.addColumnNames(list);

        // create a worker
        Task<List<Object>> worker = viewDAOImplementation.createWorker(roomInfoDAOImplementation);

        // run the task using a thread from the thread pool:
        exec.execute(worker);
    }

    private void initializeContextMenuForTableView() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit Room");
        editItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = tableView.getSelectionModel().getSelectedIndex();

                // avoid errors..
                if (index == -1)
                    return;

                // get room from table
                RoomInfo room = (RoomInfo) tableView.getItems().get(index);

                // open edit pane
                Stage editStage = new Stage();
                Parent root = null;

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ViewRoomInfo/EditRoomInfo/EditRoomInfo.fxml"));
                    root = fxmlLoader.load();
                    EditDAO controller = fxmlLoader.<EditDAO>getController();

                    ((EditRoomInfo) controller).setRoomNumberField(room.getRoom().getRoomNumber());
                    ((EditRoomInfo) controller).setArrivalDateField(room.getRoom().getArrivalDate());
                    ((EditRoomInfo) controller).setDepartureDateField(room.getRoom().getDepartureDate());
                    DecimalFormat df = new DecimalFormat("#,##0.00",
                            DecimalFormatSymbols.getInstance(Locale.US));
                    String result = df.format(room.getTotalCost());
                    ((EditRoomInfo) controller).setTotalCostField(result + "  €");
                    ((EditRoomInfo) controller).setTableView(room.getOrderList());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                editStage.setTitle("Edit Room Info");
                editStage.setScene(new Scene(root));
                editStage.getIcons().add(new Image("/Icons/edit.png"));
                editStage.show();
                editStage.setOnCloseRequest(event1 -> {
                    RoomInfoDAOImplementation roomInfoDAOImplementation = new RoomInfoDAOImplementation();

                    // create a worker
                    Task<List<Object>> worker = viewDAOImplementation.createWorker(roomInfoDAOImplementation);

                    // run the task using a thread from the thread pool:
                    exec.execute(worker);
                });
            }
        });

        contextMenu.getItems().add(editItem);
        tableView.setContextMenu(contextMenu);
        tableView.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
                event.consume();
            }
        });
    }
}
