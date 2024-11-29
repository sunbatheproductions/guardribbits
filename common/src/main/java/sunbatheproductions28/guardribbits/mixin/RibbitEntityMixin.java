package sunbatheproductions28.guardribbits.mixin;

import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import sunbatheproductions28.guardribbits.entity.GuardRibbitEntity;
import sunbatheproductions28.guardribbits.entity.goal.RibbitAIAlertGuards;

@Mixin(RibbitEntity.class)
public class RibbitEntityMixin extends PathfinderMob {
    protected RibbitEntityMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    protected void registerGoals() {
        super.registerGoals();

        this.targetSelector.addGoal(2, new RibbitAIAlertGuards(this, GuardRibbitEntity.class));
    }
}
