package stone;

public class StoneExeception extends  RuntimeException{
    public StoneExeception(String m, ASTree t) {
        super(m + " " +t.location());
    }
	public StoneExeception(String m) {
        super(m);
    }
 

}
