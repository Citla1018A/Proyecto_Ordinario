/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverminesweeper;

import buscaminasobjects.BuscaminasMp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import objects.Cliente;
import objects.Equipo;
import objects.Jugador;
import objects.Reiniciador;
import objects.Semaforo;
import objectsServerChat.ClienteChat;
import objectsServerChat.ColaMensajes;
import objectsServerChat.Organizador;

/**
 *
 * @author Citlalli
 */
public class ServerMinesweeper{

    private static ServerSocket server;
    private static ServerSocket canalNewGame;
    private static JFrame ventanaServer;
    private static JLabel lblEstado;
    private static JLabel lblEstadoDinamico;
    private static JLabel lblImagen;
    private static JPanel north;
    private static JPanel south;
    private static JPanel west;
    private static JPanel east;
    private static JPanel pnlPrincipal;
    private static JButton btnSalir;
    private static JPanel pnlBoton;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        server = new ServerSocket(1235);
        canalNewGame = new ServerSocket(1236);
        /*Frame inicialization*/
        ventanaServer = new JFrame();
        ventanaServer.setTitle("Servidor");
        ventanaServer.setLayout(new BorderLayout());
        ventanaServer.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ventanaServer.setSize(new Dimension(550, 450));
        ventanaServer.setResizable(false);
        ventanaServer.setLocationRelativeTo(null);
        ventanaServer.setBackground(Color.GREEN);
        /*Principal panel*/
        pnlPrincipal = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblImagen = new JLabel();
        cargarIcono("/images/busca.jpg", lblImagen);
        lblEstado = new JLabel("Estado: ");
        lblEstado.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblEstadoDinamico = new JLabel("Esperando");
        lblEstadoDinamico.setFont(new Font("Georgia", Font.ITALIC, 14));
        btnSalir = new JButton("Salir del servidor");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 System.exit(EXIT_ON_CLOSE);
             }
        });
        pnlBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBoton.setPreferredSize(new Dimension(ventanaServer.getWidth()-20, 50));

        pnlPrincipal.add(lblImagen);
        pnlPrincipal.add(lblEstado);
        pnlPrincipal.add(lblEstadoDinamico);
        pnlBoton.add(btnSalir);
        pnlPrincipal.add(pnlBoton);

        /*Lateral panels*/
        north = new JPanel();
        north.setBackground(Color.GREEN);
        south = new JPanel();
        btnSalir = new JButton("Salir del servidor");
        south.setBackground(Color.GREEN);
        west = new JPanel();
        west.setBackground(Color.GREEN);
        east = new JPanel();
        east.setBackground(Color.GREEN);
        
        ventanaServer.add(pnlPrincipal, BorderLayout.CENTER);
        ventanaServer.add(north,BorderLayout.NORTH);
        ventanaServer.add(south, BorderLayout.SOUTH);
        ventanaServer.add(west, BorderLayout.WEST);
        ventanaServer.add(east, BorderLayout.EAST);
        ventanaServer.setVisible(true);
        //--------------------
        //-------------------
        /*Running server*/
        lblEstadoDinamico.setText("Iniciando ");
        Thread.sleep(1000);
        lblEstadoDinamico.setText("Arrancando ");
        Thread.sleep(1000);
        while (true) {
            //COSAS DEL CHAT!!!
            ArrayList<ClienteChat> clientes = new ArrayList<>();
            ColaMensajes cola = new ColaMensajes();
            Organizador organizador = new Organizador(clientes, cola);
            organizador.start();
            BuscaminasMp buscaminas = new BuscaminasMp(16, 16, 51);
            Semaforo semaforo = new Semaforo(true, false);
            ObjectOutputStream writerRojo;
            ObjectOutputStream writerAzul;
            ObjectInputStream readerRojo;
            ObjectInputStream readerAzul;
            ObjectOutputStream writerNewGameRojo;
            ObjectOutputStream writerNewGameAzul;
            ObjectInputStream readerNewGameRojo;
            ObjectInputStream readerNewGameAzul;
            Jugador rojo;
            Jugador azul;
            lblEstadoDinamico.setText("Esperando");
            //JUGADOR ROJO
            iniciarUnChat(clientes, cola, organizador, canalNewGame);
            Socket playerRojo = server.accept();
            Socket playerRojoReinicio = server.accept();
            writerNewGameRojo = new ObjectOutputStream(playerRojoReinicio.getOutputStream());
            readerNewGameRojo = new ObjectInputStream(playerRojoReinicio.getInputStream());
            readerRojo = new ObjectInputStream(playerRojo.getInputStream());
            rojo = new Jugador((String) readerRojo.readObject(), Equipo.EquipoRojo);
            writerRojo = new ObjectOutputStream(playerRojo.getOutputStream());
            writerRojo.writeObject(rojo);
            writerRojo.writeObject(true);
            writerRojo.writeObject(buscaminas);

            lblEstadoDinamico.setText("Jugador 1 conectado");
            Thread.sleep(1500);
            lblEstadoDinamico.setText("Esperando jugador 2");

            //JUGADOR AZUL
            iniciarUnChat(clientes, cola, organizador, canalNewGame);
            Socket playerAzul = server.accept();
            Socket playerAzulReinicio = server.accept();
            writerNewGameAzul = new ObjectOutputStream(playerAzulReinicio.getOutputStream());
            readerNewGameAzul = new ObjectInputStream(playerAzulReinicio.getInputStream());
            readerAzul = new ObjectInputStream(playerAzul.getInputStream());
            azul = new Jugador((String) readerAzul.readObject(), Equipo.EquipoAzul);
            writerAzul = new ObjectOutputStream(playerAzul.getOutputStream());
            writerAzul.writeObject(azul);
            writerAzul.writeObject(false);
            writerAzul.writeObject(buscaminas);
            lblEstadoDinamico.setText("Jugador 2 conectado");
            Thread.sleep(1500);

            writerRojo.writeObject(azul);
            writerAzul.writeObject(rojo);
            Cliente jugadorRojo = new Cliente(readerRojo, writerAzul, rojo, semaforo);
            Cliente jugadorAzul = new Cliente(readerAzul, writerRojo, azul, semaforo);
            Reiniciador reiniciador = new Reiniciador(writerNewGameRojo, writerNewGameAzul, readerNewGameRojo,
                    readerNewGameAzul, semaforo);
            //System.out.println("l");
            jugadorRojo.start();
            jugadorAzul.start();
            reiniciador.start();
            //System.out.println("-");
        }
    }
    private static void cargarIcono(String path, JLabel objetive) {
        URL url = System.class.getResource(path);
        ImageIcon im = new ImageIcon(url);
        objetive.setIcon(im);
    }
    public static void iniciarUnChat(ArrayList<ClienteChat> clientes, ColaMensajes cola,
            Organizador organizador, ServerSocket server){
        
        try {
            //server = new ServerSocket(1234);
            
            //while(true){
                System.out.println("---");
                Socket socket = server.accept();
                ClienteChat cliente = new ClienteChat(socket, cola);
                clientes.add(cliente);
                cliente.start();
                        System.out.println("---");
            //}
        } catch (IOException ex) {
            Logger.getLogger(ServerMinesweeper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
