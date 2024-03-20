package commands;

import managers.CollectionManager;
import models.Coordinates;
import models.Vehicle;
import utility.Console;
import utility.ExecutionResponse;
import java.time.LocalDateTime;

public class RemoveLower extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveLower(Console console, CollectionManager collectionManager) {
        super("remove_lower {element}", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");


        Vehicle elementToRemove = parseVehicleFromString(arguments[1]);
        if (elementToRemove == null) {
            return new ExecutionResponse(false, "Невозможно разобрать элемент");
        }

        collectionManager.getCollection().removeIf(vehicle -> vehicle.compareTo(elementToRemove) < 0);
        collectionManager.update();

        return new ExecutionResponse("Элементы, меньшие, чем заданный, успешно удалены");
    }

    private Vehicle parseVehicleFromString(String input) {
        try {
            String[] parts = input.split(";"); // Предполагается, что поля разделены точкой с запятой

            // Парсинг каждого поля из строки
            long id = Long.parseLong(parts[0]);
            String name = parts[1];
            Coordinates coordinates = parseCoordinatesFromString(parts[2]); // Парсинг координат
            LocalDateTime creationDate = LocalDateTime.parse(parts[3]); // Парсинг даты
            double enginePower = Double.parseDouble(parts[4]);
            int numberOfWheels = Integer.parseInt(parts[5]);
            models.VehicleType type = models.VehicleType.valueOf(parts[6]); // Парсинг перечисления
            models.FuelType fuelType = models.FuelType.valueOf(parts[7]); // Парсинг перечисления

            // Создание объекта Vehicle
            return new Vehicle(id, name, coordinates, creationDate, enginePower, numberOfWheels, type, fuelType);
        } catch (Exception e) {
            return null; // Если парсинг невозможен, возвращаем null
        }
    }

    private Coordinates parseCoordinatesFromString(String input) {
        try {
            String[] parts = input.split(","); // Предполагается, что координаты разделены запятой
            int x = Integer.parseInt(parts[0]);
            long y = Long.parseLong(parts[1]);
            return new Coordinates(x, y);
        } catch (Exception e) {
            return null; // Если парсинг невозможен, возвращаем null
        }
    }
}
