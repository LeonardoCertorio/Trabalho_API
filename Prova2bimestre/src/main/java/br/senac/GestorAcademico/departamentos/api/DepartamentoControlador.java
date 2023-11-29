package br.senac.GestorAcademico.departamentos.api;

import br.senac.GestorAcademico.departamentos.dominio.Departamento;
import br.senac.GestorAcademico.departamentos.dominio.DepartamentoRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geral/departamentos")
@AllArgsConstructor
public class DepartamentoControlador {
    private DepartamentoRepositorio departamentoRepositorio;

    @GetMapping
    public List<Departamento> findAll(){
        return departamentoRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> findById(@PathVariable long id){
        return departamentoRepositorio.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Departamento> create(@RequestBody Departamento departamento){
        return ResponseEntity.ok((departamentoRepositorio.save(departamento)));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        departamentoRepositorio.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departamento> update(@PathVariable("id") long id,
                                        @RequestBody Departamento departamento) {
        return departamentoRepositorio.findById(id)
                .map(record -> {
                    record.setNome(departamento.getNome());
                    record.setCoordenador(departamento.getCoordenador());
                    Departamento updated = departamentoRepositorio.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}
