package com.wmods.wppenhacer.auth

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.GoogleAuthUtil

object AuthHelper {
    /**
     * Fetch an OAuth access token for the given accountName and scopes.
     * Note: This runs synchronously; callers should invoke from a background thread.
     */
    fun fetchAuthToken(context: Context, accountName: String, scopes: List<String>): String? {
        return try {
            val scopeStr = "oauth2:" + scopes.joinToString(" ")
            GoogleAuthUtil.getToken(context, accountName, scopeStr)
        } catch (e: Exception) {
            Log.e("AuthHelper", "Failed to get token", e)
            null
        }
    }
}
