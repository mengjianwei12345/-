from helper import Cryptography

from DES import constants, helper


class DES(Cryptography):
	def __init__(self, key):
		self.KEY = key

		key = helper.permute_PC1(key)
		C,D = helper.split(key, 56)

		self.key = []
		for i in range(16):
			C = helper.shift_left(C, constants.round_shifts[i])
			D = helper.shift_left(D, constants.round_shifts[i])
			self.key.append(helper.permute_PC2((C << 28) | D))

	def encrypt(self, text):
		IP = helper.permute_init(text)
		L,R = helper.split(IP, 64)

		for i in range(0, 16, 1):
			L,R = R, L ^ helper.feistel(R, self.key[i])

		return helper.permute_inverse((R << 32) | L)

	def decrypt(self, text):
		IP = helper.permute_init(text)
		L,R = helper.split(IP, 64)

		for i in range(15, -1, -1):
			L,R = R, L ^ helper.feistel(R, self.key[i])

		return helper.permute_inverse((R << 32) | L)

	def get_key(self):
		return self.KEY
