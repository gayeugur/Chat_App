/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author gayeu
 */

import application.Group;
import application.Message;
import static application.Room.ThisSohbet;
import application.anasayfa;
import static application.anasayfa.state;
import static client.Client.sInput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

class Listen extends Thread {

    OutputStream os;
    String system = System.getProperty("user.home");

    public void run() {
        while (Client.socket.isConnected()) {
            try {
                Message received = (Message) (sInput.readObject());
                switch (received.type) {
                    case UserName:
                        application.anasayfa.user1 = received.content.toString();
                        break;

                    case UserList:
                        anasayfa.ThisAnasayfaPage.list_users.setModel((DefaultListModel) received.content);
                        break;

                    case Mess:
                        String[] parts2 = received.content.toString().split("_");
                        application.Room.ThisSohbet.setVisible(true);
                        application.Room.ThisSohbet.list_mess.setText(parts2[1]);
                        break;

                    case Back:
                        ThisSohbet.setVisible(false);
                        anasayfa.ThisAnasayfaPage.setVisible(true);
                        break;

                    case BackGroup:
                        Group.ThisGrupSohbet.setVisible(false);
                        anasayfa.ThisAnasayfaPage.setVisible(true);
                        break;

                    case groupUsers:
                        application.anasayfa.ThisAnasayfaPage.setVisible(false);
                        if (state == true) {
                            application.Group.ThisGrupSohbet.setVisible(false);
                        }

                        new application.Group(received.content.toString(), 1).setVisible(true);
                        break;

                    case ChatPrivate:
                        application.anasayfa.ThisAnasayfaPage.setVisible(false);
                        String[] dizi = received.content.toString().split("-");
                        String kisi_ad = dizi[0];
                        Client.kisi_ad2 = dizi[1];
                        new application.Room().setVisible(true);
                        break;

                    case RoomName:
                        application.anasayfa.roomName = received.content.toString();
                        break;

                    case RoomNameList:
                        anasayfa.ThisAnasayfaPage.list_rooms.setModel((DefaultListModel) received.content);

                        break;

                    case GroupFileSender:
                        application.Group.ThisGrupSohbet.grup_mesaj_akisi.setText(received.content.toString());
                        break;

                    case SendFile:
                        File receivedFile = new File(system + "/Desktop/" + received.content);
                        os = new FileOutputStream(receivedFile);
                        byte content[] = (byte[]) received.fileList;
                        os.write(content);
                        break;

                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

public class Client {

    public static String kisi_ad2;
    public static Socket socket;
    public static ObjectInputStream sInput;
    public static ObjectOutputStream sOutput;
    public static Listen listenMe;

    public static void Start(String ip, int port) {
        try {
            Client.socket = new Socket(ip, port);
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();

            Message msg = new Message(Message.Message_Type.UserName);
            msg.content = application.anasayfa.ThisAnasayfaPage.txt_name.getText();
            Client.Send(msg);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
