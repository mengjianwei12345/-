from helper import Cryptography

from AES import helper


class AES(Cryptography):
	def __init__(self, key):
		self.KEY = key

		key = helper.decompose_to_matrix(key)
		RCON = [ 0x01, 0x00, 0x00, 0x00 ]

		self.key = [ helper.transpose(key) ]
		for rounds in range(10):
			x = key[3][1:] + key[3][:1]
			y = [ helper.substitute_bytes(i) for i in x ]
			z = [ y[i] ^ RCON[i] for i in range(4) ]

			key[0] = [ key[0][i] ^ z[i] for i in range(4) ]
			key[1] = [ key[1][i] ^ key[0][i] for i in range(4) ]
			key[2] = [ key[2][i] ^ key[1][i] for i in range(4) ]
			key[3] = [ key[3][i] ^ key[2][i] for i in range(4) ]

			self.key.append(helper.transpose(key))

			RCON[0] = helper.xtime(RCON[0])

	def encrypt(self, text):
		text = helper.decompose_to_matrix(text)
		text = helper.transpose(text)
		text = helper.xor(text, self.key[0])

		for rounds in range(1, 11, 1):
			text = [
				[ helper.substitute_bytes(x) for x in row ]
				for row in text
			]
			text = helper.shift_rows(text)
			if rounds != 10:
				text = helper.mix_columns(text)
			text = helper.xor(text, self.key[rounds])

		text = helper.transpose(text)
		return helper.decompose_from_matrix(text)

	def decrypt(self, text):
		text = helper.decompose_to_matrix(text)
		text = helper.transpose(text)

		for rounds in range(10, 0, -1):
			text = helper.xor(text, self.key[rounds])
			if rounds != 10:
				text = helper.mix_columns_inverse(text)
			text = helper.shift_rows_inverse(text)
			text = [
				[ helper.substitute_bytes_inverse(x) for x in row ]
				for row in text
			]

		text = helper.xor(text, self.key[0])

		text = helper.transpose(text)
		return helper.decompose_from_matrix(text)

	def get_key(self):
		return self.KEY
