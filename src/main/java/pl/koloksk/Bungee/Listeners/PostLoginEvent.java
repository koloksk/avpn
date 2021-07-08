package pl.koloksk.Bungee.Listeners;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static pl.koloksk.Common.utils.StoreData.ilosc_polaczen;

public class PostLoginEvent implements Listener {
    @EventHandler
    public void onPostLogin(net.md_5.bungee.api.event.PostLoginEvent e) {
        ilosc_polaczen++;
    }
}
