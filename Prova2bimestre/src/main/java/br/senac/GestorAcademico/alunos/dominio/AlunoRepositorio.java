package br.senac.GestorAcademico.alunos.dominio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno, Long> {
}
