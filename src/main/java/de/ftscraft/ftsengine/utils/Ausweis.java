package de.ftscraft.ftsengine.utils;

import de.ftscraft.ftsengine.main.Engine;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class Ausweis {

    private final String UUID;
    private String firstName,
            lastName,
            spitzname;

    private Gender gender;

    private String race;
    private String nation;
    private String desc;
    private String religion;
    private String forumLink;
    public final Integer id;

    private final Engine plugin;

    public Ausweis(Engine plugin, String UUID, String firstName, String lastName, String spitzname, Gender gender, String race, String nation, String desc, String religion, String link, Integer id) {
        this.plugin = plugin;
        this.UUID = UUID;
        this.firstName = firstName;
        this.spitzname = spitzname;
        this.lastName = lastName;
        this.gender = gender;
        this.race = race;
        this.nation = nation;
        this.forumLink = link;
        this.desc = desc;
        this.religion = religion;
        this.id = id;
        plugin.addAusweis(this);
    }

    public Ausweis(Engine plugin, Player player) {
        plugin.highestId++;
        this.id = plugin.highestId;
        this.UUID = player.getUniqueId().toString();
        this.plugin = plugin;
        plugin.addAusweis(this);
    }

    public boolean save() {

        File file = new File(plugin.getDataFolder() + "//ausweise//" + getUUID() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        cfg.set("firstName", firstName);
        cfg.set("lastName", lastName);
        if (gender != null)
            cfg.set("gender", gender.toString());
        cfg.set("spitzname", spitzname);
        cfg.set("race", race);
        cfg.set("nation", nation);
        cfg.set("desc", desc);
        cfg.set("id", id);
        cfg.set("religion", religion);
        cfg.set("link", forumLink);

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String getUUID() {
        return UUID;
    }

    public String getFirstName() {
        if(firstName == null)
            return null;
        return firstName.replace('_', ' ');
    }

    public String getLastName() {
        if(lastName == null)
            return null;
        return lastName.replace('_', ' ');
    }

    public Gender getGender() {
        if(gender == null)
            return null;
        return gender;
    }

    public String getRace() {
        if(race == null)
            return null;
        return race.replace('_', ' ');
    }

    public String getNation() {
        if(nation == null)
            return null;
        return nation.replace('_', ' ');
    }

    public String getDesc() {
        if(desc == null)
            return null;
        return desc;
    }

    public String getReligion() {
        if(religion == null)
            return null;
        return religion.replace('_', ' ');
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public ItemStack getAsItem() {
        //ItemStack is = new ItemStack(Material., 1);
        //ItemMeta im = is.getItemMeta();
        //im.setDisplayName("§6Personalausweis " + lastName + " #" + id);
        //is.setItemMeta(im);
        return null;
    }

    public void setSpitzname(String spitzname) {
        this.spitzname = spitzname;
    }

    public String getSpitzname() {
        if(spitzname == null)
            return null;
        return spitzname;
    }

    public void setForumLink(String forumLink) {
        this.forumLink = forumLink;
    }

    public String getForumLink() {
        return forumLink;
    }
}
