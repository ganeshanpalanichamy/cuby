
package javax.microedition.rms;


/**
 * A class representing a record store. A record store consists of a
 * collection of records which will remain persistent across multiple
 * invocations of the MIDlet. The platform is responsible for
 * making its best effort to maintain the integrity of the
 * MIDlet's record stores throughout the normal use of the
 * platform, including reboots, battery changes, etc.
 *
 * <p>Record stores are created in platform-dependent locations, which
 * are not exposed to the MIDlets. The naming space for record stores
 * is controlled at the MIDlet suite granularity. MIDlets within a
 * MIDlet suite are allowed to create multiple record stores, as long
 * as they are each given different names. When a MIDlet suite is
 * removed from a platform all the record stores associated with its
 * MIDlets will also be removed. MIDlets within a MIDlet suite can
 * access each other's record stores directly. New APIs in MIDP
 * allow for the explicit sharing of record stores if the MIDlet
 * creating the RecordStore chooses to give such permission.</p>
 *
 * <p> Sharing is accomplished through the ability to name a
 * RecordStore created by another MIDlet suite.</p>
 *
 * <P> RecordStores are uniquely named using the unique name of the
 * MIDlet suite plus the name of the RecordStore. MIDlet suites are
 * identified by the MIDlet-Vendor and MIDlet-Name attributes from the
 * application descriptor.</p>
 *
 * <p> Access controls are defined when RecordStores to be shared are
 * created. Access controls are enforced when RecordStores are
 * opened. The access modes allow private use or shareable
 * with any other MIDlet suite.</p>
 *
 * <p>Record store names are case sensitive and may consist of any
 * combination of between one and 32 Unicode characters
 * inclusive. Record store names must be unique within the scope of a
 * given MIDlet suite. In other words, MIDlets within a MIDlet suite
 * are not allowed to create more than one record store with the same
 * name, however a MIDlet in one MIDlet suite is allowed to have a
 * record store with the same name as a MIDlet in another MIDlet
 * suite. In that case, the record stores are still distinct and
 * separate.</p>
 *
 * <p>No locking operations are provided in this API. Record store
 * implementations ensure that all individual record store operations
 * are atomic, synchronous, and serialized, so no corruption will
 * occur with multiple accesses. However, if a MIDlet uses multiple
 * threads to access a record store, it is the MIDlet's responsibility
 * to coordinate this access or unintended consequences may result.
 * Similarly, if a platform performs transparent synchronization of a
 * record store, it is the platform's responsibility to enforce
 * exclusive access to the record store between the MIDlet and
 * synchronization engine.</p>
 *
 * <p>Records are uniquely identified within a given record store by
 * their recordId, which is an integer value. This recordId is used as
 * the primary key for the records. The first record created in a
 * record store will have recordId equal to one (1). Each subsequent
 * record added to a RecordStore will be assigned a recordId one
 * greater than the record added before it. That is, if two records
 * are added to a record store, and the first has a recordId of 'n',
 * the next will have a recordId of 'n + 1'. MIDlets can create other
 * sequences of the records in the RecordStore by using the
 * <code>RecordEnumeration</code> class.</p>
 *
 * <p>This record store uses long integers for time/date stamps, in
 * the format used by System.currentTimeMillis(). The record store is
 * time stamped with the last time it was modified. The record store
 * also maintains a <em>version</em> number, which is an integer that
 * is incremented for each operation that modifies the contents of the
 * RecordStore.  These are useful for synchronization engines as well
 * as other things.</p>
 *
 * @since MIDP 1.0
 */

public class RecordStore {

 
    /**
     * Deletes the named record store. MIDlet suites are only allowed
     * to delete their own record stores. If the named record store is
     * open (by a MIDlet in this suite or a MIDlet in a different
     * MIDlet suite) when this method is called, a
     * RecordStoreException will be thrown.  If the named record store
     * does not exist a RecordStoreNotFoundException will be
     * thrown. Calling this method does NOT result in recordDeleted
     * calls to any registered listeners of this RecordStore.
     *
     * @param recordStoreName the MIDlet suite unique record store to
     *          delete
     *
     * @exception RecordStoreException if a record store-related
     *          exception occurred
     * @exception RecordStoreNotFoundException if the record store
     *          could not be found
     */
    public static void deleteRecordStore(String recordStoreName)
            throws RecordStoreException, RecordStoreNotFoundException  {

        if (recordStoreName == null || recordStoreName.length() == 0) {
            throw new RecordStoreNotFoundException();
        }

   
    }

    /**
     * Open (and possibly create) a record store associated with the
     * given MIDlet suite. If this method is called by a MIDlet when
     * the record store is already open by a MIDlet in the MIDlet suite,
     * this method returns a reference to the same RecordStore object.
     *
     * @param recordStoreName the MIDlet suite unique name for the
     *          record store, consisting of between one and 32 Unicode
     *          characters inclusive.
     * @param createIfNecessary if true, the record store will be
     *          created if necessary
     *
     * @return <code>RecordStore</code> object for the record store
     *
     * @exception RecordStoreException if a record store-related
     *          exception occurred
     * @exception RecordStoreNotFoundException if the record store
     *          could not be found
     * @exception RecordStoreFullException if the operation cannot be
     *          completed because the record store is full
     * @exception IllegalArgumentException if
     *          recordStoreName is invalid
     */
    public static RecordStore openRecordStore(String recordStoreName,
                                              boolean createIfNecessary)
        throws RecordStoreException, RecordStoreFullException,
        RecordStoreNotFoundException {

    

	return null;
    }

    /**
     * Open (and possibly create) a record store that can be shared
     * with other MIDlet suites. The RecordStore is owned by the
     * current MIDlet suite. The authorization mode is set when the
     * record store is created, as follows:
     *
     * <ul>
     * <li><code>AUTHMODE_PRIVATE</code> - Only allows the MIDlet
     *          suite that created the RecordStore to access it. This
     *          case behaves identically to
     *          <code>openRecordStore(recordStoreName,
     *          createIfNecessary)</code>.</li>
     * <li><code>AUTHMODE_ANY</code> - Allows any MIDlet to access the
     *          RecordStore. Note that this makes your recordStore
     *          accessible by any other MIDlet on the device. This
     *          could have privacy and security issues depending on
     *          the data being shared. Please use carefully.</li>
     * </ul>
     *
     * <p>The owning MIDlet suite may always access the RecordStore and
     * always has access to write and update the store.</p>
     *
     * <p> If this method is called by a MIDlet when the record store
     * is already open by a MIDlet in the MIDlet suite, this method
     * returns a reference to the same RecordStore object.</p>
     *
     * @param recordStoreName the MIDlet suite unique name for the
     *          record store, consisting of between one and 32 Unicode
     *          characters inclusive.
     * @param createIfNecessary if true, the record store will be
     *          created if necessary
     * @param authmode the mode under which to check or create access.
     *          Must be one of AUTHMODE_PRIVATE or AUTHMODE_ANY.
     *          This argument is ignored if the RecordStore exists.
     * @param writable true if the RecordStore is to be writable by
     *          other MIDlet suites that are granted access.
     *          This argument is ignored if the RecordStore exists.
     *
     * @return <code>RecordStore</code> object for the record store
     *
     * @exception RecordStoreException if a record store-related
     *          exception occurred
     * @exception RecordStoreNotFoundException if the record store
     *          could not be found
     * @exception RecordStoreFullException if the operation
     *          cannot be completed because the record store is full
     * @exception IllegalArgumentException if authmode or
     *          recordStoreName is invalid
     */
    public static RecordStore openRecordStore(String recordStoreName,
                                              boolean createIfNecessary,
                                              int authmode,
                                              boolean writable)
        throws RecordStoreException, RecordStoreFullException,
               RecordStoreNotFoundException {
        RecordStore recordStore;
        boolean isExistingStorage = false;

        /*
         * First, we have to check if the record store already exists or not.
         * If we open an existing record store, "authmode" must be ignored!
         *
         */
        try {
            recordStore = openRecordStore(recordStoreName, false);
            isExistingStorage = true;
        } catch (RecordStoreNotFoundException ex)
        {
            recordStore = openRecordStore(recordStoreName, createIfNecessary);
        }

        if (!isExistingStorage) {
            try {
                //recordStore.peer.setMode(authmode, writable);
            } catch (Exception e) {
                try {
                    recordStore.closeRecordStore();
                } catch (Exception ex) {
                    // do not overthrow the real exception
                }

                try {
             //       int id = MIDletStateHandler.getMidletStateHandler().
             //           getMIDletSuite().getID();
             //       RecordStoreImpl.deleteRecordStore(
            //            classSecurityToken, id, recordStoreName);
                } catch (Exception ex) {
                    // do not overthrow the real exception
                }

                if (e instanceof RecordStoreException) {
                    throw (RecordStoreException)e;
                }

                throw (RuntimeException)e;
            }
        }

        return recordStore;
    }

    /**
     * Open a record store associated with the named MIDlet suite.
     * The MIDlet suite is identified by MIDlet vendor and MIDlet
     * name.  Access is granted only if the authorization mode of the
     * RecordStore allows access by the current MIDlet suite.  Access
     * is limited by the authorization mode set when the record store
     * was created:
     *
     * <ul>
     * <li><code>AUTHMODE_PRIVATE</code> - Succeeds only if vendorName
     *          and suiteName identify the current MIDlet suite; this
     *          case behaves identically to
     *          <code>openRecordStore(recordStoreName,
     *          createIfNecessary)</code>.</li>
     * <li><code>AUTHMODE_ANY</code> - Always succeeds.
     *          Note that this makes your recordStore
     *          accessible by any other MIDlet on the device. This
     *          could have privacy and security issues depending on
     *          the data being shared. Please use carefully.
     *          Untrusted MIDlet suites are allowed to share data but
     *          this is not recommended. The authenticity of the
     *          origin of untrusted MIDlet suites cannot be verified
     *          so shared data may be used unscrupulously.</li>
     * </ul>
     *
     * <p> If this method is called by a MIDlet when the record store
     * is already open by a MIDlet in the MIDlet suite, this method
     * returns a reference to the same RecordStore object.</p>
     *
     * <p> If a MIDlet calls this method to open a record store from
     * its own suite, the behavior is identical to calling:
     * <code>{@link #openRecordStore(String, boolean)
     * openRecordStore(recordStoreName, false)}</code></p>
     *
     * @param recordStoreName the MIDlet suite unique name for the
     *          record store, consisting of between one and 32 Unicode
     *          characters inclusive.
     * @param vendorName the vendor of the owning MIDlet suite
     * @param suiteName the name of the MIDlet suite
     *
     * @return <code>RecordStore</code> object for the record store
     *
     * @exception RecordStoreException if a record store-related
     *          exception occurred
     * @exception RecordStoreNotFoundException if the record store
     *          could not be found
     * @exception SecurityException if this MIDlet Suite is not
     *          allowed to open the specified RecordStore.
     * @exception IllegalArgumentException if recordStoreName is
     *          invalid
     */
    public static RecordStore openRecordStore(String recordStoreName,
                                              String vendorName,
                                              String suiteName)
        throws RecordStoreException, RecordStoreNotFoundException {

     
        int id;
        RecordStore recordStore;

        if (vendorName == null || suiteName == null) {
            throw new IllegalArgumentException("vendorName and " +
                                               "suiteName must be " +
                                               "non null");
        }

        if (recordStoreName.length() > 32 || recordStoreName.length() == 0) {
            throw new IllegalArgumentException();
        }

 

        return null;
    }

    /**
     * Authorization to allow access only to the current MIDlet
     * suite. AUTHMODE_PRIVATE has a value of 0.
     */
    public final static int AUTHMODE_PRIVATE = 0;

    /**
     * Authorization to allow access to any MIDlet
     * suites. AUTHMODE_ANY has a value of 1.
     */
    public final static int AUTHMODE_ANY = 1;

    /**
     * Changes the access mode for this RecordStore. The authorization
     * mode choices are:
     *
     * <ul>
     * <li><code>AUTHMODE_PRIVATE</code> - Only allows the MIDlet
     *          suite that created the RecordStore to access it. This
     *          case behaves identically to
     *          <code>openRecordStore(recordStoreName,
     *          createIfNecessary)</code>.</li>
     * <li><code>AUTHMODE_ANY</code> - Allows any MIDlet to access the
     *          RecordStore. Note that this makes your recordStore
     *          accessible by any other MIDlet on the device. This
     *          could have privacy and security issues depending on
     *          the data being shared. Please use carefully.</li>
     * </ul>
     *
     * <p>The owning MIDlet suite may always access the RecordStore and
     * always has access to write and update the store. Only the
     * owning MIDlet suite can change the mode of a RecordStore.</p>
     *
     * @param authmode the mode under which to check or create access.
     *          Must be one of AUTHMODE_PRIVATE or AUTHMODE_ANY.
     * @param writable true if the RecordStore is to be writable by
     *          other MIDlet suites that are granted access
     *
     * @exception RecordStoreException if a record store-related
     *          exception occurred
     * @exception SecurityException if this MIDlet Suite is not
     *          allowed to change the mode of the RecordStore
     * @exception IllegalArgumentException if authmode is invalid
     */
    public void setMode(int authmode, boolean writable)
            throws RecordStoreException {

    }

    /**
     * This method is called when the MIDlet requests to have the
     * record store closed. Note that the record store will not
     * actually be closed until closeRecordStore() is called as many
     * times as openRecordStore() was called. In other words, the
     * MIDlet needs to make a balanced number of close calls as open
     * calls before the record store is closed.
     *
     * <p>When the record store is closed, all listeners are removed
     * and all RecordEnumerations associated with it become invalid.
     * If the MIDlet attempts to perform
     * operations on the RecordStore object after it has been closed,
     * the methods will throw a RecordStoreNotOpenException.
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     * @exception RecordStoreException if a different record
     *          store-related exception occurred
     */
    public void closeRecordStore()
	throws RecordStoreNotOpenException, RecordStoreException {


    }

    /**
     * Returns an array of the names of record stores owned by the
     * MIDlet suite. Note that if the MIDlet suite does not
     * have any record stores, this function will return null.
     *
     * The order of RecordStore names returned is implementation
     * dependent.
     *
     * @return array of the names of record stores owned by the
     * MIDlet suite. Note that if the MIDlet suite does not
     * have any record stores, this function will return null.
     */
    public static String[] listRecordStores() {
    	return null;

    }

    /**
     * Returns the name of this RecordStore.
     *
     * @return the name of this RecordStore
     *
     * @exception RecordStoreNotOpenException if the record store is not open
     */
    public String getName() throws RecordStoreNotOpenException {
        return null;
    }

    /**
     * Each time a record store is modified (by
     * <code>addRecord</code>, <code>setRecord</code>, or
     * <code>deleteRecord</code> methods) its <em>version</em> is
     * incremented. This can be used by MIDlets to quickly tell if
     * anything has been modified.
     *
     * The initial version number is implementation dependent.
     * The increment is a positive integer greater than 0.
     * The version number increases only when the RecordStore is updated.
     *
     * The increment value need not be constant and may vary with each
     * update.
     *
     * @return the current record store version
     *
     * @exception RecordStoreNotOpenException if the record store is
     *            not open
     */
    public int getVersion() throws RecordStoreNotOpenException {
        return 0;
    }

    /**
     * Returns the number of records currently in the record store.
     *
     * @return the number of records currently in the record store
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     */
    public int getNumRecords() throws RecordStoreNotOpenException {
        return 0;
    }

    /**
     * Returns the amount of space, in bytes, that the record store
     * occupies. The size returned includes any overhead associated
     * with the implementation, such as the data structures
     * used to hold the state of the record store, etc.
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     *
     * @return the size of the record store in bytes
     */
    public int getSize() throws RecordStoreNotOpenException {

        return 0;
    }

    /**
     * Returns the amount of additional room (in bytes) available for
     * this record store to grow. Note that this is not necessarily
     * the amount of extra MIDlet-level data which can be stored,
     * as implementations may store additional data structures with
     * each record to support integration with native applications,
     * synchronization, etc.
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     *
     * @return the amount of additional room (in bytes) available for
     *          this record store to grow
     */
    public int getSizeAvailable() throws RecordStoreNotOpenException {

        return 0;
    }

    /**
     * Returns the last time the record store was modified, in the
     * format used by System.currentTimeMillis().
     *
     * @return the last time the record store was modified, in the
     *          format used by System.currentTimeMillis()
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     */
    public long getLastModified() throws RecordStoreNotOpenException {
	
        return 0;
    }

    /**
     * Adds the specified RecordListener. If the specified listener
     * is already registered, it will not be added a second time.
     * When a record store is closed, all listeners are removed.
     *
     * @param listener the RecordChangedListener
     * @see #removeRecordListener
     */
    public void addRecordListener(RecordListener listener) {

    }

    /**
     * Removes the specified RecordListener. If the specified listener
     * is not registered, this method does nothing.
     *
     * @param listener the RecordChangedListener
     * @see #addRecordListener
     */
    public void removeRecordListener(RecordListener listener) {
 

    }

    /**
     * Returns the recordId of the next record to be added to the
     * record store. This can be useful for setting up pseudo-relational
     * relationships. That is, if you have two or more
     * record stores whose records need to refer to one another, you can
     * predetermine the recordIds of the records that will be created
     * in one record store, before populating the fields and allocating
     * the record in another record store. Note that the recordId returned
     * is only valid while the record store remains open and until a call
     * to <code>addRecord()</code>.
     *
     * @return the recordId of the next record to be added to the
     *          record store
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     * @exception RecordStoreException if a different record
     *          store-related exception occurred
     */
    public int getNextRecordID()
        throws RecordStoreNotOpenException, RecordStoreException {


        return 0;
    }

    /**
     * Adds a new record to the record store. The recordId for this
     * new record is returned. This is a blocking atomic operation.
     * The record is written to persistent storage before the
     * method returns.
     *
     * @param data the data to be stored in this record. If the record
     *          is to have zero-length data (no data), this parameter may be
     *          null.
     * @param offset the index into the data buffer of the first
     *          relevant byte for this record
     * @param numBytes the number of bytes of the data buffer to use
     *          for this record (may be zero)
     *
     * @return the recordId for the new record
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     * @exception RecordStoreException if a different record
     *          store-related exception occurred
     * @exception RecordStoreFullException if the operation cannot be
     *          completed because the record store has no more room
     * @exception SecurityException if the MIDlet has read-only access
     *          to the RecordStore
     */
    public int addRecord(byte[] data, int offset, int numBytes)
        throws RecordStoreNotOpenException, RecordStoreException,
            RecordStoreFullException {

	return 0;
    }

    /**
     * The record is deleted from the record store. The recordId for
     * this record is NOT reused.
     *
     * @param recordId the ID of the record to delete
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     * @exception InvalidRecordIDException if the recordId is invalid
     * @exception RecordStoreException if a general record store
     *          exception occurs
     * @exception SecurityException if the MIDlet has read-only access
     *          to the RecordStore
     */
    public void deleteRecord(int recordId)
        throws RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

    }

    /**
     * Returns the size (in bytes) of the MIDlet data available
     * in the given record.
     *
     * @param recordId the ID of the record to use in this operation
     *
     * @return the size (in bytes) of the MIDlet data available
     *          in the given record
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     * @exception InvalidRecordIDException if the recordId is invalid
     * @exception RecordStoreException if a general record store
     *          exception occurs
     */
    public int getRecordSize(int recordId)
        throws RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

        return 0;
    }

    /**
     * Returns the data stored in the given record.
     *
     * @param recordId the ID of the record to use in this operation
     * @param buffer the byte array in which to copy the data
     * @param offset the index into the buffer in which to start copying
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     * @exception InvalidRecordIDException if the recordId is invalid
     * @exception RecordStoreException if a general record store
     *          exception occurs
     * @exception ArrayIndexOutOfBoundsException if the record is
     *          larger than the buffer supplied
     *
     * @return the number of bytes copied into the buffer, starting at
     *          index <code>offset</code>
     * @see #setRecord
     */
    public int getRecord(int recordId, byte[] buffer, int offset)
        throws RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {
	
        return 0;
    }

    /**
     * Returns a copy of the data stored in the given record.
     *
     * @param recordId the ID of the record to use in this operation
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     * @exception InvalidRecordIDException if the recordId is invalid
     * @exception RecordStoreException if a general record store
     *          exception occurs
     *
     * @return the data stored in the given record. Note that if the
     *          record has no data, this method will return null.
     * @see #setRecord
     */
    public byte[] getRecord(int recordId)
        throws RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException {

        return null;
    }

    /**
     * Sets the data in the given record to that passed in. After
     * this method returns, a call to <code>getRecord(int recordId)</code>
     * will return an array of numBytes size containing the data
     * supplied here.
     *
     * @param recordId the ID of the record to use in this operation
     * @param newData the new data to store in the record
     * @param offset the index into the data buffer of the first
     *          relevant byte for this record
     * @param numBytes the number of bytes of the data buffer to use
     *          for this record
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     * @exception InvalidRecordIDException if the recordId is invalid
     * @exception RecordStoreException if a general record store
     *          exception occurs
     * @exception RecordStoreFullException if the operation cannot be
     *          completed because the record store has no more room
     * @exception SecurityException if the MIDlet has read-only access
     *          to the RecordStore
     * @see #getRecord
     */
    public void setRecord(int recordId, byte[] newData,
                          int offset, int numBytes)
        throws RecordStoreNotOpenException, InvalidRecordIDException,
            RecordStoreException, RecordStoreFullException {


    }

    /**
     * Returns an enumeration for traversing a set of records in the
     * record store in an optionally specified order.<p>
     *
     * The filter, if non-null, will be used to determine what
     * subset of the record store records will be used.<p>
     *
     * The comparator, if non-null, will be used to determine the
     * order in which the records are returned.<p>
     *
     * If both the filter and comparator is null, the enumeration
     * will traverse all records in the record store in an undefined
     * order. This is the most efficient way to traverse all of the
     * records in a record store.  If a filter is used with a null
     * comparator, the enumeration will traverse the filtered records
     * in an undefined order.
     *
     * The first call to <code>RecordEnumeration.nextRecord()</code>
     * returns the record data from the first record in the sequence.
     * Subsequent calls to <code>RecordEnumeration.nextRecord()</code>
     * return the next consecutive record's data. To return the record
     * data from the previous consecutive from any
     * given point in the enumeration, call <code>previousRecord()</code>.
     * On the other hand, if after creation the first call is to
     * <code>previousRecord()</code>, the record data of the last element
     * of the enumeration will be returned. Each subsequent call to
     * <code>previousRecord()</code> will step backwards through the
     * sequence.
     *
     * @param filter if non-null, will be used to determine what
     *          subset of the record store records will be used
     * @param comparator if non-null, will be used to determine the
     *          order in which the records are returned
     * @param keepUpdated if true, the enumerator will keep its enumeration
     *          current with any changes in the records of the record
     *          store. Use with caution as there are possible
     *          performance consequences. If false the enumeration
     *          will not be kept current and may return recordIds for
     *          records that have been deleted or miss records that
     *          are added later. It may also return records out of
     *          order that have been modified after the enumeration
     *          was built. Note that any changes to records in the
     *          record store are accurately reflected when the record
     *          is later retrieved, either directly or through the
     *          enumeration. The thing that is risked by setting this
     *          parameter false is the filtering and sorting order of
     *          the enumeration when records are modified, added, or
     *          deleted.
     *
     * @exception RecordStoreNotOpenException if the record store is
     *          not open
     *
     * @see RecordEnumeration#rebuild
     *
     * @return an enumeration for traversing a set of records in the
     *          record store in an optionally specified order
     */
    public RecordEnumeration enumerateRecords(RecordFilter filter,
                                              RecordComparator comparator,
                                              boolean keepUpdated)
        throws RecordStoreNotOpenException {
    	return null;
    }


}
