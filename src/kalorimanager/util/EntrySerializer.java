/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kalorimanager.util;
import kalorimanager.model.Entry;
import java.io.*;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class EntrySerializer {
    public static void saveToFile(List<Entry> entries, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(entries);
        }
    }

    public static List<Entry> loadFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Entry>) ois.readObject();
        }
    }
}