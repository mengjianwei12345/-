import numpy as np
import os
import numpy.random.common
import numpy.random.bounded_integers
import numpy.random.entropy
def Caesar(plaintext,key):
    ciphertext=""
    for c in plaintext:
        if c != '\n':
            ciphertext+=chr((((ord(c)-97)+key)%26)+65)
    if not os.path.exists('./Output Files/Caesar/'):
        os.makedirs('./Output Files/Caesar/')
    file = open('Output Files/Caesar/caesar_output_key_'+str(key)+'.txt', 'a+')
    file.write(ciphertext+'\n')
    file.close()
   #print(ciphertext)

def Playfair(plaintext,key):
    ciphertext=""
    keyMat=createPlayfairMatrix(key)
    pairs=playfairPairSplit(plaintext)
    for pair in pairs:
        col0=getCol(keyMat,pair[0])
        col1=getCol(keyMat,pair[1])
        row0=getRow(keyMat,pair[0])
        row1=getRow(keyMat,pair[1])
        if col0 == col1:
            ciphertext += keyMat[(row0+1)%5][col0] + keyMat[(row1+1)%5][col1]
        elif row0 == row1:
            ciphertext +=  keyMat[row0][(col0+1)%5]+keyMat[row1][(col1+1)%5]
        else:
            ciphertext += keyMat[row0][col1] + keyMat[row1][col0]
    #print("Playfair ciphertext:")
    #print(ciphertext)
    if not os.path.exists('./Output Files/PlayFair/'):
        os.makedirs('./Output Files/PlayFair/')
    file = open('Output Files/PlayFair/playfair_output_key_' + key + '.txt', 'a+')
    file.write(ciphertext + '\n')
    file.close()

def Hill(plaintext,key):
    ciphertext=""
    pairs=hillGroupSplit(plaintext,len(key));
    pairs=convertPairsToNum(pairs)
    pairs=np.array(pairs)
    key=np.array(key)
    res=[]
    for pair in pairs:
        res.append(pair.dot(key))
    res=np.array(res)
    for i in range(len(pairs)):
        for j in range(len(pairs[0])):
            ciphertext+=chr((res[i][j]%26)+65)
    #print("Hill ciphertext:")
    #print(ciphertext)
    if not os.path.exists('./Output Files/Hill/'):
        os.makedirs('./Output Files/Hill/')
    file = open('Output Files/Hill/hill_output_key_' + str(len(key))+'x'+str(len(key)) + '.txt', 'a+')
    file.write(ciphertext + '\n')
    file.close()
def Vigenere(plaintext,key,mode):
    ciphertext=""
    keyIndex=0
    if not os.path.exists('./Output Files/Vigenere/'):
        os.makedirs('./Output Files/Vigenere/')
    file = open('Output Files/Vigenere/vigenere_output_key_' + key + '.txt', 'a+')
    if mode:
        for c in plaintext:
            if c != '\n':
                ciphertext += chr((((ord(c) - 97) + (ord(key[keyIndex])-96)) % 26) + 64)
                key +=chr((((ord(c) - 97) + (ord(key[keyIndex])-96)) % 26) + 64)
                keyIndex+=1
    else:
        for c in plaintext:
            if c != '\n':
                ciphertext += chr((((ord(c) - 97) + (ord(key[keyIndex])-96)) % 26) + 64)
                keyIndex=(keyIndex+1)%len(key)
    #print("Vigenere ciphertext:")
    #print(ciphertext)
    file.write(ciphertext + '\n')
    file.close()
def Vernam(plaintext,key):
    ciphertext = ""
    keyIndex = 0
    for c in plaintext:
        if c != '\n':
            ciphertext += chr((((ord(c) - 65) + (ord(key[keyIndex]) - 64)) % 26) + 64)
            keyIndex += 1
    #print("Vernam ciphertext:")
    #print(ciphertext)
    if not os.path.exists('./Output Files/Vernam/'):
        os.makedirs('./Output Files/Vernam/')
    file = open('Output Files/Vernam/vernam_output.txt', 'a+')
    file.write(ciphertext + '\n')
    file.close()

def hillGroupSplit(plaintext,size):
    groups=[]
    #print(plaintext)
    plaintext=plaintext[:-1]
    if len(plaintext)%size!=0:
        plaintext+='x'
    for i in range(int(len(plaintext)/size)):
        groups.append([])
    i=0
    groupId=0;
    while i<(len(plaintext)-(size-1)):
        for j in range(size):
            groups[groupId].append(plaintext[i+j])
        i+=size
        groupId+=1
    #print(groups)
    return groups

def convertPairsToNum(pairs):
    for i in range(len(pairs)):
        for j in range(len(pairs[0])):
            pairs[i][j]=ord(pairs[i][j])-65
    return pairs
def getRow(keyMat,letter):
    for i in range(5):
        if letter in keyMat[i]:
            return i
def getCol(keyMat,letter):
    for i in range(5):
        for j in range(5):
            if letter == keyMat[i][j]:
                return j




def playfairPairSplit(plaintext):
    #print(plaintext)
    plaintext=plaintext[:-1]
    pairs=[]
    initiallyOdd = 0
    if len(plaintext) % 2 != 0:
        initiallyOdd = 1
        plaintext += 'x'
    i = 0
    while i < len(plaintext) - 1:
        first = correctJ(plaintext[i])
        sec = correctJ(plaintext[i + 1])
        if first == sec:
            plaintext = plaintext[:i + 1] + 'x' + plaintext[i + 1:]
            #print(plaintext)
            sec = correctJ(plaintext[i + 1])
        i += 2
        #print(first, sec)
        pairs.append([first,sec])
    if len(plaintext) % 2 != 0 and not initiallyOdd:
        plaintext += 'x'
        #print(plaintext[len(plaintext) - 2], plaintext[len(plaintext) - 1])
        pairs.append([correctJ(plaintext[len(plaintext) - 2]), correctJ(plaintext[len(plaintext) - 1])])
    #print(pairs)
    return pairs
def createPlayfairMatrix(key):
    keyIndex=0
    letterIndex=97
    mat=[]
    for i in range(5):
        mat.append([])
    for i in range(5):
        for j in range(5):
            if keyIndex<len(key):
                while keyIndex+1<len(key) and (correctJ(key[keyIndex]) in getList(mat)):
                    keyIndex+=1
                if not correctJ(key[keyIndex]) in getList(mat):
                    (mat[i]).append(correctJ(key[keyIndex]))
                else:
                    while correctJ(chr(letterIndex)) in getList(mat):
                        letterIndex += 1
                    (mat[i]).append(correctJ(chr(letterIndex)))
                keyIndex+=1
            else:
                while correctJ(chr(letterIndex)) in getList(mat):
                    letterIndex+=1
                (mat[i]).append(correctJ(chr(letterIndex)))
    mat = np.array(mat)
    #print(mat)
    return mat
def getList(mat):
    list=[]
    for i in range(len(mat)):
        for j in mat[i]:
            list.append(j)
    return list
def correctJ(c):
    if c=='j':
        c='i'
    return c
def caesarFromFile():
    i=3
    while i<13:
        if os.path.exists('./Output Files/Caesar/'):
            open('Output Files/Caesar/caesar_output_key_' + str(i) + '.txt', 'w').close()
        file = open("Input Files/Caesar/caesar_plain.txt")
        while 1:
            line = file.readline()
            if not line:
                break
            Caesar(line, i)
        file.close()
        i*=2
def playfairFromFile():
    if os.path.exists('./Output Files/PlayFair/'):
        open('Output Files/PlayFair/playfair_output_key_rats.txt', 'w').close()
        open('Output Files/PlayFair/playfair_output_key_archangel.txt', 'w').close()
    file = open("Input Files/PlayFair/playfair_plain.txt")
    while 1:
        line = file.readline()
        if not line:
            break
        Playfair(line, 'rats')
    file.close()
    file = open("Input Files/PlayFair/playfair_plain.txt")
    while 1:
        line = file.readline()
        if not line:
            break
        Playfair(line, 'archangel')
    file.close()
def hillFromFile():
    if os.path.exists('./Output Files/Hill/'):
        open('Output Files/Hill/hill_output_key_2x2.txt', 'w').close()
        open('Output Files/Hill/hill_output_key_3x3.txt', 'w').close()
    file = open("Input Files/Hill/hill_plain_2x2.txt")
    while 1:
        line = file.readline()
        if not line:
            break
        Hill(line, [[5,17],[8,3]])
    file.close()
    file = open("Input Files/Hill/hill_plain_3x3.txt")
    while 1:
        line = file.readline()
        if not line:
            break
        Hill(line, [[2,4,12],[9,1,6],[7,5,3]])
    file.close()
def vigenereFromFile():
    if os.path.exists('./Output Files/Vigenere/'):
        open('Output Files/Vigenere/vigenere_output_key_pie.txt', 'w').close()
        open('Output Files/Vigenere/vigenere_output_key_aether.txt', 'w').close()
    file = open("Input Files/Vigenere/vigenere_plain.txt")
    while 1:
        line = file.readline()
        if not line:
            break
        Vigenere(line, "pie",False)
    file.close()
    file = open("Input Files/Vigenere/vigenere_plain.txt")
    while 1:
        line = file.readline()
        if not line:
            break
        Vigenere(line, "aether",True)
    file.close()
def vernamFromFile():
    if os.path.exists('./Output Files/Vernam/'):
        open('Output Files/Vernam/vernam_output.txt', 'w').close()
    file = open("Input Files/Vernam/vernam_plain.txt")
    while 1:
        line = file.readline()
        if not line:
            break
        Vernam(line, "SPARTANS")
    file.close()
caesarFromFile()
playfairFromFile()
hillFromFile()
vigenereFromFile()
vernamFromFile()
