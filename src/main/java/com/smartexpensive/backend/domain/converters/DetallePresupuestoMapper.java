package com.smartexpensive.backend.domain.converters;

import com.smartexpensive.backend.domain.dto.DetallePresupuestoDTO;
import com.smartexpensive.backend.domain.models.entity.DetallePresupuesto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class DetallePresupuestoMapper {

    private final ModelMapper mapper = new ModelMapper();

    //metodo para convertir de dto a entidad
    public DetallePresupuesto toEntity(DetallePresupuestoDTO detallePresupuestoDTO) {
        return mapper.map(detallePresupuestoDTO, DetallePresupuesto.class);
    }

    //metodo para convertir de entidad a dto
    public DetallePresupuestoDTO toDto(DetallePresupuesto detallePresupuesto) {
        return mapper.map(detallePresupuesto, DetallePresupuestoDTO.class);
    }

    //metodo para convertir de lista de entidad a lista de dto
    public List<DetallePresupuestoDTO> toListDtos(List<DetallePresupuesto> detallePresupuesto) {
        Type listType = new TypeToken<List<DetallePresupuestoDTO>>() {}.getType();
        return mapper.map(detallePresupuesto, listType);
    }

    //metodo para convertir de lista de dto a lista de entidad
    public List<DetallePresupuesto> toListEntities(List<DetallePresupuestoDTO> detallePresupuestoDTO) {
        Type listType = new TypeToken<List<DetallePresupuesto>>() {}.getType();
        return mapper.map(detallePresupuestoDTO, listType);
    }
}
