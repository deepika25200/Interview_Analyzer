package com.example.demo;
import org.jfree.chart.JFreeChart;
import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            //1 . reading file and changing them into java objects
            List<JavaDataModel> data = DataReader.readData("C:\\Users\\immadisetty.deepika\\Downloads\\Interview_Analyzer\\demo\\src\\main\\resources\\details.xlsx");
            //2 . create Table and inserting data
            DBMaster.createTable();
            DBMaster.datainsertion(data);
            System.out.println("---------------------Maximum interviews team-----------------------------");
            //3 . generating asked data
            DBMaster.MaxInterviewsTeam();
            System.out.println("---------------------Minimum interviews team-----------------------------");
            DBMaster.MinInterviewsTeam();
            System.out.println("---------------------Top 3 skills-----------------------------");
            DBMaster.getTop3Skills();
            System.out.println("---------------------Top 3 Panels-----------------------------");
            DBMaster.getTop3Panels();
            System.out.println("---------------------Skills in peak time-----------------------------");
            DBMaster.skillsInPeakTime();
            // 4 . generationg charts for the data
           JFreeChart chart = ChartGenerator.createChart(data);
           SwingUtilities.invokeLater(() -> new ChatViewer("Interview Chart", chart));
           JFreeChart pieChart = ChartGenerator.getPieChart(data);
           SwingUtilities.invokeLater(() -> new ChatViewer("Skills Analysis Chart", pieChart));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    }
