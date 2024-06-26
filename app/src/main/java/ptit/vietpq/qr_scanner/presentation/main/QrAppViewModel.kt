package com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class QrAppViewModel @Inject constructor(
) : ViewModel() {

    private val _stateLanguage: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val stateLoading: StateFlow<Boolean> = _stateLanguage.asStateFlow()

}
