// import oracle.jdbc.OracleConnection;
import java.io.Console;
import java.sql.*;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {

        final Console console = System.console();
        final String username = console.readLine("Username: ");
        final String password = new String(console.readPassword("Password: "));
        System.out.println("Enter JDBC connection string, e.g.: jdbc:oracle:thin:@//<host>:<port>/<SID>");
        final String jdbcConnString =  console.readLine("JDBC Connection String: ");
        String sqlQuery =  console.readLine("SQL Query (blank defaults to: `SELECT sysdate FROM dual;`): ");

        Class.forName("oracle.jdbc.driver.OracleDriver");

        final Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        // properties.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CONNECT_TIMEOUT, "10000");
        // New, append to JDBC connection string ":oracle.net.CONNECT_TIMEOUT=2000;"

        try {
            System.out.println("****** Starting JDBC Connection test *******");

            final Connection conn = DriverManager.getConnection(jdbcConnString, properties);
            final Statement statement = conn.createStatement();
            System.out.println("Running SQL query: " + sqlQuery);
            final ResultSet resultSet = statement.executeQuery(sqlQuery);
            final ResultSetMetaData rsmd = resultSet.getMetaData();
            final int columnsNumber = rsmd.getColumnCount();

            if (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + " ");
                    if (i > 1 && i < columnsNumber) System.out.print(",  ");
                }
                System.out.print("\n");
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(resultSet.getString(i) + " ");
                    if (i > 1 && i < columnsNumber) System.out.print(",  ");
                }
                System.out.print("\n");
            }

            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(resultSet.getString(i) + " ");
                    if (i > 1 && i < columnsNumber) System.out.print(",  ");
                    if (i == columnsNumber) System.out.print("\n");
                }
            }

            statement.close();
            conn.close();

            System.out.println("JDBC connection test successful!");
        } catch (SQLException ex) {
            System.out.println("Exception occurred connecting to database: " + ex.getMessage());
        }
    }
}