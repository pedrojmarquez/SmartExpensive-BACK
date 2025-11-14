package com.smartexpensive.backend.domain.services.Gastos;

import com.smartexpensive.backend.domain.converters.CategoriasMapper;
import com.smartexpensive.backend.domain.converters.DetallePresupuestoMapper;
import com.smartexpensive.backend.domain.converters.GastosMapper;
import com.smartexpensive.backend.domain.converters.PresupuestosMapper;
import com.smartexpensive.backend.domain.dto.CategoriasDTO;
import com.smartexpensive.backend.domain.dto.DetallePresupuestoDTO;
import com.smartexpensive.backend.domain.dto.GastosDTO;
import com.smartexpensive.backend.domain.dto.PresupuestosDTO;
import com.smartexpensive.backend.domain.models.dao.ICategoriasDao;
import com.smartexpensive.backend.domain.models.dao.IDetallePresupuestoDao;
import com.smartexpensive.backend.domain.models.dao.IGastoDao;
import com.smartexpensive.backend.domain.models.dao.IPresupuestosDao;
import com.smartexpensive.backend.domain.models.entity.*;
import com.smartexpensive.backend.domain.services.Usuario.UsuarioServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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

    @Autowired
    private IPresupuestosDao presupuestosDao;

    @Autowired
    private PresupuestosMapper presupuestosMapper;

    @Autowired
    private DetallePresupuestoMapper detallePresupuestoMapper;

    @Autowired
    private IDetallePresupuestoDao detallePresupuestoDao;


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

    @Override
    public PresupuestosDTO findPresupuesto(HttpServletRequest request) {

        //obtener mes y anio actual
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        int anio = Calendar.getInstance().get(Calendar.YEAR);
        //obtener usuario
        Usuario usuario = usuarioService.obtenerUsuario(request);

        Presupuestos presupuesto = presupuestosDao.findPresupuesto(usuario.getId(),11, 2025);

        //obtener detalles del presupuesto
        List<DetallePresupuesto> detalles = detallePresupuestoDao.findAllByIdPresupuesto(presupuesto.getIdPresupuesto());


        PresupuestosDTO presupuestosDTO = presupuestosMapper.toDto(presupuesto);
        presupuestosDTO.setDetallePresupuesto(detallePresupuestoMapper.toListDtos(detalles));
        //convertir a dto
        return presupuestosDTO ;
    }

    @Override
    @Transactional
    public PresupuestosDTO savePresupuesto(PresupuestosDTO presupuestosDTO, HttpServletRequest request) {
        try {
            Presupuestos presupuestos = presupuestosMapper.toEntity(presupuestosDTO);
            Usuario usuario = usuarioService.obtenerUsuario(request);
            presupuestos.setUsuario(usuario);
            //obtener todos los detalles del presupuesto
            List<DetallePresupuesto> detalles = detallePresupuestoMapper.toListEntities(presupuestosDTO.getDetallePresupuesto());
            Presupuestos presupuesto = presupuestosDao.save(presupuestos);

            //guardar todos los detalles
            for (DetallePresupuesto detalle : detalles) {
                detalle.setIdPresupuesto(presupuesto.getIdPresupuesto());
                detallePresupuestoDao.save(detalle);
            }

            return presupuestosMapper.toDto(presupuestos);
        } catch (Exception e) {
            log.error("Error al guardar el presupuesto", e);
            throw new RuntimeException("Error al guardar el presupuesto");
        }
    }
}
