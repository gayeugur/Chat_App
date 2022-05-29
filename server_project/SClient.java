/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import application.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import static server.Server.Clients;

/**
 *
 * @author gayeu
 */
public class SClient {

    public String name = "";
    public String roomName = "";
    Socket socket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    Listen listenThread;

    public SClient(Socket paramSocket) {
        this.socket = paramSocket;
        try {
            this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.sInput = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.listenThread = new Listen(this);
    }

    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class Listen extends Thread {

        SClient TheClient;
        Message msg;
        DefaultListModel userList = new DefaultListModel();
        DefaultListModel roomList = new DefaultListModel();

        Listen(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            while (TheClient.socket.isConnected()) {
                try {
                    Message received = (Message) (TheClient.sInput.readObject());
                    String parts[] = null;
                    String mess_one, mess_two;
                    switch (received.type) {
                        case UserName:
                            TheClient.name = received.content.toString();
                            Server.Send(TheClient, received);
                            for (SClient client : Clients) {
                                userList.addElement(client.name);
                            }
                            Message msg2 = new Message(Message.Message_Type.UserList);
                            msg2.content = userList;

                            for (SClient c : Clients) {
                                Server.Send(c, msg2);
                            }

                            break;
                        case RoomName:
                            TheClient.roomName = received.content.toString();
                            Server.Send(TheClient, received);
                            roomList.addElement(roomName);

                            Message msg3 = new Message(Message.Message_Type.RoomNameList);
                            msg3.content = roomList;
                            for (SClient c : Clients) {
                                Server.Send(c, msg3);
                            }
                            break;

                        case ChatPrivate:
                            parts = received.content.toString().split("-");
                            mess_one = parts[0];
                            mess_two = parts[1];
                            received.content = received.content.toString();

                            for (SClient ccp : Clients) {
                                if (ccp.name.equals(mess_one)) {
                                    Server.Send(ccp, received);
                                }
                            }
                            break;

                        case groupUsers:
                            parts = received.content.toString().split("_");
                            mess_one = parts[0]; //mesaj
                            mess_two = parts[1]; //kisiler
                            String[] parts2 = mess_two.split("-"); //kisiler bölme
                            received.content = mess_one + "_" + mess_two;

                            for (SClient c : Clients) {
                                for (String p : parts2) {
                                    if (c.name.equals(p)) {
                                        Server.Send(c, received);
                                    }
                                }
                            }
                            break;

                        case Mess:
                            parts = received.content.toString().split("_");
                            mess_one = parts[0]; //kisi
                            mess_two = parts[1]; //mesaj
                            received.content = mess_one + "_" + mess_two;

                            for (SClient c : Clients) {
                                if (c.name.equals(mess_one)) {
                                    Server.Send(c, received);
                                }
                            }
                            break;

                        case Back:
                            for (SClient k : Server.Clients) {
                                if (k.name.equals(received.content.toString())) {
                                    Server.Send(k, received);
                                }
                            }
                            break;

                        case BackGroup:
                            for (SClient k : Server.Clients) {
                                parts = received.content.toString().split("-");
                                int num = parts.length;
                                for (int i = 0; i < num; i++) {
                                    if (k.name.equals(parts[i])) {
                                        Server.Send(k, received);
                                    }
                                }
                            }
                            break;

                        case GroupFileSender:
                            parts = received.content.toString().split("_");
                            mess_one = parts[0]; //kisiler
                            mess_two = parts[1]; //mess
                            received.content = mess_two;
                            String[] mess_array = mess_one.split("-");
                            //tum grup uyelerine mesaj icerigini gonderir
                            for (SClient peopleC : Clients) {
                                for (String peopleP : mess_array) {
                                    if (peopleC.name.equals(peopleP)) {
                                        Server.Send(peopleC, received);
                                    }
                                }
                            }
                            break;

                        case SendFile:
                            Server.Send(this.TheClient, received);
                            break;

                    }
                } catch (IOException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
