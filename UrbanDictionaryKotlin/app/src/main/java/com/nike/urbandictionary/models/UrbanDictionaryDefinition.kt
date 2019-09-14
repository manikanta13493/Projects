package com.nike.urbandictionary.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "urbandictionary")
data class UrbanDictionaryDefinition(@ColumnInfo(name = "definition")
                                @SerializedName("definition") val wordDefinition: String,
                                @ColumnInfo(name = "thumbs_up")
                                @SerializedName("thumbs_up") val thumbsUpCount: String,
                                @ColumnInfo(name = "thumbs_down")
                                @SerializedName("thumbs_down") val thumbsDownCount: String,
                                @PrimaryKey
                                @ColumnInfo(name = "word")
                                @SerializedName("word")
                                 val word: String) : Parcelable
