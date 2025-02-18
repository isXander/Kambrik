package io.ejekta.kambrik.internal.mixins;

import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(StructurePool.class)
public interface StructurePoolAccessor {
    @Accessor(value = "elements")
    List<StructurePoolElement> getElements();

    @Accessor(value = "elementCounts")
    List<Pair<StructurePoolElement, Integer>> getElementCounts();

    @Accessor(value = "elementCounts")
    @Mutable
    void setElementCounts(List<Pair<StructurePoolElement, Integer>> list);
}



