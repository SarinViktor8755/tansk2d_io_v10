package com.mygdx.tanks2d.ClientNetWork;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;


public class Network {
    final public static int udpPort = 37960, tcpPort = 37960;
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
        kryo.register(StockMess.class);
    }

    /////////////////////////////////////
    public static class PleyerPosition {   //позиция
        public float xp;
        public float yp;
        public float roy_tower;
    }

    public static class PleyerPositionNom {   //ответ позиция с номером
        public Integer nom;
        public Integer xp;
        public Integer yp;
        public Integer roy_tower;
    }


    public static class StockMess {   //сообщение из стока
        public int tip;
        public float p1;
        public float p2;
        public float p3;
        public float p4;
        public String textM;
        public Integer nomer_pley;
    }
}
