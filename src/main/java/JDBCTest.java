import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCTest {

    public static void main(String[] args) throws SQLException, IOException {

        JDBCTest jdbcTest = new JDBCTest();
        Properties properties = jdbcTest.loadProperties();

        Connection connection = DriverManager
                .getConnection(properties.getProperty("url"),
                        properties.getProperty("username"),
                        properties.getProperty("password"));

        System.out.println("connection established");
        jdbcTest.addSubject(connection);
        jdbcTest.printStudents(connection);
    }

    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        InputStream stream = getClass().getResourceAsStream("db.properties");
        properties.load(stream);
        return properties;

    }

    public void printStudents(Connection connection) throws SQLException {

        String sql = "select * from subjects";
        Statement statement = connection.createStatement();
        statement.execute(sql);

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()) {
            String str = "";
            str += resultSet.getString(1) + ", ";
            str += resultSet.getString(2) + ", ";
            str += resultSet.getString(3) + ", ";
            System.out.println(str);
        }

    }

    public void addSubject(Connection connection) throws SQLException {

        String sql = "insert into subjects(name, teacher) values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "Java");
        preparedStatement.setInt(2, 4);
    }
}
