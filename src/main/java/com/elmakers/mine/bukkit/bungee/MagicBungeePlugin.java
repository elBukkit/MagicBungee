package com.elmakers.mine.bukkit.bungee;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MagicBungeePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String server;
        Player player;
        if (sender instanceof Player)
        {
            if (args.length < 1)
            {
                return false;
            }

            player = (Player)sender;
            if (!player.hasPermission("Magic.commands.server"))
            {
                return false;
            }

            if (args.length >= 2)
            {
                player = Bukkit.getPlayer(args[0]);
                server = args[1];
            }
            else
            {
                server = args[0];
            }
        }
        else
        {
            if (args.length < 2)
            {
                return false;
            }

            player = Bukkit.getPlayer(args[0]);
            server = args[1];
        }

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException ex) {
            // Impossible
        }

        player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
        return true;
    }
}