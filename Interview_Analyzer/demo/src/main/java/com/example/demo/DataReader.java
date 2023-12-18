package com.example.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
public class DataReader {
    public static List<JavaDataModel> readData(String path) throws IOException
    {
        List<JavaDataModel> ListOfData = new ArrayList<>();

        try (FileInputStream f = new FileInputStream(path);
             Workbook WB = WorkbookFactory.create(f))
        {

            Sheet sheet1 = WB.getSheetAt(0); // there is only one sheet in xl sheet so getting that
            Iterator<Row> rowIterator = sheet1.iterator();
            if (rowIterator.hasNext()) {
                rowIterator.next();
                //skipping the first row since it contains header
            }
            while (rowIterator.hasNext()) {
                Row excelrow = rowIterator.next();
                JavaDataModel model = new JavaDataModel();
                model.setDate(getValueOfCell(excelrow.getCell(0)));
                model.setMonth(getMonthCellValue(excelrow.getCell(1)));
                model.setTeam(getValueOfCell(excelrow.getCell(2)));
                model.setPanelName(getValueOfCell(excelrow.getCell(3)));
                model.setRound(getValueOfCell(excelrow.getCell(4)));
                model.setSkill(getSkill(getValueOfCell(excelrow.getCell(5))));
                model.setTime(getTimeCellValue(excelrow.getCell(6)));
                model.setCandidateCurrentLOC(getValueOfCell(excelrow.getCell(7)));
                model.setCandidatePreferredLOC(getValueOfCell(excelrow.getCell(8)));
                model.setCandidateName(getValueOfCell(excelrow.getCell(9)));

                ListOfData.add(model);
            }
        }

        return ListOfData;
    }

    private static String getSkill(String valueOfCell) {
        return (valueOfCell.equals(null))?"Kotlin":valueOfCell;
    }

    private static String getTimeCellValue(Cell cell)
    {
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                    return dateFormat.format(cell.getDateCellValue());
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    private static String getMonthCellValue(Cell cell) {
        if (cell != null)
        {
            switch (cell.getCellType())
            {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    SimpleDateFormat formatDate = new SimpleDateFormat("MMM-yy");
                    return formatDate.format(cell.getDateCellValue());
                default:
                    return "Nov-23";
            }
        }
        else
        {
            return "Nov-23";
        }
    }

    private static String getValueOfCell(Cell cell) {
        return cell != null ? cell.toString() : "";
    }
}
