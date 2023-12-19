package com.example.demo;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;

public class ChatViewer extends JFrame {
    public ChatViewer(String title, JFreeChart chart) {
        super(title);

        // Create a ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        // Set up the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);  // Center the JFrame
        setVisible(true);
    }
}
