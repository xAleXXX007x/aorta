package msifeed.mc.mellow.widgets.scene;

import msifeed.mc.mellow.layout.AnchorLayout;
import msifeed.mc.mellow.utils.Point;
import msifeed.mc.mellow.widgets.Widget;

import java.util.ArrayList;
import java.util.Optional;

public class Scene extends Widget {

    public Scene() {
        setLayout(new AnchorLayout());
    }

    public Optional<Widget> lookupWidget(Point p) {
        return fullLookup(p);
    }

    private Optional<Widget> fullLookup(Point p) {
        ArrayList<Widget> active = new ArrayList<>();
        ArrayList<Widget> pending = new ArrayList<>(getLookupChildren());
        ArrayList<Widget> nextPending = new ArrayList<>();

        while (!pending.isEmpty()) {
            for (Widget pw : pending) {
                for (Widget w : pw.getLookupChildren()) {
                    if (w.isVisible())
                        nextPending.add(w);
                }
            }
            active.addAll(pending);
            pending.clear();
            pending.addAll(nextPending);
            nextPending.clear();
        }

        // Debug list
//        GL11.glPushMatrix();
//        GL11.glScalef(0.5f, 0.5f, 0.5f);
//        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
//        int y = 5;
//        for (Widget w : active.stream().filter(widget -> widget.containsPoint(p)).sorted(Widget::isHigherThan).collect(Collectors.toList())) {
//            final String s = String.format("%s (z: %d, d: %d)", w.toString(), w.getGeometry().z, w.getWidgetTreeDepth());
//            fr.drawString(s, 5, y, 0xffffff);
//            y += fr.FONT_HEIGHT + 2;
//        }
//        GL11.glPopMatrix();
        //

        if (active.isEmpty())
            return Optional.empty();

        return active
                .stream()
                .filter(widget -> widget.containsPoint(p))
                .max(Widget::isHigherThan);
    }
}
