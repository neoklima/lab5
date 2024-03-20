package commands;

import managers.CollectionManager;
import models.Vehicle;
import utility.Ask;
import utility.Console;
import utility.ExecutionResponse;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

            console.println("* Создание нового Транспортного Средства:");
            Vehicle v = Ask.askVehicle(console, collectionManager.getFreeId());

            if (v != null && v.validate()) {
                collectionManager.add(v);
                return new ExecutionResponse("Транспортное Средство успешно добавлено!");
            } else return new ExecutionResponse(false,"Поля Транспортного Средства не валидны! Транспортное средство не создано!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false,"Отмена...");
        }
    }
}
