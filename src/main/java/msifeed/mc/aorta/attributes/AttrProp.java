package msifeed.mc.aorta.attributes;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class AttrProp<T> implements IExtendedEntityProperties {
    public T value = null;
    private final EntityAttribute<T> attribute;

    AttrProp(EntityAttribute<T> attribute) {
        this.attribute = attribute;
    }

    @Override
    public final void init(Entity entity, World world) {
        value = attribute.init(entity, world, value);
    }

    @Override
    public void saveNBTData(NBTTagCompound root) {
        attribute.saveNBTData(value, root);
    }

    @Override
    public void loadNBTData(NBTTagCompound root) {
        value = attribute.loadNBTData(root);
    }
}