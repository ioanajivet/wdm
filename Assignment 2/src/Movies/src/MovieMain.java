


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class MovieMain {
	
public static void main(String[] args) throws Exception {
/*
* Load the Hadoop configuration. IMPORTANT: the
* $HADOOP_HOME/conf directory must be in the CLASSPATH
*/
	
	Configuration conf = new Configuration();
	conf.set("xmlinput.start", "<movie>");
	conf.set("xmlinput.end", "</movie>");
	conf.set("io.serializations","org.apache.hadoop.io.serializer.JavaSerialization,org.apache.hadoop.io.serializer.WritableSerialization");

	/* We expect two arguments */
	if (args.length != 3) {
	System.err.println("Usage: MovieJob <in> <out>");
	System.exit(2);
	}
	/* Allright, define and submit the job */
	Job job = new Job(conf, "MovieMain");
	job.setJarByClass(MovieMain.class);
	
	/* Define the Mapper and the Reducer */
	job.setMapperClass(MovieMapper.class);
	job.setReducerClass(MovieReducer.class);
	
    job.setInputFormatClass(XmlInputFormat.class);
	
	/* Define the output type */
	job.setOutputKeyClass(IntWritable.class);
	job.setOutputValueClass(Text.class);
    
	/* Set the input and the output */
	Path inputPath = new Path(args[1]);
	Path outputPath = new Path(args[2]);
	FileInputFormat.addInputPath(job, inputPath);
	FileOutputFormat.setOutputPath(job, outputPath);
	
	/*The MultipleOutputs class simplifies writing to additional outputs other than the job
	 *default output via the OutputCollector passed to the map() and reduce() methods of the 
	 *Mapper and Reducer implementations.
	 */
	
	MultipleOutputs.addNamedOutput(job, "TitleAndActor", TextOutputFormat.class, NullWritable.class, 
			Text.class);
	/*Adds a named output for the job. */
    MultipleOutputs.addNamedOutput(job, "DirAndTitle", TextOutputFormat.class, NullWritable.class, 
    		Text.class);
    
    FileSystem hdfs = FileSystem.get(outputPath.toUri(), conf);
    if (hdfs.exists(outputPath)) {
        hdfs.delete(outputPath, true);
    }

	try {

		job.waitForCompletion(true);

		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
}