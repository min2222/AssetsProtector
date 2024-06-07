package com.min01.aes.mixin;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.min01.aes.AESUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleDescription;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

@Mixin(ParticleDescription.class)
public class MixinParticleDescription
{
	@Redirect(method = "fromJson", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;map(Ljava/util/function/Function;)Ljava/util/stream/Stream;", ordinal = 1), remap = false)
    private static Stream<Object> fromJson(Stream<String> instance, Function<? super String, ? extends ResourceLocation> function)
    {
    	return instance.map(t -> 
    	{
    		ResourceLocation location = new ResourceLocation(t);
			try 
			{
				Resource resource = Minecraft.getInstance().getResourceManager().getResourceOrThrow(location);
				return ImageIO.read(resource.open()) != null ? location : AESUtil.getTexture(location);
			}
			catch (IOException e) 
			{
				//e.printStackTrace();
			}
			return location;
    	});
    }
}
