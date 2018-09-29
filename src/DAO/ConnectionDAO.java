package DAO;

import java.sql.Connection;

public interface ConnectionDAO {
    final String URL = "jdbc:mysql://localhost:3306/barmanagementsystem?useSSL=false";
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String USERNAME = "poolbaradmin";
    final String PASSWORD = "poolbaradmin";

    Connection getConnection();
    void closeConnection();
    void printConnectionError(String exception);
}
