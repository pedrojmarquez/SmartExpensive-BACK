package com.smartexpensive.backend.domain.services.Gastos;

import com.smartexpensive.backend.domain.dto.GastosDTO;

import java.util.List;

public interface IGastosServices {

    public List<GastosDTO> findAll();
    public GastosDTO findById(Long id);
    public GastosDTO save(GastosDTO gastosDTO);
    public void delete(Long id);
}
