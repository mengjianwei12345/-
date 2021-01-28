#simple ceasar cipher

message = 'Getting ready for the next phase. Programming is super fun. Even when I am not coding, I think about coding.'

key = input('Key: ')
key = int(key)

mode = input('encrypt or decrypt > ')

LETTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'

upper_and_lower = LETTERS + LETTERS.lower()

translated = ''

for symbol in message:

    if symbol in upper_and_lower:

        num = upper_and_lower.find(symbol)

        if mode == 'encrypt':

            num = num + key

        elif mode == 'decrypt':

            num = num - key

        if num >= len(upper_and_lower):

            num = num - len(upper_and_lower)

        elif num < 0:

            num = num + len(upper_and_lower)

        translated = translated + upper_and_lower[num]

    else:

        translated = translated + symbol

print(translated)