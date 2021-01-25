#include "Caesar.h"
#include <string>

using namespace std;

Caesar::Caesar() {
	const int length = 26;
	_key = 3;

	for (int i = 0; i < length; i++) {
		_alphabet.push_back(char(i + 97));
	}

}

string Caesar::Decrypt(string encrypted_message) {
	string  decrypted_message;
	int length = _alphabet.length();

	for (int i = 0; i < encrypted_message.length(); i++) {
		if (encrypted_message[i] == ' ') {
			decrypted_message += encrypted_message[i];
			continue;
		}
		decrypted_message += _alphabet[(_alphabet.find(encrypted_message[i]) - _key + length) % length];
	}

	return decrypted_message;
}

string Caesar::Encrypt(string message) {
	string  encrypted_message;

	for (int i = 0; i < message.length(); i++) {
		if (message[i] == ' ') {
			encrypted_message += message[i];
			continue;
		}
		encrypted_message += _alphabet[(_alphabet.find(message[i]) + _key) % _alphabet.length()];
	}

	return encrypted_message;
}
