package msifeed.mc.aorta.client.hud;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import msifeed.mc.Bootstrap;
import msifeed.mc.aorta.core.character.Character;
import msifeed.mc.aorta.core.utils.CharacterAttribute;
import msifeed.mc.mellow.render.RenderParts;
import msifeed.mc.mellow.theme.Part;
import msifeed.mc.mellow.utils.Point;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

public enum StatusHudReplacer {
    INSTANCE;

    private Part[] sanitySprites;
    private Part[] vitalitySprites;
    private Part[] psionicsSprites;

    public static void init() {
        INSTANCE.sanitySprites = makeStatusSpriteParts(0, 5);
        INSTANCE.vitalitySprites = makeStatusSpriteParts(1, 4);
        INSTANCE.psionicsSprites = makeStatusSpriteParts(2, 4);
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }

    private static Part[] makeStatusSpriteParts(int row, int count) {
        final ResourceLocation statusSpriteMap = new ResourceLocation(Bootstrap.MODID, "textures/gui/status_hud.png");
        final Point spriteSize = new Point(32, 32);

        final Part[] parts = new Part[count];
        for (int i = 0; i < count; ++i) {
            final Part p = new Part();
            p.sprite = statusSpriteMap;
            p.pos = new Point(i * 32, row * 32);
            p.size = spriteSize;
            parts[i] = p;
        }
        return parts;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPreRenderOverlay(RenderGameOverlayEvent.Pre event) {
        switch (event.type) {
            case HEALTH:
            case ARMOR:
            case FOOD:
            case EXPERIENCE:
                event.setCanceled(true);
                break;
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL)
            return;

        final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        final Character character = CharacterAttribute.require(player);

        GL11.glPushMatrix();
        GL11.glScalef(.5f, .5f, 1f);

        int y = 10;

        final int sanityLevel = character.sanityLevel();
        RenderParts.slice(sanitySprites[sanitySprites.length - sanityLevel - 1], 10, y, 10);
        y += 40;

        final int vitalityLevel = character.vitalityLevel();
        if (vitalityLevel > 0) {
            RenderParts.slice(vitalitySprites[vitalityLevel - 1], 10, y, 10);
            y += 40;
        }

        final int psionicsLevel = character.psionicsLevel();
        if (psionicsLevel > 0) {
            RenderParts.slice(psionicsSprites[psionicsLevel - 1], 10, y, 10);
        }

        GL11.glPopMatrix();
    }
}
