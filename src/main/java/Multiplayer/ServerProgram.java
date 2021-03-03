package Multiplayer;

/*import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Listener;*/

import java.util.ArrayList;

public class ServerProgram /*extends Listener*/{
    /*static Server server;
    static int udpPort = 27960, tcpPort = 27960;
    static boolean messageReceived = false;


    public static void main(String[] args) throws Exception{
        server = new Server();
        server.getKryo().register(PacketMessage.class);
        server.getKryo().register(java.util.ArrayList.class);
        server.getKryo().register(String.class);
        server.bind(tcpPort,udpPort);
        server.start();
        server.addListener(new ServerProgram());

        while (!messageReceived){
            //Thread.sleep(1000);
        }
    }

    public void connected(Connection c){
        System.out.println("Received a connection from " + c.getRemoteAddressTCP().getHostName());
        PacketMessage packetMessage = new PacketMessage();
        packetMessage.message = messageToSend();
        c.sendTCP(packetMessage);
    }

    public void received(Connection c, Object p){
        if(p instanceof PacketMessage){
            PacketMessage packet = (PacketMessage) p;
            System.out.println("Received a message from the client: " + packet.message);

            messageReceived = true;
        }
    }

    public void disconnected(Connection c){
        System.out.println("A client disconnected");
    }

    public ArrayList<String> messageToSend() {
        ArrayList<String> listToSend = new ArrayList<>();
        listToSend.add("H");
        listToSend.add("E");
        listToSend.add("Y");
        return listToSend;
    }*/
}

