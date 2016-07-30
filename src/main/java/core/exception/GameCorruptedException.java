package core.exception;

@SuppressWarnings("serial")
public class GameCorruptedException extends LynxException {

    public GameCorruptedException() {
	super("Internal error : game corrupted. (Check logs to get more details)");
    }

}
