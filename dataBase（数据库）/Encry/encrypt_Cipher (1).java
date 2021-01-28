

public class Cipher {

    public static void main(String[] args) {
      String let = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+";
      String input = "ILOVEYOU"; //Input character
      char new_let[] = let.toCharArray();
      char new_input[] = input.toCharArray();
      int key=3; //The number of shift from left to right
      System.out.print("The encryted value of the character: "+input+" is [");
      encrypt(new_let,new_input,key);
      System.out.println("]");
      
    }
    public static void encrypt(char new_let[],char new_input[],int key){
        for(int i=0;i<new_input.length;i++){
          for(int j=0,k=0;j<new_let.length;j++){  
              
          if(new_input[i]==new_let[j]){
          if(j<new_let.length-key){ //condition for preventing variable j to be grater than the array length 
          k=j;
          System.out.print(new_let[k+key]);
          }else{
          k=(j-new_let.length)+key; //The index number minus the array length plus the number of shift
          System.out.print(new_let[k]); //so that it will turn to the 1st array value
          }
          
          } 
        }
      }
    }
}
