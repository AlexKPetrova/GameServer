import java.util.Arrays;
import java.util.Random;

public class ServerAi {

    private int[][] field;
    private int number = 0;
    private Random random = new Random(System.currentTimeMillis());

    public ServerAi() {
        this.field = new int[3][3];
        for (int i = 0; i < field.length; i++) {
            int[] row = new int[3];
            Arrays.fill(row, 0);
            field[i] = row;
        }
    }

    public void startNewGame() {
        doTurn(1);
    }


    public boolean doTurn(int nextTurn) {

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == 0){
                    field[i][j] = -1;
                    return true;
                }
            }
        }
        return false;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public int getNumber() {
        return number;
    }

    public Random getRandom() {
        return random;
    }
}