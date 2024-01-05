package cz.dpalecek.catfact.androidCatFactApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import cz.dpalecek.catfact.core.design.theme.CatFactTheme
import cz.dpalecek.catfact.core.feature.destinations
import cz.dpalecek.catfact.core.navigation.domain.DestinationRepository
import cz.dpalecek.catfact.core.navigation.domain.NavigationConfiguration
import cz.dpalecek.catfact.core.navigation.domain.ScreenAdapter
import cz.dpalecek.catfact.core.navigation.view.NavHostCAT
import cz.dpalecek.catfact.feature.catFact.domain.CatFactDestination
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CatFactAppActivity : ComponentActivity(), KoinComponent {

    private val screenAdapter: ScreenAdapter by inject()
    private val destinationRepository: DestinationRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            CatFactTheme {
                val configuration = remember {
                    NavigationConfiguration(
                        initialDestination = CatFactDestination.MAIN_SCREEN,
                        destinations = destinations,
                        screenAdapter = screenAdapter,
                        repository = destinationRepository,
                        onEmptyStack = { finish() },
                    )
                }
                NavHostCAT(configuration = configuration)
            }
        }
    }
}
