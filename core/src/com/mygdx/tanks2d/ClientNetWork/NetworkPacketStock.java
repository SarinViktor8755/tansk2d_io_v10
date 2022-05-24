package com.mygdx.tanks2d.ClientNetWork;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.tanks2d.Units.NikName;

import java.util.HashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class NetworkPacketStock {

    Client client;
//    BlockingDeque<PacketMo    del> packetDeque;
//    HashMap<Integer, PacketModel> outList; // лист выходных сообщений
//    HashMap<Integer, PacketModel> inList; // лист входных сообщений
//    CopyOnWriteArrayList<Integer> inListMass;
//    private boolean online;

//
//    int myId = -1;
//
//    int nomerOut; // это номера исходящего сообщения


    public NetworkPacketStock(Client client) {
        this.client = client;

    }

//    public void addOutgoingPackage(PacketModel packet) {
//        ///мсразу сделать отправку )))
//        this.outList.put(packet.getTime_even(), packet);
//    }
//
//    private void addIncomingPackage(Network.StockMess packet) {
//        //this.inList.put(packet.getTime_even(),packet);
//    }
//
//
//    public void updatOutLint(Client client) { // проверка расылка сообщений
//        if (!online) {
//            this.outList.clear();
//            return;
//        }
//        Integer key;
//        PacketModel temp;
//        Iterator<Integer> in = outList.keySet().iterator(); // отправка сообщений на сервер
//
//        //  System.out.println(outList);
//        // System.out.println("-------------------");
//        while (in.hasNext()) {
//            try {
//
//                key = in.next();
//                temp = outList.get(key);
//                temp.sendId(this.myId);
//                temp.getP().nomer_pley = client.getID();
//                //System.out.println("--" + outList.size());
//                if (!temp.isActual()) continue;
//                client.sendUDP(temp.getP()); // отправка пакетов
//                checkByTime(temp);
//
//                //System.out.print(" >>> "+ temp.getTime_even());
//                System.out.println(" >>>>>> otpravil paket " + " tip: " + temp.getTip());
//            } catch (ConcurrentModificationException e) {
//            }
//        }
//    }
//
//    public void updatInLint() { // проверка входящих сообщений
//        Integer key;
//        PacketModel temp;
//        Iterator<Integer> in = inList.keySet().iterator(); // входящие сообщения
//        while (in.hasNext()) {
//            try {
//                key = in.next();
//                temp = inList.get(key);
//                checkByTime(temp);// проверить по времени
//            } catch (ConcurrentModificationException e) {
//            }
//        }
//    }


    //////////////////////////////////////////////
//
//    public void setMyId(int myId) {
//        this.myId = myId;
//    }

    private void send_package_to_server(int tip, float p1, float p2, float p3, float p4, String text) //Integer tip, Integer p1, Integer p2, Integer p3, Integer p4, Integer p5, Integer p6, Integer nomer_pley, String textM,
    {
        Network.StockMessOut pack = new Network.StockMessOut();
        pack.tip = tip;
        pack.p1 = p1;
        pack.p2 = p2;
        pack.p3 = p3;
        pack.p4 = p4;
        pack.textM = text;
        int byte_0 = client.sendTCP(pack);
        System.out.println(byte_0);

    }


    public void toSendMyShot(float x, float y, float alignShoot) { // мой выстрел
          //  send_package_to_server(Heading_type.MY_SHOT, x, y, alignShoot, 0, null);
    }

    public void toSendMyNik() {
        send_package_to_server(Heading_type.MY_NIK, 0, 0, 0, 0, NikName.getNikName());
    }

    public void toSendMyTokken() {
        //send_package_to_server(Heading_type.MY_NIK, 0, 0, 0, 0, NikName.getTokken());
    }

    public void toSendButtonStartClick() {
        send_package_to_server(Heading_type.STATUS_GAME, 0, 0, 0, 0, NikName.getNikName());
    }

    public void toSendMYParameters(int hp) {
        send_package_to_server(Heading_type.MY_PARAMETERS, hp, 0, 0, 0, NikName.getNikName());
    }



}
//
//    public void toSendMyNik() {
//
//        //System.out.println("adding Packet Tokken");
//        PacketModel pm = getFreePacketModel();
//        outList.put(pm.getTime_even(), pm);
//        pm.setParametrs(Heading_type.MY_NIK, null, null, null, null, NikName.getNikName());
//    }
//
//    public void toSendButtonStartClick() {
//        //System.out.println("start button");
//        PacketModel pm = getFreePacketModel();
//        outList.put(pm.getTime_even(), pm);
//        pm.setParametrs(Heading_type.BUTTON_STARTGAME, null, null, null, null, NikName.getNikName());
//        // pm.setParametrs(Heading_type.MY_TOKKEN, null, null, null, null, null, null, NikName.getTokken());
//    }
//
//    public void toSendParametersOfPlayer(int id) { // скажи парамтры игрока )) по ID
//        PacketModel pm = getFreePacketModel();
//        outList.put(pm.getTime_even(), pm);
//        pm.setParametrs(Heading_type.PARAMETERS_PLAYER, null, null, null, null, NikName.getNikName());
//    }
//
//
//    public void toSendParametersOfPlayer() { // скажи парамтры игрока )) по ID
//        //System.out.println("start button");
//        PacketModel pm = getFreePacketModel();
//        // System.out.println("NIK  " +  NikName.getNikName());
//        pm.setParametrs(Heading_type.PARAMETERS_PLAYER, null, null, null, null, NikName.getNikName());
//    }
//
//
//    public void toSendMYParameters(int hp) {
//        PacketModel pm = getFreePacketModel();
//        System.out.println("MY_PARAMETERS >>>> ");
//        pm.setParametrs(Heading_type.MY_PARAMETERS, hp, null, null, null, , NikName.getNikName());
//    }

        /////////////////////////////////////////

//        public void markAsSent ( int nom){ // ullPointerException
//            try {
//                outList.get(nom).setActualFalse();
//            } catch (NullPointerException e) {
//                System.out.println("NullPointerException markAsSent " + nom);
//            }
//
//        }
//
//        public HashMap<Integer, PacketModel> getOutList () {
//            return outList;
//        }

        ////////////OLD
//        public void toSendMyShot ( float x, float y, float alignShoot){ // мой выстрел
//            PacketModel pm = getFreePacketModel();
//            outList.put(pm.getTime_even(), pm);
//            pm.setParametrs(Heading_type.MY_SHOT, (int) x, (int) y, (int) alignShoot, (int) (5000 + MathUtils.random(9999) + x - y), NikName.getNikName());
//            //this.updatOutLint();
//            System.out.println("----------->>>> toSendMyShot");
//        }
//
//        public void toSendMyTokken () {
//            PacketModel pm = getFreePacketModel();
//            outList.put(pm.getTime_even(), pm);
//            pm.setParametrs(Heading_type.MY_TOKKEN, null, null, null, null, NikName.getTokken());
//
//        }
//
//        public void toSendMyNik () {
//
//            //System.out.println("adding Packet Tokken");
//            PacketModel pm = getFreePacketModel();
//            outList.put(pm.getTime_even(), pm);
//            pm.setParametrs(Heading_type.MY_NIK, null, null, null, null, NikName.getNikName());
//        }
//
//        public void toSendButtonStartClick () {
//            //System.out.println("start button");
//            PacketModel pm = getFreePacketModel();
//            outList.put(pm.getTime_even(), pm);
//            pm.setParametrs(Heading_type.BUTTON_STARTGAME, null, null, null, null, NikName.getNikName());
//            // pm.setParametrs(Heading_type.MY_TOKKEN, null, null, null, null, null, null, NikName.getTokken());
//        }
//
//        public void toSendParametersOfPlayer ( int id){ // скажи парамтры игрока )) по ID
//            PacketModel pm = getFreePacketModel();
//            outList.put(pm.getTime_even(), pm);
//            pm.setParametrs(Heading_type.PARAMETERS_PLAYER, null, null, null, null, NikName.getNikName());
//        }
//
//
//        public void toSendParametersOfPlayer () { // скажи парамтры игрока )) по ID
//            //System.out.println("start button");
//            PacketModel pm = getFreePacketModel();
//            // System.out.println("NIK  " +  NikName.getNikName());
//            pm.setParametrs(Heading_type.PARAMETERS_PLAYER, null, null, null, null, NikName.getNikName());
//        }
//
//
//        public void toSendMYParameters ( int hp){
//            PacketModel pm = getFreePacketModel();
//            System.out.println("MY_PARAMETERS >>>> ");
//            pm.setParametrs(Heading_type.MY_PARAMETERS, hp, null, null, null, , NikName.getNikName());
//        }
//
//        /////////////////////////////////////////
//
//        public void markAsSent ( int nom){ // ullPointerException
//            try {
//                outList.get(nom).setActualFalse();
//            } catch (NullPointerException e) {
//                System.out.println("NullPointerException markAsSent " + nom);
//            }
//
//        }
//
//        public HashMap<Integer, PacketModel> getOutList () {
//            return outList;
//        }
//
//        //
////    public void routingInMassage(PacketModel m) {
////        // System.out.println("routingInMassage  tip:" + m.getP().tip);
////        if (m.getP().tip == Heading_type.MY_NIK) {
////
////
////            return;
////        }
////
////        if (m.getP().tip == Heading_type.MY_SHOT) {
////            //System.out.println("MY_SHOT <<< return");
////            return;
////        }
////
////        if (m.getP().tip == Heading_type.MY_TOKKEN) {
////            toSendMyNik();
////            return;
////        }
////
////        if (m.getP().tip == Heading_type.PARAMETERS_PLAYER) {
////
////            return;
////        }
////
////        if (m.getP().tip == Heading_type.STATUS_GAME) {
////
////
////            return;
////        }
////
////
////    }


