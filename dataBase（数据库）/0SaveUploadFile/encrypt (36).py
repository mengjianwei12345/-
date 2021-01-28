from random import randint

from helper import Cryptography

from RSA import helper


class RSA(Cryptography):
	def __init__(self, key=None):
		self.public_key = key
		self.private_key = None

		if key is None:
			p, q = helper.get_prime(), helper.get_prime()
			while p == q:
				q = helper.get_prime()

			N = p * q
			TOTIENT = (p - 1) * (q - 1)

			while True:
				E = randint(2, TOTIENT - 1)

				GCD, D, _ = helper.extended_euclid(E, TOTIENT)

				while D < 0:
					D += TOTIENT

				D %= TOTIENT

				if GCD == 1:
					break

			self.public_key = (E, N)
			self.private_key = (D, N)

	def encrypt(self, text):
		return helper.fast_exponentiation(
			text,
			self.public_key[0],
			self.public_key[1],
		)

	def decrypt(self, text):
		assert(self.private_key is not None)

		return helper.fast_exponentiation(
			text,
			self.private_key[0],
			self.private_key[1],
		)

	def get_key(self):
		return self.public_key
