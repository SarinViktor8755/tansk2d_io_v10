package com.mygdx.tanks2d.ClientNetWork;

public class ServiceClient {
    static public void  sendMyNik(String nikName, MainClient client){
        Network.StockMess pk = new Network.StockMess();
        pk.tip = Heading_type.MY_NIK;
        pk.textM = nikName;
        client.getClient().sendUDP(pk);
    }

    static public void  sendMyTokken(String tokke, MainClient client){
        Network.StockMess pk = new Network.StockMess();
        pk.tip = Heading_type.MY_TOKKEN;
        pk.textM = tokke;
        client.getClient().sendUDP(pk);
    }







}
