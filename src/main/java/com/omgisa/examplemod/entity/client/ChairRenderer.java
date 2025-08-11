package com.omgisa.examplemod.entity.client;

import com.omgisa.examplemod.entity.custom.ChairEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ChairRenderer extends EntityRenderer<ChairEntity> {
    public ChairRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull ChairEntity chairEntity) {
        return null;
    }

    @Override
    public boolean shouldRender(@NotNull ChairEntity livingEntity, @NotNull Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
