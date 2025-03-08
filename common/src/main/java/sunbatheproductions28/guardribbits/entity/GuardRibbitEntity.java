package sunbatheproductions28.guardribbits.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class GuardRibbitEntity extends AgeableMob implements GeoEntity, NeutralMob {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("walk");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("attack");
    private static final RawAnimation GUARD = RawAnimation.begin().thenPlay("guard");

    private static final UniformInt angerTime = TimeUtil.rangeOfSeconds(20, 39);

    private static final EntityDataAccessor<Boolean> PATROLLING = SynchedEntityData.defineId(GuardRibbitEntity.class, EntityDataSerializers.BOOLEAN);

    private int guardAnimationTick = 0;
    private int attackAnimationTick = 0;
    private BlockPos homePosition;
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;

    public GuardRibbitEntity(EntityType<GuardRibbitEntity> entityType, Level level) {
        super(entityType, level);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        // Movement and patrol goals
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true)); // Attack mobs effectively
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D)); // Adjust speed
        this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));

        // Targeting goals
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers()); // Retaliation
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Raider.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(6, new ResetUniversalAngerTargetGoal<>(this, false));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PolarBear.class, 12.0F, 1.0D, 1.5D));

    }

    public static AttributeSupplier.Builder createRibbitAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            if (this.remainingPersistentAngerTime > 0) {
                this.remainingPersistentAngerTime--;
                if (this.remainingPersistentAngerTime == 0) {
                    this.setPersistentAngerTarget(null);
                }
            }
        }

        if (this.attackAnimationTick > 0) {
            this.attackAnimationTick--;
            if (this.attackAnimationTick == 10) { // Trigger attack at a specific tick, e.g., 10
                performAttack();
            }
        }

        if (this.guardAnimationTick > 0) {
            this.guardAnimationTick--;
        }
    }


    @Override
    public boolean doHurtTarget(Entity target) {
        if (target instanceof LivingEntity livingEntity) {
            livingEntity.knockback(0.5F, Math.sin(this.getYRot() * Math.PI / 180), -Math.cos(this.getYRot() * Math.PI / 180));
            this.level().broadcastEntityEvent(this, (byte) 4);
        }
        return super.doHurtTarget(target);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPatrolling(compound.getBoolean("Patrolling"));
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Patrolling", this.isPatrolling());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
        // Attack animation
        if (this.isAttacking()) {
            state.setAnimation(ATTACK);
            return PlayState.CONTINUE;
        }
        // Guard animation
        if (this.isGuarding()) {
            state.setAnimation(GUARD);
            return PlayState.CONTINUE;
        }
        // Walk animation (when moving)
        if (state.isMoving()) {
            state.setAnimation(WALK);
            return PlayState.CONTINUE;
        }
        // Default to idle
        state.setAnimation(IDLE);
        return PlayState.CONTINUE;
    }

    private boolean isAttacking() {
        return this.getTarget() != null && this.attackAnim > 0; // Ensure the entity is actively attacking
    }

    private boolean isGuarding() {
        // Simplified logic for guarding: checking for nearby mobs
        return this.getTarget() == null && !this.level().getEntitiesOfClass(Mob.class,
                this.getBoundingBox().inflate(10), mob -> mob != this).isEmpty();
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PATROLLING, false); // Initialize patrolling as false
    }

    public boolean isPatrolling() {
        return this.entityData.get(PATROLLING);
    }

    public void setPatrolling(boolean patrolling) {
        this.entityData.set(PATROLLING, patrolling);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity attacker = source.getEntity();

        if (attacker instanceof LivingEntity && this.isFacing(attacker)) {
            // Play guarding animation
            this.level().broadcastEntityEvent(this, (byte) 5); // Custom guarding animation event

            // Reduce damage for guarding
            return super.hurt(source, amount * 0.25F); // Takes only 25% damage when guarding
        }

        // Normal damage otherwise
        return super.hurt(source, amount);
    }

    public boolean defendsRibbitAgainst(LivingEntity lastHurtByMob) {
        return !(lastHurtByMob instanceof Player);
    }

    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(UUID arg0) {
        this.persistentAngerTarget = arg0;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int arg0) {
        this.remainingPersistentAngerTime = arg0;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(angerTime.sample(random));
    }

    private boolean isFacing(Entity attacker) {
        // Calculate angle between attacker and the Guard Ribbit
        double deltaX = attacker.getX() - this.getX();
        double deltaZ = attacker.getZ() - this.getZ();
        double attackerAngle = Math.toDegrees(Math.atan2(deltaZ, deltaX)); // Angle of the attacker
        double ribbitAngle = this.getYRot() % 360.0; // Ribbit's current yaw

        // Normalize angles to [0, 360)
        attackerAngle = (attackerAngle + 360.0) % 360.0;
        ribbitAngle = (ribbitAngle + 360.0) % 360.0;

        // Check if the attacker's angle is within a ~90° cone in front of the Guard Ribbit
        double angleDifference = Math.abs(attackerAngle - ribbitAngle);
        return angleDifference <= 45.0 || angleDifference >= 315.0;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) { // Attack animation
            this.attackAnimationTick = 20;
        } else if (id == 5) { // Guarding animation
            this.guardAnimationTick = 20; // Duration for the guarding animation
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        if (this.guardAnimationTick > 0) {
            return super.getBoundingBox().inflate(0.3); // Temporarily increase hitbox size
        }
        return super.getBoundingBox();
    }

    private void performAttack() {
        LivingEntity target = this.getTarget();
        if (target != null && this.distanceToSqr(target) < this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F) {
            this.doHurtTarget(target);
        }
    }

}