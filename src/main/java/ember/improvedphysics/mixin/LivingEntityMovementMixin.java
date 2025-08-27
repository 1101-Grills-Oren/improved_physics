package ember.improvedphysics.mixin;

import ember.improvedphysics.mixin.EntityMovementMixin;
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
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.MovementType;
@Mixin(LivingEntity.class)
public abstract class LivingEntityMovementMixin extends EntityMovementMixin{
	public static float SLOW_DOWN_MULTIPLIER=0;
	/*@Inject(at = @At("HEAD"), method = "getVelocityMultiplier",cancellable=true)
	protected void getVelocityMultiplier(CallbackInfoReturnable<Float> info) {
		if(this.isOnGround()){
			this.ignoreControls=false;
		}else if(((this.getVelocity().distanceTo(Vec3d.ZERO)>1)||(this.ignoreControls))){
			info.setReturnValue(1.2F);
			this.ignoreControls=true;
			info.cancel();
		}
		//return 1 if above speed
		
	}*/
	/*@Inject(at=@At("HEAD"),method="updateVelocity",cancellable=true)
	public void movementCanceller(float speed, Vec3d movementInput,CallbackInfo info){
		if(this.ignoreControls){
			info.cancel();
		}
	}*/

	@ModifyConstant(method="travel",constant=@Constant(floatValue=0.91F),expect=2,allow=2)
	private float newValue(float value){
		return this.SLOW_DOWN_MULTIPLIER;
	}

	
	//@Inject(at=@At("HEAD"),method="updateVelocity")
	//public void updateVelocity(float speed, Vec3d movementInput){

	//}
}