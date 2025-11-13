package com.smartexpensive.backend.domain.services.Gastos;

import com.smartexpensive.backend.domain.converters.CategoriasMapper;
import com.smartexpensive.backend.domain.converters.GastosMapper;
import com.smartexpensive.backend.domain.dto.CategoriasDTO;
import com.smartexpensive.backend.domain.dto.GastosDTO;
import com.smartexpensive.backend.domain.models.dao.ICategoriasDao;
import com.smartexpensive.backend.domain.models.dao.IGastoDao;
import com.smartexpensive.backend.domain.models.entity.Categorias;
import com.smartexpensive.backend.domain.models.entity.Gasto;
import com.smartexpensive.backend.domain.models.entity.Usuario;
import com.smartexpensive.backend.domain.services.Usuario.UsuarioServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private ICategoriasDao categoriasDao;

    @Autowired
    private CategoriasMapper categoriasMapper;


    @Override
    public List<Gasto> findAll() {
        return gastoDao.findAll();
    }

    @Override
    public GastosDTO findById(Long id) {
        return null;
    }

    @Override
    public GastosDTO save(GastosDTO gastosDTO, HttpServletRequest request) {
        Usuario usuario = usuarioService.obtenerUsuario(request);
        Gasto gasto = gastosMapper.toEntity(gastosDTO);
        gasto.setUsuarioId(usuario.getId());

        //añadir usuario
        gastoDao.save(gasto);
        return gastosMapper.toDto(gasto);
    }

    @Override
    public List<GastosDTO> findByUsuarioId(HttpServletRequest request) {

        //obtener usuario
        Usuario usuario = usuarioService.obtenerUsuario(request);

        //obtener gastos
        List<Gasto> gastos = gastoDao.findGastosByUsuarioId(usuario.getId());

        //convertir a dto
        return gastosMapper.toListDtos(gastos);

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CategoriasDTO> findAllCategorias() {
        List<Categorias> categorias = categoriasDao.findAll();
        return categoriasMapper.toListDtos(categorias);
    }
}
