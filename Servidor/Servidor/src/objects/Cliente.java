/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Citlalli
 */
public class Cliente extends Thread{
    private Socket socket;
    private Socket enemigo;
    private Semaforo semaforo;
    private Jugador player;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    public Cliente (ObjectInputStream reader, ObjectOutputStream writer, Jugador player,Semaforo semaforo) throws IOException{
     this.reader = reader;
     this.player = player;
     this.semaforo = semaforo;
     this.writer = writer;
     this.reader = reader;
     this.writer = writer;
    }

    @Override
    public void run() {
        while(true){
            synchronized(semaforo){
                if(semaforo.getTurno(player)){
                try {
                     Boolean turno = (Boolean)reader.readObject();
                     semaforo.setTurno(player, turno);
                     writer.writeObject(!turno);
                    Coordenada cord = (Coordenada)reader.readObject();
                    writer.writeObject(cord);
                    System.out.println("envie turno"+player.getEquipo());
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                    
                    try {
                        semaforo.notifyAll();
                        semaforo.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }
    }
    
    
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Jugador getPlayer() {
        return player;
    }

    public void setPlayer(Jugador player) {
        this.player = player;
    }
}
