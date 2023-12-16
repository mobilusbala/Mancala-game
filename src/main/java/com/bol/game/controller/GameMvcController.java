package com.bol.game.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bol.game.exception.RecordNotFoundException;
import com.bol.game.model.Pit;
import com.bol.game.model.Player;
import com.bol.game.service.GameService;
import com.bol.game.service.PlayerService;
import com.bol.game.service.PlayerServiceImpl;


@Controller
@RequestMapping("/")
public class GameMvcController 
{
	@Autowired
	PlayerServiceImpl service;
	
	@Autowired
	private GameService gameService;

	@RequestMapping
	public String getAllEmployees(Model model) throws RecordNotFoundException 
	{
		Player player1 = service.getPlayerById("Palyer1");
		Player player2 = service.getPlayerById("Palyer2");

		model.addAttribute("player1", gameService.getAllPitsInSeq(player1));
		model.addAttribute("player2", gameService.getAllPitsInSeq(player2));
		model.addAttribute("status", gameService.getStatus().getStatus());
		model.addAttribute("turn", gameService.getStatus().getMessage());
		
		return "game-board";
	}
	
	@RequestMapping(value = "/sows/{pitId}")
	public String sortPieces(@PathVariable String pitId) throws RecordNotFoundException{
		
		gameService.gameProcess(pitId);
		
		return "redirect:/";
	}
	

	
}
