package com.koyeyu.shearingdrops;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
    public static final ItemStack SUPER_DYE = ItemBuilder.create()
            .material(Material.PAPER)
            .displayName("§e유전자 조작 염료")
            .lore("§6양의 유전자를 조작합니다.")
            .customModelData(1)
            .build();
    public static final ItemStack SHEAR_1 = ItemBuilder.create()
            .material(Material.SHEARS)
            .displayName("§d명품 가위 §f[§e1단계§f]")
            .lore("§7양털이 야무지게 깎입니다.")
            .build();
    public static final ItemStack SHEAR_2 = ItemBuilder.create()
            .material(Material.SHEARS)
            .displayName("§d명품 가위 §f[§62단계§f]")
            .lore("§7양털이 야무지게 깎입니다.")
            .build();
    public static final ItemStack SHEAR_3 = ItemBuilder.create()
            .material(Material.SHEARS)
            .displayName("§d명품 가위 §f[§c3단계§f]")
            .lore("§7양털이 야무지게 깎입니다.")
            .build();



}
