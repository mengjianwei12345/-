#include "XOR.h"

XOR::XOR()
{
}


XOR::~XOR()
{
}

int XOR::xorCipher()
{
	system("CLS");

	cout << "Input message for encryption/decryption: " << endl;
	cin.ignore();
	getline(cin, xMsg);

	cout << "Please input one alphabetic character as your key: " << endl;
	cin >> xorKey;

	// Encrypt the string 
	xEMsg = encryptDecrypt(xMsg, xorKey);
	cout << "Encrypted String: " << xEMsg << endl;

	// Decrypt the string
	xDMsg = encryptDecrypt(xEMsg, xorKey);
	cout << "Decrypted String: " << xDMsg << endl;

	system("PAUSE");
	return 0;
}


string XOR::encryptDecrypt(string xMsg, char xorKey)
{
	// Calculate length of input string 
	int len = xMsg.length();

	// perform XOR operation of key 
	// with every caracter in string 
	for (int i = 0; i < len; i++)
	{
		xMsg[i] = xMsg[i] ^ xorKey;
	}

	return xMsg;
}