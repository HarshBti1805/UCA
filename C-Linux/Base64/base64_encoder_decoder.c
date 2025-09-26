#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static const char ALPHABET[] =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
static int INV_ALPHABET[128];

void init_inv_alphabet() {
  for (int i = 0; i < 128; i++)
    INV_ALPHABET[i] = -1;
  for (int i = 0; i < 64; i++)
    INV_ALPHABET[(int)ALPHABET[i]] = i;
}

char *encode(const unsigned char *data, int length) {
  char *res =
      malloc(((length + 2) / 3 * 4) + 1); // Ensure enough capacity is allocated
  int i = 0, j = 0;

  // Since we need 3 bytes for every 4 characters :: Get the first 3 bytes in
  // each iteration
  while (i < length) {
    unsigned int b0 = data[i++];                    // First byte
    unsigned int b1 = (i < length) ? data[i++] : 0; // Second Byte
    unsigned int b2 = (i < length) ? data[i++] : 0; // Third Byte

    unsigned int block =
        (b0 << 16) | (b1 << 8) | b2; // Combining the Three Bytes

    // >>> represents the unsigned right shift AND 03xF masks the last 6 bits.
    // Since we need 4 chars we do it 4 times taking 6 bits out of the total 24
    // bits every time. These 6 bits represent the index for the ALPHABET Array.
    // So in the result append ALPHABET[index]
    res[j++] = ALPHABET[(block >> 18) & 0x3F];
    res[j++] = ALPHABET[(block >> 12) & 0x3F];

    res[j++] = (i - 1) < length ? ALPHABET[(block >> 6) & 0x3F]
                                : '=';                    // Handle Padding
    res[j++] = i < length ? ALPHABET[block & 0x3F] : '='; // Handle Padding
  }

  // Make sure to handle padding.
  // If 1 byte left -> pad with ==.
  // If only 2 bytes left -> pad with =.
  int mod = length % 3;
  if (mod == 1) {
    res[j - 1] = '=';
    res[j - 2] = '=';
  } else if (mod == 2) {
    res[j - 1] = '=';
  }

  res[j] = '\0';
  return res;
}

unsigned char *decode(const char *base64, int *out_len) {
  // Handling Padding. Base64 strings can end with = or == to make the length a
  // multiple of 4.
  // == -> 2 padding bytes -> means only 1 real byte came from the last block
  // = -> 1 padding byte -> means 2 real bytes came from the last block
  int padding = 0;
  int len = strlen(base64);
  if (len >= 2 && base64[len - 1] == '=' && base64[len - 2] == '=')
    padding = 2;
  else if (len >= 1 && base64[len - 1] == '=')
    padding = 1;

  // Since we have encoded 6 bits for each character, total bits = len * 6;
  // Further divide by 8 -> gives number of original bytes and then subtract
  // padding.
  int bytesLen = (len * 6) / 8 - padding; // calculate length of decoded bytes
  unsigned char *decoded = malloc(bytesLen);

  int byteIndex = 0;
  int i = 0;

  while (i < len) {
    // Process input in Chunks of 4
    // Base64 encoding outputs 4 characters for every 3 input bytes
    // c0..c3 | look up for their 6 bit values in INV_ALPHABET
    int c0 = base64[i] == '=' ? 0 : INV_ALPHABET[(int)base64[i]];
    int c1 = base64[i + 1] == '=' ? 0 : INV_ALPHABET[(int)base64[i + 1]];
    int c2 = base64[i + 2] == '=' ? 0 : INV_ALPHABET[(int)base64[i + 2]];
    int c3 = base64[i + 3] == '=' ? 0 : INV_ALPHABET[(int)base64[i + 3]];

    int block =
        (c0 << 18) | (c1 << 12) | (c2 << 6) | c3; // Recombine into 24 bit block

    // Extract the original bytes / characters from the block
    if (byteIndex < bytesLen)
      decoded[byteIndex++] = (block >> 16) & 0xFF; // First Char
    if (byteIndex < bytesLen)
      decoded[byteIndex++] = (block >> 8) & 0xFF; // Second Char
    if (byteIndex < bytesLen)
      decoded[byteIndex++] = block & 0xFF; // Third Char

    i += 4; // move to the next 4 base64 chars
  }

  *out_len = bytesLen;
  return decoded; // return the decoded array
}

int main() {
  init_inv_alphabet();

  const char *text = "Hello World!!";
  char *encoded = encode(
      (const unsigned char *)text,
      strlen(text)); // Creates an array of ASCII values for each character
  printf("Encoded (manual): %s\n", encoded);

  int decoded_len;
  unsigned char *decoded = decode(encoded, &decoded_len);
  printf("Decoded (manual): ");
  for (int i = 0; i < decoded_len; i++) {
    putchar(decoded[i]);
  }
  putchar('\n');

  free(encoded);
  free(decoded);
  return 0;
}
