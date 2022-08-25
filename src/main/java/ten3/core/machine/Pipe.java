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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import ten3.util.DireUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

public class Pipe extends Cable {

    public Pipe(Material m, SoundType s, String n) {

        super(m, s, n);

    }

    public int connectType(@Nonnull IWorld world, @Nonnull Direction facing, BlockPos pos) {

        BlockState sf = world.getBlockState(pos.offset(facing));

        TileEntity t = world.getTileEntity(pos);
        TileEntity tf = world.getTileEntity(pos.offset(facing));

        if(tf == null) return 0;
        if(t == null) return 0;

        //pos<
        //t tf
        //->facing

        boolean k = tf.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, DireUtil.safeOps(facing)).isPresent()
                && t.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing).isPresent();

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

}
