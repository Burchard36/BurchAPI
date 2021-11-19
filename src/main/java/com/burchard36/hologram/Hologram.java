package com.burchard36.hologram;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.util.List;

import static com.burchard36.ApiLib.convert;

public class Hologram {

    private Location position;
    private String text;
    private List<String> textList;

    private ArmorStand armorStand;

    public Hologram(final Location position, final String text) {
        this.position = position;
        this.text = text;
    }

    public Hologram(final Location position, final List<String> textList) {
        this.position = position;
        this.textList = textList;
    }

    /**
     * Removes the ArmorStand
     * @return instance of this class
     */
    public final Hologram removeHologram() {
        this.armorStand.remove();
        return this;
    }

    /**
     * Updates the Holograms Text
     * @param text text to update hologram with
     * @return instance of this class
     */
    public final Hologram updateText(final String text) {
        this.armorStand.setCustomName(convert(text));
        return this;
    }

    /**
     * Updates the Holograms text
     * @param text text to update hologram with
     * @return instance of this class
     */
    public final Hologram updateText(final List<String> text) {
        String temp = "";
        for (String x : text) {
            temp = temp + x + "\n";
        }

        this.armorStand.setCustomName(convert(temp));
        return this;
    }

    /**
     * Attaches this ArmorStand to an entity
     * @param entity Entity to attach ArmorStand to
     * @return instanceof this class
     */
    public final Hologram attach(final Entity entity) {
        entity.addPassenger(this.armorStand);
        return this;
    }

    /**
     * Creates the ArmorStand and then mskrd it invisible
     * If an invisible armor stand exists at the position, it gets cleared
     * @return instance of this class
     */
    public final Hologram createHologram() {
        this.position.getNearbyEntities(0, 0, 0).forEach((entity) -> {
            if (entity instanceof ArmorStand) {
                ArmorStand stand = (ArmorStand) entity;
                stand.remove();
            }
        });

        this.armorStand = this.position.getWorld().spawn(this.position, ArmorStand.class);

        this.armorStand.setVisible(false);
        this.armorStand.setCanPickupItems(false);
        this.armorStand.setGravity(false);
        if (this.text != null) {
            this.armorStand.setCustomName(convert(this.text));
        } else {
            String temp = "";
            for (String x : this.textList) {
                temp = temp + x + "\n";
            }
            this.armorStand.setCustomName(convert(temp));
        }
        this.armorStand.setCustomNameVisible(true);
        return this;
    }

    /**
     * Checks if a block is a ArmorStand
     * @param block Block to check
     * @return true if Block is ArmorStand
     */
    private boolean isArmorStand(final Block block) {
        return block.getType() == Material.ARMOR_STAND;
    }

    /**
     * Gets an ArmorStand from a Block
     * will return null if the block is not an ArmorStand
     * @param block Block to get ArmorStand from
     * @return ArmorStand if exists, null if it's not an armor stand
     */
    private ArmorStand getArmorStand(final Block block) {
        if (!(block.getState() instanceof ArmorStand)) return null;
        return (ArmorStand) block.getState();
    }

}
