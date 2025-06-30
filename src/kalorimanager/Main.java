/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kalorimanager;
import kalorimanager.ui.LoginFrame;
import java.util.Locale;
/**
 *
 * @author ASUS
 */
public class Main {
    public static void main(String[] args) {
        // Set default language (EN or ID)
        Locale.setDefault(Locale.ENGLISH); // atau: new Locale("id")
        
        // Tampilkan frame login pertama kali
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}