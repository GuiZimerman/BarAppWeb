package com.senac.BarAppWeb.model;

public enum NivelEnum {
    PROPRIETARIO("Proprietário do Bar"), GERENTE("Gerente"), FUNCIONARIO("Funcionário");

    private final String tipo;

    private NivelEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoEnum() {
        return tipo;
    }
    

}
