package net.thenextlvl.nbt.serialization;

public class ParserException extends IllegalArgumentException {
    public ParserException() {
    }

    public ParserException(String s) {
        super(s);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }
}
