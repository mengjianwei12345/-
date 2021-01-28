initPerm=[58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7]
permChoice1=[57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4]
permChoice2=[14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32]
expanPerm=[32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1]
perm32=[16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25]
sbox=[]
for i in range(8):
    sbox.append([])
    for j in range(4):
        sbox[i].append([])
sbox[0][0]=[14,  4, 13,  1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9,  0,  7]
sbox[0][1]=[0, 15,  7,  4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5,  3,  8]
sbox[0][2]=[4,  1, 14,  8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10,  5,  0]
sbox[0][3]=[15, 12,  8,  2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0,  6, 13]
sbox[1][0]=[15,  1,  8, 14,  6, 11,  3,  4,  9,  7,  2, 13, 12,  0,  5, 10]
sbox[1][1]=[3, 13,  4,  7, 15,  2,  8, 14, 12,  0,  1, 10,  6,  9, 11,  5]
sbox[1][2]=[0, 14,  7, 11, 10,  4, 13,  1,  5,  8, 12,  6,  9,  3,  2, 15]
sbox[1][3]=[13,  8, 10,  1,  3, 15,  4,  2, 11,  6,  7, 12,  0,  5, 14,  9]
sbox[2][0]=[10,  0,  9, 14,  6,  3, 15,  5,  1, 13, 12,  7, 11,  4,  2,  8]
sbox[2][1]=[13,  7,  0,  9,  3,  4,  6, 10,  2,  8,  5, 14, 12, 11, 15,  1]
sbox[2][2]=[13,  6,  4,  9,  8, 15,  3,  0, 11,  1,  2, 12,  5, 10, 14,  7]
sbox[2][3]=[1, 10, 13,  0,  6,  9,  8,  7,  4, 15, 14,  3, 11,  5,  2, 12]
sbox[3][0]=[7, 13, 14,  3,  0,  6,  9, 10,  1,  2,  8,  5, 11, 12,  4, 15]
sbox[3][1]=[13,  8, 11,  5,  6, 15,  0,  3,  4,  7,  2, 12,  1, 10, 14,  9]
sbox[3][2]=[10,  6,  9,  0, 12, 11,  7, 13, 15,  1,  3, 14,  5,  2,  8,  4]
sbox[3][3]=[3, 15,  0,  6, 10,  1, 13,  8,  9,  4,  5, 11, 12,  7,  2, 14]
sbox[4][0]=[2, 12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13,  0, 14,  9]
sbox[4][1]=[14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3,  9,  8,  6]
sbox[4][2]=[4,  2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6,  3,  0, 14]
sbox[4][3]=[11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10,  4,  5,  3]
sbox[5][0]=[12,  1, 10, 15,  9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11]
sbox[5][1]=[10, 15,  4,  2,  7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8]
sbox[5][2]=[9, 14, 15,  5,  2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6]
sbox[5][3]=[4,  3,  2, 12,  9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13]
sbox[6][0]=[4, 11,  2, 14, 15,  0,  8, 13,  3, 12,  9,  7,  5, 10,  6,  1]
sbox[6][1]=[13,  0, 11,  7,  4,  9,  1, 10, 14,  3,  5, 12,  2, 15,  8,  6]
sbox[6][2]=[1,  4, 11, 13, 12,  3,  7, 14, 10, 15,  6,  8,  0,  5,  9,  2]
sbox[6][3]=[6, 11, 13,  8,  1,  4, 10,  7,  9,  5,  0, 15, 14,  2,  3, 12]
sbox[7][0]=[13,  2,  8,  4,  6, 15, 11,  1, 10,  9,  3, 14,  5,  0, 12,  7]
sbox[7][1]=[1, 15, 13,  8, 10,  3,  7,  4, 12,  5,  6, 11,  0, 14,  9,  2]
sbox[7][2]=[7, 11,  4,  1,  9, 12, 14,  2,  0,  6, 10, 13, 15,  3,  5,  8]
sbox[7][3]=[2,  1, 14,  7,  4, 10,  8, 13, 15, 12,  9,  0,  3,  5,  6, 11]
shift=[1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1]
def toBin(hex):
    return ( bin(int(hex, 16))[2:] ).zfill(len(hex)*4)
def toHex(bin):
    return hex(int(bin,2))
def intToBin(num):
    return (format(num,'010b'))[6:]
def binToInt(bin):
    return int(bin,2)
def permutate(bin,perm):
    res=""
    for i in range(len(perm)):
        index=perm[i]-1
        res+=bin[index]
    return res
def invPermutate(bin,perm):
    res=""
    for i in range(len(perm)):
        pos=0
        for j in range(len(perm)):
            if i+1==perm[j]:
                pos=j
        res+=bin[pos]
    return res
def shiftLeftCircular(bin, n):
    return bin[n::] + bin[:n:]
def generateKeys(key):
    key56=permutate(key,permChoice1)
    newKeys=[]
    lastKey=""
    left=""
    right=""
    for i in range(16):
        if lastKey=="":
            lastKey=key56
        left=lastKey[:28]
        right=lastKey[28:]
        left=shiftLeftCircular(left,shift[i])
        right=shiftLeftCircular(right,shift[i])
        lastKey=left+right
        key48=permutate(lastKey,permChoice2)
        newKeys.append(key48)
    return newKeys
def xor(x, y):
    return '{1:0{0}b}'.format(len(x), int(x, 2) ^ int(y, 2))
def sboxing(input):
    i=0
    output=""
    while i<48:
        output+=intToBin(sbox[int(i/6)][binToInt(input[i]+input[i+5])][binToInt(input[i+1]+input[i+2]+input[i+3]+input[i+4])])
        i+=6
    return output

def desRound(input,key):
    leftIn=input[:32]
    rightIn=input[32:]
    expandedR=permutate(rightIn,expanPerm)
    xoredR=xor(expandedR,key)
    sboxedR=sboxing(xoredR)
    permutatedR=permutate(sboxedR,perm32)
    rightOut=xor(leftIn,permutatedR)
    leftOut=rightIn
    output=leftOut+rightOut
    return output

def des(plaintext,key,choice):
    roundKeys = generateKeys(key)
    if not choice:
        roundKeys.reverse()
    permutatedPT=permutate(plaintext,initPerm)
    roundOutput=permutatedPT
    for i in range(16):
        roundOutput=desRound(roundOutput,roundKeys[i])
    finalOutput=roundOutput[32:]+roundOutput[:32]
    finalOutput=invPermutate(finalOutput,initPerm)
    return finalOutput




while 1:
    choice=input("Enter 1 for ENCRYPTION or 0 for DECRYPTION: ")

    plaintext=input("Input plaintext: ")
    plaintext=toBin(plaintext)
    #print(len(plaintext))
    key=input("Input key: ")
    key=toBin(key)
    #print(len(key))

    numOfRound=int(input("Input number of operations: "))

    ciphertext=plaintext
    for i in range(numOfRound):
        ciphertext=des(ciphertext,key,choice)

    if choice=='1':
        print("Ciphertext:")
        print(toHex(ciphertext))
    else:
        print("Plaintext: ")
        print(toHex(ciphertext))





