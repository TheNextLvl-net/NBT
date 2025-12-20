import org.jspecify.annotations.NullMarked;

@NullMarked
module net.thenextlvl.nbt {
    exports net.thenextlvl.nbt.serialization.adapters;
    exports net.thenextlvl.nbt.serialization;
    exports net.thenextlvl.nbt;
    exports net.thenextlvl.nbt.tag;

    requires static org.jetbrains.annotations;
    requires static org.jspecify;
}