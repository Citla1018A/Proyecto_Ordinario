/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectsServerChat;

import java.util.ArrayList;

/**
 *
 * @author Citlalli
 */
public class ColaMensajes extends ArrayList<Object>{
    
 public synchronized void encolar(Object dato){
  if(dato != null){
   this.add(dato);
  }else{
   System.out.println("Introduzca un dato no nulo");
  }  
 }


 public void desencolar(){
  if(this.size() > 0){
   this.remove(0);
  }
 }
 
 
 public Object frente(){
  Object datoAuxiliar = null;
  if(this.size() > 0){
   datoAuxiliar = this.get(0);
  }
  return datoAuxiliar;  
 }
 
 
 public boolean vacia(){
  return this.isEmpty();
 }
}
