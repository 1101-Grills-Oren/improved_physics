package ember.improvedphysics;
//import jdk.javadoc.internal.doclets.formats.html.markup.Text;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.text.Text;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
@Environment(EnvType.CLIENT)
public class ImprovedPhysicsClient implements ClientModInitializer{
    static float dashSpeed=1;
    public static float getDashSpeed(){
        return ImprovedPhysicsClient.dashSpeed;
    }
    public static void setDashSpeed(float value){
        ImprovedPhysicsClient.dashSpeed=value;
    }
    /*
    private static KeyBinding keyBindingA=KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.improvedphysics.dash",
        InputUtil.Type.KEYSYM,
        InputUtil.GLFW_KEY_O,
        "category.improvedphysics.test"
    ));
    private static KeyBinding keyBindingB=KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.improvedphysics.dashplus",
        InputUtil.Type.KEYSYM,
        InputUtil.GLFW_KEY_C,
        "category.improvedphysics.test"
    ));
    private static KeyBinding keyBindingC=KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.improvedphysics.dashminus",
        InputUtil.Type.KEYSYM,
        InputUtil.GLFW_KEY_V,
        "category.improvedphysics.test"
    ));*/
    @Override
    public void onInitializeClient(){
        /*
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingA.wasPressed()){
                //client.player.sendMessage(Text.literal("Key was pressed"),false);
                PacketByteBuf buf=PacketByteBufs.create();
                buf.writeString("execute positioned 0 0 0 positioned ^ ^ ^%f run setVel @s ~-0.5 ~0.0 ~-0.5".formatted((ImprovedPhysicsClient.getDashSpeed())));
                ClientPlayNetworking.send(CommandTriggerPacket.PACKET_ID, buf);
            }//execute positioned 0 0 0 positioned ^ ^ ^1 run accelerate @s ~-0.5 ~0.0 ~-0.5
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingB.wasPressed()){
                setDashSpeed(getDashSpeed()-0.1F);
                client.player.sendMessage(Text.literal("Dash Strength %f".formatted(ImprovedPhysicsClient.getDashSpeed())),false);
            }//execute positioned 0 0 0 positioned ^ ^ ^1 run accelerate @s ~-0.5 ~0.0 ~-0.5
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingC.wasPressed()){
                setDashSpeed(getDashSpeed()+0.1F);
                client.player.sendMessage(Text.literal("Dash Strength %f".formatted(ImprovedPhysicsClient.getDashSpeed())),false);
            }//execute positioned 0 0 0 positioned ^ ^ ^1 run accelerate @s ~-0.5 ~0.0 ~-0.5
        });*/
    }
}