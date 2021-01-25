#include <iostream>
#include <bitset>
#include <cstdlib>
using namespace std;

bitset<48> subkeys[16];
//  initial IP permutation table
//  notice: values in IP are from 1 to 64
int IP[] = {
	58, 50, 42, 34, 26, 18, 10, 2,
	60, 52, 44, 36, 28, 20, 12, 4,
	62, 54, 46, 38, 30, 22, 14, 6,
	64, 56, 48, 40, 32, 24, 16, 8,
	57, 49, 41, 33, 25, 17, 9, 1,
	59, 51, 43, 35, 27, 19, 11, 3,
	61, 53, 45, 37, 29, 21, 13, 5,
	63, 55, 47, 39, 31, 23, 15, 7
};
int IPInverse[] = {
	40, 8, 48, 16, 56, 24, 64, 32,
	39, 7, 47, 15, 55, 23, 63, 31,
	38, 6, 46, 14, 54, 22, 62, 30,
	37, 5, 45, 13, 53, 21, 61, 29,
	36, 4, 44, 12, 52, 20, 60, 28,
	35, 3, 43, 11, 51, 19, 59, 27,
	34, 2, 42, 10, 50, 18, 58, 26,
	33, 1, 41,  9, 49, 17, 57, 25
};
int P1[] = {
	57, 49, 41, 33, 25, 17, 9,
	1, 58, 50, 42, 34, 26, 18,
	10,  2, 59, 51, 43, 35, 27,
	19, 11,  3, 60, 52, 44, 36,
	63, 55, 47, 39, 31, 23, 15,
	7, 62, 54, 46, 38, 30, 22,
	14,  6, 61, 53, 45, 37, 29,
	21, 13,  5, 28, 20, 12,  4
};
//  56 bits to 48 bits
int P2[] = {
	14, 17, 11, 24,  1,  5,
	3, 28, 15,  6, 21, 10,
	23, 19, 12,  4, 26,  8,
	16,  7, 27, 20, 13,  2,
	41, 52, 31, 37, 47, 55,
	30, 40, 51, 45, 33, 48,
	44, 49, 39, 56, 34, 53,
	46, 42, 50, 36, 29, 32
};
//  32 bits to 48 bits
int expand[] = {
	32,  1,  2,  3,  4,  5,
	4,  5,  6,  7,  8,  9,
	8,  9, 10, 11, 12, 13,
	12, 13, 14, 15, 16, 17,
	16, 17, 18, 19, 20, 21,
	20, 21, 22, 23, 24, 25,
	24, 25, 26, 27, 28, 29,
	28, 29, 30, 31, 32,  1
};
// feistel final permutation
int fp[] = {
	16,  7, 20, 21,
	29, 12, 28, 17,
	1, 15, 23, 26,
	5, 18, 31, 10,
	2,  8, 24, 14,
	32, 27,  3,  9,
	19, 13, 30,  6,
	22, 11,  4, 25
};
//  48 bits to 32 bits
int boxes[8][4][16] = {
	{
		{ 14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7 },
		{ 0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8 },
		{ 4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0 },
		{ 15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13 }
	},
	{
		{ 15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10 },
		{ 3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5 },
		{ 0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15 },
		{ 13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9 }
	},
	{
		{ 10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8 },
		{ 13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1 },
		{ 13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7 },
		{ 1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12 }
	},
	{
		{ 7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15 },
		{ 13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9 },
		{ 10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4 },
		{ 3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14 }
	},
	{
		{ 2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9 },
		{ 14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6 },
		{ 4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14 },
		{ 11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3 }
	},
	{
		{ 12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11 },
		{ 10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8 },
		{ 9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6 },
		{ 4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13 }
	},
	{
		{ 4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1 },
		{ 13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6 },
		{ 1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2 },
		{ 6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12 }
	},
	{
		{ 13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7 },
		{ 1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2 },
		{ 7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8 },
		{ 2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11 }
	}
};
int shiftBits[] = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

//  functions
bitset<64> charToBit(const char plain[8]);
bitset<64> initialPermutation(bitset<64> plainBits);
void getSubKeys(bitset<64> key);
void getDecryptionSubKeys();
bitset<64> transform16(bitset<64> text);
bitset<32> feistel(bitset<32> lastRight, bitset<48> oneSubKey);
bitset<32> sBoxCompress(bitset<48> xorResult);
bitset<4> intToBit(int value);
bitset<56> PC1Permutation(bitset<64> key);
bitset<48> PC2Permutation(bitset<56> tempkey);
bitset<28> LSCirculation(bitset<28> halftemp, int shiftBit);
bitset<64> finalPermutation(bitset<64> afterTrans);

//  change the char array to 64 bits
bitset<64> charToBit(const char plain[8]) {
	bitset<64> bits;
	for (int i = 0; i < 8; ++i) {
		for (int j = 0; j < 8; ++j) {
			//  here save the ascii binary bits of "12345678"
			bits[i * 8 + j] = ((plain[i] >> j) & 1);
		}
	}
	return bits;
}
//  initial permutation
bitset<64> initialPermutation(bitset<64> plainBits) {
	bitset<64> result;
	for (int i = 0; i < 64; ++i) {
		result[i] = plainBits[IP[i] - 1];
	}
	return result;
}
//  16 times transformation
bitset<64> transform16(bitset<64> text) {
	//  call feistel function
	bitset<32> lefts[17], rights[17];
	bitset<64> result;
	bitset<32> feistelRes;
	//  L0
	for (int i = 0; i < 32; ++i) {
		lefts[0][i] = text[i];
	}
	//  R0
	for (int i = 32; i < 64; ++i) {
		rights[0][i - 32] = text[i];
	}
	int count = 0;
	while (count < 16) {
		count++;
		//  Li = R£¨i-1)
		lefts[count] = rights[count - 1];
		feistelRes = feistel(rights[count - 1], subkeys[count - 1]);
		for (int i = 0; i < 32; ++i) {
			rights[count] = lefts[count - 1] ^ feistelRes;
		}
	}
	//  result = R16L16
	for (int i = 0; i < 32; ++i)
		result[i] = rights[16][i];
	for (int i = 0; i < 32; ++i)
		result[i + 32] = lefts[16][i];
	return result;
}
//  feistel 
bitset<32> feistel(bitset<32> lastRight, bitset<48> oneSubKey) {
	//  call sBoxCompress
	//  expand and make lastRight to 48 bits
	bitset<48> expandRight;
	for (int i = 0; i < 48; ++i) {
		expandRight[i] = lastRight[expand[i] - 1];
	}
	bitset<48> xorResult = expandRight ^ oneSubKey;
	bitset<32> fromSBox = sBoxCompress(xorResult);
	bitset<32> result;
	//  there is a P-permutation in the last
	for (int i = 0; i < 32; ++i) {
		result[i] = fromSBox[fp[i] - 1];
	}
	return result;
}
//  change 48 bits to 32 bits by using s-boxs
bitset<32> sBoxCompress(bitset<48> xorResult) {
	bitset<32> result;
	bitset<6> subGroups[8];
	for (int i = 0; i < 48; ++i) {
		subGroups[i / 6][i % 6] = xorResult[i];
	}
	int indexI, indexJ;
	int values[8];
	for (int i = 0; i < 8; ++i) {
		indexI = subGroups[i][0] * 2 + subGroups[i][5];
		indexJ = subGroups[i][1] * 8 + subGroups[i][2] * 4 + subGroups[i][3] * 2
			+ subGroups[i][4];
		values[i] = boxes[i][indexI][indexJ];
	}
	for (int i = 0; i < 8; ++i) {
		bitset<4> temp = intToBit(values[i]);
		for (int k = 0; k < 4; ++k)
			result[i * 4 + k] = temp[k];
	}
	return result;
}
//  decimal to binary(4 bits)
bitset<4> intToBit(int value) {
	bitset<4> result;
	for (int i = 0; i < 4; ++i) {
		result[i] = (value >> 1) & 1;
	}
	return result;
}
//  inverse permutation in the last
bitset<64> finalPermutation(bitset<64> afterTrans) {
	bitset<64> result;
	for (int i = 0; i < 64; ++i) {
		result[i] = afterTrans[IPInverse[i] - 1];
	}
	return result;
}
//  get 16 sub keys and save them in subkeys
void getSubKeys(bitset<64> key) {
	//  PC-1 permutation
	bitset<56> keyAfterPC1 = PC1Permutation(key);
	bitset<28> tempLeft, tempRight;
	bitset<28> LSlefts[17], LSrights[17];
	bitset<56> keyAfterShift;
	for (int i = 0; i < 28; ++i)
		tempLeft[i] = keyAfterPC1[i];
	for (int i = 0; i < 28; ++i)
		tempRight[i] = keyAfterPC1[i + 28];
	LSlefts[0] = tempLeft;
	LSrights[0] = tempRight;
	int count = 0;
	while (count < 16) {
		count++;
		LSlefts[count] = LSCirculation(LSlefts[count - 1], shiftBits[count - 1]);
		LSrights[count] = LSCirculation(LSrights[count - 1], shiftBits[count - 1]);
		for (int i = 0; i < 28; ++i) keyAfterShift[i] = LSlefts[count][i];
		for (int i = 0; i < 28; ++i) keyAfterShift[i + 28] = LSrights[count][i];
		//  now get the 56 bits temp key
		subkeys[count - 1] = PC2Permutation(keyAfterShift);
	}
}
//  PC-1 permutation
bitset<56> PC1Permutation(bitset<64> key) {
	bitset<56> result;
	for (int i = 0; i < 56; ++i) {
		result[i] = key[P1[i] - 1];
	}
	return result;
}
//  PC-2 permutation
bitset<48> PC2Permutation(bitset<56> tempkey) {
	bitset<48> result;
	for (int i = 0; i < 48; ++i) {
		result[i] = tempkey[P2[i] - 1];
	}
	return result;
}
//  left shift
bitset<28> LSCirculation(bitset<28> halftemp, int shiftBit) {
	bitset<28> result;
	//  left shift 1 or 2 bits
	for (int i = 0; i < 28; ++i) {
		result[i] = halftemp[(i + shiftBit) % 28];
	}
	return result;
}
//  decryption subkeys
void getDecryptionSubKeys() {
	//  PC-1 permutation
	bitset<48> temp[16];
	for (int i = 0; i < 16; ++i) {
		temp[i] = subkeys[15 - i];
	}
	for (int i = 0; i < 16; ++i) {
		subkeys[i] = temp[i];
	}
}
bitset<64> encryption(bitset<64> plainBits, bitset<64> keyBits) {
	getSubKeys(keyBits);
	bitset<64> afterInitialPer = initialPermutation(plainBits);
	bitset<64> result = transform16(afterInitialPer);
	bitset<64> finalResult = finalPermutation(result);
	return finalResult;
}
bitset<64> decryption(bitset<64> cipher, bitset<64> keyBits) {
	getDecryptionSubKeys();
	bitset<64> afterInitialPer = initialPermutation(cipher);
	bitset<64> result = transform16(afterInitialPer);
	bitset<64> finalResult = finalPermutation(result);
	return finalResult;
}
int main() {
	char plainText[] = "12345678";
	char key[] = "89674523";
	bitset<64> plainBits = charToBit(plainText);
	bitset<64> keyBits = charToBit(key);

	bitset<64> cipher = encryption(plainBits, keyBits);
	bitset<64> plain = decryption(cipher, keyBits);

	cout << "initial plain:         " << plainBits << endl;
	cout << "cipher text:           " << cipher << endl;
	cout << "decryption plain text: " << plain << endl;
	system("pause");
	return 0;
}
