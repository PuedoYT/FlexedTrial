package me.puedo.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffModHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] a) {

        Main main = Main.getInstance();

        Player p = (Player) s;
        Commands cmds = new Commands();
        OfflinePlayer target;
        if(s instanceof Player){
            if(command.getName().equals("staffmod") || command.getName().equals("vanish")){
                target = null;
                cmds.staffMode(p);
            } else if (command.getName().equals("freeze")){
                target = Bukkit.getOfflinePlayer(a[0]);
                cmds.freeze(p, target);
            } else if (command.getName().equals("tpto")){
                target = Bukkit.getOfflinePlayer(a[0]);
                cmds.TPTO(p, target);
            } else if (command.getName().equals("staffchat")){
                if(!main.isSCToggled.contains(p.getUniqueId())){
                    main.isSCToggled.add(p.getUniqueId());
                } else { main.isSCToggled.remove(p.getUniqueId()); }
            }
        } else { s.sendMessage("§cError: Only players can use this command"); }
        return false;
    }
}
