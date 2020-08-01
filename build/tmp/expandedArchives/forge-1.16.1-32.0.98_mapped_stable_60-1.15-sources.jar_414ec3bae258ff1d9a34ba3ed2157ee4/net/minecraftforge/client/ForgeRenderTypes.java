/*
 * Minecraft Forge
 * Copyright (c) 2016-2020.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.client;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.common.util.NonNullSupplier;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderState.TextureState;
import net.minecraft.client.renderer.RenderType.State;

@SuppressWarnings("deprecation")
public enum ForgeRenderTypes
{
    ITEM_LAYERED_SOLID(()-> getItemLayeredSolid(AtlasTexture.LOCATION_BLOCKS_TEXTURE)),
    ITEM_LAYERED_CUTOUT(()-> getItemLayeredCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE)),
    ITEM_LAYERED_CUTOUT_MIPPED(()-> getItemLayeredCutoutMipped(AtlasTexture.LOCATION_BLOCKS_TEXTURE)),
    ITEM_LAYERED_TRANSLUCENT(()-> getItemLayeredTranslucent(AtlasTexture.LOCATION_BLOCKS_TEXTURE)),
    ITEM_UNSORTED_TRANSLUCENT(()-> getUnsortedTranslucent(AtlasTexture.LOCATION_BLOCKS_TEXTURE)),
    ITEM_UNLIT_TRANSLUCENT(()-> getUnlitTranslucent(AtlasTexture.LOCATION_BLOCKS_TEXTURE)),
    ITEM_UNSORTED_UNLIT_TRANSLUCENT(()-> getUnlitTranslucent(AtlasTexture.LOCATION_BLOCKS_TEXTURE, false));

    /**
     * @return A RenderType fit for multi-layer solid item rendering.
     */
    public static RenderType getItemLayeredSolid(ResourceLocation textureLocation)
    {
        return Internal.layeredItemSolid(textureLocation);
    }

    /**
     * @return A RenderType fit for multi-layer cutout item item rendering.
     */
    public static RenderType getItemLayeredCutout(ResourceLocation textureLocation)
    {
        return Internal.layeredItemCutout(textureLocation);
    }

    /**
     * @return A RenderType fit for multi-layer cutout-mipped item rendering.
     */
    public static RenderType getItemLayeredCutoutMipped(ResourceLocation textureLocation)
    {
        return Internal.layeredItemCutoutMipped(textureLocation);
    }

    /**
     * @return A RenderType fit for multi-layer translucent item rendering.
     */
    public static RenderType getItemLayeredTranslucent(ResourceLocation textureLocation)
    {
        return Internal.layeredItemTranslucent(textureLocation);
    }

    /**
     * @return A RenderType fit for translucent item/entity rendering, but with depth sorting disabled.
     */
    public static RenderType getUnsortedTranslucent(ResourceLocation textureLocation)
    {
        return Internal.unsortedTranslucent(textureLocation);
    }

    /**
     * @return A RenderType fit for translucent item/entity rendering, but with diffuse lighting disabled
     * so that fullbright quads look correct.
     */
    public static RenderType getUnlitTranslucent(ResourceLocation textureLocation)
    {
        return getUnlitTranslucent(textureLocation, true);
    }

    /**
     * @return A RenderType fit for translucent item/entity rendering, but with diffuse lighting disabled
     * so that fullbright quads look correct.
     * @param sortingEnabled If false, depth sorting will not be performed.
     */
    public static RenderType getUnlitTranslucent(ResourceLocation textureLocation, boolean sortingEnabled)
    {
        return Internal.unlitTranslucent(textureLocation, sortingEnabled);
    }

    /**
     * @return Same as {@link RenderType#getEntityCutout(ResourceLocation)}, but with mipmapping enabled.
     */
    public static RenderType getEntityCutoutMipped(ResourceLocation textureLocation)
    {
        return Internal.layeredItemCutoutMipped(textureLocation);
    }

    // ----------------------------------------
    //  Implementation details below this line
    // ----------------------------------------

    private final NonNullSupplier<RenderType> renderTypeSupplier;

    ForgeRenderTypes(NonNullSupplier<RenderType> renderTypeSupplier)
    {
        // Wrap in a Lazy<> to avoid running the supplier more than once.
        this.renderTypeSupplier = NonNullLazy.of(renderTypeSupplier);
    }

    public RenderType get()
    {
        return renderTypeSupplier.get();
    }

    private static class Internal extends RenderType
    {
        private Internal(String name, VertexFormat fmt, int glMode, int size, boolean doCrumbling, boolean depthSorting, Runnable onEnable, Runnable onDisable)
        {
            super(name, fmt, glMode, size, doCrumbling, depthSorting, onEnable, onDisable);
            throw new IllegalStateException("This class must not be instantiated");
        }

        public static RenderType unsortedTranslucent(ResourceLocation textureLocation)
        {
            final boolean sortingEnabled = false;
            State renderState = State.func_228694_a_()
                    .func_228724_a_(new TextureState(textureLocation, false, false))
                    .func_228726_a_(field_228515_g_)
                    .func_228716_a_(field_228532_x_)
                    .func_228713_a_(field_228517_i_)
                    .func_228714_a_(field_228491_A_)
                    .func_228719_a_(field_228528_t_)
                    .func_228722_a_(field_228530_v_)
                    .func_228728_a_(true);
            return func_228633_a_("forge_entity_unsorted_translucent", DefaultVertexFormats.field_227849_i_, GL11.GL_QUADS, 256, true, sortingEnabled, renderState);
        }

        public static RenderType unlitTranslucent(ResourceLocation textureLocation, boolean sortingEnabled)
        {
            State renderState = State.func_228694_a_()
                    .func_228724_a_(new TextureState(textureLocation, false, false))
                    .func_228726_a_(field_228515_g_)
                    .func_228713_a_(field_228517_i_)
                    .func_228714_a_(field_228491_A_)
                    .func_228719_a_(field_228528_t_)
                    .func_228722_a_(field_228530_v_)
                    .func_228728_a_(true);
            return func_228633_a_("forge_entity_unlit_translucent", DefaultVertexFormats.field_227849_i_, GL11.GL_QUADS, 256, true, sortingEnabled, renderState);
        }

        public static RenderType layeredItemSolid(ResourceLocation locationIn) {
            RenderType.State rendertype$state = RenderType.State.func_228694_a_()
                    .func_228724_a_(new RenderState.TextureState(locationIn, false, false))
                    .func_228726_a_(field_228510_b_)
                    .func_228716_a_(field_228532_x_)
                    .func_228719_a_(field_228528_t_)
                    .func_228722_a_(field_228530_v_)
                    .func_228728_a_(true);
            return func_228633_a_("forge_item_entity_solid", DefaultVertexFormats.field_227849_i_, 7, 256, true, false, rendertype$state);
        }

        public static RenderType layeredItemCutout(ResourceLocation locationIn) {
            RenderType.State rendertype$state = RenderType.State.func_228694_a_()
                    .func_228724_a_(new RenderState.TextureState(locationIn, false, false))
                    .func_228726_a_(field_228510_b_)
                    .func_228716_a_(field_228532_x_)
                    .func_228713_a_(field_228517_i_)
                    .func_228719_a_(field_228528_t_)
                    .func_228722_a_(field_228530_v_)
                    .func_228728_a_(true);
            return func_228633_a_("forge_item_entity_cutout", DefaultVertexFormats.field_227849_i_, 7, 256, true, false, rendertype$state);
        }

        public static RenderType layeredItemCutoutMipped(ResourceLocation locationIn) {
            RenderType.State rendertype$state = RenderType.State.func_228694_a_()
                    .func_228724_a_(new RenderState.TextureState(locationIn, false, true))
                    .func_228726_a_(field_228510_b_)
                    .func_228716_a_(field_228532_x_)
                    .func_228713_a_(field_228517_i_)
                    .func_228719_a_(field_228528_t_)
                    .func_228722_a_(field_228530_v_)
                    .func_228728_a_(true);
            return func_228633_a_("forge_item_entity_cutout_mipped", DefaultVertexFormats.field_227849_i_, 7, 256, true, false, rendertype$state);
        }

        public static RenderType layeredItemTranslucent(ResourceLocation p_239268_0_) {
            RenderType.State rendertype$state = RenderType.State.func_228694_a_()
                    .func_228724_a_(new RenderState.TextureState(p_239268_0_, false, false))
                    .func_228726_a_(field_228515_g_)
                    .func_228716_a_(field_228532_x_)
                    .func_228713_a_(field_228517_i_)
                    .func_228719_a_(field_228528_t_)
                    .func_228722_a_(field_228530_v_)
                    .func_228728_a_(true);
            return func_228633_a_("forge_item_entity_translucent_cull", DefaultVertexFormats.field_227849_i_, 7, 256, true, true, rendertype$state);
        }
    }
}