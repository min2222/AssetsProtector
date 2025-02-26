package com.min01.aes.mixin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.aes.AESUtil;

import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;

@Mixin(Resource.class)
public class MixinResource 
{
	@Shadow
	@Final
	private IoSupplier<InputStream> streamSupplier;
	
	@Inject(at = @At("RETURN"), method = "open", cancellable = true)
	private void open(CallbackInfoReturnable<InputStream> cir) throws IOException
	{
		InputStream stream = cir.getReturnValue();
		byte[] array = stream.readAllBytes();
		if(array.length > 76)
		{
			try
			{
				byte[] copy = ArrayUtils.subarray(array, array.length - 60, array.length - 16);
			    Pattern pattern = Pattern.compile("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$");
				if(pattern.matcher(new String(copy)).matches())
				{
					cir.setReturnValue(AESUtil.decrypt(array));
					return;
				}
			}
			catch (Exception e) 
			{
				
			}
		}
	    cir.setReturnValue(new ByteArrayInputStream(array));
	}
}
