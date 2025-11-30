import org.jspecify.annotations.NullMarked;

@NullMarked
module net.thenextlvl.nbt {
    requires static org.jetbrains.annotations;
    requires static org.jspecify;

    exports net.thenextlvl.nbt;
    exports net.thenextlvl.nbt.file;
    exports net.thenextlvl.nbt.serialization;
    exports net.thenextlvl.nbt.serialization.adapter;
    exports net.thenextlvl.nbt.tag;
}