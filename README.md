# AssetsProtector
 a minecraft mod for protect your assets

compatible with resourcepack

Usage :
create folder named "aes" in your minecraft folder (run folder in development environmental)

put png files that you want to encrypt.

use below code with FMLClientSetupEvent to encrypt files

```
try
{
    AESUtil.encryptTextures();
}
catch (InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | NoSuchPaddingException | InvalidKeySpecException | IOException e) 
{
    e.printStackTrace();
}
```

put encrypted files to your assets folder

use AESUtil#getTexture to load texture


