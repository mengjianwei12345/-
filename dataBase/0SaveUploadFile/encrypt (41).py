from helper import Cryptography


class Transposition(Cryptography):
	def encrypt(self, text):
		while len(text) % len(self.key) != 0:
			text += 'z'

		mapping = self.key
		delta = len(self.key)

		return "".join(( text[i::delta] for i in mapping ))

	def decrypt(self, text):
		mapping = [0 for i in self.key]
		for i in range(len(self.key)):
			mapping[self.key[i]] = i

		delta = (len(text) - 1) // len(self.key) + 1

		return "".join(
			"".join(text[i::delta][x] for x in mapping)
			for i in range(delta)
		)
