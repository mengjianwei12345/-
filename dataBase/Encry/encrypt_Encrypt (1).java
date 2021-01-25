/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */







/**
 *
 * @author obyobuora
 */
public class Encrypt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String s;
        String out = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        System.out.println("Give us the msg ");
        s = br.readLine();
        for(int i = 0;i< s.length();i++){
           int ascii = (int) s.charAt(i);
           //System.out.println(ascii);
           out +=   (char) (ascii + 3) ;    // a +3 = d , d - 3 = a !
            
        }
        System.out.println(out);
        
    }
    
}
