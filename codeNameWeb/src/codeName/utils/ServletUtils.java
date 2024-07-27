package codeName.utils;

import DTO.BoardDTO;
import DTO.WordDTO;
import engine.GamePackage.Board;
import engine.GamePackage.Word;
import engine.users.UserManager;
import jakarta.servlet.ServletContext;

import com.google.gson.Gson;
import DTO.GameDTO;
import DTO.TeamDTO;
import engine.GamePackage.Game;
import engine.GamePackage.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServletUtils {

	private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
	private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";

	/*
	Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
	the actual fetch of them is remained un-synchronized for performance POV
	 */
	private static final Object userManagerLock = new Object();

	public static UserManager getUserManager(ServletContext servletContext) {

		synchronized (userManagerLock) {
			if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
				servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
			}
		}
		return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
	}

	public static String convertGamesToJson(Set<Game> games) {
		List<GameDTO> gameDTOs = new ArrayList<>();
		for (Game game : games) {
			ArrayList<TeamDTO> teamDTOs = new ArrayList<>();
			for (Team team : game.getTeams()) {
				String teamName = team.getTeamName();
				int wordsToGuess = team.getWordsToGuess();
				int numOfDefiners = team.getNumOfDefiners();
				int activeDefiners = team.getActiveDefiners();
				int numOfGuessers = team.getNumOfGuessers();
				int activeGuessers = team.getActiveGuessers();

				TeamDTO teamDTO = new TeamDTO(teamName, wordsToGuess, numOfDefiners, activeDefiners, numOfGuessers, activeGuessers);
				teamDTOs.add(teamDTO);
			}
			GameDTO gameDTO = new GameDTO(
					game.getName(),
					game.getBlackWordsCount(),
					game.getGameWordsCount(),
					game.isActive(),
					game.getDictName(),
					teamDTOs,
					game.getGameSerialNumber(),
					game.getGameBoard().getNumRows(),
					game.getGameBoard().getNumCols(),
					game.getWordsSize()
			);

			gameDTOs.add(gameDTO);
		}

		Gson gson = new Gson();
		return gson.toJson(gameDTOs);
	}

	public static String convertTeamToJson(Team team) {
		TeamDTO teamDTO = new TeamDTO(
				team.getTeamName(),
				team.getWordsToGuess(),
				team.getNumOfDefiners(),
				team.getActiveDefiners(),
				team.getNumOfGuessers(),
				team.getActiveGuessers()
		);

		Gson gson = new Gson();
		return gson.toJson(teamDTO);
	}

	public static GameDTO convertGameToDTO(Game game) {
		ArrayList<TeamDTO> teamDTOs = new ArrayList<>();
		for (Team team : game.getTeams()) {
			teamDTOs.add(new TeamDTO(
					team.getTeamName(),
					team.getWordsToGuess(),
					team.getNumOfDefiners(),
					team.getActiveDefiners(),
					team.getNumOfGuessers(),
					team.getActiveGuessers()
			));
		}

		return new GameDTO(
				game.getName(),
				game.getBlackWordsCount(),
				game.getGameWordsCount(),
				game.isActive(),
				game.getDictName(),
				teamDTOs,
				game.getGameSerialNumber(),
				game.getGameBoard().getNumRows(),
				game.getGameBoard().getNumCols(),
				game.getWordsSize()
		);
	}

	public static BoardDTO convertBoardToDTO(Board board) {
		Set<WordDTO> wordDTOs = new HashSet<>();
		for (Word word : board.getWords()) {
			WordDTO wordDTO = new WordDTO(
					word.toString(),
					word.getColor().toString(),
					word.isFound(),
					word.getSerialNumber()
			);
			wordDTOs.add(wordDTO);
		}

		return new BoardDTO(
				board.getNumRows(),
				board.getNumCols(),
				board.getNumOfBlackWords(),
				board.getNumOfWords(),
				wordDTOs
		);
	}
}
