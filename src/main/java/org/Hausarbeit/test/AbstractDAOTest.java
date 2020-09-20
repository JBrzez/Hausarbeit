package org.Hausarbeit.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Statement;
import static org.junit.Assert.assertNotNull;

public class AbstractDAOTest {
    private Statement statement;

    @Test
       public void testCreate() {
            statement = getStatement();
            assertNotNull(statement);
        }

    private Statement getStatement() {
    }
}