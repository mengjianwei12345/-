#!/usr/bin/env python3

from Crypto.Cipher import AES
import base64
import binascii
import argparse
from Crypto import Random
import io

parser = argparse.ArgumentParser(description='Encrypt any file. Algorithmus: AES128-CBC. Padding: PKCS#7')
parser.add_argument('key', metavar='Key', type=str, help='keyhex OR keyfile')
parser.add_argument('infile', metavar='infile', type=str, help='infile to read the input data')
parser.add_argument('outfile', metavar='outfile', type=str, help='outfile to write the encrypted Data')
args = parser.parse_args()

try:
   keyfile = open(args.key,'rb')
   key = keyfile.read()
   print('key: {}   key size =  {}'.format(key,len(key)))
   keyfile.close()
except:
   key = args.key
   #print("key: ", key)

with open(args.infile,'rb') as infile:
   if True:
      text = infile.read()
   infile.close    

class PKCS7Encoder(object):
    def __init__(self, k=16 ):
       self.k = k 
    def encode(self, text):
        # Pad an input string according to PKCS#7
        l = len(text)
        output = io.StringIO()
        val = self.k - (l % self.k)
        for _ in range(val):
            output.write('%02x' % val)
        return text + binascii.unhexlify(output.getvalue())

mode = AES.MODE_CBC
iv = Random.new().read(AES.block_size)
encoder = PKCS7Encoder()
padded_text = encoder.encode(text)

try:
   e = AES.new(key, mode, iv)
except:
   # ValueError: AES key must be either 16, 24, or 32 bytes long
   print("key size is {} byte, MUST be 16 byte. Check key or keyfile name".format(len(key)))
   exit(0)
   
cipher_text = e.encrypt(padded_text)

print (iv)
print (len(cipher_text))

with open (args.outfile,'rb+')as outfile:
   if True:
      outfile.write(iv)
      outfile.write(cipher_text)
      outfile.close()

#print(open(args.outfile,'r').read())
