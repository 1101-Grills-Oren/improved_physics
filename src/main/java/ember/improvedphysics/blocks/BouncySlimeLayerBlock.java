package ember.improvedphysics.blocks;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.block.Block;
import net.minecraft.block.VineBlock;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.Nullable;
import net.minecraft.item.ItemPlacementContext;

public class BouncySlimeLayerBlock extends Block {
	public static final BooleanProperty UP = ConnectingBlock.UP;
	public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
	public static final BooleanProperty EAST = ConnectingBlock.EAST;
	public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
	public static final BooleanProperty WEST = ConnectingBlock.WEST;
	public static final BooleanProperty DOWN = ConnectingBlock.DOWN;
	public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = (Map<Direction, BooleanProperty>)ConnectingBlock.FACING_PROPERTIES;
	
	private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
	private static final VoxelShape DOWN_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
	private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
	private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
	private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
	private final Map<BlockState, VoxelShape> shapesByState;


	public BouncySlimeLayerBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(UP, false).with(DOWN, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
		this.shapesByState = ImmutableMap.copyOf(
			(Map<? extends BlockState, ? extends VoxelShape>)this.stateManager
				.getStates()
				.stream()
				.collect(Collectors.toMap(Function.identity(), BouncySlimeLayerBlock::getShapeForState))
		);	
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(UP, NORTH, EAST, SOUTH, WEST,DOWN);
	}
	private BlockState getPlacementShape(BlockState state, BlockView world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		if ((Boolean)state.get(UP)) {
			state = state.with(UP, shouldConnectTo(world, blockPos, Direction.DOWN));
		}

		BlockState blockState = null;

		for (Direction direction : Direction.Type.HORIZONTAL) {
			BooleanProperty booleanProperty = getFacingProperty(direction);
			if ((Boolean)state.get(booleanProperty)) {
				boolean bl = this.shouldHaveSide(world, pos, direction);
				if (!bl) {
					if (blockState == null) {
						blockState = world.getBlockState(blockPos);
					}

					bl = blockState.isOf(this) && (Boolean)blockState.get(booleanProperty);
				}

				state = state.with(booleanProperty, bl);
			}
		}

		return state;
	}
	private static VoxelShape getShapeForState(BlockState state) {
		VoxelShape voxelShape = VoxelShapes.empty();
		if ((Boolean)state.get(UP)) {
			voxelShape = UP_SHAPE;
		}

		if ((Boolean)state.get(DOWN)) {
			voxelShape = VoxelShapes.union(voxelShape, DOWN_SHAPE);
		}

		if ((Boolean)state.get(NORTH)) {
			voxelShape = VoxelShapes.union(voxelShape, SOUTH_SHAPE);
		}

		if ((Boolean)state.get(SOUTH)) {
			voxelShape = VoxelShapes.union(voxelShape, NORTH_SHAPE);
		}

		if ((Boolean)state.get(EAST)) {
			voxelShape = VoxelShapes.union(voxelShape, WEST_SHAPE);
		}

		if ((Boolean)state.get(WEST)) {
			voxelShape = VoxelShapes.union(voxelShape, EAST_SHAPE);
		}

		return voxelShape.isEmpty() ? VoxelShapes.fullCube() : voxelShape;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return (VoxelShape)this.shapesByState.get(state);
	}
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		world
				.findClosestCollision(entity, (VoxelShape)this.shapesByState.get(state), entity.getPos().add(0.0, entity.getDimensions(entity.getPose()).height / 2.0, 0.0), 0,0,0)
				.ifPresent(posx -> {
		if (!world.isClient) {
			Vec3d eVel=entity.getVelocity();
			if(state.get(UP)&&(eVel.getY()>0)){
				world
				.findClosestCollision(entity, UP_SHAPE, entity.getPos().add(0.0, entity.getDimensions(entity.getPose()).height / 2.0, 0.0), 0,0,0)
				.ifPresent(posy -> {
				entity.setVelocity(entity.getVelocity().multiply(1,-1,1));
				entity.velocityModified=true;
				});
			}else if(state.get(DOWN)&&(eVel.getY()<0)){
				world
				.findClosestCollision(entity, DOWN_SHAPE, entity.getPos().add(0.0, entity.getDimensions(entity.getPose()).height / 2.0, 0.0), 0,0,0)
				.ifPresent(posy -> {
				entity.setVelocity(entity.getVelocity().multiply(1,-1,1));
				entity.velocityModified=true;
				});
			}
			
			if(state.get(EAST)&&(eVel.getX()>0)){
				world
				.findClosestCollision(entity, EAST_SHAPE, entity.getPos().add(0.0, entity.getDimensions(entity.getPose()).height / 2.0, 0.0), 0,0,0)
				.ifPresent(posy -> {
				entity.setVelocity(entity.getVelocity().multiply(-1,1,1));
				entity.velocityModified=true;
				});
			}else if(state.get(WEST)&&(eVel.getX()<0)){
				world
				.findClosestCollision(entity, WEST_SHAPE, entity.getPos().add(0.0, entity.getDimensions(entity.getPose()).height / 2.0, 0.0), 0,0,0)
				.ifPresent(posy -> {
				entity.setVelocity(entity.getVelocity().multiply(-1,1,1));
				entity.velocityModified=true;
				});
			}
			
			if(state.get(NORTH)&&(eVel.getZ()<0)){
				world
				.findClosestCollision(entity, NORTH_SHAPE, entity.getPos().add(0.0, entity.getDimensions(entity.getPose()).height / 2.0, 0.0), 0,0,0)
				.ifPresent(posy -> {
				entity.setVelocity(entity.getVelocity().multiply(1,1,-1));
				entity.velocityModified=true;
				});
			}else if(state.get(SOUTH)&&(eVel.getZ()>0)){
				world
				.findClosestCollision(entity, SOUTH_SHAPE, entity.getPos().add(0.0, entity.getDimensions(entity.getPose()).height / 2.0, 0.0), 0,0,0)
				.ifPresent(posy -> {
				entity.setVelocity(entity.getVelocity().multiply(1,1,-1));
				entity.velocityModified=true;
				});
			}
		}
		});
	}
	private boolean shouldHaveSide(BlockView world, BlockPos pos, Direction side) {
		if (side == Direction.DOWN) {
			return false;
		} else {
			BlockPos blockPos = pos.offset(side);
			if (shouldConnectTo(world, blockPos, side)) {
				return true;
			} else if (side.getAxis() == Direction.Axis.Y) {
				return false;
			} else {
				BooleanProperty booleanProperty = (BooleanProperty)FACING_PROPERTIES.get(side);
				BlockState blockState = world.getBlockState(pos.up());
				return blockState.isOf(this) && (Boolean)blockState.get(booleanProperty);
			}
		}
	}
	/*@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (entity.bypassesLandingEffects()) {
			super.onLandedUpon(world, state, pos, entity, fallDistance);
		} else {
			entity.handleFallDamage(fallDistance, 0.0F, world.getDamageSources().fall());
		}
	}*/

	/*@Override
	public void onEntityLand(BlockView world, Entity entity) {
		if (entity.bypassesLandingEffects()) {
			super.onEntityLand(world, entity);
		} else {
			this.bounce(entity);
		}
	}*/

	private void bounce(Entity entity) {
		Vec3d vec3d = entity.getVelocity();
		if (vec3d.y < 0.0) {
			double d = 1.1;
			entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
		}
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		double d = Math.abs(entity.getVelocity().y);
		if (d < 0.1 && !entity.bypassesSteppingEffects()) {
			double e = 1;
			entity.setVelocity(entity.getVelocity().multiply(e, 1.0, e));
		}

		super.onSteppedOn(world, pos, state, entity);
	}
	public static BooleanProperty getFacingProperty(Direction direction) {
		return (BooleanProperty)FACING_PROPERTIES.get(direction);
	}
	public static boolean shouldConnectTo(BlockView world, BlockPos pos, Direction direction) {
		return !(world.getBlockState(pos).isAir());//MultifaceGrowthBlock.canGrowOn(world, direction, pos, world.getBlockState(pos));
	}
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
		boolean bl = blockState.isOf(this);
		BlockState blockState2 = bl ? blockState : this.getDefaultState();

		for (Direction direction : ctx.getPlacementDirections()) {
			if (direction != Direction.DOWN) {
				BooleanProperty booleanProperty = getFacingProperty(direction);
				boolean bl2 = bl && (Boolean)blockState.get(booleanProperty);
				if (!bl2 && this.shouldHaveSide(ctx.getWorld(), ctx.getBlockPos(), direction)) {
					return blockState2.with(booleanProperty, true);
				}
			}
		}

		return bl ? blockState2 : null;
	}
}
