package br.senac.GestorAcademico.cursos.api;

import br.senac.GestorAcademico.cursos.dominio.Curso;
import br.senac.GestorAcademico.cursos.dominio.CursoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geral/cursos")
@AllArgsConstructor
public class CursoControlador {
    private CursoRepositorio cursoRepositorio;

    @GetMapping
    public List<Curso> findAll(){
        return cursoRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable long id){
        return cursoRepositorio.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> create(@RequestBody Curso curso){
        return ResponseEntity.ok((cursoRepositorio.save(curso)));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        cursoRepositorio.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@PathVariable("id") long id,
                                        @RequestBody Curso curso) {
        return cursoRepositorio.findById(id)
                .map(record -> {
                    record.setNome(curso.getNome());
                    record.setDepartamento(curso.getDepartamento());
                    Curso updated = cursoRepositorio.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
