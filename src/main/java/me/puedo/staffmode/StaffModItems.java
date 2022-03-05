package me.puedo.staffmode;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StaffModItems {

    public ItemStack staffmode_off(){
        ItemStack vanisher_off = new ItemStack(Material.BARRIER);
        ItemMeta vanishermeta = vanisher_off.getItemMeta();
        vanishermeta.setDisplayName("§aShow");
        vanisher_off.setItemMeta(vanishermeta);

        return vanisher_off;
    }

    public ItemStack staffmode_on(){
        ItemStack vanisher_on = new ItemStack(Material.BEDROCK);
        ItemMeta vanisheronmeta = vanisher_on.getItemMeta();
        vanisheronmeta.setDisplayName("§cHide");
        vanisher_on.setItemMeta(vanisheronmeta);

        return vanisher_on;
    }
}
