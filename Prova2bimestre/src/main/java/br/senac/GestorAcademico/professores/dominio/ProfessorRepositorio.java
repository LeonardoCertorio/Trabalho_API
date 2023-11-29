package br.senac.GestorAcademico.professores.dominio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepositorio extends JpaRepository<Professor, Long> {
}
