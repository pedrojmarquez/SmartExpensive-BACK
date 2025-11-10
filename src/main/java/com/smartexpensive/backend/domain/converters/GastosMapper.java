package com.smartexpensive.backend.domain.converters;

import com.smartexpensive.backend.domain.dto.GastosDTO;
import com.smartexpensive.backend.domain.models.entity.Gasto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class GastosMapper {
    private final ModelMapper mapper = new ModelMapper();

    //metodo para convertir de entidad a dto
    public GastosDTO toDto(Gasto gasto) {
        return mapper.map(gasto, GastosDTO.class);
    }

    //metodo para convertir de dto a entidad
    public Gasto toEntity(GastosDTO gastosDTO) {
        return mapper.map(gastosDTO, Gasto.class);
    }

    //metodo para convertir de lista de entidad a lista de dto
    public List<GastosDTO> toListDtos(List<Gasto> gastos) {
        Type listType = new TypeToken<List<GastosDTO>>() {}.getType();
        return mapper.map(gastos, listType);
    }


}
