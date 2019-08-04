package com.rr.project.myapplication.dao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.rr.project.myapplication.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "entry_table",
        foreignKeys = @ForeignKey(
                entity = Tab.class,
                parentColumns = "id",
                childColumns = "tabId"))

public class Entry implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int tabId;
    private String note;
    private float amount;
    private boolean isDebit;
    private float balance;
    private long entryTime;
    private String dateString;
    private int entryMonth;
    private int entryYear;
    private String newMonth;
    private boolean isLatestEntry;

    public Entry() {
    }

    public Entry(String note, float amount, boolean isDebit, int tabId, long entryTime, String dateString, boolean isLatestEntry) {
        this.note = note;
        this.amount = amount;
        this.isDebit = isDebit;
        this.tabId = tabId;
        this.entryTime = entryTime;
        this.dateString = dateString;
        this.entryMonth = Utils.getMonthFromMilisecs(entryTime);
        this.entryYear = Utils.getYearFromMilisecs(entryTime);
        this.isLatestEntry = isLatestEntry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public boolean isDebit() {
        return isDebit;
    }

    public void setDebit(boolean debit) {
        isDebit = debit;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getNewMonth() {
        return newMonth;
    }

    public void setNewMonth(String newMonth) {
        this.newMonth = newMonth;
    }

    public String getDateFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        return formatter.format(new Date(entryTime));
    }

    public String getBalanceAsString() {
        return Utils.removeTrailingZeros(String.valueOf(balance));
    }

    public String getAmountAsString() {
        return Utils.removeTrailingZeros(String.valueOf(amount));
    }

    public int getEntryMonth() {
        return entryMonth;
    }

    public void setEntryMonth(int entryMonth) {
        this.entryMonth = entryMonth;
    }

    public int getEntryYear() {
        return entryYear;
    }

    public void setEntryYear(int entryYear) {
        this.entryYear = entryYear;
    }

    public boolean isLatestEntry() {
        return isLatestEntry;
    }

    public void setLatestEntry(boolean latestEntry) {
        isLatestEntry = latestEntry;
    }

    // In the vast majority of cases you can simply return 0 for this.
    // There are cases where you need to use the constant `CONTENTS_FILE_DESCRIPTOR`
    // But this is out of scope of this tutorial
    @Override
    public int describeContents() {
        return 0;
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(tabId);
        dest.writeString(note);
        dest.writeFloat(amount);
        dest.writeByte((byte) (isDebit ? 1 : 0));
        dest.writeFloat(balance);
        dest.writeLong(entryTime);
        dest.writeString(dateString);
        dest.writeInt(entryMonth);
        dest.writeInt(entryYear);
        dest.writeString(newMonth);
        dest.writeByte((byte) (isLatestEntry ? 1 : 0));
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    protected Entry(Parcel in) {
        id = in.readInt();
        tabId = in.readInt();
        note = in.readString();
        amount = in.readFloat();
        isDebit = in.readByte() != 0;
        balance = in.readFloat();
        entryTime = in.readLong();
        dateString = in.readString();
        entryMonth = in.readInt();
        entryYear = in.readInt();
        newMonth = in.readString();
        isLatestEntry = in.readByte() != 0;
    }
}
