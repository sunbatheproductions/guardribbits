import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sunbatheproductions28.guardribbits.entity.GuardRibbitEntity;

import java.util.Iterator;
import java.util.List;

@Mixin(RibbitEntity.class)
public abstract class RibbitEntityMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof RibbitEntity ribbit) {
            // Get the level where the RibbitEntity is located
            if (ribbit.level() instanceof ServerLevel serverLevel) {
                // Get the attacker entity
                Entity attackerEntity = source.getEntity();
                if (attackerEntity instanceof LivingEntity attacker) {
                    // Use the alertOthers logic to alert nearby guards
                    alertOthers(ribbit, attacker, serverLevel);
                }
            }
        }
    }

    private void alertOthers(RibbitEntity ribbit, LivingEntity attacker, ServerLevel serverLevel) {
        double followDistance = ribbit.getFollowDistance(); // Use follow distance
        AABB alertBox = AABB.unitCubeFromLowerCorner(ribbit.position()).inflate(followDistance, 10.0D, followDistance); // Alert area

        // Get nearby GuardRibbitEntities
        List<GuardRibbitEntity> guards = serverLevel.getEntitiesOfClass(
                GuardRibbitEntity.class,
                alertBox,
                EntitySelector.NO_SPECTATORS
        );

        // Loop through all guards and alert them
        Iterator<GuardRibbitEntity> iterator = guards.iterator();
        while (iterator.hasNext()) {
            GuardRibbitEntity guard = iterator.next();
            if (ribbit != guard && guard.getTarget() == null && !guard.isAlliedTo(ribbit.getLastHurtByMob()) && guard.defendsRibbitsAgainst(ribbit.getLastHurtByMob())) {
                guard.setTarget(attacker);
                guard.setPersistentAngerTarget(attacker.getUUID());
                guard.startPersistentAngerTimer();
            }
        }
    }
}
