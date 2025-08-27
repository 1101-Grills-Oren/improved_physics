package ember.improvedphysics;

import ember.improvedphysics.blocks.BouncySlimeBlock;
import ember.improvedphysics.blocks.BouncySlimeLayerBlock;
//import com.example.blocks.LevitatorBlock;
//import com.example.items.CustomItem;
//import com.example.blocks.BlockEntityTypes;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import ember.improvedphysics.ImprovedPhysics;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
public class ModBlocks {
	public static Block register(Block block, String name, boolean shouldRegisterItem) {
		// Register the block and its item.
		Identifier id = new Identifier(ImprovedPhysics.MOD_ID, name);

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			BlockItem blockItem = new BlockItem(block, new Item.Settings());
			Registry.register(Registries.ITEM, id, blockItem);
		}


		return Registry.register(Registries.BLOCK, id, block);
	}
	public static final Block BOUNCY_SLIME_BLOCK = register(
		new BouncySlimeBlock(AbstractBlock.Settings.create().slipperiness(1.1F).sounds(BlockSoundGroup.SLIME).nonOpaque()),
		"slime_block",
		true
	);
	public static final Block BOUNCY_SLIME_LAYER_BLOCK = register(
		new BouncySlimeLayerBlock(
			AbstractBlock.Settings.create()
				//.mapColor(MapColor.DARK_GREEN)
				.replaceable()
				.noCollision()
				.sounds(BlockSoundGroup.VINE)
				//.pistonBehavior(PistonBehavior.DESTROY)
		),"slime_layer",true
	);
	/*public static final Block LEVITATOR_BLOCK = register(
		new LevitatorBlock(Settings.create().sounds(BlockSoundGroup.GRASS).luminance(LevitatorBlock::getLuminance),
		() -> BlockEntityTypes.LEVITATOR_BLOCK
		),
		"levitator",
		true
	);*/
	public static void initialize(){
		//BlockEntityTypes.initialize();
}
}