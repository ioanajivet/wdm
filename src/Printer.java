import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


public class Printer {
	
	public void printTuplesNumbers(PatternNode root, TPEStack rootStack, Map<Integer,String> texts) {
		// TODO clean-up
		Map<PatternNode, List<Match>> matchChildren;
		
		System.out.println(printHeader(root));
		
		for(Match m: rootStack.getMatches()){
			if(m.getStack().getPatternNode().isMarked())
				System.out.print(m.getPre() + "\t\t");
			matchChildren = m.getChildren();
			List<PatternNode> patterns = root.getChildren();
			for(int i=0; i<patterns.size(); i++){
				List<Match> childMatches = matchChildren.get(patterns.get(i));
				for(Match mat: childMatches)
					if(mat.getStack().getPatternNode().isMarked())
						System.out.print(mat.getPre() + "\t\t");
			}
			System.out.println();
		}
		
	}

	public void printTuplesText(PatternNode root, TPEStack rootStack, Map<Integer,String> texts) {
		Map<PatternNode, List<Match>> matchChildren;
		System.out.println(printHeader(root));
		
		for(Match m: rootStack.getMatches()){
			if(m.getStack().getPatternNode().isMarked())
				System.out.print(texts.get(m.getPre()) + "\t\t");
			matchChildren = m.getChildren();
			List<PatternNode> patterns = root.getChildren();
			for(int i=0; i<patterns.size(); i++){
				List<Match> childMatches = matchChildren.get(patterns.get(i));
				for(Match mat: childMatches)
					if(mat.getStack().getPatternNode().isMarked())
						System.out.print(texts.get(mat.getPre()) + "\t\t");
			}
			System.out.println();
		}
	}
	
	public void printTuplesTextInFile(PatternNode root, TPEStack rootStack, Map<Integer,String> texts) {
		Map<PatternNode, List<Match>> matchChildren;
		
		try {
			File file = new File("results/results_text.txt");

			if (!file.exists()) {
				file.createNewFile();
			}
 
			PrintWriter bw = new PrintWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(printHeader(root) + "\n");
			
			for(Match m: rootStack.getMatches()){
				if(m.getStack().getPatternNode().isMarked())
					if(texts.get(m.getPre()) == null)
						bw.write("----\t\t");
					else
						bw.write(texts.get(m.getPre()) + "\t\t");
				matchChildren = m.getChildren();
				List<PatternNode> patterns = root.getChildren();
				for(int i=0; i<patterns.size(); i++){
					List<Match> childMatches = matchChildren.get(patterns.get(i));
					for(Match mat: childMatches)
						if(mat.getStack().getPatternNode().isMarked())
							if(texts.get(mat.getPre()) == null)
								bw.write("----\t\t");
							else
								bw.write(texts.get(mat.getPre()) + "\t\t");
				}
				bw.write("\n");
			}
			
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void printTuplesNumbersInFile(PatternNode root, TPEStack rootStack, Map<Integer,String> texts) {
		Map<PatternNode, List<Match>> matchChildren;
		
		try {
			File file = new File("results/results_numbers.txt");
 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(printHeader(root) + "\n");
			
			for(Match m: rootStack.getMatches()){
				if(m.getStack().getPatternNode().isMarked())
					bw.write(m.getPre() + "\t\t\t");
				matchChildren = m.getChildren();
				List<PatternNode> patterns = root.getChildren();
				for(int i=0; i<patterns.size(); i++){
					List<Match> childMatches = matchChildren.get(patterns.get(i));
					for(Match mat: childMatches)
						if(mat.getStack().getPatternNode().isMarked())
							bw.write(mat.getPre() + "\t\t\t");
				}
				bw.write("\n");
			}
			
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	private String printHeader(PatternNode node){
		String header = "";
		if(node.isMarked())
			header += node.getName() + "\t\t";
		for(PatternNode child: node.getChildren())
			header += printHeader(child);
		return header;
	}
}
