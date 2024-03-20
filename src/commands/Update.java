package commands;

import managers.CollectionManager;
import models.Vehicle;
import utility.Console;
import utility.Ask;
import utility.ExecutionResponse;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 */
public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Update(Console console, CollectionManager collectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
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
            if (arguments.length < 3) return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            long id = -1;
            try {
                id = Long.parseLong(arguments[1].trim());
            } catch (NumberFormatException e) {
                return new ExecutionResponse(false, "ID не распознан");
            }

            var old = collectionManager.byId((int) id);
            if (old == null || !collectionManager.getCollection().contains(old)) {
                return new ExecutionResponse(false, "Элемент с указанным ID не существует");
            }

            console.println("* Создание нового Транспортного Средства:");
            var newVehicle = Ask.askVehicle(console, old.getId());
            if (newVehicle != null && newVehicle.validate()) {
                collectionManager.remove(old.getId());
                collectionManager.add(newVehicle);
                collectionManager.update();
                return new ExecutionResponse("Элемент успешно обновлен!");
            } else {
                return new ExecutionResponse(false, "Поля Транспортного Средства не валидны! Новое транспортное средство не создано!");
            }
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse(false, "Поля Транспортного Средства не валидны! Новое транспортное средство не создано!");
        }
    }
}
