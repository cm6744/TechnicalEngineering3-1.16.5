package ten3.util;

import net.minecraft.util.Direction;

import java.util.function.Supplier;

public class For {

    public static ForLoopObject<?> in(int st, int ed) {

        ForLoopObject<?> obj = new ForLoopObject<>();
        obj.start = st;
        obj.end = ed;

        return obj;

    }

    public static <T> ForLoopObject<T> in(Iterable<T> itr) {

        ForLoopObject<T> obj = new ForLoopObject<>();
        obj.values = itr;

        return obj;

    }

    public static <T> ForLoopObject<T> in(T[] arr) {

        ForLoopObject<T> obj = new ForLoopObject<>();
        obj.array = arr;

        return obj;

    }

    public enum Pos {

        START,
        END

    }

    static class ForLoopObject<T> {

        Integer start;
        Integer end;
        Iterable<T> values;
        T[] array;
        ConditionObject<T> br = (i, o) -> false;
        ConditionObject<T> ct = (i, o) -> false;
        Pos brp = Pos.START;
        Pos ctp = Pos.START;

        public void run(RunObject<T> r) {

            if(start != null && end != null) {
                for(int i = start; i < end; i++) {
                    if(ctp == Pos.START && ct.canBreakOrContinue(i, null)) continue;
                    if(brp == Pos.START && br.canBreakOrContinue(i, null)) break;
                    r.run(i, null);
                    if(ctp == Pos.END && ct.canBreakOrContinue(i, null)) continue;
                    if(brp == Pos.END && br.canBreakOrContinue(i, null)) break;
                }
            }
            else if(values != null) {
                int i = 0;
                for(T v : values) {
                    if(ctp == Pos.START && ct.canBreakOrContinue(i, v)) continue;
                    if(brp == Pos.START && br.canBreakOrContinue(i, v)) break;
                    r.run(i, v);
                    if(ctp == Pos.END && ct.canBreakOrContinue(i, v)) continue;
                    if(brp == Pos.END && br.canBreakOrContinue(i, v)) break;
                    i++;
                }
            }
            else if(array != null) {
                int i = 0;
                for(T v : array) {
                    if(ctp == Pos.START && ct.canBreakOrContinue(i, v)) continue;
                    if(brp == Pos.START && br.canBreakOrContinue(i, v)) break;
                    r.run(i, v);
                    if(ctp == Pos.END && ct.canBreakOrContinue(i, v)) continue;
                    if(brp == Pos.END && br.canBreakOrContinue(i, v)) break;
                    i++;
                }
            }
            else {
                throw new ForLoopObjectStateNotCorrectException("startIndex: " + start + ", " +
                        "endIndex: " + end + ", " + "iterable: " + values);
            }

        }

        public ForLoopObject<T> breakIf(ConditionObject<T> condition, Pos pos) {

            br = condition;
            brp = pos;
            return this;

        }

        public ForLoopObject<T> continueIf(ConditionObject<T> condition, Pos pos) {

            ct = condition;
            ctp = pos;
            return this;

        }

    }

    static class ForLoopObjectStateNotCorrectException extends RuntimeException {
        public ForLoopObjectStateNotCorrectException(String s) {
            super(s);
        }
    }

    static interface RunObject<T> {
        void run(Integer index, T obj);
    }

    static interface ConditionObject<T> {
        boolean canBreakOrContinue(Integer index, T obj);
    }

}
