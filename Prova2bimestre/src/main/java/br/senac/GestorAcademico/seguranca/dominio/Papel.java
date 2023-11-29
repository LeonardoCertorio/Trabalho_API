package br.senac.GestorAcademico.seguranca.dominio;

import java.util.Arrays;

public enum Papel {
    DIRETOR,
    COORDENADOR,
    PROFESSOR;

    public static String[] getNomesPapeis() {
         return Arrays.stream(Papel.values())
                .map(Papel::name)
                .toArray(String[]::new);
    }
}
