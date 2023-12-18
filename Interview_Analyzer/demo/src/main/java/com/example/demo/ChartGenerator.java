package com.example.demo;
import com.example.demo.JavaDataModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartGenerator {
    public static JFreeChart createChart(List<JavaDataModel> data) {
        // Example: Create a bar chart with team names and the count of interviews
        CategoryDataset dataset = getData(data);
        JFreeChart chart = ChartFactory.createBarChart(
                "Each team stastics ",
                "Team",
                "No: Interviews",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );

        // Customize chart properties as needed
        chart.setBackgroundPaint(Color.BLUE);
        return chart;
    }

    private static CategoryDataset getData(List<JavaDataModel> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Long> counts = data.stream().filter(data1->data1.getTeam() != null)
                .collect(Collectors.groupingBy(data1 -> data1.getTeam(), Collectors.counting()));
        counts.forEach((team, count) -> {
            dataset.addValue(count, "Interviews", team);
        });

        // Add other dataset entries as needed

        return dataset;
    }

   public static JFreeChart getPieChart(List<JavaDataModel> dataList) {
        Map<String, Integer> skillMap = new HashMap<>();
        for(JavaDataModel data : dataList) {
            String skill = data.getSkill();
            if(skill != null && !skill.trim().isEmpty()){
                skillMap.put(skill, skillMap.getOrDefault(skill,0) + 1);
            } else {
                skillMap.put("Not defined", skillMap.getOrDefault("Not", 0) + 1);
            }
        }
        DefaultPieDataset dataset = new DefaultPieDataset<>();
        skillMap.forEach(dataset::setValue);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "No: skills",
                dataset,
                true,
                true,
                false
        );
        return pieChart;
    }
}