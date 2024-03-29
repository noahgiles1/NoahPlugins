package com.noah.autoclicker;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

class AutoClickOverlay extends Overlay
{
    private static final Color FLASH_COLOR = new Color(255, 0, 0, 70);
    private final Client client;
    private final AutoClickerPlugin plugin;
    private final AutoClickerConfig config;
    private int timeout;

    @Inject
    AutoClickOverlay(Client client, AutoClickerPlugin plugin, AutoClickerConfig config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (plugin.isFlash() && config.flash())
        {
            final Color flash = graphics.getColor();
            graphics.setColor(FLASH_COLOR);
            graphics.fill(new Rectangle(client.getCanvas().getSize()));
            graphics.setColor(flash);
            timeout++;
            if (timeout >= 50)
            {
                timeout = 0;
                plugin.setFlash(false);
            }
        }
        return null;
    }

}
