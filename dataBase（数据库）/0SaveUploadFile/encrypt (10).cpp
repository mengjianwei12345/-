#include "Ceasar.h"

using namespace std;

Ceasar::Ceasar()
{
}


Ceasar::~Ceasar()
{
}

int Ceasar::ceasarCipher()
{
	system("CLS");

	cout << "Please choose following options:\n";
	cout << "1 = Encrypt a message.\n";
	cout << "2 = Decrypt a message.\n";
	cin >> x;

	switch (x)
	{
		// First case for encrypting a string
	case 1:
		cout << "Please enter a string: ";

		cin.ignore();
		getline(cin, str);

		for (i = 0; (i < 100 && str[i] != '\0'); i++)
		{
			str[i] += 2; // The key for encryption is 3 that is added to ASCII value
		}

		cout << "\nEncrypted string: " << str << endl;

		cout << "Do you wish to save this into a file? (0 = Yes, 1 = No)" << endl;
		cin >> case1answer;

		switch (case1answer)
		{
		case 0:
			cout << "Name your file here: ";
			cin >> filename;
			filename += "Ceaser.txt";

			output.open(filename.c_str());
			output << str;
			output.close();
			
			break;

		default:
			break;
		}

		break;

		// Second case for decrypting a string
	case 2:
		cout << "Do you wish to decrypt a string or from a file? (0 = string, 1 = file)" << endl;
		cin >> case2answer;

		switch (case2answer)
		{
		case 0:
			cout << "Please enter string: ";
			cin >> str;

			break;

		case 1:
			cout << "Enter file name: ";
			cin >> filename;
			filename += "Ceaser.txt";

			input.open(filename.c_str());

			if (input.is_open())
			{
				while (getline(input, line))
				{
					str += line;
				}

				input.close();
			}
			else
			{
				cout << "Failure opening file." << endl;
			}

			for (i = 0; (i < 100 && str[i] != '\0'); i++)
			{
				str[i] = str[i] - 2; // The key for encryption is 3 that is subtracted to ASCII value
			}

			cout << "\nDecrypted string: " << str << endl;

			break;
		}

		break;

	default:
		cout << "\nInvalid Input!\n";

		break;
	}

	system("PAUSE");
	return 0;
}