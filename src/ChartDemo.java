import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChartDemo extends JFrame {
    
   Connection con;
   ResultSet rs;
   PreparedStatement pst,pst2;


    public ChartDemo(String title) {
        super(title);

        JFreeChart chart = createProfitChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    // Static method for creating the chart
    public JFreeChart createProfitChart() {
        // Create a dataset with your data
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Fetch data from your MySQL database and add it to the dataset
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory","root","Pawaskar@2023");
            System.out.println(con);
            pst = con.prepareStatement("Select Quantity, Purchase_Price from Product");
            rs=pst.executeQuery();

            while (rs.next()) {
                dataset.addValue(rs.getDouble("profit"), "Profit", rs.getString("date"));
            }

            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a chart based on the dataset
        JFreeChart chart = ChartFactory.createLineChart("Profit Chart", "Date", "Profit", dataset, PlotOrientation.VERTICAL, true, true, false);

        return chart;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChartDemo chartDemo = new ChartDemo("Profit Chart");
            chartDemo.pack();
            chartDemo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chartDemo.setVisible(true);
        });
    }