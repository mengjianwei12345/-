



public class Rot13CipherImpl implements IRot13Cipher{

	@Override
	public String rot13Encode(String phrase) {
		
		char[]wordArray = phrase.toCharArray();
		
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) {
				
				if(wordArray[i] > 64 && wordArray[i] < 78) {
					
					wordArray[i] = (char)(wordArray[i] + 13);
					
				}else {
					
					wordArray[i] = (char)(wordArray[i] - 13);
					
				}
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) {
				
				if(wordArray[i] > 96 && wordArray[i] < 110) {
					
					wordArray[i] = (char)(wordArray[i] + 13);
					
				}else {
					
					wordArray[i] = (char)(wordArray[i] - 13);
					
				}
			}else {
				
				continue;
				
			}
		}
		
		return new String(wordArray);
	}

	@Override
	public String rot13Decode(String phrase) {
		
		char[]wordArray = phrase.toCharArray();
		
		for(int i=0; i<wordArray.length; i++) {
			
			if(GlobalMethod.checkForUpperCase(wordArray[i])) {
				
				if(wordArray[i] > 64 && wordArray[i] < 78) {
					
					wordArray[i] = (char)(wordArray[i] + 13);
					
				}else {
					
					wordArray[i] = (char)(wordArray[i] - 13);
					
				}
			}else if(GlobalMethod.checkForLowerCase(wordArray[i])) {
				
				if(wordArray[i] > 96 && wordArray[i] < 110) {
					
					wordArray[i] = (char)(wordArray[i] + 13);
					
				}else {
					
					wordArray[i] = (char)(wordArray[i] - 13);
					
				}
			}else {
				
				continue;
				
			}
		}
		
		return new String(wordArray);
	}

}
