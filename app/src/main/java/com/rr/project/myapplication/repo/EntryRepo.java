package com.rr.project.myapplication.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.rr.project.myapplication.R;
import com.rr.project.myapplication.dao.Entry;
import com.rr.project.myapplication.dao.EntryDao;
import com.rr.project.myapplication.dao.Tab;
import com.rr.project.myapplication.db.WalletRoomDB;
import com.rr.project.myapplication.utils.Utils;

import java.util.Date;
import java.util.List;

public class EntryRepo {
    private final Application application;
    private EntryDao entryDao;
    private LiveData<List<Entry>> listEntries;
    private LiveData<List<Entry>> listEntriesById;
    public LiveData<Integer> tabNameCount;
    private InsertCompleteListener insertCompleteListener;
    private UpdateCompleteListener updateCompleteListener;
    private boolean isCurrentDateEntry;

    public EntryRepo(Application application) {
        this.application = application;
        WalletRoomDB db = WalletRoomDB.getDatabase(application);
        entryDao = db.entryDao();
        listEntries = entryDao.getAllEntries();
    }

    public LiveData<List<Entry>> getListEntries() {
        return listEntries;
    }

    public void insertEntry(final Entry entry, final boolean isCurrentDateEntry) {
        Entry prevEntry = null;
        //isCurrentDateEntry = entry.getDateString().equals(Utils.getCurrentDateInString());

        if (isCurrentDateEntry) {//current date entry
            entry.setLatestEntry(true);
            prevEntry = getLastEntryByTabId(entry.getTabId());
            if (prevEntry == null && entry.isDebit()) {//there is no entry i.e there is no entry in table
                Toast.makeText(application, R.string.FIRST_ENTRY_ERROR_MSG, Toast.LENGTH_SHORT).show();
                return;
            }
        } else {//back date entry
            entry.setLatestEntry(false);
            prevEntry = getLastEntryByTabIdAndDate(entry.getTabId(), entry.getDateString());
            if (prevEntry == null)//when there is no matching dateString for the back entry then fetch the last entry as per entryTime
                prevEntry = getPrevEntryByTabIdAndDate(entry.getTabId(), entry.getEntryTime());
            entry.setEntryTime(setBackDateEntryDateTime(entry, prevEntry));
        }

        float balance = getBalance(entry, prevEntry);
        //set balance for current entry
        /*if (balance == 0) {
            Toast.makeText(application, R.string.FIRST_ENTRY_ERROR_MSG, Toast.LENGTH_SHORT).show();
            return;
        } else */
        {
            entry.setBalance(balance);
        }

        //set newMonth for entry
        entry.setNewMonth(getNewMonth(entry, prevEntry));

        insertCompleteListener = new InsertCompleteListener() {
            @Override
            public void onInsertComplete(boolean isCurrentDateEntry) {
                if (!isCurrentDateEntry)
                    updateBalanceOfEntriesAfterInsertion(entry);
            }
        };

        new insertAynscTask().execute(entry);
    }

    public void updateEntryWithAmtOrDateChange(final Entry entry, boolean isDateChange) {
        isCurrentDateEntry = entry.getDateString().equals(Utils.getCurrentDateInString());

        Entry prevEntry;
        if (isCurrentDateEntry || isTopEntry(entry)) {
//            deleteEntry(editEntry);
            prevEntry = getLastEntryByIdForUpdateCase(entry.getTabId());
        } else {
            //back date entry
            entry.setLatestEntry(false);
//            prevEntry = getLastEntryByTabIdAndDateForSameAndDateUpdateCase(entry.getTabId(), entry.getId(), entry.getDateString());
//            if (prevEntry == null)//when there is no matching dateString for the back entry then fetch the last entry as per entryTime
            prevEntry = getPrevEntryByTabIdAndDate(entry.getTabId(), /*entry.getId(),*/ entry.getEntryTime());
            if (isDateChange)
                entry.setEntryTime(setBackDateEntryDateTime(entry, prevEntry));
        }

        entry.setBalance(getBalance(entry, prevEntry));
        //set newMonth for entry
        entry.setNewMonth(getNewMonth(entry, prevEntry));

        updateCompleteListener = new UpdateCompleteListener() {
            @Override
            public void onUpdateComplete(boolean isCurrentDateEntry) {
                if (!isCurrentDateEntry)
                    updateBalanceOfEntriesAfterInsertion(entry);
            }
        };

//        updateEntry(entry);
        new updateAynscTask().execute(entry);
    }
    
    public void updateEntryWithAmtOrDateChange(final Entry entry, boolean isDateChange) {
        Entry prevEntry;
	    boolean isTopEntry = isTopEntry(entry);

        if (isTopEntryForTab && !isDateChange){//Entry is latest entry ,we need to just find the 2ndTop entry for balance calculation
		    deleteEntry(entry);//delete entry from DB but maintain entry value for insertion
		    insertEntry(entry,true);//true so that inserted entry is the latest/current date entry
        } else {
            //Entry is not the latest entry for selected TAB, can be any entry but not latest oone
		    deleteEntry(entry);//delete entry from DB but maintain entry value for insertion
	        insertEntry(entry,false);//false so that inserted entry is the back date entry
        }

    }

    private String getNewMonth(Entry entry, Entry lastEntry) {
        Date date = new Date();
        String month = (String) DateFormat.format("MMMM", date);
        String year = (String) DateFormat.format("yyyy", date);

        //Entry back date, fetch newMonth of the last entry and mark it blank for last entry as the new would be 
        //the entry on top of last entry. Return the same
        if (!isCurrentDateEntry /*|| !isTopEntry(entry)*/) {
            String newMonth = lastEntry.getNewMonth();
            lastEntry.setNewMonth("");
            lastEntry.setLatestEntry(false);
            updateEntry(lastEntry);
            return newMonth;
        }

        //When entry is current date entry & it is not the first entry i.e. entries are already present in the list
        //So calculate the newMonth for last entry as month or year could have changed & if not then set it blank
        if (lastEntry != null) {
            String lastEntryMonth = (String) DateFormat.format("MMMM", lastEntry.getEntryTime());
            String lastEntryYear = (String) DateFormat.format("yyyy", lastEntry.getEntryTime());

            if (entry.getEntryMonth() > lastEntry.getEntryMonth())
                lastEntry.setNewMonth(lastEntryMonth + "/" + lastEntryYear);
            else if (entry.getEntryYear() > lastEntry.getEntryYear())
                lastEntry.setNewMonth(lastEntryMonth + "/" + lastEntryYear);
            else
                lastEntry.setNewMonth("");

            lastEntry.setLatestEntry(false);
            updateEntry(lastEntry);
        }

        return month + "/" + year;
    }
	
    private String getNewMonth2(Entry entry, Entry lastEntry) {
        Date date = new Date();
        String month = (String) DateFormat.format("MMMM", date);
        String year = (String) DateFormat.format("yyyy", date);
	    
	
	if(lastEntry== null){//first entry in the db
		return month/year;
	}
	else if(lastEntry.getMonth() == ""){//entry is in the middle where last entry's header is blank
		reurn ""
	}
	else{//not correct logic? please verify, in this logic the previous entry's newMonth is being set considering the logic that current entry's is 
	     //having new month or year. OR simply put this is the case when current entry is insertion where its month is greater than prev entry 
	     //ie.first entry of a new month
		
	     //this is the case where we compare whether the currnet entry year/month > last entry's year/month
	    String lastEntryMonth = (String) DateFormat.format("MMMM", lastEntry.getEntryTime());
            String lastEntryYear = (String) DateFormat.format("yyyy", lastEntry.getEntryTime());

            if (entry.getEntryMonth() > lastEntry.getEntryMonth())
                lastEntry.setNewMonth(lastEntryMonth + "/" + lastEntryYear);
            else if (entry.getEntryYear() > lastEntry.getEntryYear())
                lastEntry.setNewMonth(lastEntryMonth + "/" + lastEntryYear);
            else
                lastEntry.setNewMonth("");

            lastEntry.setLatestEntry(false);
            updateEntry(lastEntry);
	}
	 

        //Entry back date, fetch newMonth of the last entry and mark it blank for last entry as the new would be 
        //the entry on top of last entry. Return the same
        if (!isCurrentDateEntry /*|| !isTopEntry(entry)*/) {
            String newMonth = lastEntry.getNewMonth();
            lastEntry.setNewMonth("");
            lastEntry.setLatestEntry(false);
            updateEntry(lastEntry);
            return newMonth;
        }

        //When entry is current date entry & it is not the first entry i.e. entries are already present in the list
        //So calculate the newMonth for last entry as month or year could have changed & if not then set it blank
        if (lastEntry != null) {
            String lastEntryMonth = (String) DateFormat.format("MMMM", lastEntry.getEntryTime());
            String lastEntryYear = (String) DateFormat.format("yyyy", lastEntry.getEntryTime());

            if (entry.getEntryMonth() > lastEntry.getEntryMonth())
                lastEntry.setNewMonth(lastEntryMonth + "/" + lastEntryYear);
            else if (entry.getEntryYear() > lastEntry.getEntryYear())
                lastEntry.setNewMonth(lastEntryMonth + "/" + lastEntryYear);
            else
                lastEntry.setNewMonth("");

            lastEntry.setLatestEntry(false);
            updateEntry(lastEntry);
        }

        return month + "/" + year;
    }

    public int updateEntry(Entry entry) {
        return entryDao.updateEntry(entry);
    }

    private float getBalance(Entry entry, Entry lastEntry) {
        if (lastEntry != null) {
            if (entry.isDebit())
                return lastEntry.getBalance() - entry.getAmount();
            else
                return lastEntry.getBalance() + entry.getAmount();
        } else {
            if (entry.isDebit()) {
                return 0;
            } else
                return entry.getAmount();
        }
    }

    public LiveData<Integer> getAllTabsWithName(Tab tab) {
        tabNameCount = entryDao.getAllTabsWithName(tab.getTabName());
        return tabNameCount;
    }

    public void deleteEntry(Entry entry) {
        Entry prevEntry = getPrevEntryByTabIdAndDate(entry.getTabId(), entry.getEntryTime());
        entryDao.deleteEntry(entry);
        if (entry.isLatestEntry()) {
            updatePrevEntry(entry, prevEntry, true);
        } else {
            if (entry.getNewMonth() != null)
                updatePrevEntry(entry, prevEntry, false);
            updateBalanceOfEntriesAfterInsertion(prevEntry);
        }

    }

    private void updatePrevEntry(Entry entry, Entry prevEntry, boolean setLatestEntry) {
        if (prevEntry.getNewMonth().equals(""))
            prevEntry.setNewMonth(entry.getNewMonth());

        prevEntry.setLatestEntry(setLatestEntry);
        updateEntry(prevEntry);
    }

    private class insertAynscTask extends AsyncTask<Entry, Void, Long> {

        @Override
        protected Long doInBackground(Entry... entries) {
            return entryDao.insert(entries[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            insertCompleteListener.onInsertComplete(isCurrentDateEntry);
        }
    }

    private class updateAynscTask extends AsyncTask<Entry, Void, Integer> {

        @Override
        protected Integer doInBackground(Entry... entries) {
            return updateEntry(entries[0]);
        }

        @Override
        protected void onPostExecute(Integer aLong) {
            super.onPostExecute(aLong);
            updateCompleteListener.onUpdateComplete(isCurrentDateEntry);
        }
    }

    public LiveData<List<Entry>> getListAllEntryByTabId(int tabId) {
        listEntriesById = entryDao.getListAllEntryByTabId(tabId);
        return listEntriesById;
    }

    public Entry getLastEntryByTabId(int tabId) {
        return entryDao.getLastEntryById(tabId);
    }

    public Entry getLastEntryByIdForUpdateCase(int tabId) {
        return entryDao.getLastEntryByIdForUpdateCase(tabId);
    }

    private Entry getLastEntryByTabIdAndDate(int tabId, String dateString) {
        return entryDao.getLastEntryByTabIdAndDate(tabId, dateString);
    }

    private Entry getPrevEntryByTabIdAndDate(int tabId, long entryTime) {
        return entryDao.getPrevEntryByTabIdAndDate(tabId, entryTime);
    }

    private Entry getLastEntryByTabIdAndDateForSameAndDateUpdateCase(int tabId, int id, String dateString) {
        return entryDao.getLastEntryByTabIdAndDateForSameAndDateUpdateCase(tabId, id, dateString);
    }

    private Entry getLastEntryByTabIdAndDateForNotSameAndDateUpdateCase(int tabId, int id, long entryTime) {
        return entryDao.getLastEntryByTabIdAndDateForNotSameAndDateUpdateCase(tabId, id, entryTime);
    }

    private long setBackDateEntryDateTime(Entry entry, Entry lastEntry) {
        long time;

        if (lastEntry.getDateString().equals(entry.getDateString()))
            time = lastEntry.getEntryTime() + 1000;// i.e add 1 sec to the dateTime of the last fetched entry so that the entry is below the last entry of the date
        else
            time = Utils.getDateInMiliSecsForBackEntry(entry.getDateString());//i.e there is no entry for given date, make the selected dateTime as entry time

        return time;
    }

    private boolean isTopEntry(Entry entry) {
        Entry fetchedEntry = entryDao.getLastEntryById(entry.getTabId());
        if (entry.getId() == fetchedEntry.getId())
            return true;
        else
            return false;
    }

    public interface InsertCompleteListener {
        void onInsertComplete(boolean isCurrentDateEntry);
    }

    public interface UpdateCompleteListener {
        void onUpdateComplete(boolean isCurrentDateEntry);
    }

    private void updateBalanceOfEntriesAfterInsertion(Entry entry) {
        Entry oneUpEntry = entryDao.getOneUpEntryByDateTime(entry.getTabId(), entry.getEntryTime());

        if (oneUpEntry != null) {
            oneUpEntry.setBalance(getBalance(oneUpEntry, entry));
            updateEntry(oneUpEntry);
            updateBalanceOfEntriesAfterInsertion(oneUpEntry);
        }
    }


}
