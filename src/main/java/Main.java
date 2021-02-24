// import oracle.jdbc.OracleConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.Console;
import java.sql.*;
import java.util.Properties;

@SpringBootApplication
public class Main {

    final static Logger LOG = LoggerFactory.getLogger(Main.class);

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
            LOG.info("****** Starting JDBC Connection test *******");

            final Connection conn = DriverManager.getConnection(jdbcConnString, properties);
            conn.setAutoCommit(false);
            final Statement statement = conn.createStatement();
            LOG.info("Running SQL query: [{}]", sqlQuery);
            final ResultSet resultSet = statement.executeQuery(sqlQuery);
            final ResultSetMetaData rsmd = resultSet.getMetaData();
            final int columnsNumber = rsmd.getColumnCount();

            if (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(rsmd.getColumnName(i) + " ");
                    if (i > 1 && i < columnsNumber) System.out.print(",  ");
                }
                System.out.println("");
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(resultSet.getString(i) + " ");
                    if (i > 1 && i < columnsNumber) System.out.print(",  ");
                }
                System.out.println("");
            }

            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.print(resultSet.getString(i) + " ");
                    if (i > 1 && i < columnsNumber) System.out.print(",  ");
                    if (i == columnsNumber) System.out.println("");
                }
            }

            statement.close();
            conn.close();

            LOG.info("JDBC connection test successful!");
        } catch (SQLException ex) {
            LOG.error("Exception occurred connecting to database: {}", ex.getMessage());
        }
    }
}