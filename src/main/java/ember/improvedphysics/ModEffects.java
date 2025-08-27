package ember.improvedphysics;
//import ember.improvedphysics.effects.TransitioningStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import ember.improvedphysics.ImprovedPhysics;
import net.minecraft.util.Identifier;
public class ModEffects{
    //public static final StatusEffect TRANSITIONING=new TransitioningStatusEffect().setFactorCalculationDataSupplier(() -> new StatusEffectInstance.FactorCalculationData(22));
    public static void initialize(){
        //Registry.register(Registries.STATUS_EFFECT,new Identifier(ImprovedPhysics.MOD_ID,"transitioning"),TRANSITIONING);
    }
}