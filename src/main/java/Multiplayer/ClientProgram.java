package Multiplayer;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;

public class ClientProgram extends Listener {
    static Client client;
    static String ip = "localhost";
    static int tcpPort = 27960, udpPort = 27960;
    static boolean messageReceived = false;

    public static void main(String[] args) throws Exception{
        client = new Client();
        client.getKryo().register(PacketMessage.class);
        client.getKryo().register(java.util.ArrayList.class);
        client.getKryo().register(String.class);
        client.start();
        client.connect(5000,ip,tcpPort,udpPort);
        client.addListener(new ClientProgram());

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
            System.out.println("Received a message from the host: " + packet.message);

            messageReceived = true;
        }
    }

    public void disconnected(Connection c){
        System.out.println("The host disconnected");
    }

    public void sendCards(){

    }

    public void cardsRecived(){

    }

    public ArrayList<String> messageToSend() {
        ArrayList<String> listToSend = new ArrayList<>();
        listToSend.add("Y");
        listToSend.add("O");
        listToSend.add("O");
        return listToSend;
    }
}


