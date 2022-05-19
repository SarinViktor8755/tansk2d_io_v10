package com.mygdx.tanks2d.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.tanks2d.AudioEngine.AudioEngine;
import com.mygdx.tanks2d.CameraGame;
import com.mygdx.tanks2d.ClientNetWork.ServiceClient;
import com.mygdx.tanks2d.InputProcessor.InputProcessorPC;
import com.mygdx.tanks2d.Locations.GameSpace;
import com.mygdx.tanks2d.MainGame;
import com.mygdx.tanks2d.ParticleEffect.ParticleCustum;
import com.mygdx.tanks2d.Screens.Controll.Controller;
import com.mygdx.tanks2d.Units.Bullets;
import com.mygdx.tanks2d.Units.Tanks.Tank;
import com.mygdx.tanks2d.Units.Tanks.TanksOther;


public class GamePlayScreen implements Screen {
    private MainGame mainGame;
    private CameraGame cameraGame;
    private SpriteBatch batch;
    private float timeInGame = 0; // время в игре
    private GameSpace gameSpace; // карта_ локация
    private AudioEngine audioEngine;// игравой движок
    private InputProcessorPC inputProcessorPC;
    private Controller controller;
    private Tank tank;
    public Vector2 pos;
    private Bullets bullets;
    public ParticleCustum pc;

    private TanksOther tanksOther;

    public GamePlayScreen(MainGame mainGame) {
        this.mainGame = mainGame;

        this.batch = new SpriteBatch();
        this.timeInGame = 0;
        this.gameSpace = new GameSpace(this,mainGame);
        this.audioEngine = new AudioEngine(this);

        this.tanksOther = new TanksOther(this);

        this.inputProcessorPC = new InputProcessorPC(this);
        Gdx.input.setInputProcessor(inputProcessorPC);
        this.pos = new Vector2(150, 150);
        this.cameraGame = new CameraGame(MainGame.WHIDE_SCREEN * 1.2f, MainGame.HIDE_SCREEN * 1.2f, gameSpace.getSizeLocationPixel(), gameSpace.WITH_LOCATION, gameSpace.HEIHT_LOCATION);
        this.cameraGame.jampCameraToPoint(pos.x, pos.y);

//        this.controller = new Controller(this, mainGame.getAssetManager().get("flatDark26.png", Texture.class), mainGame.getAssetManager().get("flatDark261.png", Texture.class));
        this.controller = new Controller(this);

        tank = new Tank(this);

        bullets = new Bullets(this);
        pc = new ParticleCustum(this, mainGame.getAssetManager().get("particle1.png", Texture.class), mainGame.getAssetManager().get("fire.png", Texture.class), mainGame.getAssetManager().get("iron.png", Texture.class),mainGame.getAssetManager().get("de.pack", TextureAtlas.class));

        //getMainGame().getMainClient().getNetworkPacketStock().toSendMyNik();
        //get
    }

    public GamePlayScreen() {
    }

    @Override
    public void show() {
        controller.getDirectionMovement().set(0, 0);
    }

    public AudioEngine getAudioEngine() {
        return audioEngine;
    }

    public void update() {
        getMainGame().updateClien();
        ServiceClient.sendMuCoordinat(tank.getPosition().x, tank.getPosition().y, tank.getTr().getAnTower());  // кинуть на сервер мои координаты

    //////////    mainGame.getMainClient().getNetworkPacketStock();

        //if(MathUtils.randomBoolean(.0005f)) pc.addPasricalExplosionDeath(getTank().getPosition().x,getTank().getPosition().y);

//        if(MathUtils.randomBoolean(.5f)) mainGame.getMainClient().getNetworkPacketStock().toSendMyTokken();
//        if(MathUtils.randomBoolean(.5f)) mainGame.getMainClient().getNetworkPacketStock().toSendMyNik();


        if (controller.isChance()) {
            controller.setChance(false);
            tank.getTr().changeTarget();
        }
        timeInGame = timeInGame + Gdx.graphics.getDeltaTime(); // игрвовое время
        if (controller.isInTuchMove()) audioEngine.pleySoundOfTracks();
        else audioEngine.stopSoundOfTracks();
        pos.add(controller.getDirectionMovement().cpy().scl(Gdx.graphics.getDeltaTime() * 1.5f)); /// движение танка Главного
    }

    public float getTimeInGame() {
        return timeInGame;
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.cameraGame.getCamera().update();
        this.batch.setProjectionMatrix(cameraGame.getCamera().combined);

        this.cameraGame.getCamera().update();

        this.batch.begin();
        this.gameSpace.renderSpace((OrthographicCamera) cameraGame.getCamera());                //рендер пространство
        this.cameraGame.getCamera().update();
        this.getGameSpace().getRadspurens().randerRadspurens(batch);                            // следы от танка
        this.pc.randerGarbage(batch);



        this.tanksOther.updateOtherTank(mainGame.getMainClient().isOnLine()); /// обновление других танков с сервреа (позиция) или локальной зоны
        this.tanksOther.randerOtherTanks(getBatch());      // визуализация других танков
        this.tank.renderTank(controller.getDirectionMovement(), controller.isInTuchMove());     //рендер основного танка

/////////////стрельба

        this.bullets.randerBullets();
        this.pc.render(getBatch());
        if(mainGame.getMainClient().isOnLine())this.startFlashForMainTank(); else startFlashForMainTankSP();                                                           // вспышка из дула и вспышка вокруг танка

/////////////
//        Vector2 smooke = tank.getPosition().cpy().sub(tank.getDirection_tower().cpy().nor().scl(-20 ));
//        batch.setColor(1,1,1,.3f);
//        getBatch().draw(mainGame.getAssetManager().get("badlogic1B.png",Texture.class),smooke.x,smooke.y,45,45);
//        batch.setColor(1,1,1,1);
        //////////////////////////////////////

        this.batch.end();
      //  this.getGameSpace().getLighting().renderLights(cameraGame.getCamera()); временно
        this.controller.draw();
        this.getBatch().setColor(1, 1, 1, 1);

    }

    public TanksOther getTanksOther() {
        return tanksOther;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public void resize(int width, int height) {
        cameraGame.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    public Tank getTank() {
        return tank;
    }

    @Override
    public void resume() {

    }

    public GameSpace getGameSpace() {
        return gameSpace;
    }


    @Override
    public void hide() {

    }

    public Bullets getBullets() {
        return bullets;
    }

    public CameraGame getCameraGame() {
        return cameraGame;
    }

    public void setCameraGame(CameraGame cameraGame) {
        this.cameraGame = cameraGame;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


    public void setPos(int x, int y) {
        this.pos.set(pos.x + x, pos.y + y);
    }

    public MainGame getMainGame() {
        return mainGame;
    }

    public void setMainGame(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    private void startFlashForMainTank() {
        Vector2 smooke = tank.getPosition().cpy().sub(tank.getDirection_tower().cpy().nor().scl(-30));
        if (controller.isAttackButon()) {
            if (!tank.redyToAttack()) return;
            System.out.println("startFlashForMainTank !! Generator new Buulet");
           // this.getMainGame().getMainClient().getNetworkPacketStock().toSendMyShot(smooke.x, smooke.y, tank.getDirection_tower().angleDeg());
        }
    }




    private void startFlashForMainTankSP() {
        Vector2 smooke = tank.getPosition().cpy().sub(tank.getDirection_tower().cpy().nor().scl(-34));
        if (controller.isAttackButon()) {
            if (!tank.redyToAttack()) return;

            bullets.addBullet(smooke,getTank().getDirection_tower().cpy(),1);
            getAudioEngine().pleySoundKickStick();
            pc.addPasricalDeath_little(smooke.x, smooke.y, 2.7f);

            System.out.println("startFlashForMainTank !! Generator new Buulet SP");
            //this.getMainGame().getMainClient().getNetworkPacketStock().toSendMyShot(smooke.x, smooke.y, tank.getDirection_tower().angleDeg());
        }
    }

    public AssetManager getAssetsManagerGame() {
        return this.mainGame.getAssetManager();
    }

}
