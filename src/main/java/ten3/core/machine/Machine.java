package ten3.core.machine;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import ten3.core.item.energy.EnergyItemHelper;
import ten3.init.template.DefBlock;
import ten3.init.TileInit;
import ten3.lib.tile.CmTileMachine;

import javax.annotation.Nullable;

import java.util.List;

public class Machine extends DefBlock {

    public static DirectionProperty dire = DirectionProperty.create("facing", (direction) -> direction != Direction.UP && direction != Direction.DOWN);
    public static BooleanProperty active = BooleanProperty.create("active");

    String tileName;

    public Machine(String name) {

        this(false, name);
        tileName = name;

    }

    public Machine(boolean solid, String name) {

        this(Material.IRON, SoundType.STONE, solid, name);
        tileName = name;

    }

    public Machine(Material m, SoundType s, boolean solid, String name) {

        super(3, 5, m, s, ToolType.PICKAXE, 2, 0, solid);
        tileName = name;

    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {

        return TileInit.getType(tileName).create();

    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if(handIn == Hand.MAIN_HAND && !worldIn.isRemote()) {
            if(MachinePostEvent.clickMachineEvent(worldIn, pos, player, hit)) {
                CmTileMachine tile = (CmTileMachine)worldIn.getTileEntity(pos);
                if(tile == null) {
                    return ActionResultType.FAIL;
                }

                NetworkHooks.openGui((ServerPlayerEntity) player, tile, (PacketBuffer packerBuffer) -> {
                    packerBuffer.writeBlockPos(tile.getPos());
                });
            }
        }

        return ActionResultType.SUCCESS;

    }

    //See:TileMachine
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

        CmTileMachine tile = ((CmTileMachine) builder.get(LootParameters.BLOCK_ENTITY));
        
        if(tile == null) {
            return Lists.newArrayList(asItem().getDefaultInstance());
        }

        ItemStack stack = EnergyItemHelper.fromMachine(tile, asItem().getDefaultInstance());

        return Lists.newArrayList(stack);

    }

    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return EnergyItemHelper.fromMachine((CmTileMachine) createTileEntity(state, worldIn), asItem().getDefaultInstance());
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {

        builder.add(dire);
        builder.add(active);

    }

    //with facing on place
    public BlockState getStateForPlacement(BlockItemUseContext context) {

        return
                this.getDefaultState()
                        .with(dire, context.getPlacementHorizontalFacing().getOpposite())
                        .with(active, false);

    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        CmTileMachine tile = (CmTileMachine) worldIn.getTileEntity(pos);
        if(tile != null) {
            EnergyItemHelper.pushToTile(tile, stack);
        }

    }

    @Override
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {

        return true;

    }

}
