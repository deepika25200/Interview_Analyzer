package com.example.demo;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class LambdaServices {
    public void topPanels(List<JavaDataModel> data) {
        Map<String,Long> countOfPanel=data.stream().filter(row->isValidMonth(row.getDate())).collect(Collectors.groupingBy(JavaDataModel::getPanelName, Collectors.counting()));
        System.out.println(countOfPanel);
        countOfPanel.entrySet().stream().sorted(Map.Entry.<String ,Long>comparingByValue().reversed()).limit(3).forEach(
                (i)-> System.out.println("Panel name  :"+i.getKey()+" Count :"+i.getValue())
        );
    }
    private boolean isValidMonth(Date date) {
        return date.getMonth()+1==10 || date.getMonth()+1==11;
    }
}
