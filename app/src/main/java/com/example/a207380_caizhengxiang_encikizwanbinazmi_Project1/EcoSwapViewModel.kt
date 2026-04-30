package com.example.a207380_caizhengxiang_encikizwanbinazmi_Project1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ================= 数据类 =================
data class SwapItemState(
    val itemName: String = "",
    val itemCondition: String = ""
)

// ================= ViewModel =================
class EcoSwapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SwapItemState())
    val uiState: StateFlow<SwapItemState> = _uiState.asStateFlow()

    fun updateItemDetails(name: String, condition: String) {
        _uiState.value = SwapItemState(itemName = name, itemCondition = condition)
    }//更新商品详情数据
}