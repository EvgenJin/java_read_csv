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
public class Utils {
    
    public static String prepare_str(String str) {
        return str.replaceAll("\"","");
    }
    public static String prepare_int (String str) {
        return str.replaceAll("\\D", "");
    }
    public static Integer get_int_value (String str) {
        return Integer.valueOf(prepare_int(str));
    }
}
