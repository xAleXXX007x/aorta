package msifeed.mc.aorta.client.gui.roller;

import msifeed.mc.aorta.core.character.Feature;
import msifeed.mc.aorta.core.rolls.RollRpc;
import msifeed.mc.mellow.layout.GridLayout;
import msifeed.mc.mellow.widgets.Widget;
import msifeed.mc.mellow.widgets.button.ButtonLabel;
import net.minecraft.entity.EntityLivingBase;

class FeatureRollsView extends Widget {
    private final EntityLivingBase entity;
    private final TargetView targetView;

    FeatureRollsView(EntityLivingBase entity, TargetView targetView) {
        this.entity = entity;
        this.targetView = targetView;

        setLayout(new GridLayout());
        for (Feature feature : Feature.values())
            addChild(makeRollButton(feature));
    }

    private Widget makeRollButton(Feature feature) {
        final ButtonLabel b = new ButtonLabel(feature.tr());
        b.setClickCallback(() -> roll(feature));
        return b;
    }

    private void roll(Feature feature) {
        if (System.currentTimeMillis() - ScreenRoller.prevRollTime < 1000)
            return;

        final String target = feature.canTarget() ? targetView.getTarget() : "";
        RollRpc.rollFeature(entity.getEntityId(), feature, target);
        ScreenRoller.prevRollTime = System.currentTimeMillis();
    }
}
