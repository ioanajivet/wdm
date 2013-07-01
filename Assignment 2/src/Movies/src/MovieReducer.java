import java.io.IOException;

import org.apache.hadoop.io.*;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class MovieReducer extends Reducer<IntWritable, Text, NullWritable, Text> {
	@SuppressWarnings("rawtypes")
	private MultipleOutputs mos;

	public void setup(Context context) {
		mos = new MultipleOutputs(context);
	}
	 
	@SuppressWarnings("unchecked")
	public void reduce(IntWritable key, Iterable<Text> values,	Context context) throws IOException, InterruptedException {
		/* Iterate on the list to compute the count */
		for (Text redValue : values){
            if (key.get() == 1){
                mos.write("TitleAndActor", NullWritable.get(), redValue);
            } 
            if (key.get() == 2){
                mos.write("DirAndTitle", NullWritable.get(), redValue);
            }
        }
	}
	
	public void cleanup(Context context) throws IOException {
		 try {
			mos.close();
		} catch (InterruptedException e) {
			System.err.println("Interrupted Exception");
			e.printStackTrace();
		}
	}
}
