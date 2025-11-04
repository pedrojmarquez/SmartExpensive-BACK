package com.smartexpensive.backend.domain.services.Gastos;

import com.smartexpensive.backend.domain.converters.GastosMapper;
import com.smartexpensive.backend.domain.dto.GastosDTO;
import com.smartexpensive.backend.domain.models.dao.IGastoDao;
import com.smartexpensive.backend.domain.models.entity.Gasto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GastosServicesImpl implements IGastosServices{

    @Autowired
    private IGastoDao gastoDao;

    @Autowired
    private GastosMapper gastosMapper;


    @Override
    public List<GastosDTO> findAll() {
        return List.of();
    }

    @Override
    public GastosDTO findById(Long id) {
        return null;
    }

    @Override
    public GastosDTO save(GastosDTO gastosDTO) {
        Gasto gasto = gastosMapper.toEntity(gastosDTO);
        gasto = gastoDao.save(gasto);
        return gastosMapper.toDto(gasto);
    }

    @Override
    public void delete(Long id) {

    }
}
