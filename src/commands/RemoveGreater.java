package commands;

import managers.CollectionManager;
import models.Vehicle;
import utility.Ask;
import utility.Console;
import utility.ExecutionResponse;

public class RemoveGreater extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveGreater(Console console, CollectionManager collectionManager) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");


        try {
            console.println("* Создание нового транспортного средства:");
            Vehicle newVehicle = Ask.askVehicle(console, collectionManager.getFreeId());

            if (newVehicle != null && newVehicle.validate()) {
                int countRemoved = collectionManager.removeGreaterThan(newVehicle);
                if (countRemoved > 0) {
                    return new ExecutionResponse("Удалено " + countRemoved + " элемент(ов) из коллекции.");
                } else {
                    return new ExecutionResponse("Нет элементов, превышающих заданный.");
                }
            } else {
                return new ExecutionResponse(false, "Некорректные данные для транспортного средства.");
            }
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Отменено.");
        }
    }
}
