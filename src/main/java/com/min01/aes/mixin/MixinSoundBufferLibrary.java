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

import org.apache.commons.codec.binary.Base64;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.min01.aes.AESUtil;

import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

@Mixin(SoundBufferLibrary.class)
public class MixinSoundBufferLibrary
{
	//lambda$getCompleteBuffer$0 for dev environmental;
	//m_174980_ for compiling;
	
	@Redirect(method = "lambda$getCompleteBuffer$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/ResourceManager;open(Lnet/minecraft/resources/ResourceLocation;)Ljava/io/InputStream;"))
	private InputStream getCompleteBuffer(ResourceManager instance, ResourceLocation resourceLocation) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, InvalidKeySpecException
	{
		byte[] array = instance.open(resourceLocation).readAllBytes();
        InputStream inputStream = Base64.isBase64(Arrays.copyOfRange(array, array.length - 60, array.length - 16)) ? AESUtil.decrypt(array) : instance.open(resourceLocation);
        return inputStream;
	}
}
