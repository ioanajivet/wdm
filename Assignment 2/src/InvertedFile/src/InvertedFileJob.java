

/**
 * Example of a simple MapReduce job: it reads 
 * file containing authors and publications, and 
 * produce each author with her publication count.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * The follozing class implements the Job submission, based on 
 * the Mapper (AuthorsMapper) and the Reducer (CountReducer)
 */
public class InvertedFileJob {

  public static void main(String[] args) throws Exception {

	/*
	 * Load the Hadoop configuration. IMPORTANT: the 
	 * $HADOOP_HOME/conf directory must be in the CLASSPATH
	 */
	Configuration conf = new Configuration();
	//conf.set("myHadoop.jar","/home/ioana/local/myHadoop.jar");
	/* We expect two arguments */
	conf.set("xmlinput.start", "<summary>");
	conf.set("xmlinput.end", "</summary>");

	if (args.length != 3) {
	  System.err.println("Usage: InvertedFileJob <in> <out>");
	  System.exit(2);
	}

	/* Allright, define and submit the job */
	Job job = new Job(conf, "InvertedFile count");
	job.setJarByClass(InvertedFileJob.class);
	
	/* Define the Mapper and the Reducer */
	job.setMapperClass(InvertedFile.InvertedFileMapper.class);
	job.setReducerClass(InvertedFile.InvertedFileReducer.class);

	/* Define the input type */
	job.setInputFormatClass(XmlInputFormat.class);
	//job.setInputFormatClass(InputFormat.class);
	/* Define the output type */
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);

	/* Set the input and the output */
	FileInputFormat.addInputPath(job, new Path(args[1]));
	FileOutputFormat.setOutputPath(job, new Path(args[2]));

	/* Do it! */
	System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
