package com.mygdx.tanks2d.ClientNetWork;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.tanks2d.ClientNetWork.VoiceChat.VoiceChatClient;


public class MainClient {
    public Client client;   //клиент
    public int myIdConnect; //Мой ИД
    private VoiceChatClient voiceChatClient;

    public MainClient() {
        int bufferSize = 22050; // Recommened value.

        client = new Client(bufferSize,bufferSize);
        client.start();
        // FrameworkMessage.Ping ping = new FrameworkMessage.Ping();
        Network.register(client);

        ///////////////VC
        voiceChatClient = new VoiceChatClient(client.getKryo());
        voiceChatClient.addReceiver(client);
////////////////

        client.addListener(new Listener() {

            public void connected(Connection connection) {
                setMyIdConnect(connection.getID());
                sendMyNik();

            }

            public void received(Connection connection, Object object) {
                router(object);

            }

            public void disconnected(Connection connection) {
            }
        });


    }

    private void sendMyNik() {
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
}
