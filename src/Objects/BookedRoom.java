package Objects;

public class BookedRoom {
    private int roomID;
    private String roomNumber;
    private String arrivalDate;
    private String departureDate;

    public BookedRoom(String roomNumber, String arrivalDate, String departureDate) {
        this.roomNumber = roomNumber;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }

    public BookedRoom() {
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    @Override
    public String toString() {
        return "{" + roomID + ", " + roomNumber + ", " + arrivalDate + ", " + departureDate + "}";
    }
}
