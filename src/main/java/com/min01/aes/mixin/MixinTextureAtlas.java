package com.min01.aes.mixin;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

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
	@Redirect(method = "load(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite$Info;IIIII)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/Resource;open()Ljava/io/InputStream;"))
	private InputStream load(Resource instance) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException
	{
		byte[] array = instance.open().readAllBytes();
	    String string = new String(Arrays.copyOfRange(array, array.length - 60, array.length - 16));
        InputStream inputStream = ImageIO.read(instance.open()) != null ? instance.open() : string.substring(string.length() - 1) == "=" ? AESUtil.decryptTexture(instance.open().readAllBytes()) : instance.open();
		return inputStream;
	}
	
	//lambda$getBasicSpriteInfos$2 for dev environmental;
	//m_174717_ for compiling;
	@Redirect(method = "m_174717_", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/Resource;open()Ljava/io/InputStream;"))
	private InputStream getBasicSpriteInfos(Resource instance) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException
	{
		byte[] array = instance.open().readAllBytes();
	    String string = new String(Arrays.copyOfRange(array, array.length - 60, array.length - 16));
        InputStream inputStream = ImageIO.read(instance.open()) != null ? instance.open() : string.substring(string.length() - 1) == "=" ? AESUtil.decryptTexture(instance.open().readAllBytes()) : instance.open();
		return inputStream;
	}
}
