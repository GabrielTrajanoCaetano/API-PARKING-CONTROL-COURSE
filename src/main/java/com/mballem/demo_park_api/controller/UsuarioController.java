package com.mballem.demo_park_api.controller;

import com.mballem.demo_park_api.controller.DTO.UsuarioCreateDto;
import com.mballem.demo_park_api.controller.DTO.UsuarioResponseDto;
import com.mballem.demo_park_api.controller.DTO.UsuarioSenhaDto;
import com.mballem.demo_park_api.controller.DTO.mapper.UsuarioMapper;
import com.mballem.demo_park_api.entity.Usuario;
import com.mballem.demo_park_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody UsuarioCreateDto createDto){
       Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
       return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getId(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody UsuarioSenhaDto dto){
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.ok().body("Senha alterada com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
        List<Usuario> user = usuarioService.buscarTodos();

        return ResponseEntity.ok(user);
    }
}
