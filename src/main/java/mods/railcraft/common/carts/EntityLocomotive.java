package mods.railcraft.common.carts;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

public abstract class EntityLocomotive extends CartBase {

    public enum Mode { RUNNING, IDLE, SHUTDOWN }

    private Mode mode = Mode.IDLE;
    private DyeColor primaryColor   = DyeColor.BLACK;
    private DyeColor secondaryColor = DyeColor.GRAY;

    protected EntityLocomotive(EntityType<?> type, Level level) {
        super(type, level);
    }

    public Mode getMode()          { return mode; }
    public void setMode(Mode mode) { this.mode = mode; }

    @Override
    public double getMaxSpeed() { return getLocomotiveMaxSpeed(); }

    /** Override in subclasses to provide locomotive-specific max speed. */
    public abstract double getLocomotiveMaxSpeed();

    public DyeColor getPrimaryColor()          { return primaryColor; }
    public DyeColor getSecondaryColor()        { return secondaryColor; }
    public void setPrimaryColor(DyeColor c)    { this.primaryColor = c; }
    public void setSecondaryColor(DyeColor c)  { this.secondaryColor = c; }

    public boolean isOwner(Player player) { return true; }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide() && mode == Mode.RUNNING) {
            applyThrottle();
        }
    }

    protected abstract void applyThrottle();

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("mode", mode.name());
        tag.putInt("primaryColor",   primaryColor.getId());
        tag.putInt("secondaryColor", secondaryColor.getId());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        try { mode = Mode.valueOf(tag.getString("mode")); } catch (IllegalArgumentException e) { mode = Mode.IDLE; }
        primaryColor   = DyeColor.byId(tag.getInt("primaryColor"));
        secondaryColor = DyeColor.byId(tag.getInt("secondaryColor"));
    }
}
