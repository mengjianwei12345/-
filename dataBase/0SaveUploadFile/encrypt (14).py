from helper import Cryptography

from CBC import helper


class CBC(Cryptography):
	def encrypt(self, text):
		LENGTH = 4 * self.key["block"]
		BLOCK = 5
		SIZE = LENGTH * BLOCK

		text = [
			(text >> (SIZE - LENGTH - LENGTH*i)) & ((1 << LENGTH) - 1)
			for i in range(BLOCK)
		]
		K = self.key["key"]
		C = self.key["C"]
		SHIFT = self.key["shift"]

		result = 0
		for x in text:
			temp = x ^ C ^ K
			temp = helper.shift_left(temp, SHIFT, LENGTH)
			result = (result << LENGTH) | temp
			C = temp

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
		C = self.key["C"]
		SHIFT = LENGTH - self.key["shift"]

		result = 0
		for x in text:
			temp = helper.shift_left(x, SHIFT, LENGTH)
			temp = temp ^ K ^ C
			result = (result << LENGTH) | temp
			C = x

		return result
