/*
package pl.koloksk.modules;


public class LogFilter implements Filter {
    AbstractFilter abstractConsoleLogListener = new AbstractFilter() {

        private Filter.Result validateMessage(Message message) {
            if (message == null) {
                return Result.NEUTRAL;
            }
            return validateMessage(message.getFormattedMessage());
        }

        private Filter.Result validateMessage(String message) {
            return INSERT_CHECK_FOR_IF_THIS_MESSAGE_SHOULD_BE_BLOCKED ? Result.DENY : Result.NEUTRAL;
        }

        @Override
        public Filter.Result filter(LogEvent event) {
            Message candidate = null;
            if (event != null) {
                candidate = event.getMessage();
            }
            return validateMessage(candidate);
        }

        @Override
        public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
            return validateMessage(msg);
        }

        @Override
        public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
            String candidate = null;
            if (msg != null) {
                candidate = msg.toString();
            }
            return validateMessage(candidate);
        }

        @Override
        public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
            return validateMessage(msg);
        }
    };
    Logger logger = (Logger) LogManager.getRootLogger();
       logger.addFilter(abstractConsoleLogListener);
}

*/
