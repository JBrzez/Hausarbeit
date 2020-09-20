package org.Hausarbeit.test;

import org.Hausarbeit.process.exceptions.DatabaseException;
import org.Hausarbeit.services.db.JDBCConnection;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


public class JDBCTest {


    @Test
    public void createTest() throws DatabaseException {
        JDBCConnection jdbc = JDBCConnection.getInstance();
        assertNotNull(jdbc);
    }
    @Test
    public void readStatementTest() throws DatabaseException { //Throwt Exception, da diese in jdbc klasse ebenso geworfen wird
        JDBCConnection con = JDBCConnection.getInstance();
        try {
            con.initConnection();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        assertNotNull(con.getStatement()); // Checkt, ob Statement nicht null
    }
    @Test
    public void deleteCon() throws DatabaseException {
        JDBCConnection con = JDBCConnection.getInstance();
        con.initConnection();
        con.closeConnection();
        assertNotNull(con);
    }



}
