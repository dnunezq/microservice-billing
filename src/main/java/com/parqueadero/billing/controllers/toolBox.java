package com.parqueadero.billing.controllers;

public class toolBox {
    public static Long concatenateDigits(Long... digits) {
        
        StringBuilder sb = new StringBuilder(digits.length);

        for (Long digit : digits) {
          sb.append(digit);
        }
       //convert the string to Long
        return Long.parseLong(sb.toString());
    }


    //function that eliminate the prefix of the bill number

    public static Long eliminatePrefix(Long BillNumber, Long numbersOfDigits) {

        Long new_x= new Long(BillNumber);

        for(int i=0;i<numbersOfDigits;i++){
             new_x = Long.parseLong(Long.toString(BillNumber).substring(i+1));
        } 

        return new_x;       
    }    
}
