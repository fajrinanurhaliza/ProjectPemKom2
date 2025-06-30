package kalorimanager.model;

import org.bson.Document;
import java.io.Serializable;

public class Entry implements Serializable {
    private String username;
    private String foodName;
    private double weight;
    private double calories;

    public Entry(String username, String foodName, double weight, double calories) {
        this.username = username;
        this.foodName = foodName;
        this.weight = weight;
        this.calories = calories;
    }

    // Getter
    public String getUsername() {
        return username;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getWeight() {
        return weight;
    }

    public double getCalories() {
        return calories;
    }

    // Convert Entry ke dokumen MongoDB
    public Document toDocument() {
        return new Document("username", username)
                .append("food", foodName)
                .append("weight", weight)
                .append("calories", calories);
    }

    // Convert dokumen MongoDB ke Entry
    public static Entry fromDocument(Document doc) {
        return new Entry(
                doc.getString("username"),
                doc.getString("food"),
                doc.getDouble("weight"),
                doc.getDouble("calories")
        );
    }
}
