package com.smartexpensive.backend.domain.converters;

import com.smartexpensive.backend.domain.dto.CategoriasDTO;
import com.smartexpensive.backend.domain.models.entity.Categorias;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class CategoriasMapper {

    private final ModelMapper mapper = new ModelMapper();

    //metodo para convertir de dto a entidad
    public Categorias toEntity(CategoriasDTO categoriasDTO) {
        return mapper.map(categoriasDTO, Categorias.class);
    }

    //metodo para convertir de entidad a dto
    public CategoriasDTO toDto(Categorias categorias) {
        return mapper.map(categorias, CategoriasDTO.class);
    }

    //metodo para convertir de lista de entidad a lista de dto
    public List<CategoriasDTO> toListDtos(List<Categorias> categorias) {
        Type listType = new TypeToken<List<CategoriasDTO>>() {}.getType();
        return mapper.map(categorias, listType);
    }

    //metodo para convertir de lista de dto a lista de entidad
    public List<Categorias> toListEntities(List<CategoriasDTO> categoriasDTO) {
        Type listType = new TypeToken<List<Categorias>>() {}.getType();
        return mapper.map(categoriasDTO, listType);
    }
}
