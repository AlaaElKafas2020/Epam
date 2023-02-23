import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserDaoTest {
    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost/ems";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private static final String TEST_DATA_XML = "src/main/resources/testdata.xml";
    private IDatabaseTester databaseTester;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        Properties props = new Properties();
        props.setProperty("driver", DRIVER_CLASS_NAME);
        props.setProperty("url", JDBC_URL);
        props.setProperty("username", USER_NAME);
        props.setProperty("password", PASSWORD);
        databaseTester = new JdbcDatabaseTester(DRIVER_CLASS_NAME, JDBC_URL, USER_NAME, PASSWORD);
        databaseTester.setDataSet(new DefaultDataSet(loadTestData()));
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onSetup();
        connection = DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
    }

    @After
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
        if (databaseTester != null) {
            databaseTester.onTearDown();
        }
    }


    @Test
    public void testFindUserById() throws Exception {
        UserDao userDao = new UserDao(connection);

        // Test case 1: Valid user id
        User user = userDao.findUserById(1);
        ITable expectedTable = loadExpectedTable("users");

        String[] columnNames = {"id", "first_name", "last_name", "email"};
        DataType[] columnTypes = {DataType.INTEGER, DataType.VARCHAR, DataType.VARCHAR, DataType.VARCHAR};
        DefaultTable actualTable = new DefaultTable(expectedTable.getTableMetaData().getTableName(), expectedTable.getTableMetaData().getColumns());

        actualTable.addRow(new Object[]{user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()});

        Assertion.assertEquals(expectedTable, actualTable);
    }



    private ITable loadExpectedTable(String tableName) throws DataSetException, MalformedURLException {
        File file = new File(TEST_DATA_XML);
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(file).getTable(tableName);
    }

    private ITable loadTestData() throws Exception {
        File file = new File(TEST_DATA_XML);
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        return builder.build(file).getTable("users");
    }
}
