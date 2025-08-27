package ember.improvedphysics.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.block.AirBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.MovementType;
@Mixin(Entity.class)
public abstract class EntityMovementMixin{
	public boolean ignoreControls=false;
	@Shadow
	public static final float DEFAULT_FRICTION=0.0F;
	@Shadow
	public abstract boolean isOnGround();
	@Shadow
	public abstract Vec3d getVelocity();
	@Inject(at = @At("HEAD"), method = "getVelocityMultiplier",cancellable=true)
	protected void getVelocityMultiplier(CallbackInfoReturnable<Float> info) {
		if(this.isOnGround()){
			this.ignoreControls=false;
		}else if(((this.getVelocity().distanceTo(Vec3d.ZERO)>1)||(this.ignoreControls))){
			info.setReturnValue(1.0989F);
			this.ignoreControls=true;
			info.cancel();
		}
		//return 1 if above speed
		
	}
	@Inject(at=@At("HEAD"),method="updateVelocity",cancellable=true)
	public void movementCanceller(float speed, Vec3d movementInput,CallbackInfo info){
		if(this.ignoreControls){
			info.cancel();
		}
	}
	@ModifyConstant(method="move",constant=@Constant(floatValue=0.6F),expect=3,allow=3)
	private float newValue(float value){
		return this.DEFAULT_FRICTION;
	}

	
	//@Inject(at=@At("HEAD"),method="updateVelocity")
	//public void updateVelocity(float speed, Vec3d movementInput){

	//}
}