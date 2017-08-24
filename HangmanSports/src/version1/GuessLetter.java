package version1;

import java.util.Scanner;

public class GuessLetter {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //用words数组存储单词
        String[] words = {"write", "that", "program", "another", "edition", "minute", "today", "update", "ready"};

        //存储是否进行下一次游戏的值(y/n)
        char anotherGame;

        //循环控制游戏次数
        do {
            //随机单词数组中的单词下标
            int indexOfWords = (int) (Math.random() * words.length);
            //获取需要猜测的单词
            String hiddenWord = words[indexOfWords];
            //设置单词显示时为******形式
            StringBuilder displayedWord = new StringBuilder();
            for (int i = 0; i < hiddenWord.length(); i++)
                displayedWord.append('*');

            //猜测正确次数
            int correctNumber = 0;
            //猜测错误次数
            int errorNumber = 0;

            //循环控制单词是否猜完
            while (correctNumber < hiddenWord.length()) {
                System.out.println("<Guess> Enter a letter in Word " + displayedWord + " > ");

                /**
                 * 获取输入的字母
                 * 输入空白字符时循环继续
                 */
                String s = input.nextLine();
                while(!s.matches("[\\S]+")){
                    s = input.nextLine();
                }
                char letter = s.charAt(0);

                if (displayedWord.indexOf(letter + "") >= 0)
                    System.out.println(letter + " is already int the word");//提示输入的字母已经猜过
                else if (hiddenWord.indexOf(letter) < 0) {
                    System.out.println(letter + " is not in the word");//提示猜测错误
                    errorNumber++;//猜测错误次数加一
                }
                else {
                    int indexOfWord = hiddenWord.indexOf(letter);//获取单词对应字母下标
                    while (indexOfWord >= 0) {
                        displayedWord.setCharAt(indexOfWord, letter);//替换*****中对应字符
                        correctNumber++;//猜测正确字数加一
                        indexOfWord = hiddenWord.indexOf(letter, indexOfWord + 1);//获取下一个下标
                    }
                }
            }

            System.out.println("The word is " + hiddenWord + ". You missed " + errorNumber + " times");
            System.out.println("Do you want to guess for another word? Enter y or n> ");
            anotherGame = input.nextLine().charAt(0);

        }while(anotherGame == 'y');

    }

}
