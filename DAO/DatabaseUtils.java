package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    public static List<String> getTableColumns(String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();
        String sql = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";

        Connection connection = Database.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    columns.add(resultSet.getString("column_name"));
                }
            }
        } finally {
            connection.close();
        }
        return columns;
    }
}
