
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
                            gameData.setCommand(Command.NEXTTURN);
                            gameData.setNumber(serverAi.getRandom().nextInt(5) + 1);
                            gameData.setField(serverAi.getField());
                            sendMessageToClient(gameData);
                            break;
                        case NEXTTURN:
                            int nextNumber = serverAi.getRandom().nextInt(5) + 1;
                            gameData.setNumber(nextNumber);
                            checkWinCondition(gameData);
                            if (Command.WIN == gameData.getCommand()) {
                                sendMessageToClient(gameData);
                                break;
                            }
                            serverAi.setField(gameData.getField());
                            serverAi.doTurn(nextNumber);
                            gameData.setField(serverAi.getField());
                            gameData.setNumber(serverAi.getRandom().nextInt(5) + 1);
                            checkWinCondition(gameData);
                            if (Command.WIN == gameData.getCommand()) {
                                gameData.setCommand(Command.LOSE);
                            }
                            sendMessageToClient(gameData);
                            //проверить не конец ли игры, сходть самим, проверить выигрышь, отправить итог
                            break;

                        default:
                            break;

                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToClient(GameData gameData) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutputStream.writeObject(gameData);
    }


    public void checkWinCondition(GameData gameData) {
        boolean isNextTurnPossible = isNextTurnPossible(gameData.getField(), gameData.getNumber());
        if (!isNextTurnPossible) {
            gameData.setCommand(Command.WIN);
        }
    }

    private boolean isNextTurnPossible(int[][] field, Integer number) {
        int count = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if(field[i][j] == 0){
                   count++;
                }
            }
        }
        return count>number;
    }
}