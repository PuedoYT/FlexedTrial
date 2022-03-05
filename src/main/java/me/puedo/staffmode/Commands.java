package me.puedo.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

import java.util.UUID;

public class Commands {

    Main main = Main.getInstance();

    StaffModItems smi = new StaffModItems();

    public boolean hasPerm(Player p, String permission){
        if(p.hasPermission(permission)) return true;
        else p.sendMessage("§cInsufficient permission"); return false;
    }


    public void vanishPlayer(Player p){
        if(hasPerm(p, "staff.vanish")){
            if(!main.isVanished.contains(p.getUniqueId())){
                for(Player pl : Bukkit.getOnlinePlayers()){
                    pl.hidePlayer(p);
                }
                for(UUID uuid : main.isVanished){
                    p.showPlayer(Bukkit.getPlayer(uuid));
                }
                main.isVanished.add(p.getUniqueId());
            } else if (main.isVanished.contains(p.getUniqueId())){
                for(Player pl : Bukkit.getOnlinePlayers()){
                    pl.showPlayer(p);
                }
                main.isVanished.remove(p.getUniqueId());
            } else return;
        } else p.sendMessage("§cUnsufficient permissions.");
    }

    public void staffMode(Player p){
        if(hasPerm(p, "staffmode.use")){
            if(!main.inStaffMod.contains(p.getUniqueId())){
                p.getInventory().setItem(8, smi.staffmode_off());
                main.inStaffMod.add(p.getUniqueId());
                vanishPlayer(p);
                p.sendMessage("§a§lStaff mode activated!");
                return;
            } else {
                main.inStaffMod.remove(p.getUniqueId());
                vanishPlayer(p);
                p.sendMessage("§c§lStaff mode de-activated!");
                p.getInventory().clear();
            }
        }
    }

    public void freeze(Player p, OfflinePlayer target){
        if(hasPerm(p, "staff.freeze")){
            if(target != null && target.isOnline())
            {
                if(!main.freezedPlayers.contains(target.getPlayer().getUniqueId())){
                    p.sendMessage("§bFreezed: " + target.getPlayer().getDisplayName());
                    target.getPlayer().setWalkSpeed(-0.9f); target.getPlayer().setFlySpeed(-0.9f);
                    main.freezedPlayers.add(target.getPlayer().getUniqueId());
                } else {
                    p.sendMessage("§bUnfreezed: " + target.getPlayer().getDisplayName());
                    target.getPlayer().setWalkSpeed(0.2f); target.getPlayer().setFlySpeed(0.2f);
                    main.freezedPlayers.remove(target.getPlayer().getUniqueId());
                }
            } else p.sendMessage("§cTarget not online!");
        } else p.sendMessage("§cNot enough permissions");
    }

    public void TPTO(Player p, OfflinePlayer target){
        Location targetloc = target.getPlayer().getLocation();
        p.teleport(targetloc);
        p.sendMessage("§aTeleported to: " + target.getPlayer().getDisplayName());
    }

}
