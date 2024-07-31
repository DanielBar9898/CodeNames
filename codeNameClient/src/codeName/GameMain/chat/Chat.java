package codeName.GameMain.chat;

import codeName.HttpClient.Http.ChatClient;
import codeName.HttpClient.Utills.MutableInt;
import engine.GamePackage.Player;
import engine.chatPackage.SingleChatEntry;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class Chat {

    private final Set<String> printedDefinerMessages = new HashSet<>();
    private final Set<String> printedTeamMessages = new HashSet<>();

    public void enterChat(Player player) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean validInput = false;

        if (player.getRole() == Player.Role.DEFINER)
            System.out.println("Enter chat (1 for Team chat, 2 for Definers chat, 0 to return to game menu):");
        else if (player.getRole() == Player.Role.GUESSER) {
            System.out.println("Welcome to TeamChat! Type EXIT to leave.");
            enterTeamChat(player);
            return;
        }
        while (!validInput) {
            String input = sc.nextLine().trim();
            if (input.matches("[012]")) {
                int chatChoice = Integer.parseInt(input);
                if (chatChoice == 0) {
                    System.out.println("Returning to game menu...");
                    return; // חזרה לתפריט המשחק
                } else if (chatChoice == 2 && player.getRole() == Player.Role.DEFINER) {
                    enterDefinerChat(player);
                    validInput = true;
                } else if (chatChoice == 1 && player.getRole() == Player.Role.DEFINER) {
                    enterTeamChatAsDefiner(player);
                    validInput = true;
                } else {
                    System.out.println("Invalid choice, please enter 0, 1, or 2:");
                }
            } else {
                System.out.println("Invalid input, please enter 0, 1, or 2:");
            }
        }
    }

    public void enterDefinerChat(Player player) throws IOException {
        ChatClient chatClient = new ChatClient();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to DefinerChat! Type EXIT to leave.");
        boolean inChat = true;
        AtomicBoolean isInChat = new AtomicBoolean(true);
        MutableInt fromIndex = new MutableInt(0);
        checkAndPrintNewMessages(chatClient, player, "definer", fromIndex, isInChat, printedDefinerMessages);

        while (inChat) {
            List<SingleChatEntry> messages = chatClient.getDefinerMessages(fromIndex.get());
            messages.forEach(entry -> {
                if (!entry.getUsername().equals(player.getName())) {
                    String message = entry.getUsername() + ": " + entry.getChatString();
                    if (printedDefinerMessages.add(message)) {
                        System.out.println(message);
                    }
                }
            });
            fromIndex.increment(messages.size());

            String message = sc.nextLine();
            if ("EXIT".equalsIgnoreCase(message.trim())) {
                inChat = false;
                isInChat.set(false);
            } else {
                chatClient.sendDefinerMessage(player, message);
                printedDefinerMessages.add(player.getName() + ": " + message); // הוספת ההודעה לאחר שנשלחה
                System.out.println(player.getName() + ": " + message); // הצגת ההודעה לאחר השליחה
            }
        }
    }

    public void enterTeamChat(Player player) throws IOException {
        ChatClient chatClient = new ChatClient();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to TeamChat! Type EXIT to leave.");
        boolean inChat = true;
        AtomicBoolean isInChat = new AtomicBoolean(true);
        MutableInt fromIndex = new MutableInt(0);
        checkAndPrintNewMessages(chatClient, player, "team", fromIndex, isInChat, printedTeamMessages);

        while (inChat) {
            List<SingleChatEntry> messages = chatClient.getTeamMessages(fromIndex.get());
            messages.forEach(entry -> {
                if (!entry.getUsername().equals(player.getName())) {
                    String message = entry.getUsername() + ": " + entry.getChatString();
                    if (printedTeamMessages.add(message)) {
                        System.out.println(message);
                    }
                }
            });
            fromIndex.increment(messages.size());

            String message = sc.nextLine();
            if ("EXIT".equalsIgnoreCase(message.trim())) {
                inChat = false;
                isInChat.set(false);
            } else {
                chatClient.sendTeamMessage(player, message);
                printedTeamMessages.add(player.getName() + ": " + message); // הוספת ההודעה לאחר שנשלחה
                System.out.println(player.getName() + ": " + message); // הצגת ההודעה לאחר השליחה
            }
        }
    }

    public void enterTeamChatAsDefiner(Player player) throws IOException {
        ChatClient chatClient = new ChatClient();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to TeamChat as a Definer! You can only read messages. Type EXIT to leave.");
        boolean inChat = true;
        AtomicBoolean isInChat = new AtomicBoolean(true);
        MutableInt fromIndex = new MutableInt(0);
        checkAndPrintNewMessages(chatClient, player, "team", fromIndex, isInChat, printedTeamMessages);

        while (inChat) {
            List<SingleChatEntry> messages = chatClient.getTeamMessages(fromIndex.get());
            messages.forEach(entry -> {
                String message = entry.getUsername() + ": " + entry.getChatString();
                if (printedTeamMessages.add(message)) {
                    System.out.println(message);
                }
            });
            fromIndex.increment(messages.size());

            String message = sc.nextLine();
            if ("EXIT".equalsIgnoreCase(message.trim())) {
                inChat = false;
                isInChat.set(false);
            }
        }
    }

    private void checkAndPrintNewMessages(ChatClient chatClient, Player player, String chatType, MutableInt fromIndex, AtomicBoolean inChat, Set<String> printedMessages) {
        Thread pollingThread = new Thread(() -> {
            while (inChat.get()) {
                try {
                    List<SingleChatEntry> newMessages = chatClient.getNewMessages(chatType, fromIndex.get());
                    if (!newMessages.isEmpty()) {
                        newMessages.forEach(entry -> {
                            String message = entry.getUsername() + ": " + entry.getChatString();
                            if (!entry.getUsername().equals(player.getName()) && printedMessages.add(message)) {
                                System.out.println(message);
                            }
                        });
                        fromIndex.increment(newMessages.size());
                    }
                    Thread.sleep(2000); // המתנה של 2 שניות בין בקשות
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        pollingThread.start();
    }
}
