package ptit.vietpq.qr_scanner.presentation.main.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import ptit.vietpq.qr_scanner.presentation.multicodes.navigation.MultiCodesDestination
import ptit.vietpq.qr_scanner.presentation.multicodes.navigation.multiCodesGraph
import ptit.vietpq.qr_scanner.presentation.onboard.navigation.OnboardDestination
import ptit.vietpq.qr_scanner.presentation.onboard.navigation.onboardGraph
import ptit.vietpq.qr_scanner.presentation.resultscan.navigation.QrCodeResultDestination
import ptit.vietpq.qr_scanner.presentation.resultscan.navigation.qrCodeResultGraph
import ptit.vietpq.qr_scanner.presentation.scan.ScanViewModel
import ptit.vietpq.qr_scanner.presentation.scan.navigation.ScanDestination
import ptit.vietpq.qr_scanner.presentation.scan.navigation.scanGraph
import ptit.vietpq.qr_scanner.presentation.setting.navigation.settingGraph
import ptit.vietpq.qr_scanner.presentation.splash.navigation.SplashDestination
import ptit.vietpq.qr_scanner.presentation.splash.navigation.SplashDestinationEnum
import ptit.vietpq.qr_scanner.presentation.splash.navigation.splashGraph
import ptit.vietpq.qr_scanner.presentation.thanks.navigation.ThanksDestination
import ptit.vietpq.qr_scanner.presentation.thanks.navigation.thanksGraph
import ptit.vietpq.qr_scanner.presentation.area_code.navigation.AreaCodeNavigation
import ptit.vietpq.qr_scanner.presentation.area_code.navigation.areaCodeNavigationGraph
import ptit.vietpq.qr_scanner.presentation.create.navigation.createGraph
import ptit.vietpq.qr_scanner.presentation.create_detail.navigation.CreateQrDetailNavigation
import ptit.vietpq.qr_scanner.presentation.create_detail.navigation.createQrDetailGraph
import ptit.vietpq.qr_scanner.presentation.create_result.navigation.CreateQrResultNavigation
import ptit.vietpq.qr_scanner.presentation.create_result.navigation.createQrResultGraph
import ptit.vietpq.qr_scanner.presentation.customize.navigation.CustomNavigation
import ptit.vietpq.qr_scanner.presentation.customize.navigation.customRoute
import ptit.vietpq.qr_scanner.presentation.history.navigation.HistoryDestination
import ptit.vietpq.qr_scanner.presentation.history.navigation.historyGraph
import ptit.vietpq.qr_scanner.presentation.history_selection.navigation.HistorySelectionNavigation
import ptit.vietpq.qr_scanner.presentation.history_selection.navigation.historySelectionGraph
import ptit.vietpq.qr_scanner.presentation.language.navigation.LanguageDestination
import ptit.vietpq.qr_scanner.presentation.language.navigation.languageGraph
import ptit.vietpq.qr_scanner.presentation.main.navigation.destination.QRNavigationDestination
import ptit.vietpq.qr_scanner.ui.common.ExitAppBottomSheet

@Composable
fun QRCodeNavHost(
  navController: NavHostController,
  startDestination: QRNavigationDestination,
  onNavigateToDestination: (QRNavigationDestination, String) -> Unit,
  onNavigateToDestinationPopUpTo: (QRNavigationDestination, String) -> Unit,
  onNavigateToDestinationPopUpToSplash: (QRNavigationDestination) -> Unit,
  onBackClick: () -> Unit,
  onShowMessage: (String) -> Unit,
  onSetSystemBarsColorTransparent: () -> Unit,
  onResetSystemBarsColor: () -> Unit,
  showBottomBar: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: ScanViewModel = hiltViewModel(),
  mainViewModel: QrAppViewModel = hiltViewModel(),
) {
  val activity = LocalContext.current as Activity

  var showLoadingDialog by rememberSaveable { mutableStateOf(false) }

  var showExitBottomSheet by rememberSaveable { mutableStateOf(false) }



  ExitAppBottomSheet(
    isBottomSheetVisible = showExitBottomSheet,
    onExitClicked = {
      showExitBottomSheet = false
      activity.finishAffinity()
    },
    onDismissRequest = {
      showExitBottomSheet = false
    },
  )

  NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = startDestination.route,
  ) {

    splashGraph(
      onSplashFinished = {
        val destination = when (it) {
          SplashDestinationEnum.TO_LANGUAGE -> LanguageDestination
          SplashDestinationEnum.TO_INTRO -> OnboardDestination
          SplashDestinationEnum.TO_MAIN -> TopLevelDestination.Scan
        }
        navController.navigate(destination.route) {
          popUpTo(SplashDestination.route) {
            inclusive = true
            saveState = false
          }
        }
      },
    )

    languageGraph(
      onLanguageFinished = {
        navController.navigate(OnboardDestination.route) {
          popUpTo(LanguageDestination.route) {
            inclusive = true
            saveState = false
          }
        }
      },
      onBackPressed = onBackClick,
    )

    onboardGraph(
      mainViewModel = mainViewModel,
      onOnboardFinished = {
        navController.navigate(TopLevelDestination.Scan.route) {
          popUpTo(OnboardDestination.route) {
            inclusive = true
            saveState = false
          }
        }
      },
    )

    scanGraph(
      viewModel = viewModel,
      qrCodeResult = {
        onNavigateToDestination(
          QrCodeResultDestination,
          QrCodeResultDestination.createNavigationRoute(it),
        )
      },
      onShowTabMultiCodes = {
        onNavigateToDestination(
          MultiCodesDestination,
          MultiCodesDestination.route,
        )
      },

      showBottomSheet = {
        showBottomBar(it)
      },
      onImagePick = {

      },
      // Todo : showExitBottomSheet, fix this
      showExitBottomSheet = {
        navController.navigate(ThanksDestination.route) {
          popUpTo(ScanDestination.route) {
            inclusive = true
            saveState = false
          }
        }
      },
    )

    multiCodesGraph(
      viewModel = viewModel,
      onBackPress = onBackClick,
      onNavigationResult = {
        onNavigateToDestination(
          QrCodeResultDestination,
          QrCodeResultDestination.createNavigationRoute(it),
        )
      },
    )

    createQrDetailGraph(
      mainViewModel = mainViewModel,
      onBackPressed = {
        onBackClick()
      },
      onCountryCodeClicked = {
        onNavigateToDestination(
          AreaCodeNavigation,
          AreaCodeNavigation.route,
        )
      },
      onCreatePressed = {
        onNavigateToDestination(
          CreateQrResultNavigation,
          CreateQrResultNavigation.route,
        )
      },
    )

    createGraph(
      onItemClicked = {
        onNavigateToDestination(
          CreateQrDetailNavigation,
          CreateQrDetailNavigation.createNavigationRoute(it.titleResId),
        )
      },
    )

    areaCodeNavigationGraph(
      onAreaSelected = {
        navController.previousBackStackEntry?.savedStateHandle?.set(AreaCodeNavigation.areaCode, it)
        navController.popBackStack()
      },
      onBackPressed = onBackClick,
    )

    createQrResultGraph(
      mainViewModel = mainViewModel,
      onBackPressed = {
        onBackClick()
      },
      onDoneButtonClicked = {
        onNavigateToDestinationPopUpTo(
          ScanDestination,
          ScanDestination.route,
        )
      },
      onEditClicked = {
        onNavigateToDestination(
          CustomNavigation,
          CustomNavigation.route,
        )
      },
    )

    customRoute { onBackClick() }

    historyGraph(
      onItemClicked = { isCreated, data ->
        if (isCreated) {
          onNavigateToDestination(
            CreateQrResultNavigation,
            CreateQrResultNavigation.route,
          )
        } else {
          onNavigateToDestination(
            QrCodeResultDestination,
            QrCodeResultDestination.createNavigationRoute(data),
          )
        }
      },
      onItemLongClicked = {
        onNavigateToDestination(
          HistorySelectionNavigation,
          HistorySelectionNavigation.createNavigationRoute(it),
        )
      },
      onScanNowClicked = {
        val route = if (it) TopLevelDestination.Create.route else TopLevelDestination.Scan.route
        navController.navigate(route) {
          popUpTo(HistoryDestination.route) {
            inclusive = true
            saveState = false
          }
        }
      },
    )

    historySelectionGraph(
      mainViewModel = mainViewModel,
      onBackPress = {
        onBackClick()
      },
    )

    settingGraph(
      onLanguageNavigating = {
        onNavigateToDestination(
          LanguageDestination,
          LanguageDestination.route,
        )
      },
    )

    qrCodeResultGraph(
      mainVieModel = mainViewModel,
      onBackPressed = {
        onNavigateToDestinationPopUpTo(
          ScanDestination,
          ScanDestination.route,
        )
      },
    )

    thanksGraph()

  }
}
