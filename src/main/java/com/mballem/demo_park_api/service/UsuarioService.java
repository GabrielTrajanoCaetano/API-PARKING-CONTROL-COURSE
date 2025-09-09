package com.mballem.demo_park_api.service;

import com.mballem.demo_park_api.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mballem.demo_park_api.repository.UsuarioRepository;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsuario(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("user not found"));

    }
}
