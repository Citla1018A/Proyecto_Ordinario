/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectsServerChat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Citlalli
 */
public class Organizador  extends Thread{
    private ArrayList<ClienteChat> clientes;
    private ColaMensajes cola;
    
    public Organizador(ArrayList<ClienteChat> clientes, ColaMensajes cola){
        this.clientes = clientes;
        this.cola = cola;
        
        
    }

    @Override
    public void run() {
        while(true){
            if(cola.vacia()){
                synchronized(cola){
                    try {
                        cola.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Organizador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }else{
                String msg = (String)cola.frente();
                cola.desencolar();
                for(int i=0; i<clientes.size(); i++){
                    try {
                        DataOutputStream writer = new DataOutputStream(clientes.get(i).getoSocket().getOutputStream());
                        writer.writeUTF(msg);
                    } catch (IOException ex) {
                        Logger.getLogger(Organizador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
}
