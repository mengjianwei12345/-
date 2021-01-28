import random
def encrypt(text):
    key=random.randint(1,26)
    key=key%27
    text=text.upper()
    print(key)
    if len(text)==1:
        e_text=dict()
        e_text['key']=key
        e_text['text']=chr(ord('A')+key-1+ord(text))
        return e_text
    k_text=chr(key+ord('A')-1)
    k_text=k_text+text[:-1]
    e_text=dict()
    e_text['key']=k_text
    e_text['text']=''
    print(k_text)
    for i,j in zip(text,k_text):
        x=ord(i)-ord('A')+1
        y=ord(j)-ord('A')+1
        z=(x+y)%26
        c=chr(z+ord('A')-1)
        e_text['text']=e_text['text']+c
    return e_text

def decrypt(text,key):
    msg=""
    text=text.upper()
    for i,j in zip(text,key):
        x=ord(i)-ord('A')+1
        y=ord(j)-ord('A')+1
        z=(x-y)%26
        c=chr(z+ord('A')-1)
        if c=='@':
            msg=msg+'Z'
        else:
            msg=msg+c
    return msg
    
