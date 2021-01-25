#include <algorithm>
#include <array>
#include <iostream>
#include <string>

void replaceAll(std::string& str, std::string str_find,
                std::string str_replace) {
  size_t index = 0;
  while (true) {
    index = str.find(str_find, index);
    if (index == std::string::npos) break;
    str.replace(index, str_find.length(), str_replace);
    index += str_replace.length();
  }
}

void toUpperCase(std::string& str) {
  for_each(str.begin(), str.end(), [](char& c) { c = toupper(c); });
}

std::string removeNonLetters(std::string str) {
  auto new_end = remove_if(str.begin(), str.end(), [](const char chr) {
    return (!(chr >= 'a' && chr <= 'z') || (chr >= 'A' && chr <= 'Z'));
  });
  str.erase(new_end, str.end());
  return str;
}

void removeDuplicates(std::string& str) {
  char previous_char = ' ';
  auto new_end = remove_if(str.begin(), str.end(), [&](const char c) {
    if (c == previous_char) {
      previous_char = c;
      return true;
    } else {
      previous_char = c;
      return false;
    }
  });
  str.erase(new_end, str.end());
}

void changeDuplicates(std::string& str) {
  char previous_char = ' ';
  int index = 1;
  for_each(str.begin(), str.end(), [&](char& c) {
    if (c == previous_char && index % 2 == 0) {
      previous_char = c;
      c = 'X';
    } else {
      previous_char = c;
    }
    index++;
  });
}

std::string except(std::string str1, std::string str2) {
  std::string result;
  for_each(str1.begin(), str1.end(), [&](char c) {
    auto index = str2.find(c, 0);
    if (index == std::string::npos) {
      result.push_back(c);
    }
  });
  return result;
}

std::array<std::array<char, 5>, 5> constructKeyMatrix(std::string key) {
  std::string alpha = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                       'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                       'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
  std::string lettersExceptKey;
  std::array<std::array<char, 5>, 5> keyMatrix;
  int n = key.length();
  lettersExceptKey = except(alpha, key);
  lettersExceptKey.erase(
      remove(lettersExceptKey.begin(), lettersExceptKey.end(), 'J'));
  // construct key keymatrix
  for (int k = 0, m = 0, i = 0; i < 5; i++) {
    for (int j = 0; j < 5; j++) {
      if (k < n) {
        keyMatrix[i][j] = key[k];
        k++;
      } else {
        keyMatrix[i][j] = lettersExceptKey[m];
        m++;
      }
    }
  }
  return keyMatrix;
}

std::string playfair(char chr1, char chr2,
                     std::array<std::array<char, 5>, 5> keyMatrix) {
  int w, x, y, z;
  std::string result;
  for (int i = 0; i < 5; i++) {
    for (int j = 0; j < 5; j++) {
      if (chr1 == keyMatrix[i][j]) {
        w = i;
        x = j;
      } else if (chr2 == keyMatrix[i][j]) {
        y = i;
        z = j;
      }
    }
  }
  if (w == y) {
    x = (x + 1) % 5;
    z = (z + 1) % 5;
    result = {keyMatrix[w][x], keyMatrix[y][z]};
  } else if (x == z) {
    w = (w + 1) % 5;
    y = (y + 1) % 5;
    result = {keyMatrix[w][x], keyMatrix[y][z]};
  } else {
    result = {keyMatrix[w][z], keyMatrix[y][x]};
  }
  return result;
}

void encrypt(std::string text, std::array<std::array<char, 5>, 5> keyMatrix) {
  std::string result;
  for (int n = text.length(), i = 0; i < n; i = i + 2) {
    result.append(playfair(text[i], text[i + 1], keyMatrix));
  }
  std::cout << result;
}

std::array<int, 4> search(std::array<std::array<char, 5>, 5> keyMatrix,
                          char chr1, char chr2) {
  std::array<int, 4> arr;
  if (chr1 == 'j')
    chr1 = 'i';
  else if (chr2 == 'j')
    chr2 = 'i';

  for (int i = 0; i < 5; i++) {
    for (int j = 0; j < 5; j++) {
      if (keyMatrix[i][j] == chr1) {
        arr[0] = i;
        arr[1] = j;
      } else if (keyMatrix[i][j] == chr2) {
        arr[2] = i;
        arr[3] = j;
      }
    }
  }
  return arr;
}

void decrypt(std::string text, std::array<std::array<char, 5>, 5> keyMatrix) {
  std::array<int, 4> arr;
  std::string result;
  int n = text.length();
  for (int i = 0; i < n; i += 2) {
    arr = search(keyMatrix, text[i], text[i + 1]);
    if (arr[0] == arr[2]) {
      result.append({keyMatrix[arr[0]][(arr[1] - 1) % 5],
                     keyMatrix[arr[0]][(arr[3] - 1) % 5]});
    } else if (arr[1] == arr[3]) {
      result.append({keyMatrix[(arr[0] - 1) % 5][arr[1]],
                     keyMatrix[(arr[2] - 1) % 5][arr[1]]});
    } else {
      result.append({keyMatrix[arr[0]][arr[3]], keyMatrix[arr[2]][arr[1]]});
    }
  }
  std::cout << result;
}

int main() {
  int choice;
  std::array<std::array<char, 5>, 5> keyMatrix;
  std::string key, text;

  std::cout << "Enter a choice:" << std::endl
            << "1.Encrypt" << std::endl
            << "2.Decrypt" << std::endl;
  std::cin >> choice;
  std::cout << "Enter key:" << std::endl;
  std::cin >> key;
  removeNonLetters(key);
  removeDuplicates(key);
  std::cout << "Enter the plain text:" << std::endl;
  std::cin >> text;
  removeNonLetters(text);
  changeDuplicates(text);
  toUpperCase(key);
  toUpperCase(text);
  replaceAll(text, "J", "I");
  if (text.length() % 2 == 0) text.push_back('X');
  keyMatrix = constructKeyMatrix(key);

  if (choice == 1) {
    encrypt(text, keyMatrix);
  } else {
    decrypt(text, keyMatrix);
  }

  return 0;
}