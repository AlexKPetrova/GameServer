
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameThreadServer extends Thread {
    private PrintWriter outputChanel;
    private BufferedReader inputChanel;
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
                    outputChanel = new PrintWriter(clientSocket.getOutputStream(), true);
                    inputChanel = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    String inputLine;
                    while ((inputLine = inputChanel.readLine()) != null) {
                        String[] split = inputLine.split(";");
                        Command command = Command.valueOf(split[0]);

                        switch (command) {

                            case NEWGAME:
                                serverAi.startNewGame();
                                sendMessageToClient(command, serverAi);
                                break;
                            case NEXTTURN:
                                command.setCommand("nextturn");
                                sendMessageToClient(command, serverAi);
                                break;
                        }

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToClient(Command command, ServerAi serverAi) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutputStream.writeObject(new GameData(command, serverAi.getField(), 3, "do your move"));
        //objectOutputStream.close();
    }
}