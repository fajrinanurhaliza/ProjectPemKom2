/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kalorimanager.model;
import org.bson.Document;
import java.io.Serializable;
/**
 *
 * @author ASUS
 */
public class FoodItem implements Serializable {
    private String name;
    private double caloriesPer100g;

    public FoodItem(String name, double caloriesPer100g) {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
    }

    public String getName() {
        return name;
    }

    public double getCaloriesPer100g() {
        return caloriesPer100g;
    }

    // Membuat objek FoodItem dari dokumen MongoDB
    public static FoodItem fromDocument(Document doc) {
        return new FoodItem(
            doc.getString("name"),
            doc.getDouble("calories_per_100g")
        );
    }

    // Digunakan saat menampilkan di JComboBox
    @Override
    public String toString() {
        return name;
    }
}