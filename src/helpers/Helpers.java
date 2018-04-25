/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import allforkids.orderManagement.models.*;
import java.text.DecimalFormat;

/**
 *
 * @author KHOUBEIB
 */
public class Helpers {

    public static Double currencyFormatter(Double input) {
        
        

        DecimalFormat formatter = new DecimalFormat("#0.00000");
        String formated = formatter.format(input);
        Double output = Double.parseDouble(formated.replace(",","."));
        
        return output;

    }

}
