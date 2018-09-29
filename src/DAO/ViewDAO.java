package DAO;

import Objects.Item;
import com.jfoenix.controls.JFXListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.List;

public interface ViewDAO {
	TableColumn getColumnName(String columnName, String field);
	void addButtonIcons(Button editBtn, Button deleteBtn);
	Callback setupTableCells(Object o, TableView tableView);
	void printRecords(GeneralDAO classType);
	void editObject(Object o);
	void addObject(Object o);
}
