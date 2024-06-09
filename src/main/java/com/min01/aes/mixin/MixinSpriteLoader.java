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

import org.apache.commons.codec.binary.Base64;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.min01.aes.AESUtil;

import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.server.packs.resources.Resource;

@Mixin(SpriteLoader.class)
public class MixinSpriteLoader
{
	@Redirect(method = "loadSprite", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/Resource;open()Ljava/io/InputStream;"))
	private static InputStream loadSprite(Resource instance) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException
	{
		byte[] array = instance.open().readAllBytes();
        InputStream inputStream = ImageIO.read(instance.open()) != null ? instance.open() : Base64.isBase64(Arrays.copyOfRange(array, array.length - 60, array.length - 16)) ? AESUtil.decryptTexture(array) : instance.open();
        return inputStream;
	}
}
