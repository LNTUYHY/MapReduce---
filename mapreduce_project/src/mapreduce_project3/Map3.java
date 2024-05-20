package mapreduce_project3;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map3 extends Mapper<LongWritable, Text, Text, Text>{
    private Text outKey = new Text();
    private Text outValue =new Text();
    
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
            throws IOException, InterruptedException {
        String[] rowAndLine = value.toString().split("\t");
        
        String row = rowAndLine[0];
        String[] lines = rowAndLine[1].split(",");

        for(int i=0;i<lines.length;i++) {
            String column = lines[i].split("_")[0];
            String valueStr = lines[i].split("_")[1];
            //key:column value:rownumber_value
            outKey.set(column);
            outValue.set(row+"_"+valueStr);
            context.write(outKey, outValue);
        }
    }
}