/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evg.csv;

/**
 *
 * @author eshahov
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public static void main(String[] args) {
        String csvFile = "C:\\temp\\IKAR-16902.csv";
        String line = "";
        String cvsSplitBy = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String str = "create table table_name";
            // string for create table by csv file 
            // at 1 line - column name
            // at 2 line - column type            
            StringBuilder str_create_table = new StringBuilder("(");
            // column name
            String[] header = br.readLine().split(cvsSplitBy);
            // column type           
            String[] header_types = br.readLine().split(cvsSplitBy);
            // count columns        
            Integer num_cols = header.length;
            for (int i = 0; i < num_cols; i++) {         
                str_create_table.append(header[i].replaceAll("\"","") + " " + header_types[i].replaceAll("\"",""));                
                if (i < num_cols-1) {
                    str_create_table.append(",");
                };
                if (i == num_cols-1) {
                    str_create_table.append(")");
                }
            }
            String final_sql_create_table = str + str_create_table + ";";
            System.err.println(final_sql_create_table);
            // cycle for lines in CSV file
            while ((line = br.readLine()) != null) {
                String[] rows = line.split(cvsSplitBy);
                StringBuilder str_insert_table = new StringBuilder("(");
                // cylce for cols in rows 
                for (int i = 0;i < num_cols;i++) {
                  if (i > 0 && i <= num_cols -1) {
                      str_insert_table.append(",");
                  }
                  try {
                    if (header_types[i].contains("Integer")) {
                        Integer int_value = null;
                        if (rows[i].replaceAll("\\D", "").equals("")) {
                            int_value = null;
                            str_insert_table.append(int_value);
                        }
                        else {
                            int_value = Integer.valueOf(rows[i].replaceAll("\\D", ""));
                            str_insert_table.append(int_value);
                        }                        
                    }
                    if (header_types[i].contains("String")) {
                        str_insert_table.append(rows[i]);
                    }
                  }
                  catch(Exception e) {
                      System.err.println(e);
                      System.err.println("error in value " + rows[i] + " -> " + line.toString());
                  }               
                }                
                str_insert_table.append(");");
                String final_insert = "insert into table_name " + str_create_table + "values" + str_insert_table;
                System.err.println(final_insert);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
