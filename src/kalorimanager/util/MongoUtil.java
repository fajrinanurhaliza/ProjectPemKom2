package kalorimanager.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
    private static final String URI = "mongodb://localhost:27017";
    private static MongoClient mongoClient = MongoClients.create(URI);
    private static MongoDatabase database = mongoClient.getDatabase("kalorimanager");

    public static MongoDatabase getDB() {
        return database;
    }
}
