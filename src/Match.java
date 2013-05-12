import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Match {
	private int pre;
	private int state;	//open = 1 or closed = 0
	private Match parent;
	private Map <PatternNode, List<Match>> children = new HashMap<PatternNode, List<Match>>();
	private TPEStack st;
	
	public Match() {
		
	}
	
	public Match(int currentPre, Match top, TPEStack s) {
		// TODO Auto-generated constructor stub
		this.pre = currentPre;
		this.parent = top;
		this.state = 1;	//default state is open
		this.st = s;
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
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof Match))
			return false;
		if (((Match)o).pre == this.pre)
			return true;
		return false;
		
	}

	public void addChildMatch(PatternNode child, Match m) {
		// TODO Auto-generated method stub
		if(children.get(child) == null){
			ArrayList<Match> mat = new ArrayList<Match>();
			mat.add(m);
			children.put(child, mat);
			System.out.println("child added to match");
		}
	}
}
