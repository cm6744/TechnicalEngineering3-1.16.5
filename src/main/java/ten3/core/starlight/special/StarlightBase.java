package ten3.core.starlight.special;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import ten3.core.block.AeroliumOre;
import ten3.core.network.ZConfigs;
import ten3.init.template.DefBlock;
import ten3.init.BlockInit;
import ten3.init.ItemInit;
import ten3.util.WorkUtil;

import java.util.List;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.SNOWY;

public class StarlightBase extends DefBlock {

    public static void fl() {
        sore = BlockInit.getBlock("tin_ore");
        pore = BlockInit.getBlock("powered_tin_ore");
        stone = BlockInit.getBlock("starlight_stone");
        ad = BlockInit.getBlock("starlight_andesite");
        dr = BlockInit.getBlock("starlight_diorite");
        gn = BlockInit.getBlock("starlight_granite");
        grass = BlockInit.getBlock("starlight_grass_block");
        sand = BlockInit.getBlock("starlight_sand");
        cry = BlockInit.getBlock("starlight_crystal");
        log = BlockInit.getBlock("starlight_log");
        lv = BlockInit.getBlock("starlight_leaves");
    }

    public static Block sore;
    public static Block pore;
    public static Block stone;
    public static Block ad;
    public static Block dr;
    public static Block gn;
    public static Block grass;
    public static Block sand;
    public static Block cry;
    public static Block log;
    public static Block lv;

    public StarlightBase(Properties p) {

        super(p.tickRandomly());

    }

    public static List<ItemStack> dropGet() {

        if(Math.random() < 0.2) {
            return Lists.newArrayList(ItemInit.getItem("starlight_dust").getDefaultInstance());
        }

        return Lists.newArrayList();

    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        ctick(worldIn, pos, random);
        growCrystal(worldIn, pos, random);
    }

    public static IParticleData typeData = new RedstoneParticleData(0.72f, 0.98f, 1.0f, 0.5f);
    //public static IParticleData typeDataBIG = new RedstoneParticleData(0.72f, 0.98f, 1.0f, 0.7f);
    public static IParticleData typeDataHollow = new RedstoneParticleData(1f, 0.98f, 0.83f, 0.7f);

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if(Math.random() < 0.2) {
            AeroliumOre.spawnParticles(worldIn, pos, typeData);
        }
    }

    public static void ctick(ServerWorld worldIn, BlockPos pos, Random random) {

        BlockState state = worldIn.getBlockState(pos);
        int v = ZConfigs.starlight_speed.get();

        for(int i = 0; i < v; i++) {
            Direction r = Direction.getRandomDirection(random);
            BlockState s = worldIn.getBlockState(pos.offset(r));
            BlockState s1 = matchCorrupt(worldIn, pos, s);
            worldIn.setBlockState(pos.offset(r), s1);
            //put into tile and save
            //TypeTileSave.put(s.getBlock(), s1.getBlock(), worldIn, pos.offset(r));
        }

        if(Math.random() < 0.1) {
            for(int i = 0; i < v; ++i) {
                BlockPos blockpos = pos
                        .add(
                                random.nextInt(7) - 3,
                                random.nextInt(8) - 5,
                                random.nextInt(7) - 3
                        );
                BlockState s = worldIn.getBlockState(blockpos);
                worldIn.setBlockState(blockpos, matchCorrupt(worldIn, blockpos, s));
            }
        }

        //grass action
        if(state.matchesBlock(grass)) {
            for(int i = 0; i < v; ++i) {
                BlockPos blockpos = pos
                        .add(
                                random.nextInt(3) - 1,
                                random.nextInt(5) - 3,
                                random.nextInt(3) - 1
                        );

                if(worldIn.getBlockState(blockpos).matchesBlock(Blocks.GRASS_BLOCK)) {
                    worldIn.setBlockState(blockpos, worldIn.getBlockState(pos)
                            .with(SNOWY, worldIn.getBlockState(blockpos.up()).matchesBlock(Blocks.SNOW)));

                    if(worldIn.getBlockState(blockpos.up()).getBlock() instanceof BushBlock) {
                        worldIn.setBlockState(blockpos.up(), StarlightBase.cry.getDefaultState());
                    }
                }
            }
        }


    }

    public static void growCrystal(ServerWorld worldIn, BlockPos pos, Random random) {

        if(!worldIn.getBlockState(pos.up()).isAir()) return;

        int[] num = new int[1];

        WorkUtil.runIn(2, pos, (pin) -> {
                    if(worldIn.getBlockState(pin).matchesBlock(cry)) {
                        num[0]++;
                    }
        });

        if(num[0] >= 2) return;

        worldIn.setBlockState(pos.up(), cry.getDefaultState());

    }

    public static BlockState matchCorrupt(World world, BlockPos pos, BlockState s) {

        if(s.matchesBlock(sore)) {
            if(Math.random() < 0.5) {
                return pore.getDefaultState();
            }
            return stone.getDefaultState();
        }
        //hard to spread in stone!
        if(s.matchesBlock(Blocks.STONE)) return stone.getDefaultState();
        if(s.matchesBlock(Blocks.ANDESITE)) return ad.getDefaultState();
        if(s.matchesBlock(Blocks.DIORITE)) return dr.getDefaultState();
        if(s.matchesBlock(Blocks.GRANITE)) return gn.getDefaultState();

        if(BlockTags.LEAVES.contains(s.getBlock())) return lv.getDefaultState();
        if(BlockTags.LOGS.contains(s.getBlock())) return log.getDefaultState();

        if(s.matchesBlock(Blocks.GRASS_BLOCK)) {
            //with snow up pos
            return grass.getDefaultState().with(SNOWY, world.getBlockState(pos.up()).matchesBlock(Blocks.SNOW));
        }
        if(s.matchesBlock(Blocks.SAND)) return sand.getDefaultState();

        return s;

    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

}
