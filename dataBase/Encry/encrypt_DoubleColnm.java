

public class DoubleColnm {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter String to be Encrypted     : ");
		String Message = in.nextLine();
		System.out.print("Enter Permutation Encryption Key : ");
		String keyLine1 = in.nextLine();

		StringTokenizer st = new StringTokenizer(keyLine1," ");
		int size = 0;
		ArrayList<Integer> EncryptionKey = new ArrayList<Integer>();
		while(st.hasMoreElements()) {
			EncryptionKey.add(Integer.parseInt(st.nextToken()));
			size++;
		}

		System.out.println("============================================");
		System.out.println("//////////////// ENCRYPTING ////////////////");
		System.out.println("============================================");
		System.out.println("Message                          : "+Message);
		Message = Message.replace(" ","");
		System.out.println("Formatting 1                     : "+Message);

		int keySize = size;
		int MessageSize = Message.length();

		for(int i = MessageSize; i < 999999; i++) {
			if(i % size == 0) break;	
			else Message = Message + "z";
		}
		System.out.println("Formatted Message                : "+Message);

		ArrayList<String> al = new ArrayList<String>();
		ArrayList<String> al2 = new ArrayList<String>();
		System.out.println("============================================");
		System.out.println("///////// STEP : WRITE ROW BY ROW //////////");
		System.out.println("============================================");

		String str = "";
		for(int i=1; i < Message.length()+1; i++) {
			str += Message.charAt(i-1);
			System.out.print(Message.charAt(i-1)+" ");
			if( i % size == 0 && i != 0) {
				System.out.println();
				al.add(str);
				str = "";
			}
		}
		System.out.println("============================================");
		System.out.println("////////////// STEP : PERMUTE //////////////");
		System.out.println("============================================");

		for(int i = 0; i < al.size(); i++){	
			for(int j =0; j < EncryptionKey.size(); j++)
				System.out.print(al.get(i).charAt(EncryptionKey.get(j)-1)+" ");
			System.out.println();
		}
		String EncryptionRound1 = "";
		String EncryptionRound2 = "";

		System.out.println("============================================");
		System.out.println("///////// STEP : WRITE COL BY COL //////////");
		System.out.println("============================================");

		for(int i = 0; i < keySize; i++) {
			for(int j =0; j < al.size(); j++) {
				EncryptionRound1 += al.get(j).charAt(EncryptionKey.get(i)-1);
				System.out.print(al.get(j).charAt(EncryptionKey.get(i)-1)+" ");
			}
		}
		System.out.println("\nRound One : "+EncryptionRound1);

		System.out.println("============================================");
		System.out.println("///////// STEP : WRITE ROW BY ROW //////////");
		System.out.println("============================================");

		for(int i=1; i < EncryptionRound1.length()+1; i++) {
			str += EncryptionRound1.charAt(i-1);
			System.out.print(EncryptionRound1.charAt(i-1)+" ");
			if( i % size == 0 && i != 0) {
				System.out.println();
				al2.add(str);
				str = "";
			}
		}

		System.out.println("============================================");
		System.out.println("////////////// STEP : PERMUTE //////////////");
		System.out.println("============================================");

		for(int i = 0; i < al2.size(); i++) {
			for(int j =0; j < EncryptionKey.size(); j++)
				System.out.print(al2.get(i).charAt(EncryptionKey.get(j)-1)+" ");
			System.out.println();
		}

		System.out.println("============================================");
		System.out.println("///////// STEP : WRITE COL BY COL //////////");
		System.out.println("============================================");

		for(int i = 0; i < keySize; i++) {
			for(int j =0; j < al2.size(); j++) {
				EncryptionRound2 += al2.get(j).charAt(EncryptionKey.get(i)-1);
				System.out.print(al2.get(j).charAt(EncryptionKey.get(i)-1)+" ");
			}
			//System.out.println();
		}
		System.out.println("\nRound Two : "+EncryptionRound2);

		System.out.println("============================================");
		System.out.println("//////////////// DECRYPTING ////////////////");
		System.out.println("============================================");

		String EncryptedText = EncryptionRound2;
		System.out.println("Encrypted Text : "+EncryptedText);

		int[] Enc_Key = new int[EncryptionKey.size()];
		int[] Dec_Key = new int[EncryptionKey.size()];
		System.out.print("Decryption Key : ");
		for(int i =0; i < EncryptionKey.size(); i++) {
			Enc_Key[i] = EncryptionKey.get(i);
			Dec_Key[EncryptionKey.get(i)-1] = i+1;
		}
		for(int i=0; i < Dec_Key.length; i++) System.out.print(Dec_Key[i]+" ");	
		System.out.println();
		System.out.println("============================================");
		System.out.println("///////// STEP : WRITE COL BY COL //////////");
		System.out.println("============================================");

		int colSize = Dec_Key.length;
		int rowSize = EncryptedText.length() / colSize;
		ArrayList<String> al3 = new ArrayList<String>();

		String tempStr = "";

		for(int i =0; i < rowSize; i++) {
			for(int j = i; j < EncryptedText.length(); j += rowSize) {
				tempStr += EncryptedText.charAt(j);
				System.out.print(EncryptedText.charAt(j)+" ");
			}
			System.out.println();
			al3.add(tempStr);
			tempStr = "";
		}

		System.out.println("============================================");
		System.out.println("////////////// STEP : PERMUTE //////////////");
		System.out.println("============================================");
		String DecryptionRound1 = "";
		for(int i = 0; i < al3.size(); i++) {
			for(int j =0; j < colSize; j++)	{
				DecryptionRound1 += al3.get(i).charAt(Dec_Key[j]-1);
				System.out.print(al3.get(i).charAt(Dec_Key[j]-1)+" ");
			}
			System.out.println();
		}
		System.out.println("============================================");
		System.out.println("///////// STEP : WRITE ROW BY ROW //////////");
		System.out.println("============================================");

		System.out.println("Decryption Round1 : "+DecryptionRound1);
		System.out.println();
		System.out.println("============================================");
		System.out.println("///////// STEP : WRITE COL BY COL //////////");
		System.out.println("============================================");

		al3 = new ArrayList<String>();

		for(int i =0; i < rowSize; i++) {
			for(int j = i; j < DecryptionRound1.length(); j += rowSize) {
				tempStr += DecryptionRound1.charAt(j);
				System.out.print(DecryptionRound1.charAt(j)+" ");
			}
			System.out.println();
			al3.add(tempStr);
			tempStr = "";
		}
		System.out.println("============================================");
		System.out.println("///////////// STEP : PERMUTE ///////////////");
		System.out.println("============================================");

		String DecryptionRound2 = "";
		for(int i = 0; i < al3.size(); i++) {
			for(int j =0; j < colSize; j++) {
				DecryptionRound2 += al3.get(i).charAt(Dec_Key[j]-1);
				System.out.print(al3.get(i).charAt(Dec_Key[j]-1)+" ");
			}
			System.out.println();
		}
		System.out.println("============================================");
		System.out.println("///////// STEP : WRITE ROW BY ROW //////////");
		System.out.println("============================================");

		System.out.println("Decryption Round2 : "+ DecryptionRound2);
	}
}