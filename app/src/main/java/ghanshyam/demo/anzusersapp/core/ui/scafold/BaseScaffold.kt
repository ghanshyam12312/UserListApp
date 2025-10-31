package ghanshyam.demo.anzusersapp.core.ui.scafold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable (() -> Unit)? = null,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = { topBar?.invoke() },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        content = content
    )
}