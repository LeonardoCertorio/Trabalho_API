package br.senac.GestorAcademico.seguranca.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioView {

    private long id;
    private String usuario;
    private String papel;
}
