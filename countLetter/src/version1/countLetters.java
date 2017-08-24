package version1;

import java.util.Scanner;

import static java.lang.Character.isLetter;

public class countLetters {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter a string: ");
        String str = input.nextLine();
        System.out.println("The number of string is: " + countLetters(str));
    }

    public static int countLetters(String s){
        int sum = 0;
        for(int i = 0; i < s.length(); i++)
        {
            //是字符时,sum加一
            if(isLetter(s.charAt(i)))
            {
                sum++;
            }
        }

        return sum;
    }
}
