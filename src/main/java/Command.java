import java.io.Serializable;

public enum Command implements Serializable {

    NEWGAME("newgame"),
    NEXTTURN("nextturn"),
    WIN("win"),
    LOSE("lose");

    Command(String command) {
        this.command = command;
    }

    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}