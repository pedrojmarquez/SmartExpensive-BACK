package com.smartexpensive.backend.domain.converters;

import com.smartexpensive.backend.domain.dto.PresupuestosDTO;
import com.smartexpensive.backend.domain.models.entity.Presupuestos;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class PresupuestosMapper {

    private final ModelMapper mapper = new ModelMapper();

    //metodo para convertir de dto a entidad
    public Presupuestos toEntity(PresupuestosDTO presupuestosDTO) {
        return mapper.map(presupuestosDTO, Presupuestos.class);
    }

    //metodo para convertir de entidad a dto
    public PresupuestosDTO toDto(Presupuestos presupuestos) {
        return mapper.map(presupuestos, PresupuestosDTO.class);
    }

    //metodo para convertir de lista de entidad a lista de dto
    public List<PresupuestosDTO> toListDtos(List<Presupuestos> presupuestos) {
        Type listType = new TypeToken<List<PresupuestosDTO>>() {}.getType();
        return mapper.map(presupuestos, listType);
    }

    //metodo para convertir de lista de dto a lista de entidad
    public List<Presupuestos> toListEntities(List<PresupuestosDTO> presupuestosDTO) {
        Type listType = new TypeToken<List<Presupuestos>>() {}.getType();
        return mapper.map(presupuestosDTO, listType);
    }
}
