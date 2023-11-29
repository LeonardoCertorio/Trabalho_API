package br.senac.GestorAcademico.seguranca.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrarUsuarioRequisicao {

    @NotNull
    public String usuario;
    @NotNull
    @Size(min = 6)
    public String senha;
    @NotNull
    @Size(min = 6)
    public String confirmacaoSenha;

    public boolean senhasNaoConferem() {
        return !senha.equals(confirmacaoSenha);
    }
}
