/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dashboardapp;

/**
 *
 * @author ROCHE
 */
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChartDemo extends JFrame {

    public ChartDemo(String title) {
        super("Profit Analysis");
        JFreeChart chart = createProfitChart(title);
        ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
       
    }

    // Static method for creating the chart
    public JFreeChart createProfitChart(String Query) {
        // Create a dataset with your data
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Fetch data from your MySQL database and add it to the dataset
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "Pawaskar@2023");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Query); 
  

            while (resultSet.next()) {
                dataset.addValue(resultSet.getDouble("Profit"), "Profit", resultSet.getString("p_category"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a chart based on the dataset
        JFreeChart chart = ChartFactory.createBarChart("Profit Chart", "Category", "Profit", dataset, PlotOrientation.VERTICAL, true, true, false);

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
}    