package com.grupobios.bioslis.bs.microbiologia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Date;

import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBTestDAO;
import com.grupobios.bioslis.microbiologia.entity.MBTest;

@ExtendWith(SystemStubsExtension.class)
public class TestEditorTest {
/*
    @Test
    public void testSignAvailableTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO dao = daoFactory.getDAO("MBTest");
        MBTest test = dao.getTestByIds(142, 36, 43);
        test.setStatus("PENDIENTE");
        test.setMicrobiologyStatus("NEGATIVO");
        dao.update(test);

        TestEditor editor = new TestEditor("142", "36", "43");

        assertTrue(editor.signTest());
        assertEquals("FIRMADO", dao.getTestByIds(142, 36, 43).getStatus());

    }

    @Test
    public void testSignNotAvailableTest() {
        DAOFactory daoFactory = new DAOFactory();
        MBTestDAO dao = daoFactory.getDAO("MBTest");
        MBTest test = dao.getTestByIds(142, 36, 43);
        test.setStatus("PENDIENTE");
        test.setMicrobiologyStatus("PENDIENTE");
        dao.update(test);

        TestEditor editor = new TestEditor("142", "36", "43");

        assertFalse(editor.signTest());
        assertEquals("PENDIENTE", dao.getTestByIds(142, 36, 43).getStatus());

    }
*/
}