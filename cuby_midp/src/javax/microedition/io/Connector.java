
package javax.microedition.io;

import java.io.*;


/**
 * This class is factory for creating new Connection objects.
 * <p>
 * The creation of Connections is performed dynamically by looking
 * up a protocol implementation class whose name is formed from the
 * platform name (read from a system property) and the protocol name
 * of the requested connection (extracted from the parameter string
 * supplied by the application programmer.)
 *
 * The parameter string that describes the target should conform
 * to the URL format as described in RFC 2396.
 * This takes the general form:
 * <p>
 * <code>{scheme}:[{target}][{params}]</code>
 * <p>
 * where <code>{scheme}</code> is the name of a protocol such as
 * <i>http</i>}.
 * <p>
 * The <code>{target}</code> is normally some kind of network
 * address.
 * <p>
 * Any <code>{params}</code> are formed as a series of equates
 * of the form ";x=y".  Example: ";type=a".
 * <p>
 * An optional second parameter may be specified to the open
 * function. This is a mode flag that indicates to the protocol
 * handler the intentions of the calling code. The options here
 * specify if the connection is going to be read (READ), written
 * (WRITE), or both (READ_WRITE). The validity of these flag
 * settings is protocol dependent. For instance, a connection
 * for a printer would not allow read access, and would throw
 * an IllegalArgumentException. If the mode parameter is not
 * specified, READ_WRITE is used by default.
 * <p>
 * An optional third parameter is a boolean flag that indicates
 * if the calling code can handle timeout exceptions. If this
 * flag is set, the protocol implementation may throw an
 * InterruptedIOException when it detects a timeout condition.
 * This flag is only a hint to the protocol handler, and it
 * does not guarantee that such exceptions will actually be thrown.
 * If this parameter is not set, no timeout exceptions will be
 * thrown.
 * <p>
 * Because connections are frequently opened just to gain access
 * to a specific input or output stream, four convenience
 * functions are provided for this purpose.
 *
 * See also: {@link DatagramConnection DatagramConnection}
 * for information relating to datagram addressing
 *
 * @version 12/17/01 (CLDC 1.1)
 * @since   CLDC 1.0
 */

public class Connector {

/*
 * Implementation notes: The open parameter is used for
 * dynamically constructing a class name in the form:
 * <p>
 * <code>com.sun.cldc.io.{platform}.{protocol}.Protocol</code>
 * <p>
 * The platform name is derived from the system by looking for
 * the system property "microedition.platform".  If this property
 * key is not found or the associated class is not present, then
 * "j2me" is used by default.
 * <p>
 * The protocol name is derived from the parameter string
 * describing the target of the connection. This takes the from:
 * <p>
 * <code> {protocol}:[{target}][ {params}] </code>
 * <p>
 * The protocol name is used for dynamically finding the
 * appropriate protocol implementation class.  This information
 * is stripped from the target name that is given as a parameter
 * to the open() method. In order to avoid problems with illegal
 * class file names, all the '-' characters in the protocol name
 * are automatically converted into '_' characters.
 */

    /**
     * Access mode READ.
     */
    public final static int READ  = 1;

    /**
     * Access mode WRITE.
     */
    public final static int WRITE = 2;

    /**
     * Access mode READ_WRITE.
     */
    public final static int READ_WRITE = (READ|WRITE);

    /**
     * The platform name.
     */
    private static String platform;

    /**
     * The root of the classes.
     */
    private static String classRoot;

    /**
     * Class initializer.
     */
    static {
        /* Set up the platform name */
        platform = System.getProperty("microedition.platform");
        if ((platform == null) || (platform.equals("generic"))) {
            platform = "j2me";
        }

        /* Set up the library class root path */
        /* This may vary from one CLDC implementation to another */
        classRoot = System.getProperty("javax.microedition.io.Connector.protocolpath");
        if (classRoot == null) {
            classRoot = "com.sun.cldc.io";
        }
    }

    /**
     * Prevent instantiation of this class.
     */
    private Connector() { }

    /**
     * Create and open a Connection.
     *
     * @param name             The URL for the connection.
     * @return                 A new Connection object.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the target of the
     *   name cannot be found, or if the requested protocol type
     *   is not supported.
     * @exception IOException  If some other kind of I/O error occurs.
     * @exception SecurityException  May be thrown if access to the
     *   protocol handler is prohibited.
     */
    public static Connection open(String name) throws IOException {
        return open(name, READ_WRITE);
    }

    /**
     * Create and open a Connection.
     *
     * @param name             The URL for the connection.
     * @param mode             The access mode.
     * @return                 A new Connection object.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the target of the
     *   name cannot be found, or if the requested protocol type
     *   is not supported.
     * @exception IOException  If some other kind of I/O error occurs.
     * @exception SecurityException  May be thrown if access to the
     *   protocol handler is prohibited.
     */
    public static Connection open(String name, int mode) throws IOException {
        return open(name, mode, false);
    }

    /**
     * Create and open a Connection.
     *
     * @param name             The URL for the connection
     * @param mode             The access mode
     * @param timeouts         A flag to indicate that the caller
     *                         wants timeout exceptions
     * @return                 A new Connection object
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the target of the
     *   name cannot be found, or if the requested protocol type
     *   is not supported.
     * @exception IOException  If some other kind of I/O error occurs.
     * @exception SecurityException  May be thrown if access to the
     *   protocol handler is prohibited.
     */
    public static Connection open(String name, int mode, boolean timeouts)
        throws IOException {
        
    	//try {
        	//TODO
        //} catch(ClassNotFoundException x ) {
        //}

        throw new ConnectionNotFoundException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                   "The requested protocol does not exist "+name
/* #endif */
        );
    }



    /**
     * Create and open a connection input stream.
     *
     * @param  name            The URL for the connection.
     * @return                 A DataInputStream.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the target of the
     *   name cannot be found, or if the requested protocol type
     *   is not supported.
     * @exception IOException  If some other kind of I/O error occurs.
     * @exception SecurityException  May be thrown if access to the
     *   protocol handler is prohibited.
     */
    public static DataInputStream openDataInputStream(String name)
        throws IOException {

        InputConnection con = null;
        try {
            con = (InputConnection)Connector.open(name, Connector.READ);
        } catch (ClassCastException e) {
            throw new IOException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                       e.toString()
/* #endif */
            );
        }

        try {
            return con.openDataInputStream();
        } finally {
            con.close();
        }
    }

    /**
     * Create and open a connection output stream.
     *
     * @param  name            The URL for the connection.
     * @return                 A DataOutputStream.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the target of the
     *   name cannot be found, or if the requested protocol type
     *   is not supported.
     * @exception IOException  If some other kind of I/O error occurs.
     * @exception SecurityException  May be thrown if access to the
     *   protocol handler is prohibited.
     */
    public static DataOutputStream openDataOutputStream(String name)
        throws IOException {

        OutputConnection con = null;
        try {
            con = (OutputConnection)Connector.open(name, Connector.WRITE);
        } catch (ClassCastException e) {
            throw new IOException(
/* #ifdef VERBOSE_EXCEPTIONS */
/// skipped                       e.toString()
/* #endif */
            );
        }

        try {
            return con.openDataOutputStream();
        } finally {
            con.close();
        }
    }

    /**
     * Create and open a connection input stream.
     *
     * @param  name            The URL for the connection.
     * @return                 An InputStream.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the target of the
     *   name cannot be found, or if the requested protocol type
     *   is not supported.
     * @exception IOException  If some other kind of I/O error occurs.
     * @exception SecurityException  May be thrown if access to the
     *   protocol handler is prohibited.
     */
    public static InputStream openInputStream(String name)
        throws IOException {

        return openDataInputStream(name);
    }

    /**
     * Create and open a connection output stream.
     *
     * @param  name            The URL for the connection.
     * @return                 An OutputStream.
     *
     * @exception IllegalArgumentException If a parameter is invalid.
     * @exception ConnectionNotFoundException If the target of the
     *   name cannot be found, or if the requested protocol type
     *   is not supported.
     * @exception IOException  If some other kind of I/O error occurs.
     * @exception SecurityException  May be thrown if access to the
     *   protocol handler is prohibited.
     */
    public static OutputStream openOutputStream(String name)
        throws IOException {

        return openDataOutputStream(name);
    }

}
