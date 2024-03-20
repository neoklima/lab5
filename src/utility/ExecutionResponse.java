package utility;

/**
 * Класс ExecutionResponse представляет результат выполнения команды.
 */
public class ExecutionResponse {
    private boolean exitCode;
    private String message;

    /**
     * Конструктор с параметрами.
     * @param exitCode код завершения
     * @param message сообщение
     */
    public ExecutionResponse(boolean exitCode, String message) {
        this.exitCode = exitCode;
        this.message = message;
    }

    /**
     * Конструктор с параметром сообщения (код завершения true).
     * @param message сообщение
     */
    public ExecutionResponse(String message) {
        this(true, message);
    }

    /**
     * Получает код завершения.
     * @return код завершения
     */
    public boolean getExitCode() {
        return exitCode;
    }

    /**
     * Получает сообщение.
     * @return сообщение
     */
    public String getMessage() {
        return message;
    }

    /**
     * Представляет объект в виде строки.
     * @return строковое представление объекта
     */
    public String toString() {
        return String.valueOf(exitCode) + ";" + message;
    }
}
