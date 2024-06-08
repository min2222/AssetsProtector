package com.min01.aes.mixin;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.min01.aes.AESUtil;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.server.packs.resources.Resource;

@Mixin(TextureAtlas.class)
public class MixinTextureAtlas
{
	//lambda$getBasicSpriteInfos$2
	//m_174717_
	@Redirect(method = "lambda$getBasicSpriteInfos$2", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/Resource;open()Ljava/io/InputStream;"))
	private InputStream getBasicSpriteInfos(Resource instance) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException
	{
        InputStream inputStream = ImageIO.read(instance.open()) != null ? instance.open() : AESUtil.decryptTexture(instance.open().readAllBytes());
		return inputStream;
	}
}
