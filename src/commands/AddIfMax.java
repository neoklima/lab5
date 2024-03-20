package commands;

import managers.CollectionManager;
import utility.Ask;
import utility.Console;
import utility.ExecutionResponse;
import models.Vehicle;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 */
public class AddIfMax extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public AddIfMax(Console console, CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");


            console.println("* Создание нового транспортного средства (add_if_max):");
            long id = collectionManager.getFreeId(); // Получаем свободный ID для нового транспортного средства
            Vehicle vehicle = Ask.askVehicle(console, id); // Запрашиваем информацию о транспортном средстве

            // Находим максимальное транспортное средство в коллекции
            Vehicle maxVehicle = collectionManager.getCollection().stream()
                    .max(Vehicle::compareTo)
                    .orElse(null);

            if (maxVehicle == null || vehicle.compareTo(maxVehicle) > 0) {
                collectionManager.add(vehicle);
                collectionManager.update();
                console.println("Транспортное средство успешно добавлено!");
                return new ExecutionResponse(true, "Транспортное средство успешно добавлено!");
            } else {
                console.println("Транспортное средство не добавлено, так как его значение не превышает максимальное значение в коллекции.");
                return new ExecutionResponse(false, "Транспортное средство не добавлено, так как его значение не превышает максимальное значение в коллекции.");
            }
        } catch (Ask.AskBreak e) {
            console.println("Процесс ввода был прерван пользователем.");
            return new ExecutionResponse(false, "Процесс ввода был прерван пользователем.");
        }
    }
}
