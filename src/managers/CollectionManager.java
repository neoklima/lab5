package managers;

import models.Vehicle;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Управляет коллекцией транспортных средств.
 */
public class CollectionManager {
    private int currentId = 1;
    private final Set<Vehicle> collection = new TreeSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    /**
     * Конструктор класса CollectionManager.
     *
     * @param dumpManager менеджер дампа для загрузки и сохранения коллекции.
     */
    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    /**
     * Получить последнее время инициализации коллекции.
     *
     * @return Последнее время инициализации.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Получить последнее время сохранения коллекции.
     *
     * @return Последнее время сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Получить коллекцию транспортных средств.
     *
     * @return Коллекция транспортных средств.
     */
    public Set<Vehicle> getCollection() {
        return collection;
    }

    /**
     * Получить транспортное средство по его ID.
     *
     * @param id ID транспортного средства.
     * @return Транспортное средство или null, если не найдено.
     */
    public Vehicle byId(long id) {
        for (Vehicle vehicle : collection) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null;
    }

    /**
     * Проверяет, содержится ли указанное транспортное средство в коллекции.
     *
     * @param vehicle Транспортное средство.
     * @return true, если содержится, иначе false.
     */
    public boolean contains(Vehicle vehicle) {
        return vehicle == null || byId(vehicle.getId()) != null;
    }

    /**
     * Получить свободный ID для нового транспортного средства.
     *
     * @return Свободный ID.
     */
    public int getFreeId() {
        while (byId(currentId) != null) {
            currentId++;
        }
        return currentId;
    }

    /**
     * Добавить транспортное средство в коллекцию.
     *
     * @param vehicle Транспортное средство.
     * @return true, если добавление успешно, иначе false.
     */
    public boolean add(Vehicle vehicle) {
        if (contains(vehicle)) {
            return false;
        }
        vehicle.setId(getFreeId());
        collection.add(vehicle);
        update();
        return true;
    }

    /**
     * Удалить транспортное средство из коллекции по его ID.
     *
     * @param id ID транспортного средства.
     * @return true, если удаление успешно, иначе false.
     */
    public boolean remove(long id) {
        Vehicle vehicle = byId(id);
        if (vehicle == null) {
            return false;
        }
        collection.remove(vehicle);
        update();
        return true;
    }

    /**
     * Обновить коллекцию.
     */
    public void update() {
        // Не нужно явно сортировать TreeSet, так как он автоматически сортируется
    }

    /**
     * Загрузить коллекцию транспортных средств из файла.
     *
     * @return true, если загрузка успешна, иначе false.
     */
    public boolean loadCollection() {
        collection.clear();
        dumpManager.readCollection(collection);
        lastInitTime = LocalDateTime.now();
        update();
        return true;
    }

    /**
     * Сохранить коллекцию транспортных средств в файл.
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Удалить из коллекции все транспортные средства, которые превышают заданное транспортное средство.
     *
     * @param vehicle Транспортное средство, с которым сравниваются элементы коллекции.
     * @return Количество удаленных элементов из коллекции.
     */
    public int removeGreaterThan(Vehicle vehicle) {
        int countRemoved = 0;
        Iterator<Vehicle> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Vehicle currentVehicle = iterator.next();
            if (currentVehicle.compareTo(vehicle) > 0) {
                iterator.remove();
                countRemoved++;
            }
        }
        return countRemoved;
    }

    /**
     * Переопределение метода toString для получения строкового представления коллекции.
     *
     * @return Строковое представление коллекции.
     */
    @Override
    public String toString() {
        if (collection.isEmpty()) {
            return "Коллекция пуста!";
        }

        StringBuilder info = new StringBuilder();
        for (Vehicle vehicle : collection) {
            info.append(vehicle).append("\n\n");
        }
        return info.toString().trim();
    }
}
