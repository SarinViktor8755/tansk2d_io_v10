package com.mygdx.tanks2d.Units.Tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.Screens.Controll.Controller;
import com.mygdx.tanks2d.Screens.GamePlayScreen;

public class Tank {
    GamePlayScreen gsp;

    //Корпус
    Vector2 position; // позиция
    Vector2 direction; //направление корпуса
    Vector2 targetCoordinat; // цель коодинаты для прицела

    //Башня - Tower
    Vector2 direction_tower;//направление корпуса башни
    //////////
    SpriteBatch sb;
    Controller controller;

    Texture img;
    Texture img_1;
    Texture imgr;
    Texture img_1r;

    Texture target;

    float raz;
    boolean rot;

    float deltaSled;
    Vector2 deltaSledVec;
    Integer command = 1; // по умолчанию 1 синяя команда временно
    final float SPEED = 90f;
    final float SPEED_ROTATION = 180f;

    private TowerRotation tr;

    private int hp;

//    private HashMap<Float, Integer> targetTreet; // цели - угол до цели - номер цели )))
//    private int target_tank;
//    private Integer nomTarget;


    public Tank(GamePlayScreen gsp) {
        deltaSledVec = new Vector2();
        this.gsp = gsp;
        position = new Vector2(MathUtils.random(0, gsp.getGameSpace().WITH_LOCATION), MathUtils.random(0, gsp.getGameSpace().HEIHT_LOCATION));
        direction = new Vector2(0, 1);
        direction_tower = new Vector2(0, 1);
        //targetCoordinat = new Vector2()
        this.sb = gsp.getBatch();

        img = gsp.getMainGame().getAssetManager().get("trb1.png");
        //img = gsp.getAssetsManagerGame().get("badlogic.png",Texture.class);
        img_1 = gsp.getMainGame().getAssetManager().get("tr.png");
        /////////
        imgr = gsp.getMainGame().getAssetManager().get("tbb1.png");
        //img = gsp.getAssetsManagerGame().get("badlogic.png",Texture.class);
        img_1r = gsp.getMainGame().getAssetManager().get("tb.png");

        //target = new Texture(Gdx.files.internal("target.png"));
        target = gsp.getMainGame().assetManager.get("target.png", Texture.class);
        hp = 100;


        img.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
        img_1.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
        target.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);

        rot = true;
        deltaSled = 0;
        tr = new TowerRotation(direction, direction_tower, position, gsp.getTanksOther().listOpponents);
        targetCoordinat = new Vector2(0, 0);
        deltaSledVec.set(this.getPosition());

    }

    public int getHp() {
        return hp;
    }

    public TowerRotation getTr() {
        return tr;
    }

    public void update(Vector2 directionMovementControll, boolean inTuch) {

        // if (MathUtils.randomBoolean(.005f)) hp = MathUtils.random(0, 80);
        // if(MathUtils.randomBoolean(.05f)) gsp.pc.addPasricalExplosionDeath(position.x, position.y);
        upDateHpHud();
////////////////////////////////////
        gsp.getGameSpace().getLighting().setLasetOn(false);
        if (this.tr.getNomTarget() != null) {
            targetCoordinat = gsp.getTanksOther().getTankForID(this.tr.getNomTarget()).getPosition();
            gsp.getGameSpace().getLighting().setLasetOn(true);
        }


        // System.out.println(targetCoordinat);

///////////////////////////////////////
        tr.update(Gdx.graphics.getDeltaTime());

        if (this.tr.getTargetSize() > 1) gsp.getController().setButtonChangingOpponent(true);
        else gsp.getController().setButtonChangingOpponent(false);
        //if (MathUtils.randomBoolean(.05f)) hp--;
        generatorSmoke();

        getTargetCamera();
        getTargetCamera(directionMovementControll);
        //  targetDetectionTower();
        if (!inTuch) return;
        raz = Math.abs(direction.angleDeg() - directionMovementControll.angleDeg());


        moveMainTank(directionMovementControll);
        //System.out.println(direction.clamp(SPEED,SPEED).len());
        generatorSled();


    }

    private void generatorSled() {
        if (MathUtils.randomBoolean(.4f))
            if (Vector2.dst2(position.x, position.y, deltaSledVec.x, deltaSledVec.y) > 200) {
                deltaSledVec.set(getPosition());
                gsp.getGameSpace().addSled(position.x + direction.x * 3, position.y + direction.y * 3, direction.angleDeg());
            }
    }

    private void moveMainTank(Vector2 directionMovementControll) { // движние основного танка
        //System.out.println(direction.len());
        if (MathUtils.sin(gsp.getTimeInGame() * 50) >= 0)
            img = gsp.getMainGame().getAssetManager().get("trb1.png");
        else img = gsp.getMainGame().getAssetManager().get("trb2.png");
        if (raz > 20)
            if ((directionMovementControll.angleDeg(direction) > 180)) {
                this.direction.setAngleDeg(direction.angleDeg() - Gdx.graphics.getDeltaTime() * SPEED_ROTATION);
            } else {
                this.direction.setAngleDeg(direction.angleDeg() + Gdx.graphics.getDeltaTime() * SPEED_ROTATION);
            }

        if ((gsp.getTanksOther().isCollisionsTanks(position.cpy().add(direction.cpy().nor().scl(3)))) != null) {  // танки другие
        } else if ((gsp.getTanksOther().isCollisionsTanks(position.cpy().add(direction.cpy().nor().scl(6)))) != null) {  // танки другие
        } else
            ////////////////////////////
            if (!gsp.getGameSpace().getMainCollision().isCollisionsRectangle(            // квараты
                    getPosition().cpy().sub(getDirection().cpy().nor().scl(-5))
            )) {

                this.position.add(direction.cpy().rotateDeg(180).clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime() / 10));

                if (gsp.getGameSpace().getMainCollision().isCollisionsRectangle(            // квараты + 45
                        getPosition().cpy().sub(getDirection().cpy().nor().scl(-5).rotateDeg(35))
                )) {
                    this.position.add(direction.cpy().rotateDeg(35).clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime() / 2));
                } else if (gsp.getGameSpace().getMainCollision().isCollisionsRectangle(            // квараты + 45
                        getPosition().cpy().sub(getDirection().cpy().nor().scl(-5).rotateDeg(-35))
                )) {
                    this.position.add(direction.cpy().rotateDeg(-35).clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime() / 2));
                }
                //   System.out.println("collision");
            } else if (!gsp.getGameSpace().getMainCollision().isCircleCircle(position)) { // КРУГИ
                this.position.add(direction.cpy().rotateDeg(180).clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime() / 10));
                if (gsp.getGameSpace().getMainCollision().isCircleCircle(            // КРУГИ + 45
                        getPosition().cpy().sub(getDirection().cpy().nor().scl(-5).rotateDeg(90))
                )) {
                    this.position.add(direction.cpy().rotateDeg(35).clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime() / 2));
                } else if (gsp.getGameSpace().getMainCollision().isCircleCircle(            // КРУГИ - 45
                        getPosition().cpy().sub(getDirection().cpy().nor().scl(-5).rotateDeg(-90))
                )) {
                    this.position.add(direction.cpy().rotateDeg(-35).clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime() / 2));
                }

                // position.cpy().add(direction.clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime() * -5));
                // this.direction.scl(-1);
            } else if (gsp.getGameSpace().checkMapBorders(position.cpy().add(direction.clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime())))) // границы карты
                this.position.add(direction.clamp(SPEED, SPEED).scl(Gdx.graphics.getDeltaTime()));
    }

    private void generatorSmoke() { // генератор Дыма для танка
        if (hp < 70) {
            //  System.out.println(hp);
            if (MathUtils.randomBoolean((100 - hp) / 380f))
                gsp.pc.addParticalsSmoke(1, position.x, position.y, hp);
        } else if (hp < 50) {
            if (MathUtils.randomBoolean(.05f))
                gsp.pc.addParticalsSmoke(1, position.x, position.y, hp);
            // if(hp < 15) if(MathUtils.randomBoolean(.05f)) gsp.pc.addPasricalExplosionDeath(position.x, position.y);
        }

//        if (hp < 30) {
//            System.out.println(hp);
//            if (MathUtils.randomBoolean((100 - hp) / 1680f))
//                gsp.pc.addPasricalExplosion(5, position.x + 16, position.y + 16);
//        }

    }

    private void upDateHpHud() {
        gsp.getController().setHPHeroTank(this.hp);
    }


    public void renderTank(Vector2 directionMovement, boolean inTouch) {
        if (tr.isRotation()) gsp.getAudioEngine().pleySoundOfTower();
        else gsp.getAudioEngine().stopSoundOfTower(); // звук башни


        tr.setRotation(false);
        update(directionMovement, inTouch);

        command = 1;

     //   if (MathUtils.randomBoolean(0.2f)) command = MathUtils.random(0, 3);
        if (command == gsp.getTanksOther().RED_COMMAND) {
            sb.draw(img,
                    position.x - 20, position.y - 20,
                    20, 20,
                    40, 40,
                    1, 1,
                    direction.angleDeg() + 180,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    true, false);

            sb.draw(img_1,
                    position.x - 20, position.y - 20,
                    20, 20,
                    40, 40,
                    1, 1,
                    direction_tower.angleDeg() + 180,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    false, false);
        } else if (command == gsp.getTanksOther().BLUE_COMMAND) {
            sb.draw(imgr,
                    position.x - 20, position.y - 20,
                    20, 20,
                    40, 40,
                    1, 1,
                    direction.angleDeg() + 180,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    true, false);

            sb.draw(img_1r,
                    position.x - 20, position.y - 20,
                    20, 20,
                    40, 40,
                    1, 1,
                    direction_tower.angleDeg() + 180,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    false, false);
        } else {
            sb.setColor(0, 0, 0, 1);
            sb.draw(imgr,
                    position.x - 20, position.y - 20,
                    20, 20,
                    40, 40,
                    1, 1,
                    direction.angleDeg() + 180,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    true, false);

            sb.draw(img_1,
                    position.x - 20, position.y - 20,
                    20, 20,
                    40, 40,
                    1, 1,
                    direction_tower.angleDeg() + 180,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    false, false);
            sb.setColor(1, 1, 1, 1);
        }
        // System.out.println(this.tr.getNomTarget());

        if (tr.getNomTarget() != null) {
            sb.setColor(1, 1, 1, .4f);
            sb.draw(target,
                    targetCoordinat.x - 35, targetCoordinat.y - 35,
                    35, 35,
                    70, 70,
                    1.1f + (MathUtils.cos(gsp.getTimeInGame() * 5) / 5), 1.1f + (MathUtils.cos(gsp.getTimeInGame() * 5) / 5),
                    (Interpolation.swing.apply(MathUtils.sin(gsp.getTimeInGame() / 4)) + 1) * 45,
                    1, 1,
                    80, 80,
                    false, false);
            sb.setColor(1, 1, 1, 1);
        }
    }

    public float returnAngle(Vector2 position) {
        return position.cpy().sub(this.position).angleDeg();
    }

    public GamePlayScreen getGsp() {
        return gsp;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public Vector2 getDirection_tower() {
        return direction_tower;
    }

    public void getTargetCamera() {
        //directionMovementControll
        Vector2 temp = position.cpy().sub(direction_tower.cpy().scl(-160));
        gsp.getCameraGame().moveFloatCameraToPoint(temp.x, temp.y, (int) 3.5); //камера перемещение
//        gsp.getGameSpace().getLighting().setPointL(position.x, position.y); //освещение перемещение
//        gsp.getGameSpace().getLighting().setCone(position.x, position.y, direction.angleDeg());
        gsp.getGameSpace().getLighting().setConeTower(position.x, position.y, direction_tower.angleDeg());
    }

    public void getTargetCamera(Vector2 t) {
        //directionMovementControll
        Vector2 temp = position.cpy().sub(t.cpy().nor().scl(-160));
        gsp.getCameraGame().moveFloatCameraToPoint(temp.x, temp.y, (int) 3.5); //камера перемещение
//        gsp.getGameSpace().getLighting().setPointL(position.x, position.y); //освещение перемещение
//        gsp.getGameSpace().getLighting().setCone(position.x, position.y, direction.angleDeg());
        gsp.getGameSpace().getLighting().setConeTower(position.x, position.y, direction_tower.angleDeg());
    }

    public boolean redyToAttack() {
        return getTr().isRedyToAttac();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
