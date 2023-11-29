package br.senac.GestorAcademico.professores.api;

import br.senac.GestorAcademico.professores.dominio.Professor;
import br.senac.GestorAcademico.professores.dominio.ProfessorRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geral/professores")
@AllArgsConstructor
public class ProfessorControlador {

    private ProfessorRepositorio professorRepositorio;

    @GetMapping
    public List<Professor> findAll(){
        return professorRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> findById(@PathVariable long id){
        return professorRepositorio.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Professor> create(@RequestBody Professor professor){
        return ResponseEntity.ok((professorRepositorio.save(professor)));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        professorRepositorio.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> update(@PathVariable("id") long id,
                                        @RequestBody Professor professor) {
        return professorRepositorio.findById(id)
                .map(record -> {
                    record.setNome(professor.getNome());
                    record.setEmail(professor.getEmail());
                    record.setDepartamento(professor.getDepartamento());
                    record.setDataNascimento(professor.getDataNascimento());
                    record.setTelefone(professor.getTelefone());
                    record.setEndereco(professor.getEndereco());
                    Professor updated = professorRepositorio.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
