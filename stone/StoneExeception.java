public class StoneExeception extends  RuntimeException{

    private static final long serialVersionUID = 8860582360359726018L;

    public StoneExeception(String m, ASTree t) {
        super(m + " " +t.location());
    }
	public StoneExeception(String m) {
        super(m);
    }
 

}
