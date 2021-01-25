

class RailFenceCipher{
	public static void main(String args[]){
		String msg="hellorailfencecipher";
		int depth=2;
		String enc = encode(msg, depth); 
		String dec = decode(enc, depth);
		System.out.println("simulation of Railfence Cipher"); 
		System.out.println("input message : " + msg);
		System.out.println("encoded message : " + enc); 
		System.out.printf( "decoded message : " + dec);
	}

	public static String encode(String msg,int depth){
		int r=depth;
		int l=msg.length();
		int c=l/r;
		int k=0;
		char mat[][]=new char[r][c];
		String enc="";
		for(int i=0;i<c;i++){
			for(int j=0;j<r;j++){
				if(k!=l)
				mat[j][i]=msg.charAt(k++);
				else
				mat[j][i]='X';
			}
		}
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				enc=enc+mat[i][j];
			}
		}
		return enc;
	}

	public static String decode(String encd,int depth){
		int r=depth;
		int l=encd.length();
		int c=l/r;
		int k=0;
		String dec="";
		char mat[][]=new char[r][c];
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				mat[i][j]=encd.charAt(k++);
			}
		}
		for(int i=0;i<c;i++){
			for(int j=0;j<r;j++){
				dec=dec+mat[j][i];
			}
		}
		return dec;

	}
}