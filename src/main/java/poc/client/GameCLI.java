package poc.client;

import poc.application.DefaultGameService;
import poc.application.GameService;
import poc.domain.character.Character;
import poc.domain.exploration.Direction;
import poc.domain.exploration.ExplorationResult;
import poc.domain.fight.FightResult;
import poc.domain.game.Game;
import poc.infrastructure.DefaultGameRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.String.format;

public class GameCLI {

	private Map<String, Direction> explorationMap;

	public static final String ANSI_RESET  = "\u001B[0m";

	public static final String ANSI_BRIGHT_BLUE   = "\u001B[94m";
	public static final String ANSI_BRIGHT_BLACK  = "\u001B[90m";
	public static final String ANSI_BRIGHT_WHITE  = "\u001B[97m";

	public static final String ANSI_BG_WHITE  = "\u001B[47m";
	public static final String ANSI_BRIGHT_BG_YELLOW = "\u001B[103m";
	public static final String ANSI_BRIGHT_BG_GREEN  = "\u001B[102m";
	public static final String ANSI_BG_RED    = "\u001B[41m";


	private GameCLI() {
		this.service = new DefaultGameService(new DefaultGameRepository());
		this.scanner = new Scanner(System.in,"UTF-8");
		explorationMap = new HashMap<>();
		explorationMap.put("1",Direction.NORTH);
		explorationMap.put("2",Direction.SOUTH);
		explorationMap.put("3",Direction.EAST);
		explorationMap.put("4",Direction.WEST);

	}


	private final Scanner scanner;
	private final GameService service;
	private ExplorationResult environment;
	private Game game;

	public static void main (String[] args){

		GameCLI cli = new GameCLI();


		while (true){
			cli.createMenu();
			String userInput = cli.scanner.next();

			switch (userInput){
				case "0":
					if(cli.gameAvailable()){
						System.out.println("Wrong option");
						break;
					}
					cli.createGame();
					break;
				case "1":
				case "2":
				case "3":
				case "4":
					if(!cli.gameAvailable()){
						System.out.println("Wrong option");
						break;
					}
					cli.explore(cli.explorationMap.get(userInput));
					break;
				case "5":
					if(!cli.gameAvailable()){
						System.out.println("Wrong option");
						break;
					}
					cli.fightOpponent();
					break;
				default:
					break;
			}

		}

	}

	private void fightOpponent() {

		FightResult fightResult = service.characterFights(game.id());

		this.environment = service.characterExplores(game.id(), Direction.STAY_PUT);
		Character character = game.character();
		if(fightResult.fightHappened()){
			if(fightResult.isOpponentDead()){
				System.out.println(format("%s survived (%s)",character.name(), character));
				System.out.println(format("%s is dead",fightResult.opponent()));
				System.out.println(colorResponse(format("Game is On!")));
			}else{
				System.out.println(format("%s is dead (%s)", character.name(), character));
				System.out.println(format("%s survived", fightResult.opponent()));
				System.out.println(colorResponse(format("Game over!")));

			}
		}
	}

	private void createGame() {
		//  prompt for the user's name
		System.out.print("Enter character name: ");
		String name = scanner.next();

		Game game = service.newGame(name, Game.GameMode.EASY);
		this.game = game;
		this.environment =service.characterExplores(game.id(), Direction.STAY_PUT);


		newGameInfo();

		pressAnyKeyToContinue();


	}

	private void newGameInfo() {
		StringBuffer buffer= new StringBuffer();
		buffer.append("\n");
		buffer.append(colorResponse(format("Created %s",game)));
		buffer.append("\n");
		buffer.append(format("Created character: %s", this.game.character()));
		buffer.append("\n");
		buffer.append(colorResponse(format("%s initial position: %s", game.character().name(), environment.position())));
		buffer.append("\n");

		System.out.println(buffer);
	}


	private void explore(Direction direction){
		this.environment = service.characterExplores(game.id(), direction);
		if(this.environment.hasMoved()){
			System.out.println(colorResponse(format("%s has moved to the %s", game.character().name(), direction)));
			System.out.println(colorResponse(format("New position %s", this.environment.position())));

		}else {
			System.out.println(colorResponse(format("%s has reached the limits to the %s (%s)", game.character().name(), direction, this.environment.position())));
		}

		pressAnyKeyToContinue();
	}

	private void createMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(ANSI_BRIGHT_BLUE+ ANSI_BRIGHT_BG_YELLOW+ "*****Available Options*****"+ANSI_RESET).append("\n");
		buffer.append(ANSI_BRIGHT_BLACK+ ANSI_BRIGHT_BG_GREEN);
		if (!gameAvailable()){
			buffer.append(colorMenuOption("*. Press 0 to create a game")).append("\n");
		}else{
			buffer.append(colorMenuOption("*. Press 1 to explore North")).append("\n");
			buffer.append(colorMenuOption("*. Press 2 to explore South")).append("\n");
			buffer.append(colorMenuOption("*. Press 3 to explore East")).append("\n");
			buffer.append(colorMenuOption("*. Press 4 to explore West")).append("\n");
			if(environment.hasAnyOpponents()){

				buffer.append(colorMenuOption("*. Press 5 to fight an opponent"))
					.append(format("(%s remaining)",game.numberOpponents()));
			}

		}
		buffer.append(ANSI_RESET);
		buffer.append("\n");
		System.out.println(buffer);
		System.out.println("Enter your choice:");

	}

	private static void pressAnyKeyToContinue()
	{
		System.out.println("Press Enter key to continue...");
		try
		{
			System.in.read();
		}
		catch(Exception e)
		{}
	}

	private boolean gameAvailable(){
		return game!=null && !game.isOver();
	}


	private String colorResponse(String response){
		return ANSI_BRIGHT_WHITE+ANSI_BG_RED+response+ANSI_RESET;
	}

	private String colorMenuOption(String text){
		return ANSI_BRIGHT_BLACK+ ANSI_BRIGHT_BG_GREEN+text+ANSI_RESET;
	}
}