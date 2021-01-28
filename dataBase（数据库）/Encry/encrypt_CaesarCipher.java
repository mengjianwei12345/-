public class CaesarCipher {
    public static String encryptCaesarCipher(String plaintext, int offset)
    {
        plaintext = plaintext.toUpperCase();

        if(offset >= 26 || offset <= -26)
        {
            offset = offset % 26;
        }

        if(offset == 0)
        {
            return plaintext;
        }

        String s = "";
        char ch;
        int chNum;
        int newChNum;

        for(int i = 0; i < plaintext.length(); i++)
        {
            if(plaintext.charAt(i) != 32)//if char is not a space
            {
                chNum = plaintext.charAt(i);
                newChNum = chNum + offset;

                if(newChNum > 90)
                {
                    newChNum -= 26;
                }

                if(newChNum < 65)
                {
                    newChNum += 26;
                }
                ch = (char)newChNum;

                s += ch;
            }
            else
            {
                s += " ";
            }
        }

        return s;
    }
}
