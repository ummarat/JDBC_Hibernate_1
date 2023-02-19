import java.sql.*;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    private static final String INSER_NEW = "INSERT INTO arhiv (fond, opis, sprava, name, stor) "
            + " VALUES(?,?,?,?,?)";

    public static void main(String[] args) throws SQLException {
        //перевірка драйвера
        registerDriver();

        Connection connection = null;
        Statement statement = null;
        PreparedStatement ps;

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();

            //створюємо нову таблицю arhiv
            System.out.println("Creating table in given database...");
            String tab_arhiv = "CREATE TABLE ARHIV " +
                    "(id int auto_increment primary key not null,\n" +
                    "fond int (25) not null,\n" +
                    "opis int (25) not null,\n" +
                    "sprava int (25) not null,\n" +
                    "name varchar (200) not null,\n" +
                    "stor int (100) not null)";

            statement.executeUpdate(tab_arhiv);

            //додаємо до таблиці данні
            statement.addBatch("INSERT INTO arhiv(fond, opis, sprava, name, stor) VALUES (1, 2, 245, 'Метрична книга', 302)");
            statement.addBatch("INSERT INTO arhiv(fond, opis, sprava, name, stor) VALUES (1, 3, 120, 'Метрична книга', 64)");
            statement.addBatch("INSERT INTO arhiv(fond, opis, sprava, name, stor) VALUES (2, 2, 789, 'Метрична книга', 541)");
            statement.executeBatch();

            //виводимо всю таблицю в консоль
            ResultSet resultSet = statement.executeQuery("SELECT * FROM arhiv");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int fond = resultSet.getInt("fond");
                int opis = resultSet.getInt("opis");
                int sprava = resultSet.getInt("sprava");
                String name = resultSet.getString("name");
                int stor = resultSet.getInt("stor");

                System.out.println(id + " " + fond + " " + opis + " "
                        + sprava + " " + name + " " + stor);
            }

            //видаляємо з таблиці данні, де "sprava = 120"
            ps = connection.prepareStatement("DELETE FROM arhiv WHERE sprava = 120");

            int res = ps.executeUpdate();
            System.out.println(res);

            //додаємо до таблиці данні
            ps = connection.prepareStatement(INSER_NEW);

            ps.setInt(1, 7);
            ps.setInt(2, 9);
            ps.setInt(3, 112);
            ps.setString(4, "Реввзькі казки");
            ps.setInt(5, 300);

            ps.execute();

        } catch (SQLException e) {
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