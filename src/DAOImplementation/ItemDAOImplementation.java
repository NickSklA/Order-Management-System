package DAOImplementation;

import DAO.GeneralDAO;
import Objects.Item;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class ItemDAOImplementation implements GeneralDAO<Item> {
    private ConnectionDAOImplementation connectionDAOImplementation;
    private Connection connection = null;

    public ItemDAOImplementation() {
        connectionDAOImplementation = new ConnectionDAOImplementation();
    }

    @Override
    public List<Item> getAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        List<Item> itemList = new ArrayList<>();
        connection = connectionDAOImplementation.getConnection();

        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM `items`";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int itemID = resultSet.getInt(1);
                String itemCategory = resultSet.getString(2);
                String itemName = resultSet.getString(3);
                double price = resultSet.getDouble(4);

                Item newItem = new Item();

                newItem.setItemID(itemID);
                newItem.setItemCategory(itemCategory);
                newItem.setItemName(itemName);
                newItem.setPrice(price);

                itemList.add(newItem);
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
            return itemList;
        }
    }

    @Override
    public int insert(Item item) {
        connection = connectionDAOImplementation.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        int itemID = -1;

        try {
            String query = "INSERT INTO `items` (`itemCategory`, `itemName`, `price`) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getItemCategory());
            statement.setString(2, item.getItemName());
            statement.setDouble(3, item.getPrice());

            statement.executeUpdate();

            // get itemID
            rs = statement.getGeneratedKeys();
            rs.next();
            itemID = rs.getInt(1);

            // set itemID to object
            item.setItemID(itemID);
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
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            connectionDAOImplementation.closeConnection();
            return itemID;
        }
    }

    @Override
    public boolean update(Item item) {
        connection = connectionDAOImplementation.getConnection();
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String query = "UPDATE `items` SET `itemCategory` = ?, `itemName` = ?, `price` = ? WHERE `itemID` = ?";
            statement = connection.prepareStatement(query);

            statement.setString(1, item.getItemCategory());
            statement.setString(2, item.getItemName());
            statement.setDouble(3, item.getPrice());
            statement.setInt(4, item.getItemID());

            statement.executeUpdate();
            success = true;
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

            connectionDAOImplementation.closeConnection();
            return success;
        }
    }

    @Override
    public boolean delete(Item item) {
        connection = connectionDAOImplementation.getConnection();
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String sql = "DELETE FROM `items` WHERE itemID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, item.getItemID());

            statement.executeUpdate();
            success = true;
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

            connectionDAOImplementation.closeConnection();
            return success;
        }
    }
	
	@Override
	public boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this item?");
		
		ButtonType yesBtn = new ButtonType("Yes");
		ButtonType noBtn = new ButtonType("No");
		alert.getButtonTypes().setAll(yesBtn, noBtn);
		
        Optional<ButtonType> result = alert.showAndWait();
		
		return result.get().equals(yesBtn);
    }

	@Override
	public String getFXMLPath(String type) {
        if (type.equals("EDIT")) {
            return "/View/ViewItems/EditItem/EditItem.fxml";
        }
        else {
            return "/View/ViewItems/AddItem/AddItem.fxml";
        }

	}
}