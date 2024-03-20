import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import utility.Runner;
import utility.StandartConsole;

public class Main {
    public static void main(String[] args) {
        var console = new StandartConsole();

        var dumpManager = new DumpManager(console);
        var collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.loadCollection()) {
            System.exit(1);
        }

        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("add_if_max", new AddIfMax(console, collectionManager));
            register("remove_greater", new RemoveGreater(console, collectionManager));
            register("remove_lower", new RemoveLower(console, collectionManager));
            register("sum_of_number_of_wheels", new SumOfNumberOfWheels(console, collectionManager));
            register("filter_by_number_of_wheels", new FilterByNumberOfWheels(console, collectionManager));
            register("print_field_ascending_fuel_type", new PrintFieldAscendingFuelType(console, collectionManager));
        }};

        new Runner(console, commandManager).interactiveMode();
    }
}
