print("Enter the plain Text:")
word_to_encrypt=raw_input()
print("Enter the Key:")
key=int(input())

def encrypt(word_to_encrypt,key):
	result=""
	for x in word_to_encrypt:
		if x.islower() and x.isalpha():
			r=(ord(x)-97+key)%26
			result=result+chr(r+97)
		elif x.isupper() and x.isalpha():
			r=(ord(x)-65+key)%26
			result=result+chr(r+65)
		else:
			result=result+x
	return result

print("encryptwaord is :")
print(encrypt(word_to_encrypt,key))

word_to_decrypt=encrypt(word_to_encrypt,key)

def decrypt(word_to_decrypt,key):
	result=""
	for x in word_to_decrypt:
		if x.islower() and x.isalpha():
			r=(ord(x)-97-key)%26
			result=result+chr(r+97)
		elif x.isupper() and x.isalpha():
			r=(ord(x)-65-key)%26
			result=result+chr(r+65)
		else:
			result=result+x
	return result


print("decryptword is :")
print(decrypt(word_to_decrypt,key))
