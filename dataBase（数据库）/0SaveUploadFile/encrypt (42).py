# transposition cipher encryption

def transpose():
    message = input('Message: ')

    key = input('Key: ')
    key = int(key)

    cipher_text = encrypt_message(key, message)

    print(cipher_text)


def encrypt_message(key, message):
    cipher_text = [''] * key

    for col in range(key):

        pointer = col

        while pointer < len(message):
            cipher_text[col] += message[pointer]

            pointer += key

    return ''.join(cipher_text)

# transpose()
