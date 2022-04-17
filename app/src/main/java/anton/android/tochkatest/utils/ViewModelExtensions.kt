import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun List<Pair<LiveData<Boolean>, () -> Unit>>.observeAllBooleans(
    lifecycleOwner: LifecycleOwner,
) {

    this.forEach { map ->
        val liveData = map.first
        val func = map.second
        liveData.observe(lifecycleOwner) {
            if (liveData.value == true) func()
        }
    }
}