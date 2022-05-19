package com.mygdx.tanks2d.Units.Tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.Screens.GamePlayScreen;
import com.mygdx.tanks2d.Utils.VectorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class OpponentsTanks { // ОДИН ТАНК

    public float color = 0;

    boolean move;
    //Корпус
    private Vector2 position; // позиция
    private Vector2 direction; //направление корпуса

    private Vector2 directionMovementControll;// управление
    //Башня - Tower
    private Vector2 direction_tower;//направление корпуса башни
    boolean live; // жизнь
    private TowerRotation towerRotation;

    int nomder;
    final float SPEED_ROTATION = 180;
    final float SPEED = 90;
    // экземпляры эффектов
    public Vector2 temp_delta;

    GamePlayScreen gsp;
    // описание
    public int hp;
    //private String nikName;
    Integer command;// команда
    private String nikPlayer;

    public Vector2 getDirectionMovementControll() {
        return directionMovementControll;
    }

    public OpponentsTanks(Vector2 position, Vector2 direction, Integer command, boolean live, int nomder, HashMap<Integer, OpponentsTanks> listOpponents, GamePlayScreen gsp) {
         //this.nikName = getNikNameGen();
        this.live = live;
        this.command = command;
        this.position = position;
        this.direction = direction;
        this.direction_tower = new Vector2(1, 0);


        this.nomder = nomder;
        towerRotation = new TowerRotation(this.direction, this.direction_tower, this.position, listOpponents);
        directionMovementControll = new Vector2(direction);
        this.gsp = gsp;
        hp = MathUtils.random(20, 100);
        move = false;
        temp_delta = new Vector2(0,0);

    }

    private void generatorSmoke() {
        if (hp < 70) {
            //  System.out.println(hp);
            if (MathUtils.randomBoolean((100 - hp) / 380f))
                gsp.pc.addParticalsSmoke(1, position.x, position.y, hp);
        } else if (hp < 50) {
            if (MathUtils.randomBoolean(.05f))
                gsp.pc.addParticalsSmoke(1, position.x, position.y, hp);
//            if (hp < 15) if (MathUtils.randomBoolean(.05f))
//                gsp.pc.addPasricalExplosionDeath(position.x, position.y);
        }
    }


    public float getColor() {
        return color;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getDirection_tower() {
        return direction_tower;
    }

    public void setDirection_tower(Vector2 direction_tower) {
        this.direction_tower = direction_tower;
    }


    public String getNikPlayer() {
        return nikPlayer;
    }

    public void setNikPlayer(String nikPlayer) {
        this.nikPlayer = nikPlayer;
        //System.out.println(" !!!!!!!!!!!!!!!!!!!!!!!!_________________ !!!!!!!");
    }

    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getNomder() {
        return nomder;
    }

    public void setNomder(int nomder) {
        this.nomder = nomder;
    }




    public void update(float delta) {
        generatorSmoke();

      // if(MathUtils.randomBoolean(.005f)) gsp.pc.addPasricalExplosionDeath(position.x,position.y); // взрыв танка
 //       towerRotation.update(delta);
//        attack();
//        move(delta);
//        generatorSmoke();
     //   generatorSmoke();




    }

    private void attack() {
        if (MathUtils.randomBoolean(0.005f)) return;
//        if (towerRotation.getNomTarget() == null) return;
//        if (!towerRotation.isRedyToAttac()) return;
        int speed = 700;
        Vector2 smooke = getPoSmoke();
       /// gsp.getBullets().addBullet(smooke, getDirection_tower().cpy().nor().scl(speed));
        gsp.getAudioEngine().pleySoundKickStick((25000 - VectorUtils.getLen2(position, gsp.getTank().getPosition())) / 25000);
//        gsp.getGameSpace().getLighting().getBuletFlash().newFlesh(position.x, position.y);

        gsp.pc.addPasricalExplosion(.3f, smooke.x, smooke.y);
        gsp.pc.addParticalsSmokeOne(smooke.x, smooke.y);

       // gsp.pc.addPasricalDeath_little(smooke.x, smooke.y,3);



    }

    public Vector2 getPoSmoke() {
        return getPosition().cpy().sub(getDirection_tower().cpy().nor().scl(-30));
    }


    private void move(float delta) { // серверная часть в будущем )))

//        if ((gsp.getTanksOther().isCollisionsTanks(position)) != null) {        // проверка колизии
//            // position.cpy().add(direction.clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime() * -5));
//            // this.direction.scl(-1);
//
//        } else

    //    ------------------------------------серверная часть
//        if (gsp.getGameSpace().checkMapBorders(
//                this.position.cpy().add(direction.clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime()))
//        )) this.position.add(direction.clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime()));
//
//
//        float raz = Math.abs(direction.angleDeg() - directionMovementControll.angleDeg());
//        if (raz > 20)
//            if ((directionMovementControll.angleDeg(direction) > 180))
//                this.direction.setAngleDeg(direction.angleDeg() - Gdx.graphics.getDeltaTime() * SPEED_ROTATION);
//            else
//                this.direction.setAngleDeg(direction.angleDeg() + Gdx.graphics.getDeltaTime() * SPEED_ROTATION);
//
//
//        if (MathUtils.randomBoolean(.004f))
//            this.directionMovementControll.rotateDeg(MathUtils.random(-360, 360));


    }


    @Override
    public String toString() {
        return "{" +
                "color=" + color +
                ", hp=" + hp +
                ", nik='" + nikPlayer + '\'' +
                '}';
    }

    public TowerRotation getTowerRotation() {
        return towerRotation;
    }

    static String getNikNameGen() {
        ArrayList<String> names = new ArrayList<>();
        names.add("Bubba");
        names.add("Honey");
        names.add("Bo");
        names.add("Sugar");
        names.add("Doll");
        names.add("Peach");
        names.add("Snookums");
        names.add("Queen");
        names.add("Ace");
        names.add("Punk");
        names.add("Sugar");
        names.add("Gump");
        names.add("Rapunzel");
        names.add("Teeny");
        names.add("MixFix");
        names.add("BladeMight");
        names.add("Rubogen");
        names.add("Lucky");
        names.add("Tailer");
        names.add("IceOne");
        names.add("Sugar");
        names.add("Gump");
        names.add("Rapunzel");
        names.add("Teeny");
        names.add("MixFix");
        names.add("BladeMight");
        names.add("Rubogen");
        names.add("Lucky");
        names.add("Tailer");
        names.add("IceOne");
        names.add("TrubochKa");
        names.add("HihsheCKA");
        return names.get(MathUtils.random(names.size() - 1)) + "_Bot";
    }

    public boolean isCollisionsTanks(Vector2 posTank) {
        float len = VectorUtils.getLen2(posTank, this.position);
        if (len > 3 && len < 1300) return true;
        return false;
    }

}
