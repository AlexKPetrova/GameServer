import java.io.Serializable;
import java.util.Arrays;

public class GameData implements Serializable {

    Command command;

    int[][] field;

    Integer number;

    String message;

    public GameData(Command command, int[][] field, Integer number, String message) {
        this.command = command;
        this.field = field;
        this.number = number;
        this.message = message;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "GameData{" +
                "command=" + command +
                ", field=" + Arrays.toString(field) +
                ", number=" + number +
                ", message='" + message + '\'' +
                '}';
    }
}
