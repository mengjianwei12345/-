alphabet = "`1234567890-=qwertyuiop[]\\asdfghjkl;' zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?"
key = "ETUjiaXM1{@:9.NY42m5V\kZgCx>)Dd$uWtSAFIpswyG&0H-\"lob_!'e*}c(3P?. "


def encrypt(message):
    newmessage=""

    for i in message:
        loc = alphabet.find(i)
        newmessage += key[loc]
    return newmessage

def decrypt(message):
    newmessage=""

    for i in message:
        loc = key.find(i)
        newmessage += alphabet[loc]
    return newmessage


unencrypted_message = "8q3475tgq8o7iweguvbaeuyrgb8347gqw83i7ryb"
encrypted_message = encrypt(unencrypted_message)
decrypted_message = decrypt(encrypted_message)


print(unencrypted_message)
print(encrypted_message)
print(decrypted_message)
