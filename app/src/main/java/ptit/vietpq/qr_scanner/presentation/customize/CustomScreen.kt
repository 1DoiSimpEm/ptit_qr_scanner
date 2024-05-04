package ptit.vietpq.qr_scanner.presentation.customize

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.customize_qr.ShapeModel
import ptit.vietpq.qr_scanner.presentation.create_result.CreateQrResultUiState
import ptit.vietpq.qr_scanner.presentation.create_result.CreateQrResultViewModel
import ptit.vietpq.qr_scanner.presentation.customize.component.ColorPickerDialog
import ptit.vietpq.qr_scanner.presentation.customize.content.ColorContent
import ptit.vietpq.qr_scanner.presentation.customize.content.LogoContent
import ptit.vietpq.qr_scanner.presentation.customize.content.ShapeContent
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrCodeShape
import io.github.alexzhirkevich.qrose.options.QrColors
import io.github.alexzhirkevich.qrose.options.QrLogo
import io.github.alexzhirkevich.qrose.options.QrOptions
import io.github.alexzhirkevich.qrose.options.QrShapes
import io.github.alexzhirkevich.qrose.options.solid
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
fun CustomRoute(
  onBackPressed: () -> Unit,
  sharedResultViewModel: CreateQrResultViewModel = viewModel(LocalContext.current as ComponentActivity),
) {
  val uiState: CreateQrResultUiState by sharedResultViewModel.uiState.collectAsStateWithLifecycle()

  CustomScreen(
    uiState = uiState,
    onSaveButtonClicked = { shape, foregroundColor, backgroundColor, logo ->
      sharedResultViewModel.saveCustomQrCode(
        shape,
        foregroundColor,
        backgroundColor,
        logo,
      )
      onBackPressed()
    },
    onBackPressed = onBackPressed,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomScreen(
    uiState: CreateQrResultUiState,
    onSaveButtonClicked: (ShapeModel, Color, Color, Int) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
  var shape by remember {
    mutableStateOf(uiState.qrShape)
  }
  var foregroundColor by remember {
    mutableStateOf(uiState.qrForegroundColor)
  }
  var backgroundColor by remember {
    mutableStateOf(uiState.qrBackgroundColor)
  }
  var logo by remember {
    mutableIntStateOf(uiState.logo ?: R.drawable.ic_none)
  }
  var showColorPicker by remember {
    mutableStateOf(Pair(false, false))
  }
  val qrCodePainter = rememberQrCodePainter(
    data = uiState.result.text,
    options = QrOptions(
      shapes = QrShapes(
        code = QrCodeShape.Default,
        ball = shape.ball,
        frame = shape.frame,
      ),
      colors = QrColors(
        dark = QrBrush.solid(foregroundColor),
        ball = QrBrush.solid(foregroundColor),
        frame = QrBrush.solid(foregroundColor),
        light = QrBrush.solid(backgroundColor),
      ),
      logo = QrLogo(
        if (logo != R.drawable.ic_none) {
          painterResource(id = logo)
        } else {
          null
        },
      ),
    ),
  )

  ColorPickerDialog(
    isShown = showColorPicker.first,
    onDismiss = {
      showColorPicker = Pair(false, false)
    },
    onColorPicked = {
      if (showColorPicker.second) {
        backgroundColor = it
      } else {
        foregroundColor = it
      }
    },
  )

  Scaffold(
    modifier = modifier,
    containerColor = QrCodeTheme.color.backgroundCard,
    contentColor = QrCodeTheme.color.primary,
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = QrCodeTheme.color.neutral5,
          titleContentColor = QrCodeTheme.color.neutral1,
        ),
        title = {
          Text(text = QrLocale.strings.edit, style = QrCodeTheme.typo.heading, color = QrCodeTheme.color.neutral1)
        },
        navigationIcon = {
          Image(
            modifier = Modifier
              .clickable {
                onBackPressed()
              }
              .padding(QrCodeTheme.dimen.marginDefault),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
          )
        },
        actions = {
          Button(
            modifier = Modifier
              .padding(end = QrCodeTheme.dimen.marginDefault),
            colors = ButtonDefaults.buttonColors(containerColor = QrCodeTheme.color.primary),
            onClick = {
              onSaveButtonClicked(
                shape,
                foregroundColor,
                backgroundColor,
                logo,
              )
            },
          ) {
            Text(text = QrLocale.strings.save, color = QrCodeTheme.color.neutral5, style = QrCodeTheme.typo.body)
          }
        },
      )
    },
    content = { innerPadding ->
      Column(
        modifier = Modifier
          .padding(
            top = innerPadding.calculateTopPadding(),
          )
          .fillMaxSize(),
      ) {
        Image(
          modifier = Modifier
            .padding(top = 72.dp)
            .size(120.dp)
            .align(Alignment.CenterHorizontally),
          painter = qrCodePainter,
          contentDescription = null,
        )

        BottomOption(
          shape = shape,
          foregroundColor = foregroundColor,
          backgroundColor = backgroundColor,
          logo = logo,
          onShapeSelected = { shape = it },
          onForegroundColorSelected = { foregroundColor = it },
          onBackgroundColorSelected = { backgroundColor = it },
          onLogoSelected = { logo = it },
          onColorWheelStarted = {
            showColorPicker = Pair(true, it)
          },
        )
      }
    },
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomOption(
  shape: ShapeModel,
  foregroundColor: Color,
  backgroundColor: Color,
  logo: Int,
  onShapeSelected: (ShapeModel) -> Unit,
  onForegroundColorSelected: (Color) -> Unit,
  onBackgroundColorSelected: (Color) -> Unit,
  onColorWheelStarted: (Boolean) -> Unit,
  onLogoSelected: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  val titles = persistentListOf(QrLocale.strings.shape, QrLocale.strings.color, QrLocale.strings.logo)
  var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
  val pagerState = rememberPagerState(pageCount = { titles.size }, initialPage = selectedTabIndex)
  val coroutineScope = rememberCoroutineScope()

  LaunchedEffect(pagerState.currentPage) {
    selectedTabIndex = pagerState.currentPage
  }

  Card(
    modifier = modifier
      .padding(top = 72.dp)
      .clip(RoundedCornerShape(topStart = QrCodeTheme.dimen.marginDefault, topEnd = QrCodeTheme.dimen.marginDefault))
      .fillMaxSize(),
    colors = CardColors(
      containerColor = QrCodeTheme.color.neutral5,
      contentColor = QrCodeTheme.color.primary,
      disabledContainerColor = QrCodeTheme.color.neutral5,
      disabledContentColor = QrCodeTheme.color.neutral1,
    ),
  ) {
    TabRow(
      modifier = Modifier
        .padding(QrCodeTheme.dimen.marginDefault)
        .clip(QrCodeTheme.shape.roundedSuperLarge),
      indicator = {},
      divider = {},
      containerColor = QrCodeTheme.color.backgroundCard,
      selectedTabIndex = selectedTabIndex,
    ) {
      titles.forEachIndexed { index, title ->
        val isSelected by remember { derivedStateOf { selectedTabIndex == index } }
        Tab(
          modifier = Modifier
            .clip(QrCodeTheme.shape.roundedSuperLarge)
            .size(108.dp, 36.dp)
            .background(if (isSelected) QrCodeTheme.color.primary else Color.Transparent),
          text = {
            Text(
              text = title,
              style = QrCodeTheme.typo.body,
              color = if (isSelected) QrCodeTheme.color.neutral5 else QrCodeTheme.color.neutral1,
            )
          },
          selected = isSelected,
          onClick = {
            selectedTabIndex = index
            coroutineScope.launch {
              pagerState.scrollToPage(index)
            }
          },
          selectedContentColor = QrCodeTheme.color.primary,
          unselectedContentColor = QrCodeTheme.color.neutral5,
        )
      }
    }

    HorizontalPager(state = pagerState) { page ->
      when (page) {
        0 -> ShapeContent(
          modifier = Modifier.padding(horizontal = 8.dp),
          shapeModel = shape,
          onShapeSelected = onShapeSelected,
        )

        1 -> ColorContent(
          modifier = Modifier.padding(horizontal = 16.dp),
          selectedForegroundColor = foregroundColor,
          selectedBackgroundColor = backgroundColor,
          onForegroundColorSelected = onForegroundColorSelected,
          onBackgroundColorSelected = onBackgroundColorSelected,
          onColorWheelStarted = onColorWheelStarted,
        )

        2 -> LogoContent(
          modifier = Modifier.padding(horizontal = 16.dp),
          selectedLogo = logo,
          onLogoSelected = onLogoSelected,
        )
      }
    }
  }
}
