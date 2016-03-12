package mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;


public class DBTest {

	public static void main(String[] args) {
		MongoDBConnect.getDB("test");
		MongoDBConnect.delete("user", new BasicDBObject("age",25));
		BasicDBObject doc=new BasicDBObject("name", "发货款了飒").append("age",15).append("sex", "男");
		MongoDBConnect.insert("user", doc);
		DBCursor cursor=MongoDBConnect.find("user");
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		
		MongoDBConnect.update("user",new BasicDBObject("name","飒飒"), new BasicDBObject("name", "飒6").append("age",20).append("sex", "女"));
		cursor=MongoDBConnect.find("user");
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		MongoDBConnect.closeConnect();
	}
}
