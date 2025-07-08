package com.chch.tudoong.utils

import android.util.Log

object ComposeLogger{
    fun logd(message: String){
        val tag = getTag()
        Log.d(tag, message)
    }

    fun logi(message: String){
        val tag = getTag()
        Log.i(tag, message)
    }

    fun logw(message: String){
        val tag = getTag()
        Log.w(tag, message)
    }

    fun loge(message: String){
        val tag = getTag()
        Log.e(tag, message)
    }

    fun loge(message: String, throwable: Throwable){
        val tag = getTag()
        Log.e(tag, message, throwable)
    }

    fun getTag() : String {
        return try {
            val stackTrace = Thread.currentThread().stackTrace
            //[0]: getCallerFunctionName [1]: logd/logi/logw/loge 함수 [2]: 실제 호출한 함수 (우리가 원하는 것)
            val callerElement = stackTrace.getOrNull(5) // 실제 호출 위치
            val className = callerElement?.className ?: "Unknown"
            val methodName = callerElement?.methodName ?: "Unknown"

            val simpleClassName = className.substringAfterLast('.')

            val tag = if (simpleClassName.endsWith("Kt") || simpleClassName.contains("ComposableSingletons")) {
                methodName // Top-level function이나 Composable 함수
            } else {
                simpleClassName // 클래스 내부 함수
            }

            if(tag.length > 23){
                tag.substring(0, 23)
            } else {
                tag
            }
        } catch (e: Exception){
            "Unknown"
        }
    }
}

fun logd(message: String) = ComposeLogger.logd(message)
fun logi(message: String) = ComposeLogger.logi(message)
fun logw(message: String) = ComposeLogger.logw(message)
fun loge(message: String) = ComposeLogger.loge(message)
fun loge(message: String, throwable: Throwable) = ComposeLogger.loge(message, throwable)