package mapreduce_project1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Red1 extends Reducer<Text, Text, Text, Text>{
    private Text outKey = new Text();
    private Text outValue = new Text();
    
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
    	String itemID = key.toString();
    	
    	Map<String,Integer> map = new HashMap<String,Integer>();
    	
    	for(Text value:values){
    		String userID = value.toString().split("_")[0];
    		String score = value.toString().split("_")[1];
    		if(map.get(userID) == null){
    			map.put(userID, Integer.valueOf(score));
    		}else{
    			Integer preScore = map.get(userID);
    			map.put(userID, preScore + Integer.valueOf(score));
    		}
    	}
    	
        StringBuilder sBuilder = new StringBuilder();
        for(Map.Entry<String,Integer> entry : map.entrySet()) {
            String userID = entry.getKey();
            String score = String.valueOf(entry.getValue());
            sBuilder.append(userID + "_" + score + ",");
        }
        String line = null;
        if(sBuilder.toString().endsWith(",")) {
            line = sBuilder.substring(0,sBuilder.length()-1);
        }
        
        outKey.set(key);
        outValue.set(line);
        
        context.write(outKey, outValue);
    }
}