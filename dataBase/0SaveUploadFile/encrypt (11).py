from helper import Cryptography

from Caesar.helper import *


class Caesar(Cryptography):
	def encrypt(self, text):
		return "".join(transform(c, self.key) for c in text)

	def decrypt(self, text):
		return "".join(transform(c, -self.key) for c in text)
