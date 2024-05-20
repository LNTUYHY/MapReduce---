import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Read {
	public static void main(String[] args) throws IOException {
		String remoteFilePath="/user/hadoop/output/Tuser_matrix.txt/part-r-00000";
    	Configuration conf = new Configuration();
		conf.set("fs.defaultFS","hdfs://localhost:9000");
	    conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");
        FileSystem fs = FileSystem.get(URI.create("hdfs://localhost:9000"),conf);
        Path remotePath = new Path(remoteFilePath);
        Path localPath = new Path("/home/hadoop/recommend");
        fs.copyToLocalFile(remotePath, localPath);
        File file = new File(localPath.toString());
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
        	String lineText = scanner.nextLine();
        	System.out.println(lineText);
        	String[] stringList = lineText.split("\t");
        	String userId = stringList[0];
        	String[] recommendList = stringList[1].split(",");
        	for (String string : recommendList) {
				String productId = string.split("_")[0];
				insert(userId, productId);
			}
        }
        
	}
	
	public static void insert(String userId, String productId) {
		Document document = new Document();
		document.append("_id", userId + "-" + productId).append("userId", userId).append("productId", productId);
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("xtgl");
		MongoCollection<Document> recommend = mongoDatabase.getCollection("recommend");
		recommend.insertOne(document);
		mongoClient.close();
	}
}
