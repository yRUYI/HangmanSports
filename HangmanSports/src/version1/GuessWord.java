package version1;

import java.util.Scanner;

public class GuessWord {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String[] words = {"words", "that", "menu", "like", "life", "program"
				, "java", "free", "world", "center", "top", "button", "application"};

		//是否进行下一次循环
		String anotherTimes = null;
		do{
			//随机选择一个单词
			String guessWord = words[(int)(Math.random() * words.length)];
			//将单词中的字母用*替换
			StringBuilder display = new StringBuilder();
			for(int i = 0; i < guessWord.length(); i++){
				display.append('*');
			}
			
			//定义正确和错误次数并初始化为0
			int correct = 0;
			int wrong = 0;
			
			while(correct < guessWord.length()){
				String read = null;
				do {
					System.out.print("<Guess> Enter a letter in the word " + display + " >");
					read = input.nextLine();
				}while(read.matches("[\\W]*"));//用户输入单词字符时退出循环
				char key = read.charAt(0);
				
				//用户输入的字母已经正确显示
				if(display.indexOf(key + "") >= 0){
					System.out.println("\t" + key + " is already in the word");
				}else if(guessWord.indexOf(key) < 0){//单词中没有用户输入字母
					System.out.println("\t" + key + " is not in the word");
					wrong++;
				}else{
					int index = guessWord.indexOf(key);//获取正确字母下标
					while(index >= 0){
						display.setCharAt(index, key);//用正确字母替换*
						correct++;//正确次数加1
						index = guessWord.indexOf(key, index + 1);//寻找下一个正确字母下标
					}
				}
			}
			
			System.out.println("The word is " + guessWord + ". You missed " + wrong + " times");
//			String anotherTimes = null;
			do {
				System.out.println("Do you want to guess for another word? Enter y or n>");
				anotherTimes = input.nextLine();
			}while(!anotherTimes.matches("[yn]"));//用户y/n时退出循环
			
		}while(anotherTimes.charAt(0) == 'y');
	}
}