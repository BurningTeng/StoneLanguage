package stone;

import java.io.IOException;

public class ParseException extends Exception {
<<<<<<< HEAD
    /**
     *
     */
    private static final long serialVersionUID = 1580580457800772223L;

    public ParseException(Token t) {
=======
    public ParseException(Token t){
>>>>>>> ae368662d71bc59bdcee2048b9733896d3f03066
        this("",t);
    }

    public ParseException(String msg, Token t) {
        super("syntax around"+ location(t) + msg);
    }

    private static String location(Token t) {
        String loc = "";
        if (t == Token.EOF){
            loc = "last string ";
        }
        else{
            loc = t.getText()+" "+t.getLineNumber();
        }
        return loc;
    }
    public ParseException(IOException e){
        super(e);
    }

	public ParseException(String msg) {
        super(msg);
	}
}
