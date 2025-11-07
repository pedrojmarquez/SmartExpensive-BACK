package com.smartexpensive.backend.domain.services.Usuario;

import com.smartexpensive.backend.domain.models.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;

public interface IUsuarioService {
    Usuario obtenerUsuario(HttpServletRequest request);
}
