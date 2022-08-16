package ten3.lib.tile;

import net.minecraft.tileentity.TileEntityType;
import ten3.util.KeyUtil;

public abstract class ApiTileMachine extends CmTileMachine {

    public ApiTileMachine(TileEntityType<?> type, String fullTranslationKey) {

        super(type, fullTranslationKey);

        component = KeyUtil.translated(fullTranslationKey);

    }

}
