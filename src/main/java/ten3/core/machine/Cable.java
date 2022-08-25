package ten3.core.machine;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
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
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.energy.CapabilityEnergy;
import ten3.core.machine.Machine;
import ten3.core.machine.MachinePostEvent;
import ten3.init.template.DefBlock;
import ten3.init.TileInit;
import ten3.util.DireUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

public class Cable extends Machine {

    public Cable(Material m, SoundType s, String n) {

        super(m, s, false, n);

    }

    public static final Map<Direction, IntegerProperty> PROPERTY_MAP;

    static {
        Map<Direction, IntegerProperty> map = Maps.newEnumMap(Direction.class);
        map.put(Direction.NORTH, crt("north"));
        map.put(Direction.EAST, crt("east"));
        map.put(Direction.SOUTH, crt("south"));
        map.put(Direction.WEST, crt("west"));
        map.put(Direction.UP, crt("up"));
        map.put(Direction.DOWN, crt("down"));
        PROPERTY_MAP = Collections.unmodifiableMap(map);
    }

    private static IntegerProperty crt(String name) {
        return IntegerProperty.create(name, 0, 2);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

        return Block.makeCuboidShape(3, 3, 3, 13, 13, 13);

    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {

        builder.add(PROPERTY_MAP.values().toArray(new Property<?>[0]));
        builder.add(Machine.active);

    }

    public void update(World world, BlockPos pos) {

        BlockState state = world.getBlockState(pos);

        for(Direction facing : Direction.values()) {
            state = state.with(PROPERTY_MAP.get(facing), connectType(world, facing, pos));
            //state = state.with!!
        }

        world.setBlockState(pos, state);

    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {

        BlockState state = getDefaultState();

        for(Direction facing : Direction.values()) {
            state = state.with(PROPERTY_MAP.get(facing), connectType(context.getWorld(), facing, context.getPos()));
        }

        return state.with(Machine.active, false);

    }

    //0-none, 1-cable-to-cable 2 cable-to-machine
    public int connectType(@Nonnull IWorld world, @Nonnull Direction facing, BlockPos pos) {

        BlockState s = world.getBlockState(pos);
        BlockState sf = world.getBlockState(pos.offset(facing));

        if(!s.hasTileEntity()) return 0;
        if(!sf.hasTileEntity()) return 0;

        TileEntity t = world.getTileEntity(pos);
        TileEntity tf = world.getTileEntity(pos.offset(facing));

        if(tf == null) return 0;
        if(t == null) return 0;

        //pos<
        //t tf
        //->facing

        boolean k = tf.getCapability(CapabilityEnergy.ENERGY, DireUtil.safeOps(facing)).isPresent()
                && t.getCapability(CapabilityEnergy.ENERGY, facing).isPresent();

        if(k) {
            if(sf.getBlock() != this) {
                return 2;
            }
            else {
                return 1;
            }
        }

        return 0;

    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if(handIn == Hand.MAIN_HAND && !worldIn.isRemote()) {
            if(MachinePostEvent.clickMachineEvent(worldIn, pos, player, hit)) {
                return ActionResultType.PASS;
            }
        }

        return ActionResultType.SUCCESS;

    }

}
