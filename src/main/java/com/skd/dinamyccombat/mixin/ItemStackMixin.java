package com.skd.dinamyccombat.mixin;

import com.skd.dinamyccombat.api.WeaponAttributes;
import com.skd.dinamyccombat.api.WeaponAttributesHelper;
import com.skd.dinamyccombat.api.component.DinamycDataComponents;
import com.skd.dinamyccombat.logic.WeaponRegistry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    abstract DataComponentPatch getComponentsPatch();

    @Unique
    public WeaponAttributes dinamyc_combat$getWeaponAttributes() {
        return WeaponRegistry.getAttributes((ItemStack) (Object) this);
    }

    @Unique
    public void dinamyc_combat$setWeaponAttributes(WeaponAttributes attributes) {
        if (attributes != null && WeaponAttributesHelper.validate(attributes)) {
            var stack = (ItemStack) (Object) this;
            if (attributes.id() != null) {
                stack.set(DinamycDataComponents.WEAPON_PRESET_ID.get(), Identifier.parse(attributes.id()));
            }
            WeaponAttributesHelper.override(stack, attributes);
        }
    }

    @Unique
    public boolean dinamyc_combat$isTwoHanded() {
        var wa = dinamyc_combat$getWeaponAttributes();
        return wa != null && wa.isTwoHanded();
    }

    @Unique
    public double dinamyc_combat$getAttackRange() {
        var wa = dinamyc_combat$getWeaponAttributes();
        return wa != null ? wa.attackRange() : 0.0;
    }
}
