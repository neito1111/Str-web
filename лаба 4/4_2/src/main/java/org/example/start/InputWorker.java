package org.example.start;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputWorker {

    public static String enterMobileNo() {
        String mobileNum = null;
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {

                System.out.println("Enter mobile number:");
                mobileNum = scanner.nextLine();

                if (mobileNum.matches("(^(\\+375|80)(29|33|44|25|17)\\d{7}$)")) return mobileNum;
                else {
                    System.out.print("enter again, u entered: ");
                    System.out.println(mobileNum.isEmpty() ? "nothing" : mobileNum);
                }
            }
        }

    }

    public static String enterStringWithRegex(String text, String excText, String regex) {
        String str = null;

        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println(text);
            str = scanner.nextLine();

            if (str.matches(regex)) return str;
            else {
                System.out.print(excText);
                System.out.println(str.isEmpty() ? "nothing" : str);
            }
        }

    }

    public static int enterInt(String text, String errorText) {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String numString = null;
        System.out.println(text);
        while (true) {
            try {
                numString = stdin.readLine();
                return Integer.parseInt(numString);

            } catch (NumberFormatException e) {
                System.out.println(errorText + numString);
            } catch (IOException e) {
                System.out.println("U entered nothing");
            }
        }
    }

    public static BigDecimal enterBigDecimal(String text, String errorText) {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String numString = null;
        System.out.println(text);
        while (true) {
            try {
                numString = stdin.readLine();
                return new BigDecimal(numString);

            } catch (NumberFormatException e) {
                System.out.println(errorText + numString);
            } catch (IOException e) {
                System.out.println("U entered nothing");
            }
        }
    }

  /*  public static String enterDate() {
        String date = null;
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {

                System.out.println("Enter date:");
                date = scanner.nextLine();

                if (date.matches("((19[6-9]\\d)|(^20\\d{2}))-(0[1-9]|(1[0-2]))-((0[1-9]$)|([1-2][0-9]$)|(3[0-1]$))")) break;
                else {
                    System.out.print("enter again, u entered: ");
                    System.out.println(date.isEmpty()? "nothing": date);
                }
            }
        } catch (NoSuchElementException e){
            e.printStackTrace();
        }

        return date;
    }
*/

    /*  public static Date enterDate(){
          String dateStr;
          try (Scanner scanner = new Scanner(System.in)) {
              while (true) {

                  System.out.println("Enter date:");
                  dateStr = scanner.next();


                  if (dateStr.matches("((19[6-9]\\d)|(^20\\d{2}))-(0[1-9]|(1[0-2]))-((0[1-9]$)|([1-2][0-9]$)|(3[0-1]$))")) {
                      return Date.valueOf(dateStr);
                  }
                  else {
                      System.out.print("enter again, u entered: ");
                      System.out.println(dateStr.isEmpty()? "nothing": dateStr);
                  }
              }
          } catch (NoSuchElementException e){
              e.printStackTrace();
          }

      }
  */
    public static void main(String[] args) {

        /*Scanner sc = new Scanner(System.in);
        Long v =  sc.nextLong();

        String m = enterStringWithRegex("Enter date:", "Enter again, u entered: ", "((19[6-9]\\d)|(^20\\d{2}))-(0[1-9]|(1[0-2]))-((0[1-9]$)|([1-2][0-9]$)|(3[0-1]$))");
        System.out.println(m);
         m = enterStringWithRegex("Enter mobile number:", "Enter again, u entered: ", "(^(\\+375|80)(29|33|44|25|17)\\d{7}$)");
        System.out.println(m);

        m = enterStringWithRegex("Enter address:", "Enter again, u entered: ", "^[a-zA-Z\\s]+[\\d]+[a-z]*$");
        System.out.println(m);

        m = enterStringWithRegex("Enter email:", "Enter again, u entered: ", "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        System.out.println(m);
*/

        int r = enterInt("Enter id", "Please enter int value, u entered: ");
        System.out.println(r);

        String m = enterStringWithRegex("Enter first name:", "Enter again, u entered: ", "^([a-zA-Z]{2,}-?[a-zA-Z]{2,})");
        System.out.println(m);

        m = enterStringWithRegex("Enter last name:", "Enter again, u entered: ", "^([a-zA-Z]{1,}\\'?-?[a-zA-Z]{2,}\\s?([a-zA-Z]{1,})?)");
        System.out.println(m);


        BigDecimal d = enterBigDecimal("Enter salary", "Please enter decimal value, u entered: ");

    }
}
