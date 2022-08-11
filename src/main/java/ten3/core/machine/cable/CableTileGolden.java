package ten3.core.machine.cable;

public class CableTileGolden extends CableTile {

    public CableTileGolden(String name) {

        super(name);

    }

    @Override
    public int getCapacity() {
        return kFE(5);
    }

}
