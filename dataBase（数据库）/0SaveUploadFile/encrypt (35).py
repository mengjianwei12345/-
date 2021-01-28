class RSA:
    def PublicKeyGenerate(self,p,q):
        '''
        p , q = two prime numbers
        publickey = (n,e)
        where n = p*q and 1< e < ICM(p-1,q-1)
        '''
        n = p*q
        for e in range(2,self.ICM(p-1,q-1),1):
            if self.GCD(e,self.ICM(p-1,q-1)) == 1:
                return n,e

    def ICM(self,a,b):
        '''Least common multiple between a and b'''
        icm = (a*b)/self.GCD(a,b)
        return int(icm)

    def GCD(self,num1,num2):
        '''Greatest common divisor between num1 and num2'''
        num1_divisors = self.FindDivisors(num1)
        num2_divisors = self.FindDivisors(num2)
        gcd = 1
        for y in range(len(num1_divisors)):
            for z in range(len(num2_divisors)):
                if num1_divisors[y] == num2_divisors[z]:
                    gcd = num1_divisors[y]
        return gcd

    def FindDivisors(self,number):
        divisorlist = []
        divisorlist.append(1)
        for x in range(1,number,1):
            if number%x == 0:
                divisorlist.append(x)
        divisorlist.append(number)
        return divisorlist

    def encrypt(self, publickey,message):
        n,e = publickey
        if message >= n:
            print("Message is too big. Message has to be smaller than n. ")
            return None
        c = (message**e)%n
        return c

    def decrypt(self,privatekey,encrypted_message):
        p,q = privatekey
        n,e = self.PublicKeyGenerate(p,q)
        d = 1
        while True:
            if (e*d)%((p-1)*(q-1))==1:
                break
            else:
                d += 1
        m = (c**d)%n
        return m

v = RSA()
c =v.encrypt(v.PublicKeyGenerate(61,53),32) # here p = 61, q = 53, message to be encrypted, m = 32, Encrypted message = c
m = v.decrypt((61,53),c)    # privatekey = (p,q)
print("Encrypted message = "+str(c))
print("Message = "+str(m))