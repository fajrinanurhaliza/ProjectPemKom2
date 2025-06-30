/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package kalorimanager.ui;
import kalorimanager.model.Entry;
import kalorimanager.model.FoodItem;
import kalorimanager.util.MongoUtil;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.io.File;                      
import java.util.List;                    
import java.util.ArrayList;               
import kalorimanager.util.EntrySerializer; 

public class MainFrame extends javax.swing.JFrame {

    private String username;

    public MainFrame(String username) {
        this.username = username;
        initComponents();
        setLocationRelativeTo(null);
        updateTexts();
        updateChart();
        btnCalculate.addActionListener(e -> hitungKalori());
    }

    private void updateTexts() {
        setTitle("KaloriManager");
        lblFood.setText("Makanan:");
        lblWeight.setText("Berat (g):");
        lblResult.setText("Kalori:");
        btnCalculate.setText("Check");
        chartPanel.setBorder(BorderFactory.createTitledBorder("Grafik Kalori"));
    }

    private void hitungKalori() {
        String namaMakanan = txtFood.getText().trim().toLowerCase();
        String beratStr = txtBerat.getText().trim();

        if (namaMakanan.isEmpty() || beratStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mohon isi semua field.");
            return;
        }

        try {
            double berat = Double.parseDouble(beratStr);

            // Cari makanan dari database referensi_makanan
            Document doc = MongoUtil.getDB().getCollection("referensi_makanan")
                    .find(new Document("name", new Document("$regex", "^" + namaMakanan + "$").append("$options", "i")))
                    .first();

            if (doc == null) {
                JOptionPane.showMessageDialog(this, "Makanan tidak ditemukan di database.");
                return;
            }

            FoodItem item = FoodItem.fromDocument(doc);
            double kalori = (item.getCaloriesPer100g() * berat) / 100.0;

            lblCalories.setText(String.format("%.2f kalori", kalori));

            Entry entry = new Entry(username, item.getName(), berat, kalori);
            MongoUtil.getDB().getCollection("entries").insertOne(entry.toDocument());

            updateChart();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Berat harus berupa angka.");
        }
    }

    private void updateChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        MongoCursor<Document> cursor = MongoUtil.getDB().getCollection("entries")
                .find(new Document("username", username)).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            dataset.addValue(doc.getDouble("calories"), "Kalori", doc.getString("food"));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Grafik Kalori",
                "Makanan",
                "Kalori",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        ChartPanel cPanel = new ChartPanel(chart);
        cPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.removeAll();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(cPanel, BorderLayout.CENTER);
        chartPanel.validate();
    }
    
        /* ----------  SERIALIZATION HELPERS  ---------- */

/** Menyimpan semua entri milik user ke sebuah file *.ser */
private void exportData() {
    JFileChooser chooser = new JFileChooser();
    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
        try {
            List<Entry> entries = MongoUtil.getDB().getCollection("entries")
                    .find(new Document("username", username))
                    .map(Entry::fromDocument)
                    .into(new ArrayList<>());
            EntrySerializer.saveToFile(entries, file);
            JOptionPane.showMessageDialog(this, "Data berhasil diekspor!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengekspor: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void importData() {
    JFileChooser chooser = new JFileChooser();
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();
        try {
            List<Entry> entries = EntrySerializer.loadFromFile(file);
            for (Entry e : entries) {
                MongoUtil.getDB().getCollection("entries").insertOne(e.toDocument());
            }
            JOptionPane.showMessageDialog(this, "Data berhasil diimpor!");
            updateChart();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengimpor: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFood = new javax.swing.JLabel();
        txtBerat = new javax.swing.JTextField();
        btnCalculate = new javax.swing.JButton();
        lblWeight = new javax.swing.JLabel();
        lblResult = new javax.swing.JLabel();
        lblCalories = new javax.swing.JLabel();
        chartPanel = new javax.swing.JPanel();
        txtFood = new javax.swing.JTextField();
        btnLogout = new javax.swing.JButton();
        btnEkspor = new javax.swing.JButton();
        btnImpor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));

        lblFood.setText("Food : ");

        btnCalculate.setBackground(new java.awt.Color(204, 204, 255));
        btnCalculate.setText("Check");

        lblWeight.setText("Weight : ");

        lblResult.setText("Calories : ");

        lblCalories.setText("Calorie Chart");

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );

        btnLogout.setBackground(new java.awt.Color(204, 204, 255));
        btnLogout.setText("Exit");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnEkspor.setBackground(new java.awt.Color(255, 204, 204));
        btnEkspor.setText("Ekspor");
        btnEkspor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEksporActionPerformed(evt);
            }
        });

        btnImpor.setBackground(new java.awt.Color(255, 255, 204));
        btnImpor.setText("Impor");
        btnImpor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImporActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFood)
                                    .addComponent(lblWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBerat, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFood, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 77, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblResult)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCalculate)))
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCalories)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(359, 359, 359)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(btnEkspor)
                        .addGap(18, 18, 18)
                        .addComponent(btnImpor)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btnLogout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFood)
                    .addComponent(txtFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBerat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWeight))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResult)
                    .addComponent(btnCalculate))
                .addGap(21, 21, 21)
                .addComponent(lblCalories)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEkspor)
                    .addComponent(btnImpor))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    /**
     * @param args the command line arguments
     */
    private void btnCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculateActionPerformed
         String namaMakanan = txtFood.getText().trim().toLowerCase();
        String beratStr = txtBerat.getText().trim();

        if (namaMakanan.isEmpty() || beratStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mohon isi semua field.");
            return;
        }

        try {
            double berat = Double.parseDouble(beratStr);

            // Ambil data makanan dari MongoDB
            Document doc = MongoUtil.getDB().getCollection("referensi_makanan")
                .find(new Document("name", new Document("$regex", "^" + namaMakanan + "$").append("$options", "i")))
                .first();

            if (doc == null) {
                JOptionPane.showMessageDialog(this, "Makanan tidak ditemukan di database.");
                return;
            }

            FoodItem item = FoodItem.fromDocument(doc);
            double weight = Double.parseDouble(beratStr);
            double kalori = (item.getCaloriesPer100g() * berat) / 100.0;

            lblCalories.setText(String.format("%.2f", kalori)); // tampilkan hasil

            Entry entry = new Entry(username, item.getName(), berat, kalori);
            MongoUtil.getDB().getCollection("entries").insertOne(entry.toDocument());

            updateChart();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Berat harus berupa angka.");
        }
    }//GEN-LAST:event_btnCalculateActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
         this,
         "Apakah Anda yakin ingin keluar?",
         "Konfirmasi Logout",
         JOptionPane.YES_NO_OPTION
     );

     if (confirm == JOptionPane.YES_OPTION) {
         this.dispose(); // Tutup MainFrame
         new LoginFrame().setVisible(true); // Buka kembali LoginFrame
     }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnEksporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEksporActionPerformed
        exportData(); 
    }//GEN-LAST:event_btnEksporActionPerformed

    private void btnImporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImporActionPerformed
        importData(); 
    }//GEN-LAST:event_btnImporActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String username = "test_user"; // Ganti dengan username yang valid dari database
                new MainFrame(username).setVisible(true);
            }
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalculate;
    private javax.swing.JButton btnEkspor;
    private javax.swing.JButton btnImpor;
    private javax.swing.JButton btnLogout;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JLabel lblCalories;
    private javax.swing.JLabel lblFood;
    private javax.swing.JLabel lblResult;
    private javax.swing.JLabel lblWeight;
    private javax.swing.JTextField txtBerat;
    private javax.swing.JTextField txtFood;
    // End of variables declaration//GEN-END:variables
}
