package commands;

import managers.CollectionManager;
import models.Vehicle;
import models.FuelType;
import utility.Console;
import utility.ExecutionResponse;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'print_field_ascending_fuel_type'. Выводит значения поля fuelType всех элементов в порядке возрастания.
 */
public class PrintFieldAscendingFuelType extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintFieldAscendingFuelType(Console console, CollectionManager collectionManager) {
        super("print_field_ascending_fuel_type", "вывести значения поля fuelType всех элементов в порядке возрастания");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        List<FuelType> fuelTypes = collectionManager.getCollection().stream()
                .map(Vehicle::getFuelType)
                .sorted(Comparator.nullsLast(Comparator.naturalOrder()))
                .distinct()
                .collect(Collectors.toList());

        if (fuelTypes.isEmpty()) {
            return new ExecutionResponse(false, "Значения поля fuelType не обнаружены в коллекции.");
        } else {
            StringBuilder responseBuilder = new StringBuilder("Значения поля fuelType всех элементов в порядке возрастания:\n");
            fuelTypes.forEach(fuelType -> responseBuilder.append(fuelType).append("\n"));
            return new ExecutionResponse(true, responseBuilder.toString().trim());
        }
    }
}
