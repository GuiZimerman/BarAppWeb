package com.senac.BarAppWeb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCargo;
    private String nivelCargo;
    
    public Cargo(){
    }
    
    public Cargo(int idCargo, String nomeCargo, NivelEnum nivelCargo) {
        this.idCargo = idCargo;
        this.nivelCargo = nivelCargo.getTipoEnum();
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getNivelCargo() {
        return nivelCargo;
    }

    public void setNivelCargo(NivelEnum nivelCargo) {
        this.nivelCargo = nivelCargo.getTipoEnum();
    }
    
    
    
    
}
