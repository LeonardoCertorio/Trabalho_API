package br.senac.GestorAcademico.alunos.api;

import br.senac.GestorAcademico.alunos.dominio.Aluno;
import br.senac.GestorAcademico.alunos.dominio.AlunoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geral/alunos")
@AllArgsConstructor
public class AlunoControlador {

    private AlunoRepositorio alunoRepositorio;

    @GetMapping
    public List<Aluno> findAll(){
        return alunoRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable long id){
        return alunoRepositorio.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Aluno> create(@RequestBody Aluno aluno){
        return ResponseEntity.ok((alunoRepositorio.save(aluno)));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        alunoRepositorio.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable("id") long id,
                                 @RequestBody Aluno aluno) {
        return alunoRepositorio.findById(id)
                .map(record -> {
                    record.setNome(aluno.getNome());
                    record.setEmail(aluno.getEmail());
                    record.setCurso(aluno.getCurso());
                    record.setDataNascimento(aluno.getDataNascimento());
                    record.setTelefone(aluno.getTelefone());
                    record.setEndereco(aluno.getEndereco());
                    Aluno updated = alunoRepositorio.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
