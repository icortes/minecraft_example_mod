package com.omgisa.examplemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.omgisa.examplemod.ExampleMod;
import com.omgisa.examplemod.entity.custom.GeckoEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GeckoRenderer extends MobRenderer<GeckoEntity, GeckoModel<GeckoEntity>> {
    public GeckoRenderer(EntityRendererProvider.Context context) {
        super(context, new GeckoModel<>(context.bakeLayer(GeckoModel.LAYER_LOCATION)), 0.25F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GeckoEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "textures/entity/gecko/gecko_blue.png");
    }

    @Override
    public void render(@NotNull GeckoEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.45F, 0.45F, 0.45F);
        } else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
