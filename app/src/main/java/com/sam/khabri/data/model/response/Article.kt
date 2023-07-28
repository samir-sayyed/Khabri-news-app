package com.sam.khabri.data.model.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
) : Parcelable {
    @Parcelize
    data class Source(
        val id: String? = null,
        val name: String? = null
    ) : Parcelable
}
