@file:OptIn(ExperimentalMaterial3Api::class)

package com.sekthdroid.projek.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sekthdroid.projek.template.ui.screens.character.CharacterDetailScreen
import com.sekthdroid.projek.template.ui.screens.characters.CharactersScreen
import com.sekthdroid.projek.template.ui.theme.ProjektTemplateTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektTemplateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "characters"
                    ) {
                        composable("characters") {
                            CharactersScreen(
                                viewModel = koinViewModel(),
                                onCharacterClicked = {
                                    println("Navigation clicked")
                                    navController.navigate("character/${it}")
                                }
                            )
                        }
                        composable(
                            route = "character/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType }),
                            enterTransition = {
                                slideInHorizontally { it }
                            },
                            exitTransition = {
                                slideOutHorizontally { -it }
                            },
                            popEnterTransition = {
                                slideInHorizontally { -it }
                            },
                            popExitTransition = {
                                slideOutHorizontally { it }
                            },
                        ) {
                            CharacterDetailScreen(
                                viewModel = koinViewModel(),
                                onBackPressed = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjektTemplateTheme {
        Greeting("Android")
    }
}