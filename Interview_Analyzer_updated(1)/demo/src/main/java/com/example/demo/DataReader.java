package com.example.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ch.qos.logback.core.CoreConstants;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.xmlbeans.impl.regex.ParseException;

public class DataReader {
    public static List<JavaDataModel> readData(String path) throws IOException
    {
        List<JavaDataModel> ListOfData = new ArrayList<>();

        try (FileInputStream f = new FileInputStream(path);
             Workbook WB = WorkbookFactory.create(f))
        {

            Sheet sheet1 = WB.getSheetAt(0);
            for (Row row : sheet1) {
                boolean skipRow = false;
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.BLANK || cell.toString().trim().isEmpty() || cell.toString()=="") {
                        skipRow = true;
                        break;
                    }
                }
                if (!skipRow) {
                    JavaDataModel model = new JavaDataModel();
                    model.setDate(parseDate(String.valueOf(row.getCell(0))));
                    model.setMonth(getMonthCellValue(row.getCell(1)));
                    model.setTeam(String.valueOf(row.getCell(2)));
                    model.setPanelName(String.valueOf(row.getCell(3)));
                    model.setRound(String.valueOf(row.getCell(4)));
                    model.setSkill(String.valueOf(row.getCell(5)));
                    model.setTime(getTimeCellValue(row.getCell(6)));
                    model.setCandidateCurrentLOC(String.valueOf(row.getCell(7)));
                    model.setCandidatePreferredLOC(String.valueOf(row.getCell(8)));
                    model.setCandidateName(String.valueOf(row.getCell(9)));
                    ListOfData.add(model);
                }
            }
            f.close();
            WB.close();
        }

        catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {

        }
        return ListOfData;
    }
    private static Date parseDate(String s) throws ParseException, java.text.ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        return sdf.parse(s);
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
}
