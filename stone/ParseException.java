import java.io.IOException;

public class ParseException extends Exception {
    public ParseException(Token t){
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
