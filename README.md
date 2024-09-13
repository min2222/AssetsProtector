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

![image](https://github.com/user-attachments/assets/4171449b-9b5f-4e1b-a902-39af7c77b146)

this is not a true, it's not something like disable encryption when resourcepack is enabled, you can just encrypt texture inside resourcepack itself so don't need to worry about it
