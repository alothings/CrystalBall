package com.example.crystalball;

import java.util.Random;

public class CrystalBall {
	// Properties about the object
	public String[] mAnswers = {
			"It is certain",
			"It is decidedly so",
			"All signs say YES",
			"What you ask is unlikely..",
			"My reply is negative",
			"It is doubtful",
			"Better not tell you now.",
			"Focus and ask again",
			"Unable to answer now..."
	};
	
	// Methods from the project	
	
	public String getAnAnswer(){	
		// the button was clicked
		String answer = ""; //"" is empty string
		//Randomly select an answer yes,no or maybe
		Random randomGenerator = new Random();
		int randomNumber = randomGenerator.nextInt(mAnswers.length);
		answer = mAnswers[randomNumber];
		
		return answer;
	}
	
}
