package br.senac.GestorAcademico.seguranca.api;

import br.senac.GestorAcademico.seguranca.api.dtos.UsuarioView;
import br.senac.GestorAcademico.seguranca.dominio.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/usuarios")
@AllArgsConstructor
public class UsuarioControlador {

    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioView> findById(@PathVariable long id) {
        return usuarioRepositorio.findById(id)
                .map(record -> new UsuarioView(record.getId(), record.getUsuario(), record.getPapel().name()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        usuarioRepositorio.deleteById(id);
    }
}
