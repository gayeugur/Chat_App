/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author 
 */
import static client.Client.sInput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import message.anasayfa;
import static message.Room.ThisSohbet;

/**
 *
 * @author busra
 */
class Listen extends Thread {

    public void run() {
        //soket bağlı olduğu sürece dön
        while (Client.socket.isConnected()) {
            try {
                //mesaj gelmesini blocking olarak dinleyen komut
                Message received = (Message) (sInput.readObject());
                //mesaj gelirse bu satıra geçer
                //mesaj tipine göre yapılacak işlemi ayır.
                switch (received.type) {
                    case Connected:
                        //cevrimici kullanicilar listesini alir ve anasayfada gunceller
                        anasayfa.ThisAnasayfa.list_users.setModel((DefaultListModel) received.content);
                        break;

                    case Name:
                        //Client'ın name bilgisini alir     
                        Client.client_name = received.content.toString();
                        break;

                    case icerik:
                        //mesaj icerigini alir 
                        String mesajlasilan;
                        String mesajakisi;
                        String[] parts = received.content.toString().split("_");
                        mesajlasilan = parts[0];
                        mesajakisi = parts[1];
                        Thread.sleep(100);
                        message.anasayfa.ThisAnasayfa.setVisible(false);
                        new message.Room(mesajlasilan).setVisible(true);
                        message.Room.ThisSohbet.list_mess.setText(mesajakisi);
                        break;

                    case icerik2:
                        //mesaj icerigini alir
                        String mesajlasilan2;
                        String mesajakisi2;
                        String[] parts2 = received.content.toString().split("_");
                        mesajakisi2 = parts2[1];
                        Thread.sleep(100);
                        message.Room.ThisSohbet.setVisible(true);
                        message.Room.ThisSohbet.list_mess.setText(mesajakisi2);
                        break;
//
//                    case durum:
//                        Thread.sleep(500);
//                        sohbet.durum = (boolean) received.content;
//                        break;
                    case baglantiKopar:
                        //karsi client sohbet kutusunu kapatirsa burada da mesajlasma sonlandirilir 
                        ThisSohbet.setVisible(false);
                        anasayfa.ThisAnasayfa.setVisible(true);
                        Thread.sleep(100);
                        break;

                    case grupUsers:
                        //gruptaki kisiler bilgisi gonderilir grup uyelerine
                        Thread.sleep(100);
                     //   grupOlustur.ThisGrupOlustur.users.setModel((DefaultListModel) received.content);
                        break;

                    case grupKisiBul:
                        //grup olusturulduktan sonra her clientin anasayfasi kapatilir ve grup sohbet penceresi baslatilir
                        if (message.anasayfa.ThisAnasayfa.isVisible()) {
                            message.anasayfa.ThisAnasayfa.setVisible(false);
                        }
                        new message.GrupOlusturs(received.content.toString(), 1).setVisible(true);
                        break;

                    case kisiBul:
                        //bireysel mesajlasmada biri sohbeti baslatinca digerinin de sayfasinda o sohbet penceresi acilir
                        if (message.anasayfa.ThisAnasayfa.isVisible()) {
                            message.anasayfa.ThisAnasayfa.setVisible(false);
                        }      
                        String[] dizi = received.content.toString().split("-");
                        String kisi_ad = dizi[0];
                        String kisi_ad2 = dizi[1];
                        System.out.println("ikinic" + kisi_ad2);     
                        
                        new message.Room(kisi_ad2,kisi_ad2).setVisible(true);
                        break;
                        
             

                        

                    case icerikGrup:
                        //gruptaki mesaj iceriklerini gunceller
                        message.GrupOlusturs.ThisGrupSohbet.grup_mesaj_akisi.setText(received.content.toString());
                        break;

                    case dosya1:
                        //yollanan dosyayi getirir ve nereye kaydedilecegini sorar
                        String[] parts4 = received.content.toString().split("_");
                        String fileName = parts4[0];
                        String Content = parts4[1];

                        byte[] content_decode = Base64.getDecoder().decode(Content);

                        JFileChooser ch = new JFileChooser();
                        ch.setCurrentDirectory(new File("C:\\Users\\busra\\Desktop\\ağlar_proje_2\\dosya_alma"));
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
                //Client.Stop();
            } //Client.Stop();
            catch (InterruptedException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

public class Client {

    //her clientın bir soketi olmalı
    public static Socket socket;

    //verileri almak için gerekli nesne
    public static ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public static ObjectOutputStream sOutput;
    //serverı dinleme thredi 
    public static Listen listenMe;
    public static String client_name;

    public static void Start(String ip, int port) {
        try {
            // Client Soket nesnesi
            Client.socket = new Socket(ip, port);
            Client.Display("Servera bağlandı");
            // input stream
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();

            //ilk mesaj olarak isim gönderiyorum
            Message msg = new Message(Message.Message_Type.Name);
            msg.content = message.anasayfa.ThisAnasayfa.txt_name.getText();
            Client.Send(msg);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //client durdurma fonksiyonu
    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listenMe.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();
                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Display(String msg) {
        System.out.println(msg);
    }

    //mesaj gönderme fonksiyonu
    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
