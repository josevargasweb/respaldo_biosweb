package com.grupobios.bioslis.entity.microbiologia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import com.grupobios.bioslis.microbiologia.bs.TestBuilder;
import com.grupobios.bioslis.microbiologia.dao.DAOFactory;
import com.grupobios.bioslis.microbiologia.dao.MBAntibiogramDAO;
import com.grupobios.bioslis.microbiologia.dao.MBTestDAO;

@ExtendWith(SystemStubsExtension.class)
public class MBAntibiogramTest {
/*
    @Test
    public void testNotExistigMBAntibiogramDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBAntibiogramDAO dao = daoFactory.getDAO("MBAntibiogram");
        MBAntibiogram readAntibiogram = dao.getAntibiogramByIds(142, 36, 37);
        System.out.println("******** DATOS ANTIBIOGRAMA NULL *********");
        System.out.println(readAntibiogram);

    }

    @Test
    public void testNotExistigMBAntibiogramMethodsDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBAntibiogramDAO dao = daoFactory.getDAO("MBAntibiogram");
        MBAntibiogram readAntibiogram = dao.getAntibiogramByIds(142, 35, 35);
        System.out.println("******** METODOS ANTIBIOGRAMA NULL *********");
        System.out.println(readAntibiogram);

    }

    @Test
    public void testMBAntibiogramDAO() {
        DAOFactory daoFactory = new DAOFactory();
        MBAntibiogramDAO dao = daoFactory.getDAO("MBAntibiogram");

        HashMap<Integer, Object> antibioticList = new HashMap<Integer, Object>();
        antibioticList.put(1, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>() {
                    {
                        put("name", "Bacteroides sp");
                        put("id", "Bacteroides sp");
                    }
                });
                put("antibiogram", new HashMap<String, Object>() {
                    {
                        put("name", "ENTEROBACTERIA");
                        put("id", "ENTEROBACTERIA");
                    }
                });
                put("antibiotics", new ArrayList<Object>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("antibiotic", "AMIKACINA");
                                put("cim", 12);
                                put("diameter", 0);
                                put("includeInReport", false);
                                put("interpretation", "SENSIBLE");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("antibiotic", "AMOXICILINA");
                                put("cim", 0);
                                put("diameter", 0);
                                put("includeInReport", true);
                                put("interpretation", "RESISTENTE");
                            }
                        });
                    }
                });
                put("methods", new ArrayList<Object>());
            }
        });
        antibioticList.put(2, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>() {
                    {
                        put("name", "Bacillus sp");
                        put("id", "Bacillus sp");
                    }
                });
                put("antibiogram", new HashMap<String, Object>() {
                    {
                        put("name", "ENTEROBACTERIA");
                        put("id", "ENTEROBACTERIA");
                    }
                });
                put("antibiotics", new ArrayList<Object>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("antibiotic", "AMIKACINA");
                                put("cim", 12);
                                put("diameter", 0);
                                put("includeInReport", false);
                                put("interpretation", "SENSIBLE");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("antibiotic", "AMOXICILINA");
                                put("cim", 0);
                                put("diameter", 0);
                                put("includeInReport", true);
                                put("interpretation", "RESISTENTE");
                            }
                        });
                    }
                });
                put("methods", new ArrayList<Object>());
            }
        });
        antibioticList.put(3, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>());
                put("antibiogram", new HashMap<String, Object>());
                put("antibiotics", new ArrayList<String>());
                put("methods", new ArrayList<Object>());
            }
        });
        antibioticList.put(4, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>());
                put("antibiogram", new HashMap<String, Object>());
                put("antibiotics", new ArrayList<String>());
                put("methods", new ArrayList<Object>());
            }
        });
        MBAntibiogram antibiogram = new MBAntibiogram("142", "36", "42", "30.000 UFC/mL", "INTRA HOSPITALARIO",
                antibioticList);
        dao.updateOrCreate(antibiogram);

        MBAntibiogram readAntibiogram = dao.getAntibiogramByIds(142, 36, 42);
        System.out.println("******** DATOS ANTIBIOGRAMA *********");
        System.out.println(readAntibiogram.getColonycount());
        System.out.println(readAntibiogram.getInfectionType());
        System.out.println(readAntibiogram.getAntibioticList());

    }

    @Test
    public void testCreateMBAntibiogram() {
        DAOFactory daoFactory = new DAOFactory();
        MBAntibiogramDAO dao = daoFactory.getDAO("MBAntibiogram");

        HashMap<Integer, Object> antibioticList = new HashMap<Integer, Object>();

        antibioticList.put(1, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>() {
                    {
                        put("name", "Bacteroides sp");
                        put("id", "Bacteroides sp");
                    }
                });
                put("antibiogram", new HashMap<String, Object>() {
                    {
                        put("name", "ENTEROBACTERIA");
                        put("id", "ENTEROBACTERIA");
                    }
                });
                put("antibiotics", new ArrayList<Object>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("antibiotic", "AMIKACINA");
                                put("cim", 0);
                                put("diameter", 0);
                                put("includeInReport", false);
                                put("interpretation", "RESISTENTE");
                            }
                        });
                        add(new HashMap<String, Object>() {
                            {
                                put("antibiotic", "AMOXICILINA");
                                put("cim", 0);
                                put("diameter", 0);
                                put("includeInReport", true);
                                put("interpretation", "SENSIBLE");
                            }
                        });
                    }
                });
                put("methods", new ArrayList<Object>());
            }
        });

        antibioticList.put(2, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>());
                put("antibiogram", new HashMap<String, Object>());
                put("antibiotics", new ArrayList<String>());
                put("methods", new ArrayList<Object>());
            }
        });

        antibioticList.put(3, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>());
                put("antibiogram", new HashMap<String, Object>());
                put("antibiotics", new ArrayList<String>());
                put("methods", new ArrayList<Object>());
            }
        });

        antibioticList.put(4, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>());
                put("antibiogram", new HashMap<String, Object>());
                put("antibiotics", new ArrayList<String>());
                put("methods", new ArrayList<Object>());
            }
        });

        MBAntibiogram antibiogram = new MBAntibiogram("142", "36", "43", "30.000 UFC/mL", "INTRA HOSPITALARIO",
                antibioticList);

        System.out.println("******** GUARDAR ANTIBIOGRAMA 1*********");
        dao.updateOrCreate(antibiogram);
        System.out.println(dao.getAntibiogramByIds(142, 36, 43).getAntibioticList());

        HashMap<Integer, Object> antibioticList3 = new HashMap<Integer, Object>();
        antibioticList3.put(1, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>() {
                    {
                        put("name", "Bacteroides sp");
                        put("id", "Bacteroides sp");
                    }
                });
                put("antibiogram", new HashMap<String, Object>() {
                    {
                        put("name", "ENTEROBACTERIA");
                        put("id", "ENTEROBACTERIA");
                    }
                });
                put("antibiotics", new ArrayList<Object>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("antibiotic", "AMOXICILINA");
                                put("cim", 0);
                                put("diameter", 0);
                                put("includeInReport", true);
                                put("interpretation", "SENSIBLE");
                            }
                        });
                    }
                });
                put("methods", new ArrayList<Object>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("method", "BETALACTAMASA");
                                put("result", "POSITIVO");
                            }
                        });
                    }
                });
            }
        });

        antibioticList3.put(2, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>() {
                    {
                        put("name", "Bacillus sp");
                        put("id", "Bacillus sp");
                    }
                });
                put("antibiogram", new HashMap<String, Object>() {
                    {
                        put("name", "ENTEROBACTERIA");
                        put("id", "ENTEROBACTERIA");
                    }
                });
                put("antibiotics", new ArrayList<Object>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("antibiotic", "AMOXICILINA");
                                put("cim", 0);
                                put("diameter", 0);
                                put("includeInReport", true);
                                put("interpretation", "SENSIBLE");
                            }
                        });
                    }
                });
                put("methods", new ArrayList<Object>());
            }
        });

        antibioticList3.put(3, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>());
                put("antibiogram", new HashMap<String, Object>());
                put("antibiotics", new ArrayList<String>());
                put("methods", new ArrayList<Object>());
            }
        });

        antibioticList3.put(4, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>());
                put("antibiogram", new HashMap<String, Object>());
                put("antibiotics", new ArrayList<String>());
                put("methods", new ArrayList<Object>());
            }
        });

        MBAntibiogram antibiogram3 = new MBAntibiogram("142", "36", "43", "30.000 UFC/mL", "CONTAMINACION",
                antibioticList3);

        System.out.println("******** GUARDAR ANTIBIOGRAMA 3*********");
        dao.updateOrCreate(antibiogram3);
        System.out.println(dao.getAntibiogramByIds(142, 36, 43).getAntibioticList());

        HashMap<Integer, Object> antibioticList2 = new HashMap<Integer, Object>();
        antibioticList2.put(1, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>() {
                    {
                        put("name", "Bacteroides sp");
                        put("id", "Bacteroides sp");
                    }
                });
                put("antibiogram", new HashMap<String, Object>() {
                    {
                        put("name", "ENTEROBACTERIA");
                        put("id", "ENTEROBACTERIA");
                    }
                });
                put("antibiotics", new ArrayList<Object>() {
                    {
                        add(new HashMap<String, Object>() {
                            {
                                put("antibiotic", "AMOXICILINA");
                                put("cim", 0);
                                put("diameter", 0);
                                put("includeInReport", true);
                                put("interpretation", "SENSIBLE");
                            }
                        });
                    }
                });
                put("methods", new ArrayList<Object>());
            }
        });

        antibioticList2.put(2, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>());
                put("antibiogram", new HashMap<String, Object>());
                put("antibiotics", new ArrayList<String>());
                put("methods", new ArrayList<Object>());
            }
        });

        antibioticList2.put(3, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>());
                put("antibiogram", new HashMap<String, Object>());
                put("antibiotics", new ArrayList<String>());
                put("methods", new ArrayList<Object>());
            }
        });

        antibioticList2.put(4, new HashMap<String, Object>() {
            {
                put("microorganism", new HashMap<String, Object>());
                put("antibiogram", new HashMap<String, Object>());
                put("antibiotics", new ArrayList<String>());
                put("methods", new ArrayList<Object>());
            }
        });

        MBAntibiogram antibiogram2 = new MBAntibiogram("142", "36", "43", "30.000 UFC/mL", "CONTAMINACION",
                antibioticList2);

        System.out.println("******** GUARDAR ANTIBIOGRAMA 2*********");
        dao.updateOrCreate(antibiogram2);
        System.out.println(dao.getAntibiogramByIds(142, 36, 43).getAntibioticList());

    }

    @Test
    public void testGetAntibioticResistance() {
        DAOFactory daoFactory = new DAOFactory();
        MBAntibiogramDAO dao = daoFactory.getDAO("MBAntibiogram");
        System.out.println(dao.getAntibioticResistance("AMIKACINA", "Escherichia coli", "3", "10"));
    }
*/
}
