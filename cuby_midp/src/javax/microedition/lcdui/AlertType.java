
package javax.microedition.lcdui;

/**
 * The <code>AlertType</code> provides an indication of the nature
 * of alerts.
 * <code>Alerts</code> are used by an application to present
 * various kinds of
 * information to the user. 
 * An <code>AlertType</code> may be used to directly signal the
 * user without changing
 * the current <code>Displayable</code>.
 * The <code>playSound</code> method can be used to spontaneously
 * generate a sound to alert the user.  For example, a game using a 
 * <code>Canvas</code> can use <code>playSound</code> to indicate
 * success or progress.
 *
 * The predefined types are <CODE>INFO</CODE>, <CODE>WARNING</CODE>,
 * <CODE>ERROR</CODE>, <CODE>ALARM</CODE>, and <CODE>CONFIRMATION</CODE>.
 * <p>
 * 
 * @see Alert
 * @since MIDP 1.0
 */
public class AlertType {

    /**
     * Information Alert identifier
     */
    static final int ALERT_INFO = 1;
    /**
     * Warning Alert identifier
     */
    static final int ALERT_WARN = 2;
    /**
     * Error Alert identifier
     */
    static final int ALERT_ERR  = 3;
    /**
     * Alarm Alert identifier
     */
    static final int ALERT_ALRM = 4;
    /**
     * Confirmation Alert identifier
     */
    static final int ALERT_CFM  = 5;
  
    /**
     * An <code>INFO</code> <code>AlertType</code> typically
     * provides non-threatening information to the
     * user. For example, a simple splash screen might be an
     * <code>INFO</code> <code>AlertType</code>.
     */
    public static final AlertType INFO = new AlertType(ALERT_INFO);

    /**
     * A <code>WARNING</code> <code>AlertType</code> is a hint
     * to warn the user of a potentially
     * dangerous operation.
     * For example, the warning message may contain the message, &quot;Warning:
     * this operation will erase your data.&quot;
     */
    public static final AlertType WARNING = new AlertType(ALERT_WARN);

    /**
     * An <code>ERROR</code> <code>AlertType</code> is a hint
     * to alert the user to an erroneous operation.
     * For example, an error alert might show the message,
     * &quot;There is not enough room to install the application.&quot;
     */
    public static final AlertType ERROR = new AlertType(ALERT_ERR);

    /**
     * An <code>ALARM</code> <code>AlertType</code> is a hint
     * to alert the user to an event for which
     * the user has previously requested to be notified.
     * For example, the message might say, &quot;Staff meeting in five
     * minutes.&quot;
     */
    public static final AlertType ALARM = new AlertType(ALERT_ALRM);

    /**
     * A <code>CONFIRMATION</code> <code>AlertType</code> is a
     * hint to confirm user actions.
     * For example, &quot;Saved!&quot; might be shown to indicate that a Save 
     * operation has completed.
     */
    public static final AlertType CONFIRMATION = new AlertType(ALERT_CFM);

    /**
     * Protected constructor for subclasses.
     */
    protected AlertType() {
    }

    /**
     * Alert the user by playing the sound for this
     * <code>AlertType</code>.
     * The <code>AlertType</code> instance is used as a hint by the device
     * to generate an appropriate sound.  Instances other than
     * those predefined above may be ignored.
     * The actual sound made by the device,
     * if any, is determined by the device. The device may
     * ignore the request, use the same sound for
     * several <code>AlertTypes</code> or use any other means
     * suitable to alert
     * the user.
     *
     * @param display to which the <code>AlertType's</code> sound
     * should be played.
     * @return <code>true</code> if the user was alerted,
     * <code>false</code> otherwise.
     * @exception NullPointerException if <code>display</code> is 
     * <code>null</code>
     */
    public boolean playSound(Display display) {
    	return false;
    }

    /**
     * Create a new AlertType given the type identifier
     *
     * @param type  The type of the new AlertType
     */
    AlertType(int type) {

    }

    /**
     * Get the type of this AlertType
     *
     * @return int  The typer identifer of this AlertType, one of:
     *              ALERT_INFO, ALERT_WARN, ALERT_ERR, ALERT_ALRM, ALERT_CFM
     */
    int getType() {
    	return 0;
    }



}
