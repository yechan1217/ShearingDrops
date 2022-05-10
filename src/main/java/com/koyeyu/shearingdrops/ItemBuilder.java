package com.koyeyu.shearingdrops;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemBuilder {
    private static Plugin plugin;

    private Material material;
    private String skullOwner;
    private int amount = 1;
    private int level;
    private String displayName;
    private Enchantment enchant;
    private PotionEffectType potionType;
    private int duration;
    private int amplifier;
    private List<String> lore = Collections.emptyList();
    private int customModelData;


    public static ItemBuilder create() {
        return new ItemBuilder();
    }

    private ItemBuilder() {}

    public ItemBuilder customModelData(Integer value){
        this.customModelData = value;
        return this;
    }

    public ItemBuilder potion(PotionEffectType value){
        this.potionType = value;
        return this;
    }

    public ItemBuilder potionTick(Integer value){
        this.duration = value;
        return this;
    }

    public ItemBuilder potionLevel(Integer value){
        this.amplifier = value;
        return this;
    }

    public ItemBuilder enchant(Enchantment enchant, Integer level){
        this.enchant = enchant;
        this.level = level;
        return this;
    }



    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder skullOwner(String skullOwner) {
        this.skullOwner = skullOwner;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }


    public ItemBuilder displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder lore(String ...lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = skullOwner != null ? buildSkull() : new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack buildEnchantedBook() {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        itemMeta.addStoredEnchant(enchant, level, false);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack buildPotion() {
        ItemStack itemStack = new ItemStack(Material.POTION, 1);
        PotionMeta itemMeta = (PotionMeta) itemStack.getItemMeta();
        itemMeta.addCustomEffect(new PotionEffect(potionType, duration, amplifier),false);
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack buildSkull() {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getPlayer(skullOwner));
        return itemStack;
    }

}