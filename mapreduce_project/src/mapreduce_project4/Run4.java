package mapreduce_project4;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Run4 {
    private static String inPath = "/user/hadoop/output/step2_output.txt";
    private static String outPath = "/user/hadoop/output/step4_output.txt";
    private static String cache = "/user/hadoop/output/step3_output.txt/part-r-00000";
    private static String hdfs ="hdfs://localhost:9000";
       
    public int run() throws URISyntaxException {
        try {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", hdfs);
            Job job = Job.getInstance(conf,"step4");
            
            job.addCacheArchive(new URI(cache+"#itemsource2"));
            
            job.setJarByClass(Run4.class);
            job.setMapperClass(Map4.class);
            job.setReducerClass(Red4.class);
            
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            
            FileSystem fs = FileSystem.get(conf);
            Path inputPath = new Path(inPath);
            if(fs.exists(inputPath)) {
                FileInputFormat.addInputPath(job, inputPath);
            }
            
            Path outputPath = new Path(outPath);
            fs.delete(outputPath,true);
            
            FileOutputFormat.setOutputPath(job, outputPath);
            System.out.println("111111...");
            return job.waitForCompletion(true)?1:-1;
        
        } catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
        return -1;                                                      
    }
//    public static void main(String[] args) {
//        try {
//            int result=-1;
//            result = new Run4().run();
//        
//            if(result == 1) {
//                System.out.println("step4 success...");
//            }
//            else if(result == -1){
//                System.out.println("step4 failed...");
//            }
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
}