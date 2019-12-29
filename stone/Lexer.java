import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

	public static String regexPat = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
			+ "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\|\\p{Punct})?";
	// public static String regexPat = "([0-9]*)";
	private Pattern pattern = Pattern.compile(regexPat);
	private boolean hasMore = false;
	private ArrayList<Token> queue = new ArrayList<>();
	private LineNumberReader reader;

	public Lexer(Reader reader) {
		hasMore = true;
		//这里需要使用this， 否则reader为null
		this.reader = new LineNumberReader(reader);
	}

	// 这个是仅仅是为了读出单词 所以都去之后删除
	public Token read() {
		if (fillQueue(0)) {
			return queue.remove(0);
		}
		return Token.EOF;
	}

	// 这个方法类似缓存的方法 保存下来，方便后续的语法分析
	public Token peek(int i) {
		// Token token = null;
		if (fillQueue(i)) {
			return queue.get(i);
		}
		return Token.EOF;

	}

	public boolean fillQueue(int i) {
		if (i >= queue.size()) {
			if (hasMore) {
				readLine();
			} else {
				return false;
			}

		}
		return true;
	}

	// 匹配结果
	public void readLine() {
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Matcher match = pattern.matcher(line);
		int lineNo = reader.getLineNumber();
		// Sets the transparency of region bounds for this matcher
		// Sets the anchoring of region bounds for this matcher.
		match.useTransparentBounds(true).useAnchoringBounds(false);
		int pos = 0;
		int len = line.length();
		while (pos < len) {
			match.region(pos, len);
			if (match.lookingAt()) {
				addToken(match, lineNo);
				pos = match.end();
			} else {
				try {
					throw new ParseException("badToken at line" + lineNo);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}		
		}
		//添加一个文件终结符号
		queue.add(new IdToken(lineNo,Token.EOL));
	}
	
	private void addToken(Matcher match, int lineNo) {
		//第一个分组的结果
		String g1 =  match.group(1);
		if (g1 != null){
			String g2 = match.group(2);
			if (g2 == null){
				String g3 = match.group(3);
				if (g3 != null){
					queue.add(new NumToken(lineNo, Integer.valueOf(g3)));
				}
				else{
					String g4 = match.group(4);
					if (g4 != null){
						queue.add(new StringToken(lineNo,g4));
					}
					else{
						queue.add(new IdToken(lineNo,g1));
					}
				}

			}
		}
	}

	// 整形字面量
	protected  class  NumToken  extends  Token{
		private  int value;
		protected  NumToken(int line,int val) {
			super(line);
			this.value = val;
		}
		@Override
		public boolean   isNumber() {return true;}
		@Override
		public   String getText(){return String.valueOf(value);}
		@Override
		public  int    getLineNumber(){return value; }
	}
	//标识符
	protected   class IdToken extends   Token{
		private  String identifier;
		protected  IdToken(int  line,String  id){
			super(line);
			this.identifier = id;
		}
		@Override
		public   boolean  isIdentifier(){return  true;}
		@Override
		public   String   getText(){return  identifier;}
	}
	//字符串字面量
	protected   class   StringToken   extends  Token{
		private String  str;
		protected   StringToken(int line,String string){
			super(line);
			str = string;
		}
		@Override
		public  boolean  isString(){return  true;}
		@Override
		public   String  getText(){return  str;}
	}
}
