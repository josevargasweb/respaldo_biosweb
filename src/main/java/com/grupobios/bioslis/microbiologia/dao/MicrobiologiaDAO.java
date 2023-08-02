package com.grupobios.bioslis.microbiologia.dao;

import java.util.List;

import com.grupobios.bioslis.entity.Microbiologia;
import com.grupobios.bioslis.microbiologia.dto.MicrobiologiaOrdenPacienteDTO;

public interface MicrobiologiaDAO {
    List<Microbiologia> getAll();
    Microbiologia getByName(String name);
    void save(Microbiologia microbiologia);
    void delete(Microbiologia microbiologia);
}
