package pl.koloksk.Common.utils;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;


public class LogFilter implements Filter {
    private static final String[] toCheck = new String[] { "UUID of player", "logged in with entity","lost connection", "disconnected", "disconnecting", "handleDisconnection", "timed out", "left the game", "joined the game" };

    public Filter.Result filter(LogEvent paramLogEvent) {
        Filter.Result result = Filter.Result.ACCEPT;
        if (StoreData.attack = false)
            return result;
        String str = paramLogEvent.getMessage().getFormattedMessage().toLowerCase();
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = toCheck).length, b = 0; b < i; ) {
            String str1 = arrayOfString[b];
            if (str.contains(str1)) {
                result = Filter.Result.DENY;
                break;
            }
            b++;
        }
        return result;
    }

    public Filter.Result getOnMatch() {
        return null;
    }

    public Filter.Result getOnMismatch() {
        return null;
    }

    public LifeCycle.State getState() {
        return null;
    }

    public void initialize() {}

    public boolean isStarted() {
        return false;
    }

    public boolean isStopped() {
        return false;
    }

    public void start() {}

    public void stop() {}

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object paramObject) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object paramObject1, Object paramObject2) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object paramObject1, Object paramObject2, Object paramObject3) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7, Object paramObject8) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7, Object paramObject8, Object paramObject9) {
        return null;
    }

    public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7, Object paramObject8, Object paramObject9, Object paramObject10) {
        return null;
    }

    public static void enableFilter(){
        Logger logger = (Logger) LogManager.getRootLogger();
        logger.addFilter(new LogFilter());
    }
    public static void disableFilter(){
        Logger logger = (Logger) LogManager.getRootLogger();
        logger.addFilter(null);
    }
}
