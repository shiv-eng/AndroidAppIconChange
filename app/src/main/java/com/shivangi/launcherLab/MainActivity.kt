package com.shivangi.launcherLab

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.core.content.edit
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.shivangi.launcherLab.ui.theme.LauncherLabTheme


enum class AppLauncherIcons(val themeName: String, val imageRes: Int) {
    Default_Theme(".DefaultTheme",    R.mipmap.ic_launcher),
    Theme_One     (".MyIconOne",      R.mipmap.my_launch_icon_one),
    Theme_Two     (".MyIconTwo",      R.mipmap.my_launch_icon_two),
    Theme_Three   (".MyIconThree",    R.mipmap.my_launch_icon_three),
}

const val CURRENT_ICON_KEY = "CURRENT_ICON_KEY"
const val PREFERENCE_NAME  = "MY_APP_SHARED_PREF"

class MainActivity : ComponentActivity() {

    private val sharedPref by lazy {
        getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LauncherLabTheme {
                MyApp(onChangeAppIcon = { icon ->
                    val currentIcon = sharedPref.getString(
                        CURRENT_ICON_KEY,
                        AppLauncherIcons.Default_Theme.themeName
                    )
                    if (currentIcon == icon.themeName) return@MyApp
                    changeIcon(icon, currentIcon!!)
                })
            }
        }
    }

    private fun changeIcon(selectedIcon: AppLauncherIcons, currentIcon: String) {
        // 1) Enable new selected icon
        packageManager.setComponentEnabledSetting(
            ComponentName(this, "$packageName${selectedIcon.themeName}"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        // 2) Disable or default the old one
        val oldState = if (currentIcon == AppLauncherIcons.Default_Theme.themeName)
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        else
            PackageManager.COMPONENT_ENABLED_STATE_DEFAULT

        packageManager.setComponentEnabledSetting(
            ComponentName(this, "$packageName$currentIcon"),
            oldState,
            PackageManager.DONT_KILL_APP
        )
        // 3) Store in SharedPreferences
        sharedPref.edit {
            putString(CURRENT_ICON_KEY, selectedIcon.themeName)
            apply()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun MyApp(onChangeAppIcon: (AppLauncherIcons) -> Unit) {
  
    var selectedIcon by rememberSaveable { mutableStateOf(AppLauncherIcons.Default_Theme) }

    //bottomsheet
    val infoSheetState = rememberModalBottomSheetState()
    var showInfoSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Launcher Lab",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Tap on the icon below to change the app icon.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    GlideImage(
                        model = selectedIcon.imageRes,
                        contentDescription = "Selected Icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                onChangeAppIcon(selectedIcon)
                                showInfoSheet = true
                            }
                    )
                }

                // Slider with dot indicator at the bottom
                IconSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    selectedIcon = selectedIcon,
                    onIconTap = { icon ->
                        selectedIcon = icon
                    }
                )
            }

            // Bottom sheet just to notify user that icon is set
            if (showInfoSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showInfoSheet = false },
                    sheetState = infoSheetState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Icon changed successfully!",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        GlideImage(
                            model = selectedIcon.imageRes,
                            contentDescription = "Selected Icon",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(120.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { showInfoSheet = false },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("OK", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IconSlider(
    modifier: Modifier,
    selectedIcon: AppLauncherIcons,
    onIconTap: (AppLauncherIcons) -> Unit
) {
    val icons = listOf(
        AppLauncherIcons.Default_Theme,
        AppLauncherIcons.Theme_One,
        AppLauncherIcons.Theme_Two,
        AppLauncherIcons.Theme_Three
    )
    val selectedIndex = icons.indexOf(selectedIcon).coerceAtLeast(0)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Slider
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(icons) { _, icon ->
                GlideImage(
                    model = icon.imageRes,
                    contentDescription = icon.themeName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onIconTap(icon)
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Dot indicator
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            icons.forEachIndexed { i, _ ->
                val dotColor = if (i == selectedIndex) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.LightGray
                }
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(dotColor)
                )
            }
        }
    }
}
