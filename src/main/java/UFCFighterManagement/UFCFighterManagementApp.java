package UFCFighterManagement;

import UFCFighterManagement.dao.FighterDAO;
import UFCFighterManagement.dao.FighterDAOMySQLImpl;
import UFCFighterManagement.dto.FighterDTO;
import UFCFighterManagement.filter.FighterFilter;
import UFCFighterManagement.filter.FighterFilters;
import UFCFighterManagement.util.JsonConverter;

import java.util.List;
import java.util.Scanner;

public class UFCFighterManagementApp {
    private static final FighterDAO fighterDAO = new FighterDAOMySQLImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\nUFC Fighter Management System");
            System.out.println("1. List all fighters");
            System.out.println("2. Find fighter by ID");
            System.out.println("3. Delete fighter");
            System.out.println("4. Add fighter");
            System.out.println("5. Update fighter");
            System.out.println("6. Filter fighters");
            System.out.println("7. JSON Options");
            System.out.println("8. Convert fighter to JSON");
            System.out.println("9. Exit");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        listAllFighters();
                        break;
                    case 2:
                        findFighterById();
                        break;
                    case 3:
                        deleteFighterById();
                        break;
                    case 4:
                        addNewFighter();
                        break;
                    case 5:
                        updateFighter();
                        break;
                    case 6:
                        filterFighters();
                        break;
                    case 7:
                        showJson();
                        break;
                    case 8:
                        convertFighterToJson();
                        break;
                    case 9:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear bad input
            }
        }

        System.out.println("Exiting UFC Fighter Management System. Goodbye!");
        scanner.close();
    }

    private static void listAllFighters() {
        System.out.println("\nAll UFC Fighters:");
        List<FighterDTO> fighters = fighterDAO.getAllFighters();
        for (FighterDTO fighter : fighters) {
            System.out.println(fighter);
        }
    }

    private static void findFighterById() {
        System.out.print("\nEnter fighter ID to find: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        FighterDTO fighter = fighterDAO.getFighterById(id);
        if (fighter != null) {
            System.out.println("Fighter found: " + fighter);
        } else {
            System.out.println("Fighter with ID " + id + " not found.");
        }
    }

    private static void deleteFighterById() {
        System.out.print("\nEnter fighter ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        boolean deleted = fighterDAO.deleteFighterById(id);
        if (deleted) {
            System.out.println("Fighter with ID " + id + " was deleted successfully.");
        } else {
            System.out.println("Failed to delete fighter with ID " + id + " (maybe it doesn't exist).");
        }
    }

    private static void addNewFighter() {
        System.out.println("\nAdd New UFC Fighter");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter weight class: ");
        String weightClass = scanner.nextLine();

        System.out.print("Enter record (e.g., 20-3-0): ");
        String record = scanner.nextLine();

        System.out.print("Enter stance (Orthodox/Southpaw): ");
        String stance = scanner.nextLine();

        System.out.print("Enter reach (inches): ");
        double reach = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        System.out.print("Enter coach name: ");
        String coach = scanner.nextLine();

        FighterDTO newFighter = new FighterDTO(0, name, age, weightClass, record, stance, reach, coach, "UFC");
        FighterDTO insertedFighter = fighterDAO.insertFighter(newFighter);

        System.out.println("New fighter added with ID: " + insertedFighter.getId());
    }

    private static void updateFighter() {
        System.out.print("\nEnter fighter ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        FighterDTO existingFighter = fighterDAO.getFighterById(id);
        if (existingFighter == null) {
            System.out.println("Fighter with ID " + id + " not found.");
            return;
        }

        System.out.println("Current fighter details: " + existingFighter);
        System.out.println("\nEnter new details (leave blank to keep current value):");

        System.out.print("Name (" + existingFighter.getName() + "): ");
        String name = scanner.nextLine();
        name = name.isEmpty() ? existingFighter.getName() : name;

        System.out.print("Age (" + existingFighter.getAge() + "): ");
        String ageInput = scanner.nextLine();
        int age = ageInput.isEmpty() ? existingFighter.getAge() : Integer.parseInt(ageInput);

        System.out.print("Weight Class (" + existingFighter.getWeightClass() + "): ");
        String weightClass = scanner.nextLine();
        weightClass = weightClass.isEmpty() ? existingFighter.getWeightClass() : weightClass;

        System.out.print("Record (" + existingFighter.getRecord() + "): ");
        String record = scanner.nextLine();
        record = record.isEmpty() ? existingFighter.getRecord() : record;

        System.out.print("Stance (" + existingFighter.getStance() + "): ");
        String stance = scanner.nextLine();
        stance = stance.isEmpty() ? existingFighter.getStance() : stance;

        System.out.print("Reach (" + existingFighter.getReach() + "): ");
        String reachInput = scanner.nextLine();
        double reach = reachInput.isEmpty() ? existingFighter.getReach() : Double.parseDouble(reachInput);

        System.out.print("Coach (" + existingFighter.getCoach() + "): ");
        String coach = scanner.nextLine();
        coach = coach.isEmpty() ? existingFighter.getCoach() : coach;

        FighterDTO updatedFighter = new FighterDTO(
                id,
                name,
                age,
                weightClass,
                record,
                stance,
                reach,
                coach,
                "UFC"
        );

        boolean success = fighterDAO.updateFighter(id, updatedFighter);
        if (success) {
            System.out.println("Fighter updated successfully!");
            System.out.println("Updated details: " + fighterDAO.getFighterById(id));
        } else {
            System.out.println("Failed to update fighter.");
        }
    }

    private static void filterFighters() {
        System.out.println("\nFilter UFC Fighters");
        System.out.println("1. By minimum age");
        System.out.println("2. By weight class");
        System.out.println("3. By stance");
        System.out.println("4. By coach");
        System.out.println("5. By reach range");
        System.out.println("6. By minimum wins");
        System.out.println("7. Combined filters");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        FighterFilter filter = null;

        switch (choice) {
            case 1:
                System.out.print("Enter minimum age: ");
                int age = scanner.nextInt();
                filter = FighterFilters.ageAtLeast(age);
                break;
            case 2:
                System.out.print("Enter weight class: ");
                String weightClass = scanner.nextLine();
                filter = FighterFilters.weightClassEquals(weightClass);
                break;
            case 3:
                System.out.print("Enter stance: ");
                String stance = scanner.nextLine();
                filter = FighterFilters.stanceEquals(stance);
                break;
            case 4:
                System.out.print("Enter coach name: ");
                String coach = scanner.nextLine();
                filter = FighterFilters.coachEquals(coach);
                break;
            case 5:
                System.out.print("Enter minimum reach: ");
                double minReach = scanner.nextDouble();
                System.out.print("Enter maximum reach: ");
                double maxReach = scanner.nextDouble();
                filter = FighterFilters.reachBetween(minReach, maxReach);
                break;
            case 6:
                System.out.print("Enter minimum wins: ");
                int minWins = scanner.nextInt();
                filter = FighterFilters.winsAtLeast(minWins);
                break;
            case 7:
                System.out.println("Creating combined filter...");
                // Example: Fighters over 25 with Orthodox stance
                filter = FighterFilters.and(
                        FighterFilters.ageAtLeast(25),
                        FighterFilters.stanceEquals("Orthodox")
                );
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        List<FighterDTO> filteredFighters = fighterDAO.findFightersApplyFilter(filter);
        System.out.println("\nFiltered Fighters (" + filteredFighters.size() + " found):");
        filteredFighters.forEach(System.out::println);
    }

    private static void showJson() {
        // Get one fighter
        FighterDTO fighter = fighterDAO.getFighterById(1);
        if (fighter != null) {
            String fighterJson = JsonConverter.fighterToJson(fighter);
            System.out.println("\nSingle Fighter JSON:");
            System.out.println(fighterJson);
        }

        // Get all fighters
        List<FighterDTO> fighters = fighterDAO.getAllFighters();
        String allFightersJson = JsonConverter.fightersToJson(fighters);
        System.out.println("\nAll Fighters JSON:");
        System.out.println(allFightersJson);
    }

    private static void convertFighterToJson() {
        System.out.print("\nEnter fighter ID to convert to JSON: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        FighterDTO fighter = fighterDAO.getFighterById(id);
        if (fighter != null) {
            String json = fighter.toJson();
            System.out.println("\nFighter JSON:");
            System.out.println(json);
        } else {
            System.out.println("Fighter with ID " + id + " not found.");
        }

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}

