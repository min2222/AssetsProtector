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

use AESUtil#getTexture to load texture

if texture is loaded from json (like item, block, particle) you don't need to do additional stuff, just put encrypted texture in assets folder

for sounds :
AESUtil.encryptFiles(".ogg");
.oggencrypted -> .ogg

