package com.grupobios.bioslis.bs;

import com.grupobios.bioslis.dao.DatosFichas_DAO;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(SystemStubsExtension.class)
public class FichasExamenesTest {

    @Autowired
   private DatosFichas_DAO datosFichas_dao;

    /*
    @Test
    public void testDAO() throws BiosLISDaoException {

        List<DatosFichasexamenes> lst = OrdenServiceImpl.getAll();
        assert(0 <lst.size());
        
        
    }

    @Test
    public void testgetOrdenesInformeResultadosO() throws BiosLISDaoException {

        List<OrdenInformeResultadoDTO> lst = OrdenServiceImpl.getOrdenesInformeResultados(null);
        assert(0 <lst.size());
        
        
    }

     }
   @Test
    public void testMailSender() throws IOException, BiosLISException{
    
        BioslisMailSenderImpl ms = new BioslisMailSenderImpl();
        
        ms.enviarMail("eric.nicholls@grupobios.cl", "Prueba bioslis", "Hola");


 
}
*/   



}