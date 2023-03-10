package de.ftscraft.ftsengine.commands;

import de.ftscraft.ftsengine.main.Engine;
import de.ftscraft.ftsengine.utils.Ausweis;
import de.ftscraft.ftsengine.utils.ItemStacks;
import de.ftscraft.ftsengine.utils.Messages;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CMDbuch implements CommandExecutor {

    private Engine plugin;
    private final String IDENTIFIER = "§B§U§C§H§F§T§S";

    public CMDbuch(Engine plugin) {
        this.plugin = plugin;
        plugin.getCommand("buch").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(cs instanceof Player)) {
            cs.sendMessage("Dieser Befehl ist nur für Spieler");
            return true;
        }

        Player p = (Player) cs;

        if (args.length == 0) {

        }

        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }

        PlayerInventory inv = p.getInventory();

        if (!inv.contains(Material.PAPER) || !inv.contains(Material.INK_SAC)) {
            p.sendMessage(Messages.PREFIX + "Du brauchst Papier und Tinte im Inventar.");
            return true;
        }

        removeItemsFromInventory(inv);

        if (isBookByPlugin(p.getInventory().getItemInMainHand())) {
            ItemStack book = p.getInventory().getItemInMainHand();
            BookMeta bookMeta = (BookMeta) book.getItemMeta();
            String title = bookMeta.getTitle();
            if (title != null && title.length() > 12)
                title = title.substring(12);
            else title = "";
            if(!title.equals(p.getName())) {
                p.sendMessage("§cDas ist nicht dein Brief!");
                return true;
            }
            if(bookMeta.getPageCount() == 50) {
                p.sendMessage(Messages.PREFIX + "Das Buch hat bereits 50 Seiten. Mehr geht net!");
                return true;
            }
            bookMeta.addPages(Component.text(message.toString()));
            book.setItemMeta(bookMeta);
            p.sendMessage(Messages.PREFIX + "Die Seite wurde hinzugefügt");
            return true;
        }

        ItemStack bookItemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) bookItemStack.getItemMeta();
        bookMeta.setTitle("§eBrief von " + p.getName());
        Ausweis ausweis = plugin.getAusweis(p);
        bookMeta.lore(List.of(Component.text(IDENTIFIER)));
        bookMeta.addPages(Component.text(message.toString()));
        bookMeta.setAuthor(ausweis.getFirstName() + " " + ausweis.getLastName());
        bookItemStack.setItemMeta(bookMeta);
        inv.addItem(bookItemStack);
        p.sendMessage(Messages.PREFIX + "Das Buch sollte nun in deinem Inventar sein. Um weitere Seiten dort zu schreiben halte es in deiner (Haupt-)Hand");
        return false;
    }

    private boolean isBookByPlugin(ItemStack is) {

        if (is.getType() != Material.WRITTEN_BOOK)
            return false;

        BookMeta meta = (BookMeta) is.getItemMeta();
        if (meta.getLore() != null) {
            List<String> lore = meta.getLore();
            if (lore.size() > 0) {
                return lore.get(0).equals(IDENTIFIER);
            }
        }

        return false;
    }

    private void removeItemsFromInventory(PlayerInventory inv) {

        boolean removedPaper = false;
        boolean removedInk = false;
        for (int i = 0; i < inv.getContents().length; i++) {
            ItemStack item = inv.getContents()[i];
            if (item != null) {
                if (!removedPaper && item.getType() == Material.PAPER) {
                    item.setAmount(item.getAmount() - 1);

                    removedPaper = true;

                    if(removedInk)
                        break;

                } else if (!removedInk && item.getType() == Material.INK_SAC) {
                    removedInk = true;

                    if (Math.random() <= 0.05) {
                        item.setAmount(item.getAmount() - 1);
                    }

                    if(removedPaper)
                        break;
                }
            }

        }

    }

}
