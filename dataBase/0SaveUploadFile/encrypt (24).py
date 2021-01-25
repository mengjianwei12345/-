from helper import Cryptography

from CBC import helper


class ECB(Cryptography):
	def encrypt(self, text):
		LENGTH = 4 * self.key["block"]
		BLOCK = 5
		SIZE = LENGTH * BLOCK

		text = [
			(text >> (SIZE - LENGTH - LENGTH*i)) & ((1 << LENGTH) - 1)
			for i in range(BLOCK)
		]
		K = self.key["key"]
		SHIFT = self.key["shift"]

		result = 0
		for x in text:
			temp = x ^ K
			temp = helper.shift_left(temp, SHIFT, LENGTH)
			result = (result << LENGTH) | temp

		return result

	def decrypt(self, text):
		LENGTH = 4 * self.key["block"]
		BLOCK = 5
		SIZE = LENGTH * BLOCK

		text = [
			(text >> (SIZE - LENGTH - LENGTH*i)) & ((1 << LENGTH) - 1)
			for i in range(BLOCK)
		]
		K = self.key["key"]
		SHIFT = LENGTH - self.key["shift"]

		result = 0
		for x in text:
			temp = helper.shift_left(x, SHIFT, LENGTH)
			temp = temp ^ K
			result = (result << LENGTH) | temp

		return result
