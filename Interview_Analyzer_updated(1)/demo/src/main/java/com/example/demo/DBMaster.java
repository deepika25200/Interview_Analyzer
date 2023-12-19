package com.example.demo;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.List;
public class DBMaster {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Saideepika@1";
    private static final String TABLE_NAME = "Interview_Data"; // Table name to be created
    private static final BasicDataSource dataSource = new BasicDataSource();
    static {
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
    }

    public static void createTable() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "date VARCHAR(20)," +
                    "month VARCHAR(20)," +
                    "team VARCHAR(50)," +
                    "panelName VARCHAR(50)," +
                    "round VARCHAR(50)," +
                    "skill VARCHAR(50)," +
                    "time VARCHAR(50)," +
                    "candidateCurrentLoc VARCHAR(50)," +
                    "candidatePreferredLoc VARCHAR(50)," +
                    "candidateName VARCHAR(50)" +
                    ")";

            statement.executeUpdate(createTableQuery);

            System.out.println("Table '" + TABLE_NAME + "' created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void datainsertion(List<JavaDataModel> data) {
        data.parallelStream().forEach(tuple -> {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO " + TABLE_NAME + " (date, month, team, panelName, round, skill, time, " +
                                 "candidateCurrentLoc, candidatePreferredLoc, candidateName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                preparedStatement.setDate(1, tuple.getSqlDate());
                preparedStatement.setString(2, tuple.getMonth());
                preparedStatement.setString(3, tuple.getTeam());
                preparedStatement.setString(4, tuple.getPanelName());
                preparedStatement.setString(5, tuple.getRound());
                preparedStatement.setString(6, tuple.getSkill());
                preparedStatement.setString(7, tuple.getTime());
                preparedStatement.setString(8, tuple.getCandidateCurrentLoc());
                preparedStatement.setString(9, tuple.getCandidatePreferredLoc());
                preparedStatement.setString(10, tuple.getCandidateName());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public static void MaxInterviewsTeam() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT team,COUNT(*) AS maxcount FROM interview_data\n" +
                    "            WHERE month LIKE 'Oct%' OR month LIKE 'Nov%'\n" +
                    "                    GROUP BY team \n" +
                    "                    ORDER BY maxcount DESC \n" +
                    "                    LIMIT 1;";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                System.out.println("Maximum interviews done by the team :" + resultSet.getString("team"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void MinInterviewsTeam() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT team,COUNT(*) AS mincount FROM interview_data \n" +
                    "                    WHERE month LIKE 'Oct%' OR month LIKE 'Nov%' \n" +
                    "                    GROUP BY team \n" +
                    "                    ORDER BY mincount \n" +
                    "                    LIMIT 1";

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                System.out.println("Minimum interviews done by the team :" + resultSet.getString("team"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void getTop3Skills() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Query to find the top 3 skills for the month of October and November 2023 using a view on the database
            String query = "create or replace view  skills_view as select skill,count(*) as SkillCount \n" +
                    "from interview_data \n" +
                    "WHERE month LIKE 'Oct%' OR month LIKE 'Nov%'\n" +
                    "group by Skill \n" +
                    "Order by skillcount desc limit 3;";
            statement.executeUpdate(query);
            String Query2 = "SELECT * FROM skills_view;";
            ResultSet resultSet = statement.executeQuery(Query2);
            while (resultSet.next()) {
                System.out.println("Skill name: " + resultSet.getString("skill") +
                        ", Candidates Count: " + resultSet.getInt("skillCount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void getTop3Panels() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Query to find the top 3 panels for the month of October and November 2023 using lambda streams
            String query = "SELECT panelName, COUNT(*) as panelCount\n" +
                    "FROM interview_data\n" +
                    "WHERE month IN ('Oct-23', 'Nov-23')\n" +
                    "GROUP BY panelName\n" +
                    "ORDER BY panelCount DESC\n" +
                    "LIMIT 3;";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println("Panel name :" + resultSet.getString("panelName") +
                        ", Number of Interview: " + resultSet.getInt("panelCount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void skillsInPeakTime() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Query to find the top 3 skills for which the interviews were conducted in the Peak Time
            String query = "select skill,count(*) as SkillCount \n" +
                    "from interview_data \n" +
                    "where time = (select time from interview_data\n" +
                    "group by time Order by count(*) desc limit 1)\n" +
                    "group by Skill\n" +
                    "Order by skillcount \n" +
                    "desc limit 3;";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Skill in Peak Time: " + resultSet.getString("skill") +
                        ", Interview Count: " + resultSet.getInt("SkillCount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
