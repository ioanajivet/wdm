import java.util.List;
import java.util.Map;


public class Match {
	private int pre;
	private int state;	//open = 1 or closed = 0
	private Match parent;
	private Map <PatternNode, List<Match>> children;
	private TPEStack st;
	
	public Match() {
		
	}
	
	public Match(int currentPre, Match top, TPEStack s) {
		// TODO Auto-generated constructor stub
	}
	public int getStatus() {
		return state;
	}
	
	public int getPre(){
		return pre;
	}
	
	public Map<PatternNode, List<Match>> getChildren(){
		return children;
	}

	public void close() {
		// TODO Auto-generated method stub
		state = 0;
		
	}
}
