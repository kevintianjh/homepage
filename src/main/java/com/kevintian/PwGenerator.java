package com.kevintian;
 
import java.util.ArrayList;
import java.util.Random; 

public class PwGenerator {
	
	private Random rand = new Random();
	private boolean useLowerCase;
	private boolean useUpperCase;
	private boolean useNumber;
	private boolean useSpecialChar;
	
	public PwGenerator(boolean useLowerCase, boolean useUpperCase, boolean useNumber, boolean useSpecialChar) {
		this.useLowerCase = useLowerCase;
		this.useUpperCase = useUpperCase;
		this.useNumber = useNumber;
		this.useSpecialChar = useSpecialChar;
	}
	
	public String generate() {
		StringBuilder pw = new StringBuilder();
		
		ArrayList<String> choices = new ArrayList<>();
		if(this.useLowerCase) {
			choices.add("1");
		}
		
		if(this.useUpperCase) {
			choices.add("2");
		}
		
		if(this.useNumber) {
			choices.add("3");
		}
		
		if(this.useSpecialChar) {
			choices.add("4");
		}
		
		for(int count = 0; count < 15; count++) {
			String choiceStr = choices.get(rand.nextInt(choices.size()));
			int choice = Integer.parseInt(choiceStr);
			
			switch(choice) {
				case 1:
					pw.append((char)rand.nextInt(97, 123));
					break;
				case 2: 
					pw.append((char)rand.nextInt(65, 91)); 
					break;
				case 3:
					pw.append((char)rand.nextInt(48, 58)); 
					break;
				case 4:  
					pw.append((char)rand.nextInt(33, 48));
					break;
			}
		}
		
		return pw.toString();
	}
}

