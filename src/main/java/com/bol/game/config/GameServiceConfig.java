package com.bol.game.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bol.game.service.GameService;
import com.bol.game.service.GameServiceImpl;
import com.bol.game.service.PitService;
import com.bol.game.service.PitServiceImpl;
import com.bol.game.service.PlayerService;
import com.bol.game.service.PlayerServiceImpl;

@Configuration
public class GameServiceConfig {

	@Bean
	public GameService gameService(){
		GameService gameService = new GameServiceImpl();
		return gameService;
	}
	
	@Bean
	public PlayerService playerService(){
		PlayerService playerService = new PlayerServiceImpl();
		return playerService;
	}
	
	@Bean
	public PitService pitService(){
		PitService pitService = new PitServiceImpl();
		return pitService;
	}
}
