package teka.android.organiks_platform_android.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.screens.MultiTurnScreen
import teka.android.organiks_platform_android.presentation.feature_ai_assistant.presentation.screens.GeminiAnalystScreen
import teka.android.organiks_platform_android.presentation.feature_dashborad.DashboardScreen
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.profile.ProfileScreen
import teka.android.organiks_platform_android.presentation.feature_home.HomeScreen
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionHome.ProductionHomeScreen
import teka.android.organiks_platform_android.presentation.feature_records.screens.productionRecording.ProductionRecordingScreen
import teka.android.organiks_platform_android.presentation.feature_records.screens.remoteRecords.RemoteRecordsScreen
import teka.android.organiks_platform_android.presentation.feature_settings.SettingsScreen
import teka.android.organiks_platform_android.presentation.module_customers.form.AddCustomerForm
import teka.android.organiks_platform_android.presentation.module_fruits.form.FruitRecordingForm
import teka.android.organiks_platform_android.presentation.module_customers.list_screen.CustomersListScreen
import teka.android.organiks_platform_android.presentation.module_invoice.create_invoice.CreateInvoiceScreen
import teka.android.organiks_platform_android.presentation.module_fruits.list_screen.FruitRecordsListScreen
import teka.android.organiks_platform_android.presentation.module_invoice.invoice_list_screen.InvoiceListScreen
import teka.android.organiks_platform_android.presentation.module_invoice.pdf_viewer.PDFViewerScreen
import teka.android.organiks_platform_android.ui.animations.scaleIntoContainer
import teka.android.organiks_platform_android.ui.animations.scaleOutOfContainer


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainNavGraph(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
        route = MAIN_GRAPH_ROUTE
    ) {

       composable(
           route = AppScreens.FirebaseProfileAppScreens.route,
       ) {
           ProfileScreen(navController = navController)
       }

        composable(
            route = AppScreens.HomeScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

        ){
            HomeScreen(
                navController = navController
            )
        }


        composable(
            route = AppScreens.ProductionHome.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

        ){
            ProductionHomeScreen(
                navController = navController,
                onNavigate = { id ->
                    navController.navigate(route = "${AppScreens.ProductionRecording.route}?id=$id")
                }
            )
        }

        composable(
            route = AppScreens.RemoteRecordsScreens.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

        ){
            RemoteRecordsScreen(
                onNavigate = { id ->
                    navController.navigate(
                        route = "${AppScreens.ProductionRecording.route}?id=$id"
                    )
                },
                navController = navController
            )
        }

        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }
        composable(
            route = AppScreens.FruitRecordingForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            FruitRecordingForm(
                navController = navController
            )
        }

        composable(
            route = AppScreens.CreateInvoiceScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            },
            arguments = listOf(
                navArgument("fruitCollectionId") { type = NavType.StringType },
            )
        ){
            CreateInvoiceScreen(
                navController = navController
            )
        }

        composable(
            route = AppScreens.PDFViewerScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            },
            arguments = listOf(
                navArgument("filePath") { type = NavType.StringType },
            )
        ){ backStackEntry ->
//            val filePath = backStackEntry.arguments?.getString("filePath") ?: return@composable
            val filePath = backStackEntry.arguments?.getString("filePath")?.let { Uri.decode(it) } ?: return@composable

            PDFViewerScreen(
                filePath = filePath,
                navController = navController
            )
        }

        composable(
            route = AppScreens.InvoiceListScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            InvoiceListScreen(
                navController = navController
            )
        }


        composable(
            route = AppScreens.AddCustomerForm.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

        ){
            AddCustomerForm(
                navController = navController
            )
        }

        composable(
            route = AppScreens.FruitListScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

        ){
            FruitRecordsListScreen(
                navController = navController
            )
        }

        composable(
            route = AppScreens.CustomerListScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

        ){
            CustomersListScreen(
                navController = navController
            )
        }

        composable(
            route = "${ AppScreens.ProductionRecording.route }?id={id}",
            arguments = listOf(navArgument("id"){type = NavType.IntType}),
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }

        ){
            val id = it.arguments?.getInt("id") ?: -1

            ProductionRecordingScreen(
                id = id,
                navController = navController,
            )
        }


        composable(
            route = AppScreens.DashboardAppScreens.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
            DashboardScreen(
                navController = navController
            )
        }

        composable(
            route = AppScreens.GeminiChatAppScreens.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ){
//            GeminiChatScreen()
            MultiTurnScreen()
        }

        composable(
            route = AppScreens.GeminiAnalystAppScreens.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            },
            arguments = listOf(
                navArgument("farmerDataId") { type = NavType.IntType },
                navArgument("autoGenerate") { type = NavType.BoolType; defaultValue = false }
            )
        ){ backStackEntry ->
            val farmerDataId = backStackEntry.arguments?.getInt("farmerDataId") ?: 1
            val autoGenerate = backStackEntry.arguments?.getBoolean("autoGenerate") ?: false


            GeminiAnalystScreen(
                farmerDataId = farmerDataId,
                autoGenerate = autoGenerate
            )
        }

        composable(
            route = AppScreens.ProfileAppScreens.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
            ){
            SettingsScreen()
        }
    }
}