/*
 * Copyright (c) 2021 - 2022 LambdAurora <email@lambdaurora.dev>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.lambdaurora.aurorasdeco.mixin.client;

import dev.lambdaurora.aurorasdeco.block.entity.BlackboardBlockEntity;
import dev.lambdaurora.aurorasdeco.client.Wind;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	@Inject(method = "setWorld", at = @At("HEAD"))
	private void onSetWorld(ClientWorld world, CallbackInfo ci) {
		BlackboardBlockEntity.onWorldChange(world);
		Wind.get().reset();
	}

	@ModifyVariable(
			method = "startIntegratedServer(Ljava/lang/String;Ljava/util/function/Function;Ljava/util/function/Function;ZLnet/minecraft/client/MinecraftClient$WorldLoadAction;)V",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/MinecraftClient$WorldLoadAction;NONE:Lnet/minecraft/client/MinecraftClient$WorldLoadAction;",
					ordinal = 0
			),
			ordinal = 2,
			index = 11,
			name = "bl2",
			require = 0
	)
	private boolean replaceIsExperimental(boolean isExperimental) {
		return false;
	}
}
