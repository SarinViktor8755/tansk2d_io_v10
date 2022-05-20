package com.mygdx.tanks2d.ClientNetWork;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.tanks2d.ClientNetWork.VoiceChat.VoiceChatClient;
import com.mygdx.tanks2d.MainGame;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.TreeMap;


public class MainClient {
    public float aplphaConnect;
    private Client client;   //клиент
    private boolean onLine;
    private MainGame mg;
    private int myIdConnect;

    final public static int udpPort = 37001, tcpPort = 37001;
    final  public static String host = "127.0.0.1";

    private NetworkPacketStock networkPacketStock;
    public TreeMap<Integer, Network.PleyerPositionNom> otherPlayer;
//    public HashMap<Integer, Boolean> frameUpdates; //Обновления кадра для играков
//    public ArrayDeque<PacketModel> inDequePacket; // входящие пакеты для обработки;

    public MainClient() {
        client = new Client();
        client.start();

        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(client);

        try {
            client.connect(10_000, host, Network.udpPort);
            // Server communication after connection can go here, or in Listener#connected().
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.networkPacketStock = new NetworkPacketStock(client);
        otherPlayer = new TreeMap<>();
        onLine = true;

       // this.startClient();

        client.addListener(new Listener() {

            public void connected(Connection connection) {
                setMyIdConnect(connection.getID());
                networkPacketStock.toSendMyTokken();
            }

            public void received(Connection connection, Object object) {
                router(object);

            }

            public void disconnected(Connection connection) {
            }
        });
        System.out.println(client.isConnected() + "!!!!!!!!!!!!!!!1");
    }

    private void startClient() {
        System.out.println(client.isConnected());
        this.client = new Client();
        Network.register(client);
        this.client.start();


    }


    public int getMyIdConnect() {
        return myIdConnect;
    }

    public void setMyIdConnect(int myIdConnect) {
        this.myIdConnect = myIdConnect;
    }

    public Client getClient() {
        return client;
    }

    public void router(Object object) {

    }


    public boolean isOnLine() {
        return true;
    }

    public void upDateClient() {

    }
}
