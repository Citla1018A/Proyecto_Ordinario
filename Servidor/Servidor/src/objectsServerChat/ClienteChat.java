/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objectsServerChat;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *
 * @author Citlalli
 */
public class ClienteChat extends Thread{
    private Socket oSocket;
    private DataInputStream reader;
    private JDialog dialoguito;
    private JTextField inutil;
    ColaMensajes colaMensajes;
    public ClienteChat (Socket socket, ColaMensajes colaMensajes) throws IOException{
        oSocket = socket;
        this.colaMensajes = colaMensajes;
        reader = new DataInputStream(socket.getInputStream());
        //writer = new DataOutputStream(socket.getOutputStream());
        inutil = new JTextField(30);
        /*di = new JDialog();
        dialoguito.setSize(500, 500);
        dialoguito.setLocationRelativeTo(null);
        dialoguito.setLayout(new FlowLayout());
        dialoguito.add(inutil);
        dialoguito.setVisible(true);*/
        
        
    }

    @Override
    public void run() {
        
        while(true){
            try {
                
                String strFileContents = null;
                strFileContents = reader.readUTF();
                if(strFileContents != null){
                    //inutil.setText(strFileContents);
                    synchronized(colaMensajes){
                        colaMensajes.encolar(strFileContents);
                        colaMensajes.notifyAll();
                    }
                } 
            } catch (IOException ex) {
                Logger.getLogger(ClienteChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the oSocket
     */
    public Socket getoSocket() {
        return oSocket;
    }

    /**
     * @param oSocket the oSocket to set
     */
    public void setoSocket(Socket oSocket) {
        this.oSocket = oSocket;
    }
    
    
}
