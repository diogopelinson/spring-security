package br.com.devpelinson.spring_boot_essentials.dto;

import java.math.BigDecimal;

public interface AvaliacoesFisicasProjection {
    Integer getIdAluno();
    String getNomeAluno();
    Integer getIdAvaliacao();
    BigDecimal getPeso();
    BigDecimal getAltura();
    BigDecimal getPorcentagem_gordura_corporal();

}
