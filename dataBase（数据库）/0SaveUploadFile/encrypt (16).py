import binascii


class ChaCha20:
    """
    Main class for encryption and decryption with ChaCha20 Encryption Algorithm.
    """

    def __init__(self, key, nonce, counter):
        self.key = key
        self.nonce = nonce
        self.counter = counter

    def rotate_left(self, val, n):
        """
        Perform left rotation operation.

        :param val: input 32 bit unsigned integer
        :param n: shift value
        :return: shift output
        """
        return ((val << n) & 0xffffffff) | (val >> (32 - n))

    def chacha_quarter_round(self, a, b, c, d):
        """
        Perform ChaCha Quarter Round operation
        :param a: test vector element
        :param b: test vector element
        :param c: test vector element
        :param d: test vector element
        :return: output matrix
        """
        a = (a + b) & 0xffffffff
        d ^= a
        d = self.rotate_left(d, 16)
        c = (c + d) & 0xffffffff
        b ^= c
        b = self.rotate_left(b, 12)
        a = (a + b) & 0xffffffff
        d ^= a
        d = self.rotate_left(d, 8)
        c = (c + d) & 0xffffffff
        b ^= c
        b = self.rotate_left(b, 7)
        return [hex(a), hex(b), hex(c), hex(d)]

    def quarter_round_on_the_cha_cha_state(self, chacha_state, a, b, c, d):
        """
        Perform Quarter Round on the ChaCha State operation.

        :param chacha_state: input chacha state list
        :param a: chacha state position
        :param b: chacha state position
        :param c: chacha state position
        :param d: chacha state position
        :return: modified chacha state list
        """
        y = self.chacha_quarter_round(chacha_state[a], chacha_state[b], chacha_state[c], chacha_state[d])
        chacha_state[a] = y[0]
        chacha_state[b] = y[1]
        chacha_state[c] = y[2]
        chacha_state[d] = y[3]

        for n in range(len(chacha_state)):
            if isinstance(chacha_state[n], str):
                chacha_state[n] = int(chacha_state[n], 16)

        return chacha_state

    def swap_endianness(self, x):
        """
        Perform swap endianess operation
        :param x: a number to modify
        :return: modified x 
        """
        return (((x << 24) & 0xFF000000) |
                ((x << 8) & 0x00FF0000) |
                ((x >> 8) & 0x0000FF00) |
                ((x >> 24) & 0x000000FF))

    def serialize(self, string, length_in_bytes):
        """
        Perform serialization
        :param string: string to serialize
        :param length_in_bytes: expected length of string in bytes
        :return: list of serialized elements
        """
        str_list = []
        for i in range(0, length_in_bytes * 2, 8):
            str_list.append(hex(self.swap_endianness(int(string[i:i + 8], 16))))
        for n in range(len(str_list)):  # remove '0x' from hex
            str_list[n] = str_list[n][2:]
        return str_list

    def add_padding(self, list):
        """
        Make sure each list element is exactly the length of 8 - add padding in case there are some '0's cut off at the beginnig
        :param list: string to serialize
        :return: string formed from joined list elements
        """
        return ''.join([i.rjust(8, '0') for i in list])

    def to_chacha_state(self):
        """
        Form chacha state based on a key, noncce and counter provided in a constructor
        :return: chacha state
        """
        constants = [hex(0x61707865), hex(0x3320646e), hex(0x79622d32), hex(0x6b206574)]
        chacha_state = constants + self.serialize(self.key, 32) + [hex(self.counter)] + self.serialize(self.nonce, 12)
        for n in range(len(chacha_state)):
            chacha_state[n] = int(chacha_state[n], 16)
        return chacha_state

    def inner_block(self, chacha_state):
        """
        Perform inner block function - successively call Quarter Round function alternating between 'column rounds' and 'diagonal rounds'
        :param chacha_state: chacha state on which Qarter Round funcions are performed
        """
        self.quarter_round_on_the_cha_cha_state(chacha_state, 0, 4, 8, 12)
        self.quarter_round_on_the_cha_cha_state(chacha_state, 1, 5, 9, 13)
        self.quarter_round_on_the_cha_cha_state(chacha_state, 2, 6, 10, 14)
        self.quarter_round_on_the_cha_cha_state(chacha_state, 3, 7, 11, 15)
        self.quarter_round_on_the_cha_cha_state(chacha_state, 0, 5, 10, 15)
        self.quarter_round_on_the_cha_cha_state(chacha_state, 1, 6, 11, 12)
        self.quarter_round_on_the_cha_cha_state(chacha_state, 2, 7, 8, 13)
        self.quarter_round_on_the_cha_cha_state(chacha_state, 3, 4, 9, 14)

    def chacha20_block(self):
        """
        Transforms a ChaCha state by running multiple quarter rounds, add the original state to the result and serialize the result
        :return: serialized block 
        """
        working_state = self.to_chacha_state()
        state = self.to_chacha_state()

        for n in range(10):
            self.inner_block(working_state)

        for n in range(len(working_state)):
            state[n] += working_state[n]
            state[n] = hex(state[n])
            if len(state[n]) == 11:  # handle overloading
                state[n] = state[n][3:]
            else:
                state[n] = state[n][2:]  # romove '0x'

        return self.add_padding(self.serialize(self.add_padding(state), 64))

    def encryption(self, plaintext):
        """
        Encrypt a given plaintext - perform XOR operation on calculated keystream and plaintext 
        :param plaintext: text to encrypt
        :return: encrypted text
        """
        encrypted_message = ''
        key_stream = self.chacha20_block()
        for j in range(len(plaintext) // 64):
            block = plaintext[j * 64:(j * 64 + 64)]
            sunscreen = ''.join(hex(ord(a))[2:].rjust(2, '0') for a in block)  # convert string to string of hex values
            encrypted_message += ''.join(hex(int(a, 16) ^ int(b, 16))[2:] for a, b in
                                         zip(sunscreen, key_stream))  # XOR string of hex values with key_stream

        if len(plaintext) % 64 != 0:
            j = len(plaintext) // 64
            block = plaintext[j * 64:]
            sunscreen = ''.join(hex(ord(a))[2:].rjust(2, '0') for a in block)
            encrypted_message += ''.join(hex(int(a, 16) ^ int(b, 16))[2:] for a, b in zip(sunscreen, key_stream))
        return encrypted_message

    def decryption(self, ciphertext):
        """
        Decrypt a given ciphertext - call Encryption function on an encrypted text, 
        which performs XOR operation on calculated keystream and given ciphertext 
        :param ciphertext: text to decrypt
        :return: decrypted text
        """
        encrypted = ''.join(chr(int(ciphertext[i:i + 2], 16)) for i in range(0, len(ciphertext), 2))
        decipher = self.encryption(encrypted)
        return decipher

    def _generate_nonce(self):
        """
        My proposal is to generate nonce in real time for example ---> sha256(string(key)+string(unix_timestamp))

        :return:
        """
        # this method will be added later

# Here will be more methods connected with streaming via network
