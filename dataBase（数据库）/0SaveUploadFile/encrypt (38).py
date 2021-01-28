from math import sqrt
from random import randint
'''
public key = <e, n>
private key = <d, n>
Steps:
    - generate 2 prime numbers p and q   example 11 and 17
    - n = p * q                             n = 11 * 17 = 187
    - totient   Q(n) = (p-1)*(q-1)         Q = 10 * 16 = 160        usually named Phi

    - e * d = 1     mod Q(n)                meaning select e so that they should be multiplicative inverse in mod Q(n)
        condition: Q should not share a factor with e
    - using the above equation find d
        reformatting the equation:
            d = (1+Q*K)/e       such that K is natural number
            we can iterate through K until d is natural number
                                        in our example: select e and d such that e * d = 1 mod 160   means e * d = 161 or 321 or 481 ...
                                        for e * d = 161 we can choose (e=23 and d=17)
                                        another condition: Q should not share a factor with e

Encryption and Decryption Process:
property to be used
    - pow(x, (e*d)) = x mod n

encryption process:
    - given message m:
        - c = pow(m, e) mod n

decryption process:
    - given message m:
        - m = pow(c, d) mod n = pow (m, e*d) = m mod n
        because of the condition (m<n)  ==> m = m mod n = m

        '''

# select random prime number from given range
def primePool(p_range: list, iternum: int):
    '''select random prime number from given range'''
    prime_num = None
    while(iternum > 0 and prime_num == None):
        num = randint(p_range[0], p_range[1])
        if num > 1:
            for i in range(2, int(sqrt(num))):
                if (num % i) == 0:
                    # print(num)
                    iternum -= 1
                    return primePool(p_range, iternum)
            prime_num = num
            print(prime_num)
    return prime_num

if __name__ == '__main__':
    primePool([10000000000, 10000000000000], 20)