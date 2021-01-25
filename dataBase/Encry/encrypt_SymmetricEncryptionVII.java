// The "SymmetricEncryption" class.



public class SymmetricEncryptionVII
{
    static Console c;           // The output console


    public static String encode (String message, String t, String coder)
    {
	// KEY IS ENTERED HERE ----------------
	int a = Integer.parseInt (coder.substring (0));
	int b = Integer.parseInt (coder.substring (1));
	int c = Integer.parseInt (coder.substring (2));
	//-------------------------------------

	int keepra = a;
	int keeprb = b;
	int keeprc = c;
	for (int i = 0 ; i <= message.length () - 1 ; i++)
	{
	    int decoy = ((int) (Math.random () * 26 + 100));

	    if (message.charAt (i) == ' ')
		t = t + " ";

	    else if (i == a || i == b || i == c)
	    {
		t = t + (int) (message.charAt (i)) + decoy;

		if (i == a)
		{

		    a = a + keepra;
		}

		if (i == b)
		{
		    b = b + keeprb;
		}

		if (i == c)
		{
		    c = c + keeprc;
		}
	    }

	    else
		t = t + (int) (message.charAt (i));
	}


	return (t);
    }


    public static String shift (String t, int rack)
    {
	// REFORM LETTERS
	String word = "";
	String tempWord = "";
	int rackNum = 3;     // THE NUMBER BY WHICH THE GROUPING IS MADE
	int countNum = 0;
	for (int i = 0 ; i <= t.length () - 1 ; i++)
	{
	    if (t.charAt (i) == ' ' || i == t.length () - 1)
	    {
		if (((t.charAt (i) == ' ') && (countNum == 1 || countNum == 2)) || (i == t.length () - 1 && countNum <= rackNum))
		{


		    word = word + tempWord + t.charAt (i);
		    tempWord = "";
		}

		else
		{
		    word = word + " ";
		    countNum = 0;
		}
	    }


	    else
	    {
		tempWord = tempWord + t.charAt (i);
		countNum++;
	    }


	    if (countNum == rackNum)
	    {
		String newWord = "";

		for (int j = 0 ; j <= tempWord.length () - 1 ; j++)
		{
		    if (tempWord.charAt (j) != ' ')
			newWord = newWord + tempWord.charAt (j);
		}

		if (((Integer.parseInt (newWord) < 58 && !(Integer.parseInt (newWord) > 33 && Integer.parseInt (newWord) < 47))) || (Integer.parseInt (newWord) > 126 && !(Integer.parseInt (newWord) > 217 && Integer.parseInt (newWord) < 256 && Integer.parseInt (newWord) != 173)))
		    word = word + tempWord;
		else
		    word = word + ((char) (Integer.parseInt (tempWord)));
		countNum = 0;
		tempWord = "";
	    }
	}

	return (word);
    }



    public static String unshift (String t)
    {
	// REFORM NUMBERS
	String word = "";
	String tempWord = "";
	int countNum = 0;
	for (int i = 0 ; i <= t.length () - 1 ; i++)
	{
	    if (t.charAt (i) == ' ' || t.charAt (i) == '1' || t.charAt (i) == '2' ||
		    t.charAt (i) == '3' || t.charAt (i) == '4' || t.charAt (i) == '5' ||
		    t.charAt (i) == '6' || t.charAt (i) == '7' || t.charAt (i) == '8' ||
		    t.charAt (i) == '9' || t.charAt (i) == '0')
		word = word + t.charAt (i);

	    else
	    {
		if ((int) (t.charAt (i)) < 100 && (int) (t.charAt (i)) > 31)
		    word = word + '0';
		word = word + (int) (t.charAt (i));
	    }
	}

	return (word);
    }


    public static String decode (String message, String t, String decoder)
    {
	// KEY IS ENTERED HERE ----------------
	int a = Integer.parseInt (decoder.substring (0));
	int b = Integer.parseInt (decoder.substring (1));
	int c = Integer.parseInt (decoder.substring (2));
	//-------------------------------------

	int keepra = a;
	int keeprb = b;
	int keeprc = c;
	int n = 0;
	int o = 0;
	String collect = "";
	int main = 0;
	int collector = 0;
	String newMessage = "";
	for (int i = 0 ; i <= message.length () - 1 ; i++)
	{
	    if (message.charAt (i) == ' ')
		newMessage = newMessage + " ";

	    else if (((message.charAt (i) == '9' || message.charAt (i) == '6' || message.charAt (i) == '7' || message.charAt (i) == '8'
			    || message.charAt (i) == '4' || message.charAt (i) == '3' || message.charAt (i) == '5') && (o == 0)) || n == 1)
	    {

		collect = collect + (message.charAt (i));
		n++;

	    }

	    else if (message.charAt (i) == '1' || o == 1 || o == 2)
	    {

		collect = collect + (message.charAt (i));
		o++;

	    }

	    if (n == 2 || o == 3)
	    {
		newMessage = newMessage + (char) (Integer.parseInt (collect));
		collect = "";
		n = 0;
		o = 0;
	    }

	}

	for (int i = 0 ; i <= newMessage.length () - 1 ; i++)
	{
	    if (main == i)
	    {
		if (newMessage.charAt (i) == ' ')
		{
		    t = t + " ";
		    collector++;
		}

		else if (collector == a || collector == b || collector == c)
		{
		    t = t + (char) (newMessage.charAt (i));
		    main++;


		    if (collector == a)
			a = a + keepra;

		    if (collector == b)
			b = b + keeprb;

		    if (collector == c)
			c = c + keeprc;

		    collector++;
		}


		else
		{
		    t = t + (char) (newMessage.charAt (i));
		    collector++;
		}
		main++;
	    }
	}
	return (t);
    }


    public static void main (String[] args)
    {
	c = new Console ();

	String coder = "";
	while (!coder.equals ("111"))
	{
	    c.println();
	    c.println("-----------------------------------------------------");
	    c.println ("Enter a code: ");
	    coder = c.readLine ();
	    String message = shift (encode (c.readLine (), "", coder), 2);
	    c.println ();
	    c.println("Encrypted: ");
	    c.println (message);


	    c.getChar ();
	    Console decode = new Console ();
	    decode.println ("Enter the code: ");
	    String decoder = decode.readLine ();
	    message = (decode (unshift (message), "", decoder));
	    decode.println ("Decoded: " + message);
	}

    }
}





