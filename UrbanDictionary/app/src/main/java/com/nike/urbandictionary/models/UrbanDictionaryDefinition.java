package com.nike.urbandictionary.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

@Entity(tableName = "urbandictionary")
public class UrbanDictionaryDefinition implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    @SerializedName("word")
    private String word;
    @ColumnInfo(name = "definition")
    @SerializedName("definition")
    private String wordDefinition;
    @ColumnInfo(name = "thumbs_up")
    @SerializedName("thumbs_up")
    private String thumbsUpCount;
    @ColumnInfo(name = "thumbs_down")
    @SerializedName("thumbs_down")
    private String thumbsDownCount;

    public UrbanDictionaryDefinition(String wordDefinition, String thumbsUpCount, String thumbsDownCount, String word) {
        this.wordDefinition = wordDefinition;
        this.thumbsUpCount = thumbsUpCount;
        this.thumbsDownCount = thumbsDownCount;
        this.word = word;
    }

    public void setWordDefinition(String wordDefinition) {
        this.wordDefinition = wordDefinition;
    }

    public void setThumbsUpCount(String thumbsUpCount) {
        this.thumbsUpCount = thumbsUpCount;
    }

    public void setThumbsDownCount(String thumbsDownCount) {
        this.thumbsDownCount = thumbsDownCount;
    }

    public void setWord(String word) {
        this.word = word;
    }

    protected UrbanDictionaryDefinition(Parcel in) {
        wordDefinition = in.readString();
        thumbsUpCount = in.readString();
        thumbsDownCount = in.readString();
        word = in.readString();
    }

    public static final Creator<UrbanDictionaryDefinition> CREATOR = new Creator<UrbanDictionaryDefinition>() {
        @Override
        public UrbanDictionaryDefinition createFromParcel(Parcel in) {
            return new UrbanDictionaryDefinition(in);
        }

        @Override
        public UrbanDictionaryDefinition[] newArray(int size) {
            return new UrbanDictionaryDefinition[size];
        }
    };

    public String getWordDefinition() {
        return wordDefinition;
    }

    public String getThumbsUpCount() {
        return thumbsUpCount;
    }

    public String getThumbsDownCount() {
        return thumbsDownCount;
    }

    public String getWord() {
        return word;
    }

    public static Comparator<UrbanDictionaryDefinition> COMPARE_BY_THUMBSUP = new Comparator<UrbanDictionaryDefinition>() {
        public int compare(UrbanDictionaryDefinition one, UrbanDictionaryDefinition other) {
            return Integer.compare(Integer.parseInt(other.thumbsUpCount), Integer.parseInt(one.thumbsUpCount));
        }
    };

    public static Comparator<UrbanDictionaryDefinition> COMPARE_BY_THUMBSDOWN = new Comparator<UrbanDictionaryDefinition>() {
        public int compare(UrbanDictionaryDefinition one, UrbanDictionaryDefinition other) {
            return Integer.compare(Integer.parseInt(other.thumbsDownCount), Integer.parseInt(one.thumbsDownCount));
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(wordDefinition);
        parcel.writeString(thumbsUpCount);
        parcel.writeString(thumbsDownCount);
        parcel.writeString(word);
    }
}
