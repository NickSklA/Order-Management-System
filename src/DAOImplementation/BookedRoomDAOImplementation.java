package DAOImplementation;

import DAO.GeneralDAO;
import Objects.BookedRoom;
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

public class BookedRoomDAOImplementation implements GeneralDAO<BookedRoom> {
    private ConnectionDAOImplementation connectionDAOImplementation;
    private Connection connection = null;

    public BookedRoomDAOImplementation() {
        connectionDAOImplementation = new ConnectionDAOImplementation();
    }

    @Override
    public List<BookedRoom> getAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        List<BookedRoom> bookedRoomList = new ArrayList<>();
        connection = connectionDAOImplementation.getConnection();

        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM `booked_rooms`";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int roomID = resultSet.getInt(1);
                String roomNumber = resultSet.getString(2);
                String arrivalDate = resultSet.getDate(3).toString();
                String departureDate = resultSet.getDate(4).toString();

                BookedRoom newBookedRoom = new BookedRoom();

                newBookedRoom.setRoomID(roomID);
                newBookedRoom.setRoomNumber(roomNumber);
                newBookedRoom.setArrivalDate(arrivalDate);
                newBookedRoom.setDepartureDate(departureDate);

                bookedRoomList.add(newBookedRoom);
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
            return bookedRoomList;
        }
    }

    @Override
    public int insert(BookedRoom booked_room) {
        connection = connectionDAOImplementation.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        int bookedRoomID = -1;

        try {
            String query = "INSERT INTO `booked_rooms` (`roomNumber`, `arrivalDate`, `departureDate`) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, booked_room.getRoomNumber());
            statement.setDate(2, java.sql.Date.valueOf(booked_room.getArrivalDate()));
            statement.setDate(3, java.sql.Date.valueOf(booked_room.getDepartureDate()));

            statement.executeUpdate();

            // get bookedRoomID
            rs = statement.getGeneratedKeys();
            rs.next();
            bookedRoomID = rs.getInt(1);

            // set bookerRoomID to object
            booked_room.setRoomID(bookedRoomID);
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
            return bookedRoomID;
        }
    }

    @Override
    public boolean update(BookedRoom booked_room) {
        connection = connectionDAOImplementation.getConnection();
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String query = "UPDATE `booked_rooms` SET `roomNumber` = ?, `arrivalDate` = ?, `departureDate` = ? WHERE `roomID` = ?";
            statement = connection.prepareStatement(query);

            statement.setString(1, booked_room.getRoomNumber());
            statement.setDate(2, java.sql.Date.valueOf(booked_room.getArrivalDate()));
            statement.setDate(3, java.sql.Date.valueOf(booked_room.getDepartureDate()));
            statement.setInt(4, booked_room.getRoomID());

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
    public boolean delete(BookedRoom booked_room) {
        connection = connectionDAOImplementation.getConnection();
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String sql = "DELETE FROM `booked_rooms` WHERE roomID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, booked_room.getRoomID());

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
        alert.setContentText("Are you sure you want to delete this booked room?");

        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No");
        alert.getButtonTypes().setAll(yesBtn, noBtn);

        Optional<ButtonType> result = alert.showAndWait();

        return result.get().equals(yesBtn);
    }

	@Override
	public String getFXMLPath(String type) {
        if (type.equals("EDIT")){
            return "/View/ViewBookedRooms/EditBookedRoom/EditBookedRoom.fxml";
        }
        else {
            return "/View/ViewBookedRooms/AddBookedRoom/AddBookedRoom.fxml";
        }

	}
}