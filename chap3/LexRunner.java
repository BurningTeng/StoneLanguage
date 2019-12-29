public class LexRunner {

    public static void main(String[] args) {
        System.out.println("Burning");
        Lexer lex = null;
        Reader reader = null;
        try {
            reader = CDialog.file();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        if (reader == null) {
            System.out.println("You don't select any file");
        } else {
            lex = new Lexer(reader);
            Token t = null;
            while ((t = lex.read()) != Token.EOF){
                System.out.println(t.getText());
            }
        }

        
    }

}