package mapreduce_project1;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map1 extends Mapper<LongWritable, Text, Text, Text>{
    private Text outKey = new Text();
    private Text outValue =new Text();
   
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
            throws IOException, InterruptedException {
        
        //matrix row number
        String[] values = value.toString().split(",");
        String userID = values[0];
        String itemID = values[1];
        String score = values[2];
        
        outKey.set(itemID);
        outValue.set(userID + "_" + score);
        
        context.write(outKey, outValue);
    }
}

