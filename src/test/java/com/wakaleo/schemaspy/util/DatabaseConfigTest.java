/*
 * JDBCHelperTest.java
 * JUnit based test
 *
 * Created on 18 May 2007, 14:15
 */

package com.wakaleo.schemaspy.util;

import java.net.URI;

import junit.framework.TestCase;

/**
 * 
 * @author john
 */
public class DatabaseConfigTest extends TestCase {

    public DatabaseConfigTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    private final String[][] DATABASE_TYPES_TEST_DATA = {
            { "jdbc:derby:testdb", "derby", null, null, "testdb" },
            { "jdbc:db2:testdb", "db2", null, null, "testdb" },
            { "jdbc:firebirdsql://localhost/testdb", "firebirdsql", "localhost", null, "testdb" },
            { "jdbc:firebirdsql://server:9999/testdb", "firebirdsql", "server", "9999", "testdb" },
            { "jdbc:hsqldb:hsql://localhost/testdb", "hsqldb", "localhost", null, "testdb" },
            { "jdbc:hsqldb:hsql://server:9999/testdb", "hsqldb", "server", "9999", "testdb" },
            { "jdbc:informix-sqli://localhost/testdb:INFORMIXSERVER=server", "informix-sqli", "localhost", null, "testdb/INFORMIXSERVER=server" },
            { "jdbc:microsoft:sqlserver://server:9999;databaseName=testdb",
                    "mssql", "server", "9999", "testdb" },
            { "jdbc:jtds://server:9999/testdb", "mssql-jtds","server", "9999", "testdb"  },
            { "jdbc:mysql://localhost/testdb", "mysql", "localhost", null, "testdb"},
            { "jdbc:oracle:oci8:@testdb", "ora", null, null, "testdb" },
            { "jdbc:oracle:thin:@server:9999:testdb", "orathin", "server", "9999", "testdb" },
            { "jdbc:postgresql://localhost/testdb", "pgsql", "localhost", null, "testdb" },
            { "jdbc:sybase:Tds:server:9999/testdb", "sybase", "server", "9999", "testdb" } };

    public void testExtractDbUrl() throws Exception {
    	for (String[] testDataEntry : DATABASE_TYPES_TEST_DATA) {
    		String expectedDatabaseType = testDataEntry[1];
    		String expectedHostname = testDataEntry[2];
    		String expectedPort = testDataEntry[3];
    		String expectedDbName = testDataEntry[4];
    		
    		DatabaseConfig config = new DatabaseConfig(testDataEntry[0]);
    		assertEquals(testDataEntry[0] + " type mismatch", expectedDatabaseType, config.getType());
    		assertEquals(testDataEntry[0] + " hostname mismatch", expectedHostname, config.getHost());
    		assertEquals(testDataEntry[0] + " port mismatch", expectedPort, config.getPort());
    		assertEquals(testDataEntry[0] + " database mismatch", expectedDbName, config.getDatabase());
    	}
    }
}
