package br.senac.GestorAcademico.seguranca.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrarRequisicao {

    @NotBlank
    private String usuario;
    @NotBlank
    private String senha;
}
