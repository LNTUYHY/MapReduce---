package mapreduce_project4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map4 extends Mapper<LongWritable, Text, Text, Text>{
    private Text outKey = new Text();
    private Text outValue =new Text();
    private DecimalFormat df = new DecimalFormat("0.00");
    private List<String> cacheList = new ArrayList<String>();
    
    protected void setup(Context context)
            throws IOException, InterruptedException {
        super.setup(context);
        FileReader fr = new FileReader("itemsource2");
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while((line=br.readLine())!=null) {
            cacheList.add(line);
        }
        fr.close();
        br.close();
    }

    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
            throws IOException, InterruptedException {
        String row_matrix1 = value.toString().split("\t")[0];
        String[] column_value_array_matrix1 = value.toString().split("\t")[1].split(",");
         
        for(String line:cacheList) {
            String row_matrix2 = line.toString().split("\t")[0];
            String[] column_value_array_matrix2 = line.toString().split("\t")[1].split(",");
            
            double numberator = 0;
            for(String column_value_matrix1:column_value_array_matrix1) {
                String column_matrix1 = column_value_matrix1.split("_")[0];
                String value_matrix1 = column_value_matrix1.split("_")[1];
                
                for(String column_value_matrix2:column_value_array_matrix2) {
                    if(column_value_matrix2.startsWith(column_matrix1 + "_")) {
                        String value_matrix2 = column_value_matrix2.split("_")[1];
                        numberator += Double.valueOf(value_matrix1) *Integer.valueOf(value_matrix2); 
                    }
                }
            }

            outKey.set(row_matrix1);
            outValue.set(row_matrix2+"_"+df.format(numberator));
            context.write(outKey, outValue);
        }
    }
}
