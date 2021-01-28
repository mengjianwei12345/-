def shift(letter, shift_amount):

    value=ord(letter)+shift_amount

    while value>126:
        value-=95 
                  
    new_letter=chr(value)
    return new_letter

#--------------------------------------

def shift2(letter, shift_amount):

    value=ord(letter)-shift_amount

    while value<32:
        value+=95

    #print(value)

    new_letter=chr(value)                 
    return new_letter

#--------------------------------------

def encrypt(message, shift_amount):
    result=""

    for letter in message:
        result+=shift(letter, shift_amount)
    return result

#--------------------------------------

def decrypt(message, shift_amount):
    result=""

    for letter in message:
        result+=shift2(letter, shift_amount)
        #print(shift2(letter, shift_amount))
    return result

#--------------------------------------

secret_message = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ 123!@#"
encrypted_message = encrypt(secret_message, 4779)
decrypted_message = decrypt(encrypted_message, 4779)
print(secret_message)
print(encrypted_message)
print(decrypted_message)

