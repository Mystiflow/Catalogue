package io.mystiflow.catalogue;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CatalogueListener implements Listener {

    private final CataloguePlugin plugin;

    public CatalogueListener(CataloguePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        cancelDelays(event.getPlayer());
    }

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent event) {
        cancelDelays(event.getPlayer());
    }

    private void cancelDelays(ProxiedPlayer player) {
        plugin.getCatalogue().getMessages()
                .forEach(message -> message.getActionables().stream().forEach(actionName -> {
                            plugin.getCatalogue().getAction(actionName).ifPresent(action ->
                                    action.getActiveDelays().remove("$" + player.getName()));
                        })
                );
    }
}
