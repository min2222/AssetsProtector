package com.min01.aes.mixin;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.aes.AESUtil;

import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.Resource.IoSupplier;

@Mixin(Resource.class)
public class MixinResource 
{
	@Shadow
	@Final
	private IoSupplier<InputStream> streamSupplier;
	
	@Inject(at = @At("RETURN"), method = "open", cancellable = true)
	private void open(CallbackInfoReturnable<InputStream> cir) throws IOException
	{
		byte[] array = this.streamSupplier.get().readAllBytes();
		if(array.length > 60)
		{
			byte[] copy = ArrayUtils.subarray(array, array.length - 61, array.length - 17);
		    Pattern pattern = Pattern.compile("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$");
			if(pattern.matcher(new String(copy)).matches())
			{
				try
				{
					cir.setReturnValue(AESUtil.decrypt(array));
				}
				catch(InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | NoSuchPaddingException | InvalidKeySpecException | IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
