package br.senac.GestorAcademico.coordenadores.dominio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadorRepositorio extends JpaRepository<Coordenador, Long> {
}
