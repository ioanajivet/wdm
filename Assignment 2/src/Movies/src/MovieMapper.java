import java.io.IOException;

import java.util.Map.Entry;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;


public class MovieMapper extends Mapper<Object, Text, IntWritable, Text> {
	private final static IntWritable one = new IntWritable(1);
	private final static IntWritable two = new IntWritable(2);
	
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		String content = value.toString();
		
		MovieParser parser = new MovieParser(content);
		MovieSet movie = parser.parseDocument();
		
		for (Entry<String, Artist> entry : movie.getActors().entrySet()) {
		    String role = entry.getKey();
		    Artist mvalue = entry.getValue();
		    String titleAndActor = movie.getTitle() + "\t" + mvalue.getFirstName()+ ""+ mvalue.getLastName()
		    						+"\t"+ mvalue.getBirthDate() + "\t"+ role;
		 
		    context.write(one,new Text(titleAndActor));
		}
		
		String dirAndTitle = movie.getDirector().getFirstName() + ""+ 
				movie.getDirector().getLastName() +
				"\t" + movie.getTitle() + "\t"+ movie.getYear();
		
		context.write(two, new Text(dirAndTitle));
	} 
}