package ptit.vietpq.qr_scanner.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ptit.vietpq.qr_scanner.presentation.main.navigation.QRCodeNavHost
import ptit.vietpq.qr_scanner.presentation.main.navigation.TopLevelDestination
import ptit.vietpq.qr_scanner.presentation.splash.navigation.SplashDestination
import kotlinx.collections.immutable.ImmutableList
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.internal.QRCodeScannerScanBarcodeTheme

@Composable
fun QrCodeApp(modifier: Modifier = Modifier, appState: QrCodeAppState = rememberQrAppState()) {
  QRCodeScannerScanBarcodeTheme {
    Scaffold(
      modifier = modifier,
      bottomBar = {
        AnimatedVisibility(
          visible = appState.shouldShowBottomBar,
          enter = EnterTransition.None,
          exit = ExitTransition.None,
        ) {
          Column {
            QrCodeBottomBar(
              destinations = appState.topLevelDestinations,
              currentDestination = appState.currentTopLevelDestination,
              onNavigateToDestination = appState::navigate,
            )
            Box(
              modifier = remember {
                Modifier
                  .navigationBarsPadding()
              },
            )
          }
        }
      },
      snackbarHost = {
      },
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .padding(paddingValues = innerPadding)
          .consumeWindowInsets(paddingValues = innerPadding),
      ) {
        QRCodeNavHost(
          navController = appState.navController,
          startDestination = SplashDestination,
          onNavigateToDestination = appState::navigate,
          onNavigateToDestinationPopUpTo = appState::navigateWithPopUpTo,
          onNavigateToDestinationPopUpToSplash = appState::navigateWithPopUpToRoute,
          onBackClick = appState::onBackClick,
          onShowMessage = { message -> appState.showMessage(message) },
          onSetSystemBarsColorTransparent = { },
          onResetSystemBarsColor = { },
          showBottomBar = appState::setShowBottomSheetDialog,
        )
      }
    }
  }
}

@Composable
private fun QrCodeBottomBar(
  destinations: ImmutableList<TopLevelDestination>,
  currentDestination: TopLevelDestination,
  onNavigateToDestination: (TopLevelDestination) -> Unit,
  modifier: Modifier = Modifier,
  color: Color = QrCodeTheme.color.background,
) {
  BottomNavigation(
    modifier = modifier,
    backgroundColor = color,
  ) {
    destinations.forEach { destination ->
      val selected = destination == currentDestination

      val icon = painterResource(id = destination.iconResourceId)
      val text = stringResource(id = destination.textResourceId)

      val tint by animateColorAsState(
        if (selected) {
          QrCodeTheme.color.primary
        } else {
          QrCodeTheme.color.neutral3
        },
        label = text,
      )
      BottomNavigationItem(
        icon = {
          Icon(
            painter = icon,
            tint = tint,
            contentDescription = text,
          )
        },
        label = {
          Text(
            text = text,
            color = tint,
            maxLines = 1,
            style = QrCodeTheme.typo.innerBoldSize12Line16,
          )
        },
        selected = selected,
        onClick = { onNavigateToDestination(destination) },
      )
    }
  }
}

private val BottomBarEnterTransition = fadeIn() + expandVertically(expandFrom = Alignment.Top)
private val BottomBarExitTransition = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
