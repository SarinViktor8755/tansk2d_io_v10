package com.mygdx.tanks2d.Units.Tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.ClientNetWork.Network;
import com.mygdx.tanks2d.ClientNetWork.PacketModel;
import com.mygdx.tanks2d.Screens.GamePlayScreen;
import com.mygdx.tanks2d.Utils.VectorUtils;

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class TanksOther { /// много танков )))

    public static int RED_COMMAND = 1;
    public static int BLUE_COMMAND = 2;

    private BitmapFont textFont;

    HashMap<Integer, OpponentsTanks> listOpponents;

    HashMap<Integer, Float> listSled;
    HashMap<Integer, Vector2> deltaSledVec;

    GamePlayScreen gsp;

    Texture img;
    Texture img_1;

    Texture imgB;
    Texture img_1B;

    Texture img2;
    Texture imgB2;

    Texture b, tower;

    Vector2 temp;
    Vector2 tRotation;

    BotAdmin botAdmin;


    public TanksOther(GamePlayScreen gsp) {
        this.gsp = gsp;

        img = gsp.getMainGame().getAssetManager().get("tbb1.png");
        img2 = gsp.getMainGame().getAssetManager().get("tbb2.png");

        img_1 = gsp.getMainGame().getAssetManager().get("tb.png");

        imgB = gsp.getMainGame().getAssetManager().get("trb1.png");
        imgB2 = gsp.getMainGame().getAssetManager().get("trb2.png");

        img_1B = gsp.getMainGame().getAssetManager().get("tr.png");

        textFont = new BitmapFont();
        textFont.setColor(Color.WHITE);
        textFont.setUseIntegerPositions(true);
        this.listOpponents = new HashMap<>();
        this.listSled = new HashMap<>();

        temp = new Vector2(0, 0);
        tRotation = new Vector2(0, 0);
        deltaSledVec = new HashMap<>();


//        for (int i = 0; i < 0; i++) {
//            int nom = MathUtils.random(100, 1000);
//            OpponentsTanks r = new OpponentsTanks(
//                    new Vector2(MathUtils.random(50, gsp.getGameSpace().WITH_LOCATION), MathUtils.random(50, gsp.getGameSpace().HEIHT_LOCATION)),
//                    new Vector2(MathUtils.random(.5f, 1f), MathUtils.random(.5f, 1f)),
//                    MathUtils.random(RED_COMMAND, BLUE_COMMAND), true,
//                    nom, listOpponents, gsp
//            );
//            listOpponents.put(nom, r);
//            listSled.put(nom, .0f);
//        }

    }

    public void setTankPosition(Network.PleyerPositionNom p, Boolean newFrame) {  /// ЛОГИКА ПЕРЕМЕЩЕНИЯ ТАНКА БЕЗ СИГНАЛА
//        if (listOpponents.containsKey(p.nom)) { //Если такой опоннет есть - работаем с ним - нет - сощдаем новый
//            OpponentsTanks ot = listOpponents.get(p.nom);
//            /////////////
//            if (!newFrame) { // нет фрейма
//                if (ot.move) {
//                    temp.set(ot.getDirection()).clamp(90, 90).scl(Gdx.graphics.getDeltaTime());
//
//                    if (temp.len2() > 0.5929847) {
//                        ot.move = false;
//                        return;
//                    }
//
//                    //System.out.println(temp.len() + "  " + temp.len2());
////                    ot.getPosition().add(temp);
////                    System.out.println(temp.len());
//
//                }
//
//                //     System.out.println("frame FALSE");
//
//            } else { // есть фрейм - тогда смещаем танк к реальной точке перемещения
//
//                temp.set(p.xp, p.yp);
//
//                ot.getDirection().setAngleRad(temp.angleDeg());
//                ot.getPosition().add(temp.sub(ot.getPosition().cpy()).scl(Gdx.graphics.getDeltaTime() * 10));
//                //ot.getPosition().sub(temp.scl(Gdx.graphics.getDeltaTime()));
////                System.out.println("frame TRUE");
//            }
//
//            float rotation = tRotation.set(p.xp, p.yp).cpy().sub(ot.getPosition()).angleDeg();
//
//            if (temp.set(p.xp, p.yp).sub(ot.getPosition().cpy()).scl(10).len2() > 50)
//                ot.move = true;
//            else ot.move = false;
//            ot.getDirection().setAngleDeg(rotation);
//            ot.getDirection_tower().setAngleDeg(p.roy_tower);
//            if (ot.move)
//                listSled.put(p.nom, listSled.get(p.nom) + Gdx.graphics.getDeltaTime()); /// СЛЕДЫ
//        } else {
//            try {
//                createOponent(p.xp, p.yp,p.nom,p.roy_tower);
//            } catch (NullPointerException e) {
//
//            }
//        }
    }

    /////////////
    public int createOponent(float x, float y, int nomer, float rotation) {

        OpponentsTanks r = new OpponentsTanks(
                new Vector2(x, y),
                new Vector2(1, 0),
                0, true,
                nomer, listOpponents, gsp
        );
        listOpponents.put(nomer, r);
        listSled.put(nomer, .0f);
        return nomer;
    }


    private boolean inCanMove(int x, int y) {
        gsp.getGameSpace().checkMapBorders(x, y);
        return true;
    }

    private void updateColor(OpponentsTanks t, float dt) {
        if (t.getCommand() != 0 && t.color < 1) t.color = t.color + dt;
    }

    public void randerOtherTanks(SpriteBatch sb) {
        OpponentsTanks t;
        System.out.println(this.listOpponents.size());
        for (Map.Entry<Integer, OpponentsTanks> tank : this.listOpponents.entrySet()) {
            t = tank.getValue();
            updateColor(t, Gdx.graphics.getDeltaTime());
            if (t.getNikPlayer() != null) {
                textFont.draw(sb, t.getNikPlayer(), t.getPosition().x - t.getNikPlayer().length() * 4, t.getPosition().y + 50);
            }


            t.update(Gdx.graphics.getDeltaTime());

            //   t.setCommand(MathUtils.random(1,2));
            //  System.out.println("command " + t.getCommand());


            sb.setColor(t.getColor(), t.getColor() + .5f, t.getColor(), 1);
            if (t.getCommand() == RED_COMMAND) {
                if (!t.move) b = img;
                else if (MathUtils.sin(gsp.getTimeInGame() * 50) >= 0) b = img;
                else b = img2;
                tower = img_1;
            } else {
                // b = imgB;
                if (!t.move) b = imgB;
                else if (MathUtils.sin(gsp.getTimeInGame() * 50) >= 0) b = imgB;
                else b = imgB2;
                tower = img_1B;
            }

            sb.draw(b,
                    t.getPosition().x - 20, t.getPosition().y - 20,
                    20, 20,
                    40, 40,
                    1, 1,
                    t.getDirection().angleDeg() + 180,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    true, false);

            sb.draw(tower,
                    t.getPosition().x - 20, t.getPosition().y - 20,
                    20, 20,
                    40, 40,
                    1, 1,
                    t.getDirection_tower().angleDeg() + 180,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    false, false);


            addSled(t);
        }
        sb.setColor(1, 1, 1, 1);
    }


    private void addSled(OpponentsTanks t) {
        try {
            float dst_sled = Vector2.dst2(t.getPosition().x, t.getPosition().y, deltaSledVec.get(t.nomder).x, deltaSledVec.get(t.nomder).y);
            if (dst_sled > 210) {
                deltaSledVec.put(t.getNomder(), t.getPosition().cpy());
                gsp.getGameSpace().addSled(t.getPosition().x, t.getPosition().y, t.getDirection().angleDeg());
            }
        } catch (NullPointerException e) {
            deltaSledVec.put(t.nomder, new Vector2(t.getPosition().x, t.getPosition().y));
        }

    }

    public void updateOtherTank(boolean onLine) {
         updateClienOtherTank();

    }

    public void updateClienOtherTank() {
        boolean flag_mess = false; /// флаг типа делать запрос по никам или нет
        try {
            Iterator key = gsp.getMainGame().getMainClient().otherPlayer.keySet().iterator();
            while (key.hasNext()) {
                int n = (int) key.next();
                Network.PleyerPositionNom p = gsp.getMainGame().getMainClient().otherPlayer.get(n);
///////////////Аопросить имя игрока
//                try {
//                    if (this.listOpponents.get(n).getNikPlayer().length() < 1) flag_mess = true;
//                } catch (NullPointerException e) {
//                    if (((VectorUtils.getLen2(gsp.getTank().getPosition(), p.xp, p.yp) < 110_000))) {
//                        gsp.getMainGame().getMainClient().getNetworkPacketStock().toSendParametersOfPlayer(n);// nikname отображение
//                    }
//                }
                //////////////////
                setTankPosition(p, gsp.getMainGame().getMainClient().frameUpdates.get(p.nom));
                gsp.getMainGame().getMainClient().frameUpdates.put(p.nom, false); /// закрывает флаг о рендере __
            }
            /// обработка входящих сообщенийв
//            ArrayDeque<PacketModel> in = gsp.getMainGame().getMainClient().inDequePacket;
//            //System.out.println("size IN :: "+in.size());
//            if (in.size() > 0) routingInMassage(in.pollFirst()); /// тут что то не так )))

            //if (flag_mess) if (MathUtils.randomBoolean(.01f))
                //gsp.getMainGame().getMainClient().getNetworkPacketStock().toSendParametersOfPlayer();// nikname отображение

        } catch (ConcurrentModificationException e) {
        }
    }

    public void updateLocalTank() {

    }

//    public void routingInMassage(PacketModel m) {
////
////        if (m.getP().tip == Heading_type.MY_NIK) {
////            gsp.getGameSpace().getLighting().getBuletFlash().newFlesh(m.getP().p1, m.getP().p2);
////            return;
////        }
////
////        if (m.getP().tip == Heading_type.SHELL_RUPTURE) {
////            System.out.println("BOOOOOOOOM!!!!!!!!!!!  " + m.getP().p1 + "  " + m.getP().p2);
////            //gsp.getBullets().
////            gsp.getBullets().removeBullet(m.getP().p3);
////            gsp.pc.addPasricalDeath_little(m.getP().p1, m.getP().p2, 2.7f);
////            return;
////        }
////
////        if (m.getP().tip == Heading_type.MY_SHOT) {
////            //    System.out.println("Shoot " + m.getP().time_even + " np " + m.getP().nomer_pley);
////            temp.set(m.getP().p1, m.getP().p2);
////            tRotation.set(0, 400);
////            tRotation.setAngleDeg(m.getP().p3); /// навправление
////            // System.out.println(tempr.len());
////
////            gsp.getBullets().addBullet(temp, tRotation.cpy(), m.getP().p4);
////
////            ////////////
////            //  if (m.getP().p5 != gsp.getMainGame().getMainClient().myIdConnect) {
////            gsp.getAudioEngine().pleySoundKickStick();
//////                gsp.pc.addPasricalExplosion(.3f, m.getP().p1, m.getP().p2);
//////                gsp.pc.addParticalsSmokeOne(m.getP().p1, m.getP().p2);
////            gsp.pc.addPasricalDeath_little(m.getP().p1, m.getP().p2, 2.7f);
////            gsp.getGameSpace().getLighting().getBuletFlash().newFlesh(m.getP().p1, m.getP().p2);
////            //  }
/////////////////////
////
////
////            ////////////
////
////            return;
////        }
////
////
////        if (m.getP().tip == Heading_type.DISCONECT_PLAYER) {
////            System.out.println("Disconect");
////            try {
////                listOpponents.get(m.getP().p1).live = false;
////            } catch (NullPointerException e) {
////            }
////            return;
////        }
////
////
////        if (m.getP().tip == Heading_type.HP_PLAYER) {
////            System.out.println("!!!!!!!!HP!!!!!!!!!!!!!!1");
////            System.out.println("plaer " + m.getP().p1);
////            System.out.println("hp " + m.getP().p2);
////            System.out.println();
////            try {
////                listOpponents.get(m.getP().p1).hp = m.getP().p1;
////            } catch (NullPointerException e) {
////                gsp.getTank().setHp(m.getP().p2);
////            }
////
////
////            return;
////        }
////
////
////        if (m.getP().tip == Heading_type.PARAMETERS_PLAYER) {
////            System.out.println("<<<-- PARAMETERS_PLAYER");
////            System.out.println(m.getP());
////            //if(m.getP().p1 == -1)
////            //gsp.getMainGame().getMainClient().getNetworkPacketStock().toSendMYParameters(gsp.getTank().getHp()); // перекинуть в тану
////            try {
////                OpponentsTanks t = gsp.getTanksOther().listOpponents.get(m.getP().p6);
////                t.hp = m.getP().p1;
////                t.command = MathUtils.random(1, 2);
////                System.out.println(">>coomand :: " + m.getP().p3);
////                //    System.out.println(m.getP().textM + " nik!");
////                System.out.println("==============++++ nik ikniknik " + m.getP().textM);
////                t.setNikPlayer(m.getP().textM);
////                // System.out.println(t);
////            } catch (Exception e) {
////            }
////
////            return;
////        }
//
//
//    }

    public OpponentsTanks getTankForID(int id) {
        return this.listOpponents.get(id);
    }

    public Integer isCollisionsTanks(Vector2 pos) {
        for (Map.Entry<Integer, OpponentsTanks> tank : this.listOpponents.entrySet()) {
            if (tank.getValue().isCollisionsTanks(pos)) return tank.getKey();
        }
        return null;
    }


}
