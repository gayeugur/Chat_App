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
import static server.Server.Clients;

/**
 *
 * @author gayeu
 */
public class SClient {

    int id;
    public String name = "";
    Socket soket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    Listen listenThread;


    public SClient(Socket gelenSoket, int id) {
        this.soket = gelenSoket;
        this.id = id;
        try {
            this.sOutput = new ObjectOutputStream(this.soket.getOutputStream());
            this.sInput = new ObjectInputStream(this.soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //thread nesneleri
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

        Listen(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            while (TheClient.soket.isConnected()) {
                try {            
                    Message received = (Message) (TheClient.sInput.readObject());
                    switch (received.type) {
                        case UserName:
                            //kisinin isim bilgisini aldıktan sonra baglanti kurar
                            TheClient.name = received.content.toString();
                            Thread.sleep(500);
                            Server.Send(TheClient, received);
                            Thread.sleep(500);
                            Server.BaglantiKur(received);
                            break;

                        case ChatPrivate:
                            String[] dizi = received.content.toString().split("-");
                            String kisi_ad = dizi[0];
                            String kisi_ad2 = dizi[1];
                            received.content = received.content.toString();

                            for (SClient c : Clients) {
                                if (c.name.equals(kisi_ad)) {
                                    Server.Send(c, received);

                                }
                            }

                            break;

                        case groupUsers:
                            //Secili kisilerle grup olusturur
                            Server.CreateGrup(received);

                            break;

                        case Mess:
                            //karsi clienta mesaji iletir
                             Server.KarsiyaGonder(received);
                            
                    
                            break;

                        case Back:
                            //Clientlardan biri konusmadan ciktiktan sonra karsiki clienta bunun bilgisi gider 
                            //konusma sonlanir
                            SClient c = Server.ClientBul(received.content.toString());
                            Server.Send(c, received);
                            Thread.sleep(100);
                            break;

                       // case NotConnect:
                            //clientlardan biri konusmadan cikinca o konusmayi takip eden threadi durdurur
                         //   Thread.sleep(100);
                            // TheClient.pairThread.stop();
                           // break;

                   //     case grupUsers:
                            //grup olusturmak icin online kullanicilar bilgisini tasir
                     //       Server.BaglantiKur2(received);
                      //      Thread.sleep(100);
                       //     break;

                        case GroupFileSender:
                            //gruptaki tum uyelere mesaji iletir
                            Server.tumUyelereGonder(received);
                            break;
                        case PrivateFileSender:
                            //gruptaki tum uyelere mesaji iletir
                            Server.tumUyelereGonder(received);
                            break;

                        case File:
                            //gruptaki kullanicilara dosyayi gonderir
                            Server.dosyaGonder(received);
                            break;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
