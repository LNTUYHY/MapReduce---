package mapreduce_project1;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Run1 {
    private static String inPath = "/user/hadoop/user_matrix.txt";
    
    private static String outPath = "/user/hadoop/output/Tuser_matrix.txt";
    
    private static String hdfs ="hdfs://localhost:9000";
    
    public static int run() {
        try {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", hdfs);
            Job job = Job.getInstance(conf,"step1");
            
            job.setJarByClass(Run1.class);
            job.setMapperClass(Map1.class);
            job.setReducerClass(Red1.class);
            
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
            
            return job.waitForCompletion(true)?1:-1;
        
        }catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
//    public static void main(String[] args) {
//        int result = -1;
//        result = new Run1().run();
//        if(result==1) {
//            System.out.println("step1 success...");
//        }else if(result==-1) {
//            System.out.println("step1 failed...");
//        }
//    }
}