import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import mapreduce_project1.Run1;
import mapreduce_project2.Run2;
import mapreduce_project3.Run3;
import mapreduce_project4.Run4;
import mapreduce_project5.Run5;

public class main_project {
	/** 
     * 判断路径是否存在 
     */ 
    public static boolean test(Configuration conf, String path) 
    		throws IOException { 
        FileSystem fs = FileSystem.get(conf); 
        return fs.exists(new Path(path)); 
    } 
    /** 
     * 复制文件到指定路径 
     * 若路径已存在，则进行覆盖 
     */ 
    public static void copyFromLocalFile(Configuration conf, String localFilePath, String remoteFilePath) throws IOException { 
        FileSystem fs = FileSystem.get(conf); 
        Path localPath = new Path(localFilePath); 
        Path remotePath = new Path(remoteFilePath); 
        /* fs.copyFromLocalFile 第一个参数表示是否删除源文件，第二个参数表示是否覆盖 */ 
        fs.copyFromLocalFile(false, true, localPath, remotePath); 
        fs.close(); 
    }
	
    public class MyFSDataInputStream extends FSDataInputStream {
	    public MyFSDataInputStream(InputStream in) {
			super(in);
			// TODO Auto-generated constructor stub
		}
	}
	public static String readline() throws IOException {
    	String remoteFilePath="/user/hadoop/output/Tuser_matrix.txt/part-r-00000";
    	Configuration conf = new Configuration();
		conf.set("fs.defaultFS","hdfs://localhost:9000");
	    conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");
        FileSystem fs = FileSystem.get(URI.create("hdfs://localhost:9000"),conf);
        Path remotePath = new Path(remoteFilePath);
        FSDataInputStream in = fs.open(remotePath);
//        BufferedReader d = new BufferedReader(new InputStreamReader(in));
        Scanner d = new Scanner(new InputStreamReader(in));
//        d.toString();
//        System.out.println(d.toString());
        String line = null;
        String a="未读取到文件尾";
//        if ((line = d.readLine()) != null) {
//        	d.close();
//            in.close();
//        	System.out.println(line+"\r");
//            return line;
//            }
        if(!d.hasNext()) {
        	String str = d.next();
        	System.out.println(str);
        	
        }
        d.close();
        return a;
    }
	public static void main(String[] args) throws URISyntaxException, IOException {
//		mongodb -> file on hdfs   inPath = "/user/hadoop/user_matrix.txt";
		//实例化一个mongo客户端
        MongoClient  mongoClient=new MongoClient("localhost",27017);
        //实例化一个mongo数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("xtgl");
        //获取数据库中某个集合
        MongoCollection<Document> action = mongoDatabase.getCollection("action");
        FindIterable<Document> action_find = action.find(Filters.gt("userId", -1));
        MongoCursor<Document> action_cursor = action_find.iterator();
        File file = new File("/usr/local/user_matrix.txt");
        if(!file.exists()) {
        	file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(writer);
        while(action_cursor.hasNext()) {
        	Document doc = action_cursor.next();
        	Number doc3 = (Number)doc.get("score");
        	Integer score = doc3.intValue();
//        	System.out.println(doc1_1);
//        	System.out.println(doc1);
        	out.write(doc.get("userId")+","+doc.get("productId")+","+score+"\r");
        	
//        	System.out.println(doc.get("userId")+","+doc.get("productId")+","+doc.get("score")+"\r");
        }
        out.close();
        Configuration conf = new Configuration(); 
        conf.set("fs.default.name","hdfs://localhost:9000");
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy","NEVER");
        conf.set("dfs.client.block.write.replace-datanode-on-failure.enable","true");
        String localFilePath = "/usr/local/user_matrix.txt";    // 本地路径 
        String remoteFilePath = "/user/hadoop/user_matrix.txt";    // HDFS路径 
        try { 
         /* 判断文件是否存在 */ 
         Boolean fileExists = false; 
         if (main_project.test(conf, remoteFilePath)) { 
          fileExists = true; 
          System.out.println(remoteFilePath + " 已存在."); 
         }
         /* 进行处理 */ 
         if ( !fileExists) { // 文件不存在，则上传 
          main_project.copyFromLocalFile(conf, localFilePath, remoteFilePath); 
          System.out.println(localFilePath + " 已上传至 " + remoteFilePath); 
         } 
         else{    // 选择覆盖 
          main_project.copyFromLocalFile(conf, localFilePath, remoteFilePath); 
          System.out.println(localFilePath + " 已覆盖 " + remoteFilePath); 
         } 
        } catch (Exception e) { 
         e.printStackTrace(); 
        } 
//		
//		new Run1().run();
//		new Run2().run();
//		new Run3().run();
//		new Run4().run();
//		new Run5().run();
//		file on hdfs -> monodb    outPath = "/user/hadoop/output/step5_output.txt";
		main_project.readline();
	}
}
