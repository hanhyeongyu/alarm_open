package com.example.template.core.auth

import com.example.template.core.auth.model.OAuthToken
import com.example.template.core.preference.PrefKey
import com.example.template.core.preference.PreferenceKeys
import com.example.template.core.preference.PreferenceStorage


val PreferenceKeys.Token: PreferenceStorage.Key<OAuthToken?>
    get() = PrefKey<OAuthToken?>(name = "stringKey", defaultValue = null, encrypted = true)
