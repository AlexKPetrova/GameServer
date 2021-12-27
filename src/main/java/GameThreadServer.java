
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameThreadServer extends Thread{
    private PrintWriter outputChanel;
    private BufferedReader inputChanel;
    private Socket clientSocket;
    private final ServerSocket serverSocket;

    public GameThreadServer(ServerSocket serverSocket) {

        this.serverSocket = serverSocket;

    }

    @Override
    public void run() {
        try {
            clientSocket = serverSocket.accept();
            outputChanel = new PrintWriter(clientSocket.getOutputStream(), true);
            inputChanel = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            ServerAi serverAi = new ServerAi();
            while ((inputLine = inputChanel.readLine()) != null){
                String[] split = inputLine.split(";");
                Command command = Command.valueOf(split[0]);

                switch (command){

                    case NEWGAME:
                        serverAi.startNewGame();

                        //Кусок с сериализацией

//                        String s = "";
//                        ObjectOutputStream objectOutputStream = new ObjectOutputStream();
//                        objectOutputStream.writeObject(new GameData(command.setCommand("nextturn",);));





                        //конец куска с сериализацией

                        sendMessageToClient("NEXTTURN;" + serverAi.getDataForSending() + ";3; do your move");
                        break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessageToClient(String msg){
        outputChanel.println(msg);
    }
}