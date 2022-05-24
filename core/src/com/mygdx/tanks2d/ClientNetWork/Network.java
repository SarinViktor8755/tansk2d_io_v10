package com.mygdx.tanks2d.ClientNetWork;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;


public class Network {
    final public static int udpPort = 37001, tcpPort = 37001;
    final  public static String host = "127.0.0.1";

    //final public static String ip = "176.62.66.63";

    //final public static String ip = "185.231.68.81";
    //final public static String ip = "omskSarin2020.online";
 final  public static String ip = "127.0.0.1";
  // final public static String ip = "92.124.144.204";

    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Integer.class);
        kryo.register(PleyerPosition.class);
        kryo.register(PleyerPositionNom.class);
        kryo.register(StockMessOut.class);
        kryo.register(StockMessInClient.class);
    }

    /////////////////////////////////////
    public static class PleyerPosition {   //позиция
        public float xp;
        public float yp;
        public float roy_tower;
    }

    public static class PleyerPositionNom {   //ответ позиция с номером
        public int nom;
        public float xp;
        public float yp;
        public float roy_tower;
    }


    public static class StockMessOut {   //сообщение из стока
        public int tip;
        public float p1;
        public float p2;
        public float p3;
        public float p4;
        public String textM;
    }

    public static class StockMessInClient {   //сообщение из стока
        public int tip;
        public float p1;
        public float p2;
        public float p3;
        public float p4;
        public String textM;
        Integer nomerPlayer;
    }
}
