package log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * Created by Anur IjuoKaruKas on 2018/11/8
 *
 * console颜色
 *
 * https://blog.csdn.net/u012693119/article/details/79716306
 */
public class EasyHighlightingCompositeConverter extends ForegroundCompositeConverterBase<ILoggingEvent> {

    /**
     * (non-Javadoc)
     *
     * @see ForegroundCompositeConverterBase#
     * getForegroundColorCode(java.lang.Object)
     */
    protected String getForegroundColorCode(ILoggingEvent event) {
        switch (event.getLevel()
                     .toInt()) {
        case Level.ERROR_INT:
            return ANSIConstants.RED_FG;
        case Level.WARN_INT:
            return ANSIConstants.YELLOW_FG;
        case Level.INFO_INT:
            return ANSIConstants.GREEN_FG;
        case Level.DEBUG_INT:
            return ANSIConstants.MAGENTA_FG;
        default:
            return null;
        }
    }
}