/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import buscaminasobjects.BuscaminasMp;
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
public class Reiniciador extends Thread{
    private Socket socket;
    private ObjectOutputStream writerRojo;
    private ObjectOutputStream writerAzul;
    private ObjectInputStream readerRojo;
    private ObjectInputStream readerAzul;
    private Semaforo semaforo;
    public Reiniciador (ObjectOutputStream writerRojo,
    ObjectOutputStream writerAzul,
    ObjectInputStream readerRojo,
    ObjectInputStream readerAzul, Semaforo semaforo){
        this.writerAzul = writerAzul;
        this.writerRojo = writerRojo;
        this.readerRojo = readerRojo;
        this.readerAzul = readerAzul;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("REINICIANDO");
                Boolean respuestaAzul = (Boolean) readerAzul.readObject();
                System.out.println("ESPERANDO");
                if (respuestaAzul) {
                    writerRojo.writeObject(true);
                    Boolean respuestaRojo = (Boolean) readerRojo.readObject();
                    if (respuestaRojo) {
                        if (respuestaAzul && respuestaRojo) {
                            System.out.println("SE VA A REINICIAR");
                            writerAzul.writeObject(true);
                            System.out.println("--------");
                            BuscaminasMp buscaminas = new BuscaminasMp(16, 16, 51);
                            writerAzul.writeObject(buscaminas);
                            writerRojo.writeObject(buscaminas);
                            /*writerRojo.writeObject(true);
                            writerAzul.writeObject(false);
                            System.out.println("REINICIADOR ENVIÓ EL JUEGO Y LOS TURNOS A LOS JUGADORES!!!");
                            synchronized (semaforo) {
                                semaforo.setTurnoAzul(false);
                                semaforo.setTurnoRojo(true);
                            }*/
                            System.out.println("--");
                            System.out.println("");

                        } else {
                            writerAzul.writeObject(false);
                            writerRojo.writeObject(false);
                        }
                    }else{
                        System.out.println("-");
                        writerAzul.writeObject(false);
                        writerRojo.writeObject(true);
                    }
                }else{
                    System.out.println("-");
                    writerRojo.writeObject(false);
                    readerRojo.readObject();
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Reiniciador.class.getName()).log(Level.SEVERE, null, ex);
            }
            //here
            System.out.println("----");
        }
    }
}
