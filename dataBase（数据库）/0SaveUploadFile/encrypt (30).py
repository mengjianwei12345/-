import hashlib 


str2hash = "Hello there"

 
result = hashlib.md5(str2hash.encode()) 


print("The hexadecimal of the hash is : ", end ="") 
print(result.hexdigest())