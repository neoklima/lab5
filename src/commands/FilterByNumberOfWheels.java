package commands;

import managers.CollectionManager;
import models.Vehicle;
import utility.Console;
import utility.ExecutionResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'filter_by_number_of_wheels'. Выводит элементы, значение поля numberOfWheels которых равно заданному.
 */
public class FilterByNumberOfWheels extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public FilterByNumberOfWheels(Console console, CollectionManager collectionManager) {
        super("filter_by_number_of_wheels <NUMBER_OF_WHEELS>", "вывести элементы, значение поля numberOfWheels которых равно заданному");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments.length != 2) {
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }

        try {
            int numberOfWheels = Integer.parseInt(arguments[1]);
            List<Vehicle> filteredVehicles = filterByNumberOfWheels(numberOfWheels);

            if (filteredVehicles.isEmpty()) {
                return new ExecutionResponse(false, "Транспортных средств с количеством колес " + numberOfWheels + " не обнаружено.");
            } else {
                StringBuilder responseBuilder = new StringBuilder();
                responseBuilder.append("Транспортные средства с количеством колес ").append(numberOfWheels).append(":\n");
                filteredVehicles.forEach(vehicle -> responseBuilder.append(vehicle).append("\n"));
                return new ExecutionResponse(true, responseBuilder.toString().trim());
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Некорректное значение для количества колес!");
        }
    }

    private List<Vehicle> filterByNumberOfWheels(int numberOfWheels) {
        return collectionManager.getCollection().stream()
                .filter(vehicle -> vehicle.getNumberOfWheels() == numberOfWheels)
                .collect(Collectors.toList());
    }
}
