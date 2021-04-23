package by.victor.jwd.controller.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder class that creates path to Controller servlet with necessary command
 * and adds any parameters
 */
public class CommandPath {

    private static final String CONTROLLER_COMMAND = "Controller?command=";
    private static final String PARAM_DELIMITER = "&";
    private static final String PARAM_EQUALS = "=";

    private CommandName command;
    private final Map<String, String> params;

    private CommandPath() {
        params = new HashMap<>();
    }

    public static Builder createCommand(CommandName command) {
        return new CommandPath().new Builder(command);
    }

    @Override
    public String toString() {
        StringBuilder path = new StringBuilder(CONTROLLER_COMMAND).append(command.toString().toLowerCase());
        params.forEach((key, value) -> path
                .append(PARAM_DELIMITER)
                .append(key)
                .append(PARAM_EQUALS)
                .append(value));
        return path.toString();
    }

    public class Builder {

        private Builder(CommandName command) {
            CommandPath.this.command = command;
        }

        public Builder addParam(String key, String value) {
            CommandPath.this.params.put(key, value);
            return this;
        }

        public String createPath() {
            return CommandPath.this.toString();
        }

    }
}
