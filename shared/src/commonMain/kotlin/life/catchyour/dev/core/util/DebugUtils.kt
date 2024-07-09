package life.catchyour.dev.core.util

import androidx.compose.ui.Modifier
import life.catchyour.dev.core.compose.viewmodel.TimeCapsule
import life.catchyour.dev.core.compose.viewmodel.VMState

fun Modifier.debugInputPointer(
   // context: Context,
    timeTravelCapsule: TimeCapsule<out VMState>,
): Modifier {
//    return if (BuildConfig.DEBUG) {
//        this.pointerInput(Unit) {
//            detectTapGestures(
//                onLongPress = {
//                    showDebugAlertDialog(context, timeTravelCapsule)
//                }
//            )
//        }
//    } else this
    return this
}

//private fun showDebugAlertDialog(
//    context: Context,
//    timeTravelCapsule: TimeCapsule<out VMState>,
//) {
//    val alertDialogBuilder = AlertDialog.Builder(context, R.style.DebugDialogTheme)
//    val adapter = ArrayAdapter<DebugState>(context, R.layout.debug_menu_item)
//    adapter.addAll(timeTravelCapsule.getStates().mapIndexed(::DebugState))
//    alertDialogBuilder.setAdapter(adapter) { dialog, which ->
//        timeTravelCapsule.selectState(which)
//    }
//
//    alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
//        dialog.dismiss()
//    }
//
//    alertDialogBuilder.setPositiveButton("Ok") { dialog, which ->
//        dialog.dismiss()
//    }
//
//    val dialog = alertDialogBuilder.create()
//    dialog.show()
//}

private data class DebugState(val index: Int, val state: VMState) {
    override fun toString(): String {
        return "${index + 1}. $state"
    }
}