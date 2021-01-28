



public class AtbashCipherImpl implements IAtbashCipher{

	@Override
	public String atbashEncode(String phrase) {
		
		//Convert phrase to char array
		char[]wordArray = phrase.toCharArray();
		
		//Iterate phrase
		for(int i=0; i<wordArray.length; i++) {
			
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) { // Check if character is upper case in ascii
				
				//Get the value of the char after adding 25 and subtracting it to 90
				int charCount = (char) ((wordArray[i] + 25) - 90);
				
				if(charCount > 1) { // If char is > 1 then char exceeded 90 after adding 25
					wordArray[i] = (char) (90 - charCount);
				}else { // If char is less than 1 or equal to 0 then it is 90 by default
					wordArray[i] = 90;
				}
				
				
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) { // Check if character is lower case in ascii
				
				//Get the value of the char after adding 25 and subtracting it to 122
				int charCount = (char) ((wordArray[i] + 25) - 122);
				
				if(charCount > 1) { // If char is > 1 then char exceeded 90 after adding 25
					wordArray[i] = (char) (122 - charCount);
				}else { // If char is less than 1 or equal to 0 then it is 122 by default
					wordArray[i] = 122;
				}
				
			}else { // Special characters gets skipped
				continue;
			}
			
		}
		
		return new String(wordArray);
	}

	@Override
	public String atbashDecode(String phrase) {
		
		//Convert phrase to char array
		char[]wordArray = phrase.toCharArray();
		
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) { // Check if character is upper case in ascii
				
				//Get the value of the char after adding 25 and subtracting it to 90
				int charCount = (char) ((wordArray[i] + 25) - 90);
				
				if(charCount > 1) { // If char is > 1 then char exceeded 90 after adding 25
					wordArray[i] = (char) (90 - charCount);
				}else { // If char is less than 1 or equal to 0 then it is 90 by default
					wordArray[i] = 90;
				}
				
				
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) { // Check if character is lower case in ascii
				
				//Get the value of the char after adding 25 and subtracting it to 90
				int charCount = (char) ((wordArray[i] + 25) - 122);
				
				if(charCount > 1) { // If char is > 1 then char exceeded 122 after adding 25
					wordArray[i] = (char) (122 - charCount);
				}else { // If char is less than 1 or equal to 0 then it is 122 by default
					wordArray[i] = 122;
				}
				
			}else { // Special characters gets skipped
				continue;
			}
			
		}
		
		return new String(wordArray);
	}
	
	

}
