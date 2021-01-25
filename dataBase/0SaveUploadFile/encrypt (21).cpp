#include "md5.h"
#include <fstream>
#include <sstream>
#include <iomanip>
#include <conio.h>
#include <vector>
#include <string.h> 

vector<string> userData;
string decToHexString(UINT32 ans);
void loadData();
void printResult(UINT32 result[4]);
void login();
void signin();

MD5::MD5(string str) {
	message = str;
	generateT();
	//  initialize the result to be IV
	for (int i = 0; i < 4; ++i)
		result[i] = IV[i];
}
void MD5::generateT() {
	//  pay attention that the index of T is begin with 0
	for (int i = 0; i < 64; ++i) {
		T[i] = (UINT32)(pow(2, 32)*abs(sin(i + 1)));
	}
}

UINT32 MD5::F(UINT32 B, UINT32 C, UINT32 D) {
	return (B & C) | (~B & D);
}
UINT32 MD5::G(UINT32 B, UINT32 C, UINT32 D) {
	return (B & D) | (C & ~D);
}
UINT32 MD5::H(UINT32 B, UINT32 C, UINT32 D) {
	return B ^ C ^ D;
}
UINT32 MD5::I(UINT32 B, UINT32 C, UINT32 D) {
	return C ^ (B | ~D);
}

BYTE* MD5::expand() {
	const BYTE* str = message.c_str();
	int len = strlen(str);
	//  1(byte) is first expand 10000000 and promise that it expands at least 1 bit
	//  8(bytes) is the binary bits(64bits) of initial message len
	UINT32 zeroN = 64 - (len + 1 + 8) % 64;
	BYTE* res = new BYTE[len + 1 + zeroN + 8 + 1];
	for (int i = 0; i < len; ++i)
		res[i] = str[i];
	res[len] = 0x80;  //  10000000
	for (int i = len + 1; i < len + 1 + zeroN; ++i)
		res[i] = 0;
	//  len is calculate as bytes and when expand it should be calculate as bits and thus multiply 8
	UINT64 binaryLen = (UINT64)len * 8;
	memcpy(res + len + 1 + zeroN, &binaryLen, 8);
	return res;
}
//  get index j of X[k]
UINT32 MD5::getIndexOfX(UINT32 j, UINT32 k) {
	switch (j) {
	case 0:
		return k;
	case 1:
		return (1 + 5 * k) % 16;
	case 2:
		return (5 + 3 * k) % 16;
	case 3:
		return (7 * k) % 16;
	}
}
void MD5::rotate(UINT32 &A, UINT32 &B, UINT32 &C, UINT32 &D) {
	UINT32 temp = A;
	A = D;
	D = C;
	C = B;
	B = temp;
}
UINT32 MD5::getFunctionRes(UINT32 cycleN, UINT32 B, UINT32 C, UINT32 D) {
	switch (cycleN) {
	case 0:
		return F(B, C, D);
	case 1:
		return G(B, C, D);
	case 2:
		return H(B, C, D);
	case 3:
		return I(B, C, D);
	}
}

UINT32 MD5::shiftLeft(UINT32 src, UINT32 shiftN) {
	return (src << shiftN) | (src >> (32 - shiftN));
}
void MD5::processEndian() {
	UINT32 src;
	for (int i = 0; i < 4; i++) {
		//  0x01234567 to 0x67452301
		src = result[i];
		memcpy(result + i, ((BYTE *)&src) + 3, 1);
		//  UINT32->4 bytes
		//  void *memcpy(void *dest, const void *src, size_t n);
		//  data where saved in memory src copy to memory dest
		memcpy((BYTE*)(result + i) + 1, ((BYTE*)&src) + 2, 1);
		memcpy((BYTE*)(result + i) + 2, ((BYTE*)&src) + 1, 1);
		memcpy((BYTE*)(result + i) + 3, ((BYTE*)&src), 1);
	}
}
void MD5::getResult() {
	BYTE* expandMessage = expand();
	int n = strlen(expandMessage);
	UINT32 A, B, C, D, tempA;
	//  4 cycles
	for (int i = 0; i < n - 1; i += 64) {
		//  Lth block
		int L = i / 64;
		// result(q) is the value for CV(q+1)
		A = result[0];
		B = result[1];
		C = result[2];
		D = result[3];
		//  4 cycles
		for (int j = 0; j < 4; ++j) {
			//  16 steps
			for (int k = 0; k < 16; ++k) {
				UINT32 funRes = getFunctionRes(j, B, C, D);
				//  *4 is because 4bytes is UINT32
				UINT32 indexOfX = i + getIndexOfX(j, k) * 4;  
				//  when calculate, all use the little endian and swap the result at the end
				UINT32 shiftRes = shiftLeft((A + funRes + *(UINT32*)(expandMessage + indexOfX)
					+ T[j * 16 + k]), s[j][k % 4]);
				A = B + shiftRes;
				rotate(A, B, C, D);
			}
		}
		// UINT32 r = (UINT32)pow(2, 32);
		result[0] = result[0] + A;
		result[1] = result[1] + B;
		result[2] = result[2] + C;
		result[3] = result[3] + D;
	}
	processEndian();
	delete expandMessage;
}

string decToHexString(UINT32 ans) {
	string str = "";
	string tempstr;
	stringstream ss;
	UINT32 a, b;
	b = ans;
	while (b != 0) {
		a = b % 16;
		b = b / 16;
		switch (a) {
		case 0: case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:case 9:
			ss.clear();
			tempstr = "";
			ss << a;
			ss >> tempstr;
			str += tempstr;
			break;
		case 10:
			str += "A";
			break;
		case 11:
			str += "B";
			break;
		case 12:
			str += "C";
			break;
		case 13:
			str += "D";
			break;
		case 14:
			str += "E";
			break;
		case 15:
			str += "F";
			break;
		}
	}
	int n = str.length();
	if (n < 8) {
		str.insert(n, 8 - n, '0');
	}
	string reverseStr = "12345678";
	for (int i = 0; i < 8; ++i)
		reverseStr[i] = str[7 - i];
	return reverseStr;
}
//  load user information from test.txt
void loadData() {
	userData.clear();
	string str;
	ifstream infile("../file/test.txt");
	if (!infile.is_open()) {
		cout << "can not open file" << endl;
		return;
	}
	while (getline(infile, str)) {
		if (str.length() == 0) return;
		userData.push_back(str);
	}
}

void printResult(UINT32 result[4]) {
	cout << "result: ";
	for (int i = 0; i < 4; ++i) {
		cout << decToHexString(result[i]) << " ";
	}
	cout << endl;
}

void login() {
	loadData();
	string name = "";
	char ch;
	string password = "";
	string str;
	cout << "input name: ";
	cin >> name;
	cout << "input password: ";
	// stimulate the password input(hide)
	while ((ch = _getch()) != '\r') {
		password += ch;
		_putch('*');
	}
	ifstream infile("../file/test.txt");
	if (!infile.is_open()) {
		cout << "can not open file" << endl;
		return;
	}
	string nameSaved = "";
	string passSaved = "";
	while (getline(infile, str)) {
		nameSaved = "";
		passSaved = "";
		if (str.length() == 0) break;
		int i = 0;
		while (i != str.length()) {
			if (str[i] == '|') break;
			else nameSaved += str[i];
			i++;
		}
		if (name != nameSaved)
			continue;
		i++;
		while (i != str.length()) {
			passSaved += str[i];
			++i;
		}
		MD5 md5(password);
		md5.getResult();
		string passConfirm = "";
		for (int i = 0; i < 4; ++i) {
			passConfirm += decToHexString(md5.result[i]);
			if (i < 3) passConfirm += " ";
		}
		cout << "\nmd5 password: " << passConfirm << endl;
		if (passConfirm == passSaved) {
			cout << "Login Success£º Hello " + name + "!" << endl;
			return;
		}
		else {
			cout << "Wrong PassWord!" << endl;
			return;
		}
	}
	cout << "\nSign in first please!" << endl;
	infile.close();
}
void signin() {
	loadData();
	string name = "";
	char ch;
	string password = "";
	string str;
	cout << "input name: ";
	cin >> name;
	cout << "input password: ";
	// stimulate the password input(hide)
	while ((ch = _getch()) != '\r') {
		password += ch;
		_putch('*');
	}
	ofstream outFile("../file/test.txt");
	if (!outFile.is_open()) {
		cout << "\nCan not open file to write!" << endl;
		return;
	}
	MD5 md5(password);
	md5.getResult();
	string md5pw = "";
	for (int i = 0; i < 4; ++i) {
		md5pw += decToHexString(md5.result[i]);
		if (i < 3) md5pw += " ";
	}
	cout << "\nmd5 password: " << md5pw << endl;
	string newUser = name + "|" + md5pw;
	userData.push_back(newUser);
	for (int i = 0; i < userData.size(); ++i) {
		outFile << userData[i] << endl;
	}
	outFile.close();
}

int main() {
	loadData();
	int choose = 0;
	while (1) {
		cout << "\nchoose (0 or 1) :" << endl;
		cout << "0: sign in" << endl;
		cout << "1: login in" << endl;
		cin >> choose;
		if (choose == 1) {
			login();
		}
		else {
			signin();
		}
	}
	system("pause");
	return 0;
}
