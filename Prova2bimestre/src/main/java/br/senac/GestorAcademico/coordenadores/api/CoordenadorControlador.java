package br.senac.GestorAcademico.coordenadores.api;

import br.senac.GestorAcademico.coordenadores.dominio.Coordenador;
import br.senac.GestorAcademico.coordenadores.dominio.CoordenadorRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geral/coordenadores")
@AllArgsConstructor
public class CoordenadorControlador {
    private CoordenadorRepositorio coordenadorRepositorio;

    @GetMapping
    public List<Coordenador> findAll(){
        return coordenadorRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coordenador> findById(@PathVariable long id){
        return coordenadorRepositorio.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Coordenador> create(@RequestBody Coordenador coordenador){
        return ResponseEntity.ok((coordenadorRepositorio.save(coordenador)));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        coordenadorRepositorio.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coordenador> update(@PathVariable("id") long id,
                                               @RequestBody Coordenador coordenador) {
        return coordenadorRepositorio.findById(id)
                .map(record -> {
                    record.setNome(coordenador.getNome());
                    record.setEmail(coordenador.getEmail());
                    record.setDataNascimento(coordenador.getDataNascimento());
                    record.setTelefone(coordenador.getTelefone());
                    record.setEndereco(coordenador.getEndereco());
                    Coordenador updated = coordenadorRepositorio.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
