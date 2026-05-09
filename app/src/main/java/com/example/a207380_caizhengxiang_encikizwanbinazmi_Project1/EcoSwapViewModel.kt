package com.example.a207380_caizhengxiang_encikizwanbinazmi_Project1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class SwapItemState(
    val itemName: String = "",
    val itemCondition: String = ""
)

class EcoSwapViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SwapItemState())
    val uiState: StateFlow<SwapItemState> = _uiState.asStateFlow()


    private val _itemList = MutableStateFlow<List<SwapItemState>>(emptyList())
    val itemList: StateFlow<List<SwapItemState>> = _itemList.asStateFlow()


    fun updateItemDetails(name: String, condition: String) {
        _uiState.value = SwapItemState(name, condition)
    }


    fun submitCurrentItem() {
        if (_uiState.value.itemName.isNotBlank()) {
            _itemList.value = _itemList.value + _uiState.value
            _uiState.value = SwapItemState() // 清空，方便下次添加
        }
    }
}