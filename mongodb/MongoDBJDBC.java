package mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;


public class MongoDBJDBC {
	public static void main( String args[] ){

		try {
			// 连接到数据库
			DB db = getDb();
			
			if(db!=null){
				//创建集合
				//DBCollection coll = db.createCollection("mycol",null);
				//System.out.println("Collection created successfully");
				//获取集合
				DBCollection colls = db.getCollection("mycol");
				System.out.println("Collection mycol selected successfully");
				// 插入文档
				BasicDBObject doc = new BasicDBObject("title", "MongoDB").append("description", "database")
						.append("likes", 100).append("url", "http://www.w3cschool.cc/mongodb/")
						.append("by", "w3cschool.cc");
				colls.insert(doc);
				System.out.println("Document inserted successfully");
				//读取文档
				DBCursor cursor = colls.find();
				int i = 1;
				while (cursor.hasNext()) {
					System.out.println("Inserted Document: " + i);
					System.out.println(cursor.next());
					i++;
				}

				cursor = colls.find();
				// 更新文档
				while (cursor.hasNext()) {
					DBObject queryDocument = cursor.next();
					DBObject updateDocument = queryDocument;
					updateDocument.put("likes", 200);
					System.out.println(updateDocument);
					WriteResult r = colls.update(queryDocument, updateDocument);
					System.out.println("Document updated result:" + r);
				}
				cursor = colls.find();
				int j = 1;
				while (cursor.hasNext()) {
					System.out.println("Updated Document: " + j);
					System.out.println(cursor.next());
					j++;
				}
				DBObject myDoc = colls.findOne();
				colls.remove(myDoc);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public static DB getDb(){
		try {
			// 连接到 mongodb 服务  Since 2.10.0, uses MongoClient  
			//MongoClient mongo = new MongoClient("localhost", 27017);
			
			Mongo mongo = new Mongo("127.0.0.1",27017);  
			// 连接到数据库
			DB db = mongo.getDB("test");
			System.out.println("Connect to database successfully");
			return db;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}
}
