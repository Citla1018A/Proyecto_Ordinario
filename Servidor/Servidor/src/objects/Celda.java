/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;

/**
 *
 * @author Citlalli
 */
public class Celda implements Serializable{
    private CeldaEstado estado;
    private Boolean mina;
    private Integer numero;
    private Integer x;
    private Integer y;
    
    public Celda(Integer x, Integer y){
        this.x=x;
        this.y=y;
        this.estado = CeldaEstado.CERRADO;
        this.mina = false;
        
    }
    
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public CeldaEstado getEstado() {
        return estado;
    }

    public Boolean isMina() {
        return mina;
    }

    public void setEstado(CeldaEstado estado) {
        this.estado = estado;
    }

    public void setMina(Boolean mina) {
        this.mina = mina;
    }
    
    public void marcarCelda(){
        switch(estado){
            case CERRADO:
                this.setEstado(CeldaEstado.BANDERA);
            case BANDERA:
                this.setEstado(CeldaEstado.INTERROGACION);
            case INTERROGACION:
                this.setEstado(CeldaEstado.CERRADO);
            default:
                throw new AssertionError();
        }
    }
    public void nextEdo(){
        switch (estado) {
            case CERRADO:
                estado = CeldaEstado.BANDERA;
                break;
            case BANDERA:
                estado = CeldaEstado.INTERROGACION;
                break;
            case INTERROGACION:
                estado = CeldaEstado.CERRADO;
                break;                
        }
    }
    public void abrirCelda(){
        if (estado == CeldaEstado.CERRADO){
            this.estado = CeldaEstado.ABIERTO;
        }
    }
    
    @Override
    public String toString(){
        
        switch(estado){
            case CERRADO:
                //if(this.isMina()){
                   // return "[*]";
                //}else{
                     return String.format("[-]");
                //}
            case BANDERA:
                return String.format("[^]");
            case BANDERAMALA:
                return String.format("[X]");
            case MINA:
                return String.format("[*]");
            case INTERROGACION:
                return String.format("[?]");
            case ABIERTO:
               if(numero==0){
                    return "[ ]";
                }else{
                    return "["+numero+"]";
                }
            case BOOM:
                return String.format("[B]");
            default:
                throw new AssertionError();
        }
    }
   
    
}