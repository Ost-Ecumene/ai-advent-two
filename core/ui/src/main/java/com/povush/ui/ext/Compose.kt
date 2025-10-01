package com.povush.ui.ext

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope

@Composable
public fun <T> rememberMutableStateOf(value: T): MutableState<T> = remember {
    mutableStateOf(value)
}

@Composable
public inline fun rememberLambda(
    crossinline lambda: () -> Unit
): () -> Unit = remember {
    { lambda.invoke() }
}

@Composable
public inline fun rememberLambda(
    key: Any?,
    crossinline lambda: () -> Unit
): () -> Unit = remember(key) {
    { lambda.invoke() }
}

@Composable
public fun <T> MutableState<T>.launchedBy(byValue: T): MutableState<T> {
    LaunchedEffect(byValue) {
        value = byValue
    }
    return this
}

@Composable
public inline fun <T> T.launched(crossinline block: suspend CoroutineScope.(T) -> Unit) {
    LaunchedEffect(key1 = this) {
        block(this@launched)
    }
}

@Composable
public inline fun <T> T?.launchedNotNull(crossinline block: suspend CoroutineScope.(T) -> Unit) {
    LaunchedEffect(key1 = this) {
        this@launchedNotNull?.let {
            block(it)
        }
    }
}

@Composable
public fun <T> rememberDerivedStateOf(calculation: () -> T): State<T> = remember {
    derivedStateOf(calculation)
}

@Composable
public fun <T> rememberMutableListOfItems(vararg items: T): SnapshotStateList<T> = remember {
    SnapshotStateList<T>().also { it.addAll(items.toList()) }
}

@Composable
public fun <T> rememberMutableListOf(list: List<T> = listOf()): SnapshotStateList<T> = remember {
    list.toMutableStateList()
}

@Composable
public fun <K, V> rememberMutableMapOf(vararg pairs: Pair<K, V>): SnapshotStateMap<K, V> = remember {
    SnapshotStateMap<K, V>().apply { putAll(pairs.toMap()) }
}

@Composable
public fun <K, V> rememberMutableMapOf(map: Map<K, V>): SnapshotStateMap<K, V> = remember {
    SnapshotStateMap<K, V>().also { it.putAll(map.toMap()) }
}

@Composable
public fun <T> rememberStateOf(value: T): State<T> = remember {
    mutableStateOf(value)
}

@Composable
public fun <T> rememberSaveableMutableStateOf(
    value: T,
    key: String? = null
): MutableState<T> = rememberSaveable(
    key = key
) {
    mutableStateOf(value)
}

@Composable
public fun rememberInteractionSource(): MutableInteractionSource = remember {
    MutableInteractionSource()
}

@Composable
public fun Dp.px(): Float = LocalDensity.current.run { this@px.toPx() }

@Composable
public fun Dp.roundPx(): Int = LocalDensity.current.run { this@roundPx.roundToPx() }

@Composable
public fun Dp.shape() = RoundedCornerShape(this)

public fun Density.px(dp: Dp): Float = run { dp.toPx() }
public fun Density.roundPx(dp: Dp): Int = run { dp.roundToPx() }

@Composable
public fun statusBarHeight(): Dp = WindowInsets.statusBars.only(
    WindowInsetsSides.Top
).asPaddingValues().calculateTopPadding()

@Composable
public fun navigationBarHeight(): Dp = WindowInsets.navigationBars.only(
    WindowInsetsSides.Bottom
).asPaddingValues().calculateBottomPadding()

@Composable
public fun LaunchOnStarted(block: suspend CoroutineScope.() -> Unit) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            block()
        }
    }
}

@Composable
public fun LaunchOnDestroyed(block: suspend CoroutineScope.() -> Unit) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.DESTROYED) {
            block()
        }
    }
}

@Composable
public fun LaunchOnPause(block: () -> Unit) {
    LifecycleEventEffect(
        Lifecycle.Event.ON_PAUSE
    ) {
        block()
    }
}

@Composable
public fun LaunchDispose(block: () -> Unit) {
    DisposableEffect(Unit) {
        onDispose {
            block()
        }
    }
}

@Composable
public fun LaunchOnResumed(block: suspend CoroutineScope.() -> Unit) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
            block()
        }
    }
}

@Composable
public fun LaunchOnCreated(block: suspend CoroutineScope.() -> Unit) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(key1 = Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.CREATED) {
            block()
        }
    }
}

@Composable
public fun LaunchAlone(block: suspend CoroutineScope.() -> Unit) {
    LaunchedEffect(Unit) {
        block()
    }
}

@Composable
public fun LaunchAloneSaveable(block: suspend CoroutineScope.() -> Unit) {
    var firstLaunched by rememberSaveableMutableStateOf(false)
    LaunchedEffect(Unit) {
        if (!firstLaunched) {
            block()
            firstLaunched = true
        }
    }
}

/**
 * [block] вызывается на 2+ раз, в первый раз вызов не происходит.
 */
@Composable
public fun <T : Any> LaunchUpdate(key1: T?, block: suspend CoroutineScope.(key: T?) -> Unit) {
    var first by rememberMutableStateOf(true)

    LaunchedEffect(key1) {
        if (first) {
            first = false
            return@LaunchedEffect
        }
        block(key1)
    }
}

@Composable
public fun <T : Any> LaunchNotNull(key1: T?, block: suspend CoroutineScope.(key: T) -> Unit) {
    LaunchedEffect(key1) {
        if (key1 != null) {
            block(key1)
        }
    }
}

@Composable
public fun <T : Any> LaunchNotNullAlone(key1: T?, block: suspend CoroutineScope.(key: T) -> Unit) {
    var launched by rememberMutableStateOf(false)
    LaunchedEffect(key1) {
        if (key1 != null && !launched) {
            launched = true
            block(key1)
        }
    }
}

@Composable
public fun <T : Any> LaunchIf(key1: T?, vararg keyEquals: T, block: suspend CoroutineScope.(key: T) -> Unit) {
    LaunchedEffect(key1) {
        key1 ?: return@LaunchedEffect
        keyEquals.find {
            it == key1
        }?.let {
            block(it)
        }
    }
}
