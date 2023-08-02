package com.grupobios.bioslis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;

import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SystemStubsExtension.class)
public class DAOFactoryTest {
/*
    @Test
    public void testSQLDelgatedDAOFactory(EnvironmentVariables environmentVariable) {
        environmentVariable.set("BIOSLIS_PERSISTENCE", "");
        DAOFactory daoFactory = new DAOFactory();
        assertEquals("com.grupobios.bioslis.dao.rdbms.DAODelegatedRDBMSFactory", daoFactory.getDelegatedClassName());
    }

    @Test
    public void testDefaultDelgatedDAOFactory(EnvironmentVariables environmentVariable){
        environmentVariable.set("BIOSLIS_PERSISTENCE", "MEMORY");
        DAOFactory daoFactory = new DAOFactory();
        assertEquals("com.grupobios.bioslis.dao.memory.DAODelegatedMemoryFactory", daoFactory.getDelegatedClassName());
    }
*/
}