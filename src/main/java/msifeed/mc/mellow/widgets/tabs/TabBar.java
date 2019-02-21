package msifeed.mc.mellow.widgets.tabs;

import msifeed.mc.mellow.Mellow;
import msifeed.mc.mellow.layout.ListLayout;
import msifeed.mc.mellow.render.RenderShapes;
import msifeed.mc.mellow.theme.Part;
import msifeed.mc.mellow.utils.Geom;
import msifeed.mc.mellow.widgets.Widget;

import java.util.ArrayList;

class TabBar extends Widget {
    private final int borderColor = Mellow.getColor("dark_border");

    private final TabArea parent;
    private final ArrayList<TabButton> tabs = new ArrayList<>();
    private TabButton activeTab = null;
    private int activeTabIndex = -1;

    TabBar(TabArea parent) {
        this.parent = parent;
        setLayout(ListLayout.HORIZONTAL);
    }

    void addTab(String name) {
        final TabButton tabButton = new TabButton(this, name);
        final int tabIndex = tabs.size();
        tabButton.setClickCallback(() -> selectTab(tabIndex));
        tabs.add(tabButton);
        addChild(tabButton);
    }

    boolean isActiveTab(TabButton tabButton) {
        return activeTab == tabButton;
    }

    void selectTab(int i) {
        activeTab = tabs.get(i);
        activeTabIndex = i;
        parent.selectContent(i);
    }

    @Override
    protected void postRenderSelf() {
        if (activeTab == null)
            return;

        final int thickness = 2;
        final Geom self = getGeometry();
        final Geom active = activeTab.getGeometry();

        final Geom geom = new Geom(getGeometry());
        geom.z = active.z + 1;
        geom.y = active.y + active.h - 1;
        geom.h = geom.y;

        geom.x = self.x;
        geom.w = active.x;
        RenderShapes.line(geom, thickness, borderColor);

        geom.x = active.x + active.w;
        geom.w = self.x + self.w;
        RenderShapes.line(geom, thickness, borderColor);
    }
}
