package pl.koloksk.Bukkit.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;

import static pl.koloksk.Bukkit.Check.Check;
import static pl.koloksk.Common.utils.StoreData.ilosc_polaczen;

public class AsyncPlayerPreLoginEvent implements Listener {
    @EventHandler
    public void Onjoin(org.bukkit.event.player.AsyncPlayerPreLoginEvent e) throws IOException {

        ilosc_polaczen++;
        Check(1, e.getAddress().getHostAddress(), e);
    }

}
