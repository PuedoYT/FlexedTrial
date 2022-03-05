package me.puedo.staffmode;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import me.puedo.staffmode.Main;

import java.util.UUID;

public class StaffModeEventsListener implements Listener {

    Main main = Main.getInstance();


    StaffModItems smi = new StaffModItems();
    Commands cmds = new Commands();

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(Main.getInstance().freezedPlayers.contains(p.getUniqueId())){
            p.sendMessage(
                    "§c§lYou are frozen §8§l| §c§lDO NOT DISCONNECT\n"
                    + "§7Join discord.flexed.com within 5 minutes");
        } else return;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(main.isVanished != null){
            if(main.inStaffMod.contains(p.getUniqueId())) {
                e.setJoinMessage(null);
                cmds.vanishPlayer(p);
                for(UUID uuid : main.isVanished){
                    p.hidePlayer(Bukkit.getPlayer(uuid));
                }
            }
            return;
        } else System.out.println("NO VANISHED PLAYERS"); return;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(main.freezedPlayers.contains(p.getUniqueId())){
            Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(), "Disconnected whilst frozen", null, null);
        } else if(main.inStaffMod.contains(p.getUniqueId())) {
            e.setQuitMessage(null);
        } else return;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack it = e.getItem();

        if(!main.inStaffMod.contains(p.getUniqueId())) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;

        switch(p.getInventory().getItemInHand().getType()){
            case BARRIER:
                cmds.vanishPlayer(p);
                p.sendMessage("§aOther players can now see you!");
                p.getInventory().remove(Material.BARRIER); p.getInventory().setItem(8, smi.staffmode_on());
            break;
            case BEDROCK:
                cmds.vanishPlayer(p);
                p.sendMessage("§cOther players can no longer see you!");
                p.getInventory().remove(Material.BEDROCK); p.getInventory().setItem(8, smi.staffmode_off());
            break;
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        if(e.getMessage().startsWith("#") && p.hasPermission("staffchat")){

            e.setCancelled(true);

            Bukkit.getOnlinePlayers().stream()
                    .filter(players -> players.hasPermission("staffchat"))
                    .forEach(players -> players.sendMessage("§7[§bSTAFF CHAT§7] §r" + p.getDisplayName() + ": §f" + e.getMessage().substring(1)));

        } else if(main.isSCToggled.contains(p.getUniqueId()) && p.hasPermission("staffchat")){
            e.setCancelled(true);

            Bukkit.getOnlinePlayers().stream()
                    .filter(players -> players.hasPermission("staffchat"))
                    .forEach(players -> players.sendMessage("§7[§bSTAFF CHAT§7] §r" + p.getDisplayName() + ": §f" + e.getMessage().substring(0)));
        } else return;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e){
        if(main.inStaffMod.contains(e.getPlayer().getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(main.inStaffMod.contains(e.getPlayer().getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(main.inStaffMod.contains(e.getPlayer().getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e){
        if(main.inStaffMod.contains(e.getPlayer().getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(main.inStaffMod.contains(((Player) e.getEntity()).getPlayer().getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(main.inStaffMod.contains(e.getPlayer().getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(main.inStaffMod.contains(e.getWhoClicked().getUniqueId())) e.setCancelled(true);
    }
}
