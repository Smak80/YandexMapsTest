package ru.smak.mapstest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import ru.smak.mapstest.ui.theme.MapsTestTheme

class MainActivity : ComponentActivity() {

    private val mvm by lazy {
        ViewModelProvider(this)[MapViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
        setContent {
            MapsTestTheme {
                MainUI (Modifier.fillMaxSize()){
                    YaMap(Modifier.fillMaxSize())
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    modifier: Modifier = Modifier,
    content: @Composable ()->Unit,
){
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(stringResource(id = R.string.title))
            })
        }
    ) {
        Column(Modifier.padding(it)) {
            content()
        }
    }
}

@Composable
fun YaMap(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier) {
        AndroidView(factory = {
            MapView(it).apply {
                //onStart()
                mapWindow.map.move(
                    CameraPosition(
                        Point(55.7921, 49.1222),
                        17.0f,
                        0.0f,
                        30.0f
                    )
                )
            }
        })
    }
}