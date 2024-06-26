package mapreduce_project2;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Red2 extends Reducer<Text, Text, Text, Text>{
    private Text outKey = new Text();
    private Text outValue = new Text();
    
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        for(Text text:values) {
            sb.append(text+",");
        }
        String line = null;
        if(sb.toString().endsWith(",")) {
            line = sb.substring(0,sb.length()-1);
        }
        
        outKey.set(key);
        outValue.set(line);
        
        context.write(outKey, outValue);
    } 
}