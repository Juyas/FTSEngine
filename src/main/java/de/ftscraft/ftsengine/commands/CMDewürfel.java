package de.ftscraft.ftsengine.commands;

import de.ftscraft.ftsengine.main.Engine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

public class CMDewürfel implements CommandExecutor {

    private Engine plugin;

    public CMDewürfel(Engine plugin) {

        this.plugin = plugin;
        plugin.getCommand("ewürfel").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if(!(cs instanceof Player)) {
            return true;
        }

        Player p = (Player) cs;

        if(args.length == 1) {
            String arg = args[0];
            String[] num = arg.split("w");
            int amount = Integer.parseInt(num[0]);
            int size = Integer.parseInt(num[1]);

            int[] dices = new int[amount];

            for (int i = 0; i < amount; i++) {
                Random random = new Random();
                int n = random.nextInt(size) + 1;
                dices[i] = n;

            }

            p.sendMessage(Arrays.toString(dices));


        }


        return false;
    }

}
