# AssetsProtector
 a minecraft mod for protect your assets

compatible with resourcepack

Usage :
create folder named "aes" in your minecraft folder (run folder in development environmental)

put files that you want to encrypt.

use below code with FMLClientSetupEvent to encrypt files (make sure remove code when publishing mod)

```
try
{
    AESUtil.encryptFiles(".png");
}
catch (InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | NoSuchPaddingException | InvalidKeySpecException | IOException e) 
{
    e.printStackTrace();
}
```

put encrypted files to your assets folder (make sure change .pngencrypted to .png)

then everything should be done, you don't need to do additional stuff

for sounds :
AESUtil.encryptFiles(".ogg");
.oggencrypted -> .ogg

![image](https://github.com/user-attachments/assets/4992bc9b-f2b2-4d6c-8ada-1c82ec63aca0)
