package com.mdrapp.de.data.model.pref

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class BikeCategoriesPref(
    @SerializedName("ids") val ids: List<String>
)

fun String.toCategoryList(): List<String>? = Gson().fromJson(this, BikeCategoriesPref::class.java)?.ids

fun List<String>.toBikeCategoriesPref(): String = Gson().toJson(BikeCategoriesPref(ids = this))
