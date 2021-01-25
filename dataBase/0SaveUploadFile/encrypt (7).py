"""
Implements AES 128 Encryption
Private key encryption
  Fast and efficient algorithm
  Key must remain a secret, and both parties require the key
Uses Cipher Block Chaining (CBC) over Electronic CodeBook (ECB)
  CBC: Block C depends not only on the outcome of Block B, but also Block A
    i.e. if Block C == Block A, the outputs will still be different
  ECB: Each Block with the same values, have the same encrypted output
Uses a random initialization vector to xor with the first Block of data
  This causes the same plaintext to output a different cipher text
  Does not have to remain secret as it should only be used once
This code is susceptible to Padding Oracle
  Padding Oracle: https://www.youtube.com/watch?v=aH4DENMN_O4 @10:00
    To mitigate, use Message Authentication Code (MAC)
Written by Michael Schuetze on 3/13/2020.
"""
from base64 import b64encode, b64decode

# Will need to download pycryptodome
# https://pypi.org/project/pycryptodome/
from Crypto.Cipher import AES
from Crypto.Random import get_random_bytes
from Crypto.Util.Padding import pad, unpad


class AESEncryption:

    __BLOCK_LENGTH = 128
    __KEY_LENGTH = 128  # 192, 256

    def __init__(self):
        # Only need to generate secret key once
        self.__sec_key = get_random_bytes(AESEncryption.__KEY_LENGTH // 8)  # Division converts bits to bytes

    def encode(self, plaintext):
        """
        Encrypts an plaintext message
        :param plaintext: message to be encrypted
        :return: 16 byte initialization vector concatenated to the front of the cipher text
        """
        # Need to generate initialization vector for every encoding
        iv = get_random_bytes(AESEncryption.__BLOCK_LENGTH // 8)  # Division converts bits to bytes

        # AES - Advanced Encryption Standard
        # CBC - Cipher Block Chaining
        # PKCS7 - Padding Standard to pad data up to a 255 byte (2040 bit) block
        #         1 byte missing: 0x01 is added
        #         2 byte missing: 0x0202 is added
        #         ...
        #         8 byte missing: 0x0808080808080808 is added
        cipher = AES.new(self.__sec_key, AES.MODE_CBC, iv)
        encoded = cipher.encrypt(pad(plaintext.encode('utf-8'), 16, 'pkcs7'))

        # Attach the initialization vector to the encoding so the decoding process has access to it
        return b64encode(iv + encoded)

    def decode(self, iv_cipher):
        """
        Decrypts an encoded message
        :param iv_cipher: 16 byte initialization vector concatenated to the front of the cipher text
        :return: String containing the original encrypted message
        """
        iv_cipher = b64decode(iv_cipher)

        # Remove initialization vector from the front of the cipher text
        iv = iv_cipher[:AESEncryption.__BLOCK_LENGTH // 8]  # Division converts bits to bytes
        ciphertext = iv_cipher[AESEncryption.__BLOCK_LENGTH // 8:]

        # AES - Advanced Encryption Standard
        # CBC - Cipher Block Chaining
        # PKCS7 - Padding Standard to pad data up to a 255 byte (2040 bit) block
        #         1 byte missing: 0x01 is added
        #         2 byte missing: 0x0202 is added
        #         ...
        #         8 byte missing: 0x0808080808080808 is added
        cipher = AES.new(self.__sec_key, AES.MODE_CBC, iv)
        return unpad(cipher.decrypt(ciphertext), 16, 'pkcs7').decode()


def bytes_to_hex(bytes):
    bytes = bytes.hex().upper()
    return ' '.join(a+b for a,b in zip(bytes[::2], bytes[1::2]))


def main():
    encryption = AESEncryption()
    sec_key = encryption._AESEncryption__sec_key

    plain_text = "Hello World!"

    for rounds in range(3):
        encoded = encryption.encode(plain_text)
        decoded = encryption.decode(encoded)

        # Remove initialization vector from the front of the cipher text
        encoded = b64decode(encoded)
        iv = encoded[:AESEncryption._AESEncryption__BLOCK_LENGTH // 8]  # Division converts bits to bytes
        cipher_text = encoded[AESEncryption._AESEncryption__BLOCK_LENGTH // 8:]
        cipher_text_b64 = b64encode(cipher_text)

        print("-------------------------------------------")
        print("Init Vector: " + bytes_to_hex(iv))
        print("Secret Key:  " + bytes_to_hex(sec_key))
        print("Encrypted:   " + bytes_to_hex(cipher_text))
        print("Decrypted:   " + bytes_to_hex(decoded.encode()))
        print("-------------------------------------------")
        print("Plain Text:  " + plain_text)
        print("Encrypted:   " + cipher_text_b64.decode())
        print("Decrypted:   " + decoded)
        print("-------------------------------------------")

if __name__ == "__main__":
    main()
