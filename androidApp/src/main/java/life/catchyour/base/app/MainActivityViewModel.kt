package life.catchyour.base.app

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel constructor(
) : ViewModel() {


    private val mutableUiState = MutableStateFlow(MainViewState())
    val uiState: StateFlow<MainViewState> = mutableUiState
}


data class MainViewState(
    val loading: Boolean = false
)