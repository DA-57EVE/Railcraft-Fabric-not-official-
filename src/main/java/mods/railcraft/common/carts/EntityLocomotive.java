package mods.railcraft.common.carts;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

public abstract class EntityLocomotive extends CartBase {

    public enum LocoMode { SHUTDOWN, IDLE, RUNNING;
        public static final LocoMode[] VALUES = values();
    }

    public enum LocoSpeed { SLOWEST, SLOWER, NORMAL, MAX;
        public static final LocoSpeed[] VALUES = values();
    }

    private LocoMode mode    = LocoMode.SHUTDOWN;
    private LocoSpeed speed  = LocoSpeed.MAX;
    private boolean reverse  = false;
    private DyeColor primaryColor   = DyeColor.BLACK;
    private DyeColor secondaryColor = DyeColor.GRAY;

    protected EntityLocomotive(EntityType<?> type, Level level) {
        super(type, level);
    }

    public LocoMode  getMode()    { return mode; }
    public LocoSpeed getSpeed()   { return speed; }
    public boolean   isReverse()  { return reverse; }

    public void setMode(LocoMode mode)    { this.mode = mode; }
    public void setSpeed(LocoSpeed speed) { this.speed = speed; }
    public void setReverse(boolean rev)   { this.reverse = rev; }

    public boolean isRunning()  { return mode == LocoMode.RUNNING; }
    public boolean isIdle()     { return mode == LocoMode.IDLE; }
    public boolean isShutdown() { return mode == LocoMode.SHUTDOWN; }

    @Override
    public double getMaxSpeed() { return getLocomotiveMaxSpeed(); }

    public abstract double getLocomotiveMaxSpeed();

    public DyeColor getPrimaryColor()          { return primaryColor; }
    public DyeColor getSecondaryColor()        { return secondaryColor; }
    public void setPrimaryColor(DyeColor c)    { this.primaryColor = c; }
    public void setSecondaryColor(DyeColor c)  { this.secondaryColor = c; }

    public boolean isOwner(Player player) { return true; }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide() && isRunning()) {
            applyThrottle();
        }
    }

    protected abstract void applyThrottle();

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("locoMode",  mode.name());
        tag.putString("locoSpeed", speed.name());
        tag.putBoolean("reverse", reverse);
        tag.putInt("primaryColor",   primaryColor.getId());
        tag.putInt("secondaryColor", secondaryColor.getId());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        try { mode  = LocoMode.valueOf(tag.getString("locoMode")); }
        catch (IllegalArgumentException e) {
            // migrate old "RUNNING" value from before the enum rename
            mode = "RUNNING".equals(tag.getString("mode")) ? LocoMode.RUNNING : LocoMode.SHUTDOWN;
        }
        try { speed = LocoSpeed.valueOf(tag.getString("locoSpeed")); }
        catch (IllegalArgumentException e) { speed = LocoSpeed.MAX; }
        reverse        = tag.getBoolean("reverse");
        primaryColor   = DyeColor.byId(tag.getInt("primaryColor"));
        secondaryColor = DyeColor.byId(tag.getInt("secondaryColor"));
    }
}
