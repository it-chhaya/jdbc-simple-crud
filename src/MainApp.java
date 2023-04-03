import model.Topic;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static JdbcImpl jdbc;
    private static Scanner scanner;

    public static void main(String[] args) {

        jdbc = new JdbcImpl();
        scanner = new Scanner(System.in);

        Topic topic = new Topic();
        System.out.print("Enter name: ");
        topic.setName(scanner.nextLine());
        System.out.print("Enter description: ");
        topic.setDescription(scanner.nextLine());
        topic.setStatus(true);

        insertTopic(topic);
        selectTopics();

    }

    // Insert record
    private static void insertTopic(Topic topic) {
        try (Connection conn = jdbc.dataSource().getConnection()) {
            String insertSql = "INSERT INTO topics (name, description, status) " +
                    "VALUES(?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(insertSql);
            statement.setString(1, topic.getName());
            statement.setString(2, topic.getDescription());
            statement.setBoolean(3, topic.getStatus());
            int count = statement.executeUpdate();
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void selectTopics() {
        try (Connection conn = jdbc.dataSource().getConnection()) {
            // 1. Create SQL Statement object
            String selectSql = "SELECT * FROM topics";
            PreparedStatement statement = conn.prepareStatement(selectSql);
            // 2. Execute SQL Statement object
            ResultSet resultSet = statement.executeQuery();
            // 3. Process Result with ResultSet
            List<Topic> topics = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Boolean status = resultSet.getBoolean("status");
                topics.add(new Topic(id, name, description, status));
            }

            topics.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
