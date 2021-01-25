#include "RSA.h"

// Constructors
RSA::RSA()
{
}

// Destructor
RSA::~RSA()
{
}

// Functions
int RSA::rsaCipher()
{
	system("CLS");

	cout << "Enter first prime number: ";
	cin >> x;

	flag = prime(x);
	if (flag == 0)
	{
		cout << "\nInvalid input: " << x << "\n";

		system("PAUSE");
		exit(0);
	}

	cout << "\nEnter a second prime number: ";
	cin >> y;

	flag = prime(y);
	if (flag == 0 || x == y)
	{
		cout << "\nInvalid input: " << y << "\n";

		system("PAUSE");
		exit(0);
	}

	cout << "\nEnter string to encrypt: ";
	cin.ignore();
	getline(cin, msg);

	for (i = 0; msg[i] != NULL; i++)
	{
		m[i] = msg[i];
	}

	n = x * y;
	t = (x - 1) * (y - 1);

	encryption_key();
	cout << "\nPossible values for e and d:\n";

	for (i = 0; i < j - 1; i++)
	{
		cout << "\n" << e[i] << "\t" << d[i];
	}

	encrypt();
	decrypt();

	system("PAUSE");
	return 0;
}

int RSA::prime(long int pr)
{
	int i;
	j = sqrt(pr);

	for (i = 2; i <= j; i++)
	{
		if (pr % i == 0)
		{
			return 0;
		}
	}

	return 1;
}

void RSA::encryption_key()
{
	int k = 0;

	for (i = 2; i < t; i++)
	{
		if (t % i == 0)
		{
			continue;
		}

		flag = prime(i);
		if (flag == 1 && i != x && i != y)
		{
			e[k] = i;
			flag = cd(e[k]);

			if (flag > 0)
			{
				d[k] = flag;
				k++;
			}

			if (k == 99)
			{
				break;
			}
		}
	}
}

long int RSA::cd(long int a)
{
	long int k = 1;
	while (1)
	{
		k = k + t;
		if (k % a == 0)
		{
			return(k / a);
		}
	}
}

void RSA::encrypt()
{
	long int pt, ct, key = e[0], k, len;
	i = 0;
	len = msg.length();

	while (i != len)
	{
		pt = m[i];
		pt = pt - 96;
		k = 1;
		for (j = 0; j < key; j++)
		{
			k = k * pt;
			k = k % n;
		}

		temp[i] = k;
		ct = k + 96;
		en[i] = ct;

		i++;
	}

	en[i] = -1;

	cout << "\n\nThe encrypted message is: ";
	for (i = 0; en[i] != -1; i++)
	{
		cout << en[i];
	}

	cout << endl;
}

void RSA::decrypt()
{
	long int pt, ct, key = d[0], k;
	i = 0;
	while (en[i] != -1)
	{
		ct = temp[i];
		k = 1;
		for (j = 0; j < key; j++)
		{
			k = k * ct;
			k = k % n;
		}

		pt = k + 96;
		m[i] = pt;

		i++;
	}

	m[i] = -1;

	cout << "\nThe decrypted message is: ";
	for (i = 0; m[i] != -1; i++)
	{
		cout << m[i];
	}

	cout << endl;
}