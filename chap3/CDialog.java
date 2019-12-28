

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CDialog extends Reader {

    private  String  buffer = null;
    private  int pos = 0;

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
       if (buffer == null){
           String in  = showDialog();
           if (in == null){
                return -1;
           }else{
                System.out.print(in);
                buffer = in +"\n";
                pos = 0;
           }
       } 
       int length = buffer.length();
       int size = 0;
       //注意这里的len和length
       while (pos <  length && size  < len   ){

            cbuf[off +  size++] = buffer.charAt(pos++);
       }
       if (pos == length){

          buffer = null;
       }
       return size;
        
    }

    @Override
    public void close() throws IOException { }

    public String showDialog(){
            JTextArea  area = new JTextArea(20, 40);
            JScrollPane panel = new JScrollPane(area);
            int  result = JOptionPane.showOptionDialog(null,panel,"input",
             JOptionPane.OK_CANCEL_OPTION,
             JOptionPane.PLAIN_MESSAGE,
             null,null,null );
             if (result == JOptionPane.OK_OPTION){
                return  area.getText();
             }else{

                return null;
             }

    }
    
    public static  Reader  file()   throws  FileNotFoundException{
        JFileChooser chooser = new JFileChooser();
        if  (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            System.out.println(chooser.getSelectedFile().getAbsoluteFile());
            return new BufferedReader(new FileReader(chooser.getSelectedFile()));

        }
        else{
            throw new FileNotFoundException("no file specified");

        }
        

    }

}