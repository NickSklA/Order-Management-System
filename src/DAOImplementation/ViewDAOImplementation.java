package DAOImplementation;

import DAO.AddDAO;
import DAO.EditDAO;
import DAO.GeneralDAO;
import DAO.ViewDAO;
import Objects.BookedRoom;
import Objects.Item;
import Objects.Order;
import Objects.RoomInfo;
import View.ViewBookedRooms.EditBookedRoom.EditBookedRoom;
import View.ViewItems.EditItem.EditItem;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import View.ViewOrders.EditOrder.EditOrder;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ViewDAOImplementation implements ViewDAO {
	private TableView tableView;
	private ProgressIndicator progressInd;
	
	public ViewDAOImplementation() {
		tableView = new TableView();
	}
	
	@Override
	public TableColumn getColumnName(String columnName, String field) {
		TableColumn column = new TableColumn(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(field));
        if (!field.equals("itemListToPrint"))
            column.setStyle("-fx-alignment: CENTER; -fx-font-size: 17px");
        else {
            column.setStyle("-fx-alignment: CENTER_LEFT; -fx-font-size: 17px");
        }
		return column;
	}

	@Override
	public void addButtonIcons(Button editBtn, Button deleteBtn) {
		ImageView editIcon = new ImageView("/Icons/edit.png");
		editIcon.setFitWidth(24);
		editIcon.setFitHeight(24);
		editBtn.setGraphic(editIcon);

		ImageView deleteIcon = new ImageView("/Icons/delete.png");
		deleteIcon.setFitWidth(24);
		deleteIcon.setFitHeight(24);
		deleteBtn.setGraphic(deleteIcon);
	}

	@Override
	public Callback setupTableCells(Object o, TableView tableView) {
		Callback cellFactory = new Callback<TableColumn<Object, String>, TableCell<Object, String>>() {
			@Override
			public TableCell call(final TableColumn<Object, String> param) {
				final TableCell cell = new TableCell<Object, String>() {
					final Button editBtn = new Button("");
					final Button deleteBtn = new Button("");

					HBox hBox = new HBox(editBtn, deleteBtn);

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);

						if (empty) {
							setGraphic(null);
							setText(null);
						}
						else {
							editBtn.setStyle("-fx-background-color: transparent");
							deleteBtn.setStyle("-fx-background-color: transparent");

							addButtonIcons(editBtn, deleteBtn);

							hBox.setAlignment(Pos.CENTER);
							setGraphic(hBox);
							setText(null);
							
							GeneralDAO finalDaoImplementation = assignTableType(o);
								
							editBtn.setOnAction(event -> {
								Object object = tableView.getItems().get(getIndex());
								editObject(object);
							});

							deleteBtn.setOnAction(event -> {
								boolean confirmDelete = finalDaoImplementation.confirmDelete();
								if (confirmDelete) {
									Object object = tableView.getItems().get(getIndex());
									finalDaoImplementation.delete(object);
									tableView.getItems().remove(object);
								}
							});
						}
					}
				};

				return cell;
			}
		};

		return cellFactory;
	}


	private GeneralDAO assignTableType(Object o) {
		GeneralDAO daoImplementation = null;
		
		if (o instanceof Item) {
			daoImplementation = new ItemDAOImplementation();
		}
		else if (o instanceof Order) {
			daoImplementation = new OrderDAOImplementation();
		}
		else if (o instanceof BookedRoom) {
			daoImplementation = new BookedRoomDAOImplementation();
		}
		
		return daoImplementation;
	}


	@Override
	public void printRecords(GeneralDAO classType) {
		ObservableList<Object> list = FXCollections.observableList(classType.getAll());
		tableView.getItems().clear();
		tableView.setItems(list);
	}

	// create task worker to send the query
    // when completes set retrieved data to tableview
    public Task<List<Object>> createWorker(GeneralDAO classType) {

	    // create the task
        Task<List<Object>> task = new Task<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                return classType.getAll();
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
	
	@Override
	public void editObject(Object o) {
		Stage editStage = new Stage();
		Parent root = null;
		GeneralDAO finalDaoImplementation = assignTableType(o);
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(finalDaoImplementation.getFXMLPath("EDIT")));
			root = fxmlLoader.load();
			EditDAO controller = fxmlLoader.<EditDAO>getController();
			
			if (finalDaoImplementation instanceof ItemDAOImplementation) {
				Item item = ((Item) o);

				((EditItem) controller).setItemID(item.getItemID());
				((EditItem) controller).setItemCategoryField(item.getItemCategory());
				((EditItem) controller).setItemNameField(item.getItemName());
				((EditItem) controller).setItemPriceField(String.valueOf(item.getPrice()));
			}
			else if (finalDaoImplementation instanceof OrderDAOImplementation) {
				Order order = ((Order) o);
				
				((EditOrder) controller).setOrderID(order.getOrderID());
				((EditOrder) controller).setOrderRoomNumberField(order.getRoomNumber());
				((EditOrder) controller).setItemList(order.getItemList());
				((EditOrder) controller).setOrderDateField(order.getDate());
				((EditOrder) controller).setOrderTimeField(order.getTime());
				((EditOrder) controller).setOrderStatusBox(order.isStatus());
			}
			else if (finalDaoImplementation instanceof BookedRoomDAOImplementation) {
				BookedRoom bookedRoom = ((BookedRoom) o);

				((EditBookedRoom) controller).setBookedRoomID(bookedRoom.getRoomID());
				((EditBookedRoom) controller).setRoomNumberField(bookedRoom.getRoomNumber());
				((EditBookedRoom) controller).setArrivalDatePicker(bookedRoom.getArrivalDate());
				((EditBookedRoom) controller).setDepartureDatePicker(bookedRoom.getDepartureDate());
			}
			
			editStage.setTitle("Edit " + o.getClass().getSimpleName());
			editStage.setScene(new Scene(root));
			editStage.getIcons().add(new Image("/Icons/edit.png"));
			editStage.show();
			editStage.setOnCloseRequest(e -> {
			    Object record = controller.getUpdatedObject();
			    if (record != null) {
                    finalDaoImplementation.update(record);
                    int index = tableView.getItems().indexOf(o);
                    tableView.getItems().remove(o);
                    tableView.getItems().add(index, record);
                }
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void addObject(Object o) {
		Stage addStage = new Stage();
		Parent root = null;
		GeneralDAO finalDaoImplementation = assignTableType(o);

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(finalDaoImplementation.getFXMLPath("ADD")));
			root = fxmlLoader.load();
			AddDAO controller = fxmlLoader.<AddDAO>getController();

			addStage.setTitle("Add " + o.getClass().getSimpleName());
			addStage.setScene(new Scene(root));
			addStage.getIcons().add(new Image("/Icons/edit.png"));
			addStage.show();
			addStage.setOnCloseRequest(e -> {
			    Object record = controller.getObject();
			    if (record != null) {
                    finalDaoImplementation.insert(record);
                    tableView.getItems().add(record);
                }
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void addColumnNames(TableColumn[] list) {
		tableView.getColumns().addAll(Arrays.asList(list));
	}
	
	public void setTableView(TableView tableView) {
		this.tableView = tableView;
	}

	public void setProgressInd(ProgressIndicator progressInd) {this.progressInd = progressInd;}
}