import java.io.*;
import java.sql.*;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement = null;

        registerDriver();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\JavaProjects\\Cyberbionic\\JDBC_Hibernate\\Les_1\\JDBC1_work2\\src\\sql.txt"));
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            while (reader.readLine() != null) {

                String string = reader.readLine();

                if (string == null) {
                    continue;
                } else {
                    statement = connection.prepareStatement(string);
                    statement.execute();
                }

            }
            reader.close();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static void registerDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}