package com.sam.khabri.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sam.khabri.data.model.response.Article
import java.lang.reflect.Type

class TypeConverter {

    @TypeConverter
    fun fromSource(source: Article.Source?): String? {
        if (source == null) return null

        val gson = Gson()
        val type: Type = object :
            TypeToken<Article.Source>() {}.type
        return gson.toJson(source, type)
    }

    @TypeConverter
    fun toSource(source: String?) : Article.Source?{
        if (source == null) return null

        val gson = Gson()
        val type : Type = object :
            TypeToken<Article.Source>(){}.type
        return gson.fromJson(source, type)
    }

}