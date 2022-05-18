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
import static client.Client.sInput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import application.Message;
import static application.Room.ThisSohbet;
import application.anasayfa;


class Listen extends Thread {

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
                        Thread.sleep(100);
                        application.Room.ThisSohbet.setVisible(true);
                        application.Room.ThisSohbet.list_mess.setText(parts2[1]);
                        break;

                    case Back:
                        ThisSohbet.setVisible(false);
                        anasayfa.ThisAnasayfaPage.setVisible(true);
                        Thread.sleep(100);
                        break;

                    case groupUsers:
                        application.anasayfa.ThisAnasayfaPage.setVisible(false);
                        new application.Group(received.content.toString(), 1).setVisible(true);
                        break;

                    case ChatPrivate:
                        application.anasayfa.ThisAnasayfaPage.setVisible(false);
                        String[] dizi = received.content.toString().split("-");
                        String kisi_ad = dizi[0];
                        Client.kisi_ad2 = dizi[1];
                        new application.Room().setVisible(true);
                        break;

                    case GroupFileSender:
                        application.Group.ThisGrupSohbet.grup_mesaj_akisi.setText(received.content.toString());
                        break;

                    case PrivateFileSender:
                        application.Room.ThisSohbet.list_mess.setText(received.content.toString());
                        break;

                    case File:
                        String[] parts4 = received.content.toString().split("_");
                        String fileName = parts4[0];
                        String Content = parts4[1];
                        byte[] content_decode = Base64.getDecoder().decode(Content);
                        JFileChooser ch = new JFileChooser();
                        ch.setCurrentDirectory(new File("C:\\Users\\gayeu\\Desktop"));
                        int c = ch.showSaveDialog(null);
                        if (c == JFileChooser.APPROVE_OPTION) {
                            FileOutputStream out = new FileOutputStream(ch.getSelectedFile());
                            out.write(content_decode);
                            out.close();
                        }
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
            System.out.println("Coonect to server");
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
