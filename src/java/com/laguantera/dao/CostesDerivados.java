package com.laguantera.dao;
// Generated 21-dic-2010 23:39:28 by Hibernate Tools 3.2.1.GA



/**
 * CostesDerivados generated by hbm2java
 */
public class CostesDerivados  implements java.io.Serializable {


     private CostesDerivadosId id;
     private TiposCoste tiposCoste;
     private Vehiculos vehiculos;
     private Float valor;

    public CostesDerivados() {
    }

	
    public CostesDerivados(CostesDerivadosId id, TiposCoste tiposCoste, Vehiculos vehiculos) {
        this.id = id;
        this.tiposCoste = tiposCoste;
        this.vehiculos = vehiculos;
    }
    public CostesDerivados(CostesDerivadosId id, TiposCoste tiposCoste, Vehiculos vehiculos, Float valor) {
       this.id = id;
       this.tiposCoste = tiposCoste;
       this.vehiculos = vehiculos;
       this.valor = valor;
    }
   
    public CostesDerivadosId getId() {
        return this.id;
    }
    
    public void setId(CostesDerivadosId id) {
        this.id = id;
    }
    public TiposCoste getTiposCoste() {
        return this.tiposCoste;
    }
    
    public void setTiposCoste(TiposCoste tiposCoste) {
        this.tiposCoste = tiposCoste;
    }
    public Vehiculos getVehiculos() {
        return this.vehiculos;
    }
    
    public void setVehiculos(Vehiculos vehiculos) {
        this.vehiculos = vehiculos;
    }
    public Float getValor() {
        return this.valor;
    }
    
    public void setValor(Float valor) {
        this.valor = valor;
    }




}


