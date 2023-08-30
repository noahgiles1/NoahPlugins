package com.example;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Point;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import com.noah.runelite.utils.Utils;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class ExamplePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ExampleConfig config;

	@Inject
	private Utils extUtils;

	private ExecutorService executorService;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
		executorService = Executors.newSingleThreadExecutor();
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
		executorService.shutdown();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Subscribe
	public void onGameTick(GameTick gameTick)
	{
		if (client.getGameState() == GameState.LOGGED_IN)
		{
			executorService.submit(() -> {
				log.info("Executing on executor thread separate to runelite client");

				Rectangle rect = client.getLocalPlayer().getCanvasTilePoly().getBounds();
				log.info("Player tile location: " + client.getLocalPlayer().getCanvasTilePoly().getBounds());

				Point point = extUtils.getClickPoint(rect);
				log.info("Random point in object: " + point);

				log.info("Sending click at random point");
				//extUtils.click(client.getMouseCanvasPosition());
			});
		}
	}

	@Provides
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}
