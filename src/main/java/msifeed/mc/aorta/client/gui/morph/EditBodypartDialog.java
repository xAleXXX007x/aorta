package msifeed.mc.aorta.client.gui.morph;

import msifeed.mc.aorta.core.character.BodyPart;
import msifeed.mc.mellow.layout.GridLayout;
import msifeed.mc.mellow.layout.ListLayout;
import msifeed.mc.mellow.widgets.Widget;
import msifeed.mc.mellow.widgets.basic.Separator;
import msifeed.mc.mellow.widgets.button.ButtonLabel;
import msifeed.mc.mellow.widgets.droplist.DropList;
import msifeed.mc.mellow.widgets.text.Label;
import msifeed.mc.mellow.widgets.text.TextInput;
import msifeed.mc.mellow.widgets.window.Window;

import java.util.Arrays;
import java.util.function.Consumer;

class EditBodypartDialog extends Window {
    private final BodyPart bodypart;
    private final ButtonLabel doneBtn = new ButtonLabel();

    EditBodypartDialog(Widget origin, Consumer<BodyPart> consumer) {
        this(origin, new BodyPart(), consumer);
        setTitle("New bodypart");
        doneBtn.setLabel("Add part");
    }

    EditBodypartDialog(Widget origin, BodyPart prevBodyPart, Consumer<BodyPart> consumer) {
        super(origin);

        this.bodypart = new BodyPart(prevBodyPart);

        setTitle("Edit bodypart");
        setFocused(this);

        final Widget content = getContent();
        content.setLayout(ListLayout.VERTICAL);

        final Widget params = new Widget();
        params.setLayout(new GridLayout());
        content.addChild(params);

        params.addChild(new Label("Name"));
        final TextInput nameInput = new TextInput();
        if (bodypart.name != null)
            nameInput.setText(bodypart.name);
        nameInput.setCallback(s -> bodypart.name = s);
        params.addChild(nameInput);

        params.addChild(new Label("Max health"));
        final TextInput healthInput = new TextInput();
        healthInput.setText(String.valueOf(bodypart.maxHealth));
        healthInput.setFilter(EditBodypartDialog::healthFilter);
        healthInput.setCallback(s -> bodypart.maxHealth = (short) healthInput.getInt());
        params.addChild(healthInput);

        params.addChild(new Label("Fatal"));
        final DropList<Boolean> fatalList = new DropList<>(Arrays.asList(false, true));
        fatalList.selectItem(bodypart.vital ? 1 : 0);
        fatalList.setSelectCallback(f -> bodypart.vital = f);
        params.addChild(fatalList);

        content.addChild(new Separator());

        final Widget footer = new Widget();
        footer.setLayout(ListLayout.HORIZONTAL);
        content.addChild(footer);

        doneBtn.setLabel("Apply");
        doneBtn.getMargin().left = doneBtn.getMargin().right = 10;
        doneBtn.setClickCallback(() -> {
            if (isBodypartInvalid())
                return;
            getParent().removeChild(this);

            bodypart.health = (short) Math.min(bodypart.health, bodypart.maxHealth);
            consumer.accept(bodypart);
        });
        footer.addChild(doneBtn);

        if (!isBodypartInvalid()) {
            final ButtonLabel removeBtn = new ButtonLabel("Remove");
            removeBtn.setClickCallback(() -> {
                getParent().removeChild(this);
                consumer.accept(null);
            });
            footer.addChild(removeBtn);
        }

        final ButtonLabel cancelBtn = new ButtonLabel("Cancel");
        cancelBtn.setClickCallback(() -> getParent().removeChild(this));
        footer.addChild(cancelBtn);
    }

    @Override
    protected void updateSelf() {
        doneBtn.setDisabled(isBodypartInvalid());
    }

    private boolean isBodypartInvalid() {
        return bodypart.name == null
                || bodypart.name.isEmpty()
                || bodypart.maxHealth < 1;
    }

    private static boolean healthFilter(String s) {
        return s.length() < 5 && TextInput.isUnsignedInt(s);
    }
}
