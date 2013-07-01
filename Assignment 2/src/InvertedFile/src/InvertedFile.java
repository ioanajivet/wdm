

/**
 * Import the necessary Java packages
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;


public class InvertedFile {

  /**
   * The Mapper class -- it takes the text between <summary>...</summary> 
   * from the input file and tokenizes the words. The output value contains 
   * the pair: <term,filename>.
   */
  public static class InvertedFileMapper extends
	  Mapper<Object, Text, Text, Text> {

	private Text term = new Text();
	private Text filename = new Text();
	
	public void map(Object key, Text value, Context context)
		throws IOException, InterruptedException {

		FileSplit fileSplit = (FileSplit)context.getInputSplit(); 
		filename.set(fileSplit.getPath().getName());
		
		System.out.println("blablablbla");
		
		//clean up summary
		String summary = value.toString();
		summary = summary.substring(summary.indexOf('>')+1, summary.lastIndexOf('<'));
		summary = summary.replaceAll("[^a-zA-Z0-9 \t\n]", "");
		System.out.println(summary);
		
		StringTokenizer tokenizer = new StringTokenizer(summary);
		System.out.println("Found tokens: " + tokenizer.countTokens());
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			System.err.println("Token: " + token);
		     term.set(token);
		 	 context.write(term, filename);
		}
	}
  }

  /**
   * The Reducer class -- receives pairs (term, <list of filenames>) and
   * sums up the values for each filename independently.
   */
  public static class InvertedFileReducer extends
	  Reducer<Text, Text, Text, Text> {
	private Text result = new Text();
	private HashMap<String, Integer> documents = new HashMap<String,Integer>();
	
	public void reduce(Text key, Iterable<Text> values, 
			Context context)
		throws IOException, InterruptedException {
	  
	  /* Iterate on the list to compute the count */
	  int totalCount = 0;
	  for (Text val : values) {
		totalCount++;
		addDocument(val.toString());
	  }
	  
	  double noDoc = context.getConfiguration().getInt("mapreduce.input.num.files", 1);
	  double idf = Math.log10(noDoc/documents.size()); 
	  
	  String resultText = totalCount + " - idf: " + idf;
	  for(String file: documents.keySet())
		  resultText += "\n\t  " + file + " (" + documents.get(file) + ") ";
	  result.set(resultText);
	  
	  documents.clear();
	  
	  context.write(key, result);
	}
	
	public void addDocument(String filename){
		if(documents.containsKey(filename))
			documents.put(filename, documents.get(filename)+1);
		else
			documents.put(filename, 1);
	}
  }
}
