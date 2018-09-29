package View.ViewItems;

import DAOImplementation.ItemDAOImplementation;
import Objects.Item;
import DAOImplementation.ViewDAOImplementation;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class ContentItemsController implements Initializable {
    @FXML
    private TableView<Item> tableView;

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

		TableColumn itemCategoryColumn   = viewDAOImplementation.getColumnName("ITEM CATEGORY", "itemCategory");
		TableColumn itemNameColumn   = viewDAOImplementation.getColumnName("ITEM NAME", "itemName");
		TableColumn itemPriceColumn  = viewDAOImplementation.getColumnName("ITEM PRICE", "price");
		TableColumn itemActionColumn = viewDAOImplementation.getColumnName("ACTION", "DUMMY");
        itemActionColumn.setCellFactory(viewDAOImplementation.setupTableCells(new Item(), tableView));
		
		ItemDAOImplementation itemDAOImplementation = new ItemDAOImplementation();
		TableColumn[] list = new TableColumn[] {itemCategoryColumn, itemNameColumn, itemPriceColumn, itemActionColumn};
		
		viewDAOImplementation.setTableView(tableView);
		viewDAOImplementation.setProgressInd(progressInd);
		viewDAOImplementation.addColumnNames(list);

        // create a worker
        Task<List<Object>> worker = viewDAOImplementation.createWorker(itemDAOImplementation);

        // run the task using a thread from the thread pool:
        exec.execute(worker);
    }

	@FXML
    private void onAddItemClick() {
		viewDAOImplementation.addObject(new Item());
	}
}