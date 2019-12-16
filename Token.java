
package  stone;
public   abstract     class  Token{
    public  static final Token EOF = new Token(-1){};   //end of fine
    public  static   final String  EOL = "\\n" ;//end of line
    private  int lineNumber;

    public   Token(int line){

        lineNumber = line;
    }

    public   int  getLineNumber() {return  lineNumber;}
    public boolean   isIdentifier() {return false;}
    public boolean   isNumber() {return false;}
    public boolean  isString() {return false;}
    public int getNumber() {throw new StoneExeception("not number token");}
    public String  getText() {return "";}
}     