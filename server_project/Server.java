/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import application.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gayeu
 */
class ServerThread extends Thread {

    public void run() {
        while (!Server.serverSocket.isClosed()) {
            try {
                System.out.println("Client Bekleniyor");
                Socket clientSocket = Server.serverSocket.accept();
                System.out.println("Client Geldi");
                SClient nclient = new SClient(clientSocket);
                Server.Clients.add(nclient);
                System.out.println("Client eklendi");
                nclient.listenThread.start();

            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

public class Server {

    public static void main(String[] args) {
        Server.Start(7000);
    }

    public static ServerSocket serverSocket;
    public static int serverPort = 0;
    public static ServerThread runThread;

    public static ArrayList<SClient> Clients = new ArrayList<>();

    public static void Start(int port) {
        try {
            Server.serverPort = port;
            Server.serverSocket = new ServerSocket(Server.serverPort);
            Server.runThread = new ServerThread();
            Server.runThread.start();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Send(SClient cl, Message msg) {
        try {
            cl.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
