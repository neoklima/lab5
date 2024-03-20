package commands;

import managers.CollectionManager;
import utility.Console;
import utility.ExecutionResponse;
import models.Vehicle;

/**
 * Команда 'sum_of_number_of_wheels'. Выводит сумму значений поля numberOfWheels для всех элементов коллекции.
 */
public class SumOfNumberOfWheels extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public SumOfNumberOfWheels(Console console, CollectionManager collectionManager) {
        super("sum_of_number_of_wheels", "вывести сумму значений поля numberOfWheels для всех элементов коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");


        int sumOfNumberOfWheels = getSumOfNumberOfWheels();
        if (sumOfNumberOfWheels == 0) {
            return new ExecutionResponse(false, "Коллекция пуста!");
        }

        return new ExecutionResponse("Сумма количества колес всех транспортных средств: " + sumOfNumberOfWheels);
    }

    private int getSumOfNumberOfWheels() {
        return collectionManager.getCollection().stream()
                .mapToInt(Vehicle::getNumberOfWheels)
                .sum();
    }
}
