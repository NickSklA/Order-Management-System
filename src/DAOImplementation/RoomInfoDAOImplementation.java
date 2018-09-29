package DAOImplementation;


import DAO.GeneralDAO;
import Objects.BookedRoom;
import Objects.Item;
import Objects.Order;
import Objects.RoomInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RoomInfoDAOImplementation implements GeneralDAO<RoomInfo>{
    private ConnectionDAOImplementation connectionDAOImplementation;
    private Connection connection = null;

    public RoomInfoDAOImplementation() {
        connectionDAOImplementation = new ConnectionDAOImplementation();
    }

    @Override
    public List<RoomInfo> getAll() {
        List<RoomInfo> roomInfoList = new ArrayList<>();

        // get all rooms where departure date > localDate.now()
        List<BookedRoom> roomList = getAllRooms();

        // for each room get its orders
        for (BookedRoom room : roomList) {

            RoomInfo roomInfo = new RoomInfo();
            roomInfo.setRoom(room);
            roomInfo.setOrderList(getRoomOrderList(room.getRoomID(), room.getRoomNumber()));

            roomInfoList.add(roomInfo);
        }

        // sort list by roomNumber
        Collections.sort(roomInfoList, new Comparator<RoomInfo>() {
            @Override
            public int compare(RoomInfo roomInfo1, RoomInfo roomInfo2) {
                String[] roomNumber1 = roomInfo1.getRoom().getRoomNumber().split("-|\\s+");
                String[] roomNumber2 = roomInfo2.getRoom().getRoomNumber().split("-|\\s+");
                return Integer.valueOf(roomNumber1[0]) - Integer.valueOf(roomNumber2[0]); // Ascending
            }
        });

        return roomInfoList;
    }

    private List<Order> getRoomOrderList(int roomID, String roomNumber) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Order> orderList = new ArrayList<>();
        connection = connectionDAOImplementation.getConnection();

        String query = "SELECT orders.* FROM orders WHERE orders.roomID = ?";

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, roomID);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderID = resultSet.getInt(1);
                roomID = resultSet.getInt(2);
                String date = resultSet.getDate(3).toString();
                String time = resultSet.getTime(4).toString();
                boolean status = resultSet.getBoolean(5);

                Order newOrder = new Order();

                newOrder.setOrderID(orderID);
                newOrder.setRoomID(roomID);
                newOrder.setRoomNumber(roomNumber);
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

    private List<BookedRoom> getAllRooms() {
        Statement statement = null;
        ResultSet resultSet = null;
        List<BookedRoom> roomList = new ArrayList<>();
        connection = connectionDAOImplementation.getConnection();
        String query = "SELECT * FROM booked_rooms WHERE booked_rooms.departureDate >= CURDATE()";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int roomID = resultSet.getInt(1);
                String roomNumber = resultSet.getString(2);
                String arrivalDate = resultSet.getString(3);
                String departureDate = resultSet.getString(4);

                BookedRoom room = new BookedRoom();

                room.setRoomID(roomID);
                room.setRoomNumber(roomNumber);
                room.setArrivalDate(arrivalDate);
                room.setDepartureDate(departureDate);

                roomList.add(room);
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
            return roomList;
        }
    }

    @Override
    public boolean update(RoomInfo roomInfo) {
        return false;
    }


    // not using the following methods.. //

    @Override
    public int insert(RoomInfo roomInfo) {
        return -1;
    }

    @Override
    public boolean delete(RoomInfo roomInfo) {
        return false;
    }

    @Override
    public boolean confirmDelete() {
        return false;
    }

    @Override
    public String getFXMLPath(String type) {
        return null;
    }
}
