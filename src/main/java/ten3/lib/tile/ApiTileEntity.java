package ten3.lib.tile;

import net.minecraft.tileentity.TileEntityType;
import ten3.util.KeyUtil;

public abstract class ApiTileEntity extends CmTileEntity {

    public ApiTileEntity(TileEntityType<?> type, String fullTranslationKey) {

        super(type, fullTranslationKey);

        component = KeyUtil.translated(fullTranslationKey);

    }

}
