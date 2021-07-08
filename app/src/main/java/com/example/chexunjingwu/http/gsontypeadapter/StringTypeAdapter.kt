package com.example.chexunjingwu.http.gsontypeadapter

import android.util.Log
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class StringTypeAdapter : TypeAdapter<String>() {
    override fun write(out: JsonWriter?, value: String?) {
        try {
            if (value == null) {
                out?.nullValue()
                return
            }
            out?.value(value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun read(`in`: JsonReader?): String {
        try {
            if (`in`!!.peek() == JsonToken.NULL) {
                `in`.nextNull()
                Log.e("TypeAdapter", "null is not a string")
                return ""
            }
        } catch (e: Exception) {
            Log.e("TypeAdapter", "Not a String", e)
        }
        return `in`!!.nextString()
    }

//    @Throws(IOException::class)
//    override fun write(out: JsonWriter, value: String?) {
//        try {
//            if (value == null) {
//                out.nullValue()
//                return
//            }
//            out.value(value)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    @Throws(IOException::class)
//    override fun read(`in`: JsonReader): String? {
//        try {
//            if (`in`.peek() == JsonToken.NULL) {
//                `in`.nextNull()
//                Log.e("TypeAdapter", "null is not a string")
//                return ""
//            }
//        } catch (e: Exception) {
//            Log.e("TypeAdapter", "Not a String", e)
//        }
//        return `in`.nextString()
//    }
}