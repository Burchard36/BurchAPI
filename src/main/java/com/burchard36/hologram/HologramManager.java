package com.burchard36.hologram;

import java.util.HashMap;

public class HologramManager {


    private final HashMap<String, Hologram> holograms;

    public HologramManager() {
        this.holograms = new HashMap<>();
    }

    public final void addHologram(final String key, final Hologram hologram) {
        this.holograms.putIfAbsent(key, hologram.createHologram());
    }

    public final void removeAll() {
        this.holograms.forEach((key, hologram) -> hologram.removeHologram());
    }

    public final Hologram getHologram(final String key) {
        return this.holograms.get(key);
    }

}
