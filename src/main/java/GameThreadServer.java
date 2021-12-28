
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameThreadServer extends Thread {

    private Socket clientSocket;
    private final ServerSocket serverSocket;

    public GameThreadServer(ServerSocket serverSocket) {

        this.serverSocket = serverSocket;

    }

    @Override
    public void run() {
        while (true) {
            try {

                clientSocket = serverSocket.accept();

                ServerAi serverAi = new ServerAi();

                while (true) {

                    ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                    GameData gameData = (GameData) objectInputStream.readObject();

                    Command command = gameData.getCommand();

                    switch (command) {

                        case NEWGAME:
                            serverAi.startNewGame();
                            sendMessageToClient(command, serverAi);
                            break;
                        case NEXTTURN:
                            sendMessageToClient(command, serverAi);
                            break;
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToClient(Command command, ServerAi serverAi) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutputStream.writeObject(new GameData(command, serverAi.getField(), 3, "do your move"));
    }
}