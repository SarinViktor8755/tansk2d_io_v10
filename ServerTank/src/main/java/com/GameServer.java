package main.java.com;

import static com.mygdx.tanks2d.ClientNetWork.Network.register;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.tanks2d.ClientNetWork.Network;


import java.io.IOException;

import main.java.com.Units.ListPlayers;

public class GameServer {

    Server server;

    public final long timerTact = 50; //ms поток таймер циклов , рассылвает координаты ботов ))
    public final long timerLogic = (long) (this.timerTact / 3f); // таймер поведения ботов

    Network.PleyerPositionNom ppn = new Network.PleyerPositionNom();
    long previousStepTime; // шаг для дельты
    ListPlayers lp = new ListPlayers(this);

    public GameServer(String[] args, ServerLauncher serverLauncher) throws IOException {
        server = new Server();
        register(server);
        server.bind(Network.tcpPort, Network.udpPort);
        server.start();

        previousStepTime = System.currentTimeMillis();
        server.addListener(new Listener() {
                               @Override
                               public void connected(Connection connection) {
                                   lp.addPlayer(connection.getID());
                               }

                               @Override
                               public void disconnected(Connection connection) {
                                   super.disconnected(connection);
                               }

                               @Override
                               public void received(Connection connection, Object object) {
                                   if (object instanceof Network.PleyerPosition) {
                                       lp.updatePosition(connection.getID(),(Network.PleyerPosition) object);
                                       if(MathUtils.randomBoolean())lp.sendToAllPlayerPosition(connection.getID(),(Network.PleyerPosition) object);
                                       return;
                                   }


                               }
                           }
        );
    }

    public Server getServer() {
        return server;
    }
}
