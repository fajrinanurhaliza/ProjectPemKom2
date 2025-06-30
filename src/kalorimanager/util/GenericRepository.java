/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kalorimanager.util;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
/**
 *
 * @author ASUS
 * @param <T>
 */
public class GenericRepository<T> {
    private final MongoCollection<Document> collection;

    public GenericRepository(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public void insert(T obj, Function<T, Document> toDocument) {
        collection.insertOne(toDocument.apply(obj));
    }

    public List<T> findAll(Function<Document, T> fromDocument) {
        List<T> result = new ArrayList<>();
        for (Document doc : collection.find()) {
            result.add(fromDocument.apply(doc));
        }
        return result;
    }
}