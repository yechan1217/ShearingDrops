package com.koyeyu.shearingdrops;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class ShearingDrops extends JavaPlugin implements Listener {
    List<ItemStack> item = new ArrayList<ItemStack>();
    List<Sheep> opSheep = new LinkedList<>();
    Random ran = new Random();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        setItem();
        setRecipe();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    @EventHandler
    public void onPlayerUseDye(PlayerInteractAtEntityEvent event){
        if (event.getHand() != EquipmentSlot.HAND) {return;}
        if (event.getRightClicked().getType() == EntityType.SHEEP && opSheep.contains((Sheep)event.getRightClicked())) {return;}
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand().clone();
        tool.setAmount(1);
        if (tool.equals(ItemManager.SUPER_DYE) && event.getRightClicked().getType().equals(EntityType.SHEEP)){
            Sheep sheep = (Sheep) event.getRightClicked();
            new Thread(() -> {
                List<DyeColor> setColor = new ArrayList<DyeColor>(List.of(DyeColor.values()));
                while (!sheep.isDead()){
                    Bukkit.getScheduler().runTask(this, () -> {
                        sheep.setColor(setColor.get(ran.nextInt(setColor.size())));
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }).start();
            event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
            event.getPlayer().playSound(event.getPlayer().getLocation(),Sound.ENTITY_GENERIC_DRINK,0.5f,2f);
            opSheep.add(sheep);
        }
    }

    @EventHandler
    public void onSheared(PlayerShearEntityEvent e){
        if (e.getEntity().getType() == EntityType.SHEEP) {
            Sheep sheep = (Sheep) e.getEntity();
            if (opSheep.contains(sheep)) {
                ItemStack tool = e.getItem();
                if (tool.equals(ItemManager.SHEAR_1)) {
                    sheared(e, sheep, 5);
                }else if (tool.equals(ItemManager.SHEAR_2)){
                    sheared(e, sheep, 30);
                }else if (tool.equals(ItemManager.SHEAR_3)){
                    sheared(e, sheep, 150);
                }
            }
        }
    }

    private void sheared(PlayerShearEntityEvent e, Sheep sheep, int i) {
        e.setCancelled(true);
        sheep.setSheared(true);
        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
        dropOpItem(ran.nextInt(i+1), e.getEntity().getLocation(), e.getEntity().getWorld());
    }

    public void dropOpItem(int amount, Location location, World world){
        for (int i = 0; i < amount; i++) {world.dropItem(location, item.get(ran.nextInt(item.size())));}
    }

    public void setRecipe(){
        NamespacedKey key1 = new NamespacedKey(this, "dna");
        ShapedRecipe recipe1 = new ShapedRecipe(key1, ItemManager.SUPER_DYE);
        recipe1.shape("123", " 4 ", "567");
        recipe1.setIngredient('1', Material.RED_DYE);
        recipe1.setIngredient('2', Material.ORANGE_DYE);
        recipe1.setIngredient('3', Material.YELLOW_DYE);
        recipe1.setIngredient('4', Material.GLASS_BOTTLE);
        recipe1.setIngredient('5', Material.GREEN_DYE);
        recipe1.setIngredient('6', Material.BLUE_DYE);
        recipe1.setIngredient('7', Material.PURPLE_DYE);

        NamespacedKey key2 = new NamespacedKey(this, "luxuryShears1");
        ShapedRecipe recipe2 = new ShapedRecipe(key2, ItemManager.SHEAR_1);
        recipe2.shape("111", "121", "111");
        recipe2.setIngredient('1', Material.IRON_INGOT);
        recipe2.setIngredient('2', Material.SHEARS);


        NamespacedKey key3 = new NamespacedKey(this, "luxuryShears2");
        ShapedRecipe recipe3 = new ShapedRecipe(key3, ItemManager.SHEAR_2);
        recipe3.shape("111", "121", "111");
        recipe3.setIngredient('1', Material.GOLD_INGOT);
        recipe3.setIngredient('2', Material.SHEARS);

        NamespacedKey key4 = new NamespacedKey(this, "luxuryShears3");
        ShapedRecipe recipe4 = new ShapedRecipe(key4, ItemManager.SHEAR_3);
        recipe4.shape("111", "121", "111");
        recipe4.setIngredient('1', Material.DIAMOND);
        recipe4.setIngredient('2', Material.SHEARS);

        Bukkit.addRecipe(recipe1);
        Bukkit.addRecipe(recipe2);
        Bukkit.addRecipe(recipe3);
        Bukkit.addRecipe(recipe4);
    }

    public void setItem(){
        item.add(new ItemStack(Material.DIAMOND,1));
        item.add(new ItemStack(Material.IRON_INGOT,1));
        item.add(new ItemStack(Material.GOLD_INGOT,1));
        item.add(new ItemStack(Material.NETHERITE_INGOT,1));
        item.add(new ItemStack(Material.EXPERIENCE_BOTTLE,10));
        item.add(new ItemStack(Material.FIREWORK_ROCKET,10));
        item.add(new ItemStack(Material.HONEY_BOTTLE,2));
        item.add(new ItemStack(Material.TOTEM_OF_UNDYING,1));
        item.add(new ItemStack(Material.DIAMOND_HELMET));
        item.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
        item.add(new ItemStack(Material.DIAMOND_LEGGINGS));
        item.add(new ItemStack(Material.DIAMOND_BOOTS));
        item.add(new ItemStack(Material.GOLDEN_CARROT,5));
        item.add(new ItemStack(Material.RED_DYE,1));
        item.add(new ItemStack(Material.ORANGE_DYE,1));
        item.add(new ItemStack(Material.YELLOW_DYE,1));
        item.add(new ItemStack(Material.GREEN_DYE,1));
        item.add(new ItemStack(Material.BLUE_DYE,1));
        item.add(new ItemStack(Material.PURPLE_DYE,1));
        item.add(new ItemStack(Material.ELYTRA,1));
        item.add(new ItemStack(Material.TNT,5));
        item.add(new ItemStack(Material.BEDROCK,2));
        item.add(new ItemStack(Material.OBSIDIAN,2));
        item.add(new ItemStack(Material.ARROW,16));
        item.add(new ItemStack(Material.SPECTRAL_ARROW,16));
        for (Enchantment value : Enchantment.values()) {
            item.add(ItemBuilder.create().enchant(value, ran.nextInt(5)+1).buildEnchantedBook());
        }
        for (PotionEffectType value : PotionEffectType.values()) {
            item.add(ItemBuilder.create().displayName("§b유전자 변이 포션").potion(value).potionTick((ran.nextInt(5)+1)*60).potionLevel(ran.nextInt(5)).buildPotion());
        }
    }

}
