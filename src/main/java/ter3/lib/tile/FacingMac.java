package ten3.lib.tile;

import net.minecraft.util.Direction;

public enum FacingMac {

    FRONT,
    BACK,
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static FacingMac getFace(Direction front, Direction check) {

        //  S
        //E = W
        //  N

        if(front == check) return FRONT;

        if(front == Direction.NORTH) {
            switch(check) {
                case EAST:
                    return LEFT;
                case WEST:
                    return RIGHT;
                case SOUTH:
                    return BACK;
            }
        }
        if(front == Direction.EAST) {
            switch(check) {
                case NORTH:
                    return RIGHT;
                case WEST:
                    return BACK;
                case SOUTH:
                    return LEFT;
            }
        }
        if(front == Direction.SOUTH) {
            switch(check) {
                case EAST:
                    return RIGHT;
                case WEST:
                    return LEFT;
                case NORTH:
                    return BACK;
            }
        }
        if(front == Direction.WEST) {
            switch(check) {
                case EAST:
                    return BACK;
                case NORTH:
                    return LEFT;
                case SOUTH:
                    return RIGHT;
            }
        }

        return FRONT;

    }

}
