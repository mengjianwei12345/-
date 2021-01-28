import sys

class VigenereCipher:
	def __init__(self,key,word_to_encrypt,word_to_decrypt):
		self.key=key.upper()
		self.word_to_encrypt=word_to_encrypt.upper()
		self.word_to_decrypt=word_to_decrypt.upper()
	def ord_finder(self,let):
		return(ord(let)-65)
	def chr_finder(self,val):
		return(chr(val+65))

	def encrypt(self):
		result=""
		for i in xrange(len(self.word_to_encrypt)):
			letter_word=self.word_to_encrypt[i]
			if letter_word.isalpha():
				letter_key=self.key[i%len(self.key)]
				w=self.ord_finder(letter_word)
				k=self.ord_finder(letter_key)
				ans=(w+k)%26
				final=self.chr_finder(ans)
				result=result+final
			else:
				result=result+letter_word
		return(result)

	def decrypt(self):
		result=""
		for i in xrange(len(self.word_to_decrypt)):
			letter_word=self.word_to_decrypt[i]
			if letter_word.isalpha():
				letter_key=self.key[i%len(self.key)]
				w=self.ord_finder(letter_word)
				k=self.ord_finder(letter_key)
				ans=(w-k)%26
				final=self.chr_finder(ans)
				result=result+final
			else:
				result+=letter_word
		return(result)

d=VigenereCipher("counton","vigenere cipher !","xwargseg wvivrt !")
print(d.encrypt())
print(d.decrypt())


