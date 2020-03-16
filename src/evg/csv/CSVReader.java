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
import static evg.csv.Utils.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;

/**
 * 
 * Class for read csv 
 */

public class CSVReader {

    @SuppressWarnings("empty-statement")
    public static void generate_sql(String table_name, String csvFile) throws Exception {
//        String csvFile = "C:\\temp\\IKAR-16902.csv";        
        String csv_line = "";
        String cvsSplitBy = ";";
        String str_for_create_table = "create table " + table_name;
        StringBuilder str_create_table = new StringBuilder("(");
        StringBuilder str_values_table = new StringBuilder("(");
        Integer num_cols;
        Integer rownum = 1;
        String final_sql_create_table;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // column name - 1 row
            String[] header_name = br.readLine().split(cvsSplitBy);
            // column type - 2 row 
            String[] header_types = br.readLine().split(cvsSplitBy);
            // count columns              
            num_cols = header_name.length;
            str_create_table.append("id Integer ,");
            str_values_table.append("id,");
            Integer int_value;
            for (int i = 0; i < num_cols; i++) {
                str_create_table.append(prepare_str(header_name[i])).append(" ").append(prepare_str(header_types[i]));
                str_values_table.append(prepare_str(header_name[i]));
                // append "," for all without last column
                if (i < num_cols-1) {
                    str_create_table.append(",");
                    str_values_table.append(",");
                };
                // append ")" for last column
                if (i == num_cols-1) {
                    str_create_table.append(")");
                    str_values_table.append(")");
                }
            }
            // final string for create table
            final_sql_create_table = str_for_create_table + str_create_table + ";";
            System.err.println(final_sql_create_table);
            // execute create table 
            try {
                Statement stmt = ConnectBean.getInstance().getConnection().createStatement();
                stmt.executeUpdate(final_sql_create_table);
                ConnectBean.getInstance().getConnection().commit();
            }
            catch(Exception e) {
                System.err.println(e);
            }
            // cycle for lines in CSV file
            while ((csv_line = br.readLine()) != null) {      
                String[] rows = csv_line.split(cvsSplitBy);
                StringBuilder str_insert_table = new StringBuilder("(");
                str_insert_table.append(rownum++);
                str_insert_table.append(",");
//                rownum = ++rownum;
                // cylce for cols in rows 
                for (int i = 0;i < num_cols;i++) {             
                  if (i > 0 && i <= num_cols -1) {
                      str_insert_table.append(",");
                  }
                  try {
                    // prepare Integer
                    if (header_types[i].contains("Integer")) {                        
                        if (prepare_int(rows[i]).equals("")) {
                            int_value = null;
                            str_insert_table.append(int_value);
                        }
                        else {
                            int_value = get_int_value(rows[i]);
                            str_insert_table.append(int_value);
                        }                        
                    }
                    // prepare String
                    if (header_types[i].contains("String")) {
                        str_insert_table.append(rows[i]);
                    }
                  }
                  catch(Exception e) {
                      System.err.println(e);
                      System.err.println("error in value " + rows[i] + " -> " + csv_line);
                  }               
                }
                str_insert_table.append(");");
                String final_insert = "insert into "+ table_name + " " + str_values_table + "values" + str_insert_table;
                System.err.println(final_insert);
                // execute insert into statement          
                try {
                    Statement stmt = ConnectBean.getInstance().getConnection().createStatement();
                    stmt.executeUpdate(final_insert);                          
                }
                catch(Exception e) {
                    System.err.println(e);
                }                
            }
            ConnectBean.getInstance().getConnection().commit();
        } catch (IOException e) {
            System.err.println(e);
        }

    }    
}
