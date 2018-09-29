package DAOImplementation;

import DAO.GeneralDAO;
import Objects.Item;
import Objects.Order;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class OrderDAOImplementation implements GeneralDAO<Order> {
    private ConnectionDAOImplementation connectionDAOImplementation;
    private Connection connection = null;

    public OrderDAOImplementation() {
        connectionDAOImplementation = new ConnectionDAOImplementation();
    }

    @Override
    public List<Order> getAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        List<Order> orderList = new ArrayList<>();
        connection = connectionDAOImplementation.getConnection();

        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM `orders`";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int orderID = resultSet.getInt(1);
                int roomID = resultSet.getInt(2);
                String date = resultSet.getDate(3).toString();
                String time = resultSet.getTime(4).toString();
                boolean status = resultSet.getBoolean(5);

                Order newOrder = new Order();

                newOrder.setOrderID(orderID);
                newOrder.setRoomID(roomID);
                newOrder.setRoomNumber(getRoomNumber(roomID)); // use function getRoomNumber() to find roomNumber
                newOrder.setItemList(getItemList(orderID));    // use function getItemList() to get items of specific order
                newOrder.setItemListToPrint();
                newOrder.setDate(date);
                newOrder.setTime(time);
                newOrder.setStatus(status);

                orderList.add(newOrder);
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
            return orderList;
        }
    }

    // method to get specific orders
    public List<Order> get(String orderCategory, String orderDate, String orderStatus) {
        Statement statement = null;
        ResultSet resultSet = null;
        List<Order> orderList = new ArrayList<>();
        connection = connectionDAOImplementation.getConnection();

        // bar category: <> extra
        // extra category: = extra
        // all categories: nothing to check
        String categoryParameter = null;
        if (orderCategory.equals("Bar Category")) {categoryParameter = "items.itemCategory <> 'Extra' ";}
        else if (orderCategory.equals("Extra Category")) {categoryParameter = "items.itemCategory = 'Extra' ";}
        else if (orderCategory.equals("All Categories")) {categoryParameter = "(items.itemCategory = 1 OR 1=1)";}

        // today: = now()
        // last 7 days: > NOW() - INTERVAL 7 DAY
        // last month: > NOW() - INTERVAL 30 DAY
        String dateParameter = null;
        if (orderDate.equals("Today")) {dateParameter = " orders.date = CURDATE() ";}
        else if (orderDate.equals("Yesterday")) {dateParameter = " orders.date = CURDATE() - INTERVAL 1 DAY ";}
        else if (orderDate.equals("Last 7 days")) {dateParameter = " orders.date > CURDATE() - INTERVAL 7 DAY ";}
        else if (orderDate.equals("Last month")) {dateParameter = " orders.date > CURDATE() - INTERVAL 30 DAY ";}
        else if (orderDate.equals("All")) {dateParameter = " (orders.date = 1 OR 1=1) ";}

        // paid: = true
        // unpaid: = false
        // all: nothing to check
        String statusParameter = null;
        if (orderStatus.equals("Paid")) {statusParameter = " orders.status = true ";}
        else if (orderStatus.equals("Unpaid")) {statusParameter = " orders.status = false ";}
        else if (orderStatus.equals("All")) {statusParameter = " (orders.status = 1 OR 1=1) ";}


        try {
            statement = connection.createStatement();
            String query = "SELECT DISTINCT orders.* FROM orders, order_item, items WHERE " +
                                   statusParameter + " AND " +
                                   dateParameter + "AND " +
                                   "orders.orderID = order_item.orderID AND " +
                                   "order_item.itemID = items.itemID AND " +
                                    categoryParameter;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int orderID = resultSet.getInt(1);
                int roomID = resultSet.getInt(2);
                String date = resultSet.getDate(3).toString();
                String time = resultSet.getTime(4).toString();
                boolean status = resultSet.getBoolean(5);

                Order newOrder = new Order();

                newOrder.setOrderID(orderID);
                newOrder.setRoomID(roomID);
                newOrder.setRoomNumber(getRoomNumber(roomID)); // use function getRoomNumber() to find roomNumber
                newOrder.setItemList(getItemList(orderID));    // use function getItemList() to get items of specific order
                newOrder.setItemListToPrint();
                newOrder.setDate(date);
                newOrder.setTime(time);
                newOrder.setStatus(status);

                orderList.add(newOrder);
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
            return orderList;
        }
    }

    // add this function to return roomNumber instead of roomID..
    private String getRoomNumber(int roomID) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String roomNumber = "-1";
        connection = connectionDAOImplementation.getConnection();

        try {
            String query = "SELECT booked_rooms.roomNumber FROM booked_rooms WHERE booked_rooms.roomID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, roomID);
            resultSet = statement.executeQuery();


            while (resultSet.next()) {
                roomNumber = resultSet.getString(1);
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
            return roomNumber;
        }

    }

    // add this function to return item list of specific order
    private List<Item> getItemList(int orderID) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Item> itemList = new ArrayList<>();
        connection = connectionDAOImplementation.getConnection();

        try {
            String query = "SELECT items.* FROM order_item, items WHERE order_item.itemID = items.itemID AND orderID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, orderID);
            resultSet = statement.executeQuery();

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
    public int insert(Order order) {
        connection = connectionDAOImplementation.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        int orderID = -1;

        try {
            String query = "INSERT INTO `orders` (`roomID`, `date`, `time`, `status`) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, order.getRoomID());
            statement.setDate(2, java.sql.Date.valueOf(order.getDate()));
            String[] time = order.getTime().split(Pattern.quote("."));
            statement.setTime(3, java.sql.Time.valueOf(time[0]));
            statement.setBoolean(4, order.isStatus());

            statement.executeUpdate();

            // get orderID
            rs = statement.getGeneratedKeys();
            rs.next();
            orderID = rs.getInt(1);

            // set orderID to object
            order.setOrderID(orderID);

            // insert items to order_item using specific orderID
            insertOrderItems(orderID, order.getItemList());
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
            return orderID;
        }
    }


    private void insertOrderItems(int orderID, List<Item> itemList) {
        connection = connectionDAOImplementation.getConnection();
        PreparedStatement statement = null;

        try {
            String query = "INSERT INTO `order_item` (`orderID`, `itemID`) VALUES (?, ?)";

            for (Item item : itemList) {
                statement = connection.prepareStatement(query);
                statement.setInt(1, orderID);
                statement.setInt(2, item.getItemID());
                statement.executeUpdate();
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
        }
        connectionDAOImplementation.closeConnection();
    }

    @Override
    public boolean update(Order order) {
        connection = connectionDAOImplementation.getConnection();
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String query = "UPDATE `orders` SET `roomID` = ?, `date` = ?, `time` = ?, `status` = ? WHERE `orderID` = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1, order.getRoomID());
            statement.setDate(2, java.sql.Date.valueOf(order.getDate()));
            String[] time = order.getTime().split(Pattern.quote("."));
            statement.setTime(3, java.sql.Time.valueOf(time[0]));
            statement.setBoolean(4, order.isStatus());
            statement.setInt(5, order.getOrderID());

            statement.executeUpdate();

            // handle order's itemList
            List<Item> previousItemList = getItemList(order.getOrderID());
            List<Item> updatedItemList = order.getItemList();
            List<Item> finalItemList = new ArrayList<>();

            // find the common items on both lists and remove them..
            // the items that remain add them to table
            for (int i = 0; i < updatedItemList.size(); i++) {
                boolean notExist = true;
                for (int j = 0; j < previousItemList.size(); j++) {
                    if (updatedItemList.get(i).getItemID() == previousItemList.get(j).getItemID()) {
                        previousItemList.remove(previousItemList.get(j));
                        notExist = false;
                        break;
                    }
                }
                if (notExist) {
                    finalItemList.add(updatedItemList.get(i));
                }
            }

            // insert new items to the table
            insertOrderItems(order.getOrderID(), finalItemList);

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
    public boolean delete(Order order) {
        connection = connectionDAOImplementation.getConnection();
        PreparedStatement statement = null;
        boolean success = false;

        try {
            // delete order's items
            success = deleteOrderItems(order.getOrderID());

            String sql = "DELETE FROM `orders` WHERE orderID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, order.getOrderID());

            statement.executeUpdate();
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

    private boolean deleteOrderItems(int orderID) {
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String sql = "DELETE FROM `order_item` WHERE orderID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, orderID);
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

            return success;
        }
    }

    @Override
    public boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this order?");

        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No");
        alert.getButtonTypes().setAll(yesBtn, noBtn);

        Optional<ButtonType> result = alert.showAndWait();

        return result.get().equals(yesBtn);
    }

	@Override
	public String getFXMLPath(String type) {
        if (type.equals("EDIT")) {
            return "/View/ViewOrders/EditOrder/EditOrder.fxml";
        }
        else {
            return "/View/ViewOrders/AddOrder/AddOrder.fxml";
        }
	}
}