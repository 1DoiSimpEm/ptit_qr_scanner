package ptit.vietpq.qr_scanner.presentation.onboard

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.OnboardPage
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.main.QrAppViewModel
import kotlinx.coroutines.launch
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale
import ptit.vietpq.qr_scanner.ui.common.scaleBounce

@Composable
internal fun OnboardRoute(
  mainViewModel: QrAppViewModel,
  viewModel: OnboardViewModel = hiltViewModel(),
  onOnboardFinished: () -> Unit,
) {
  val rememberOnBroadInvoke by rememberUpdatedState(newValue = onOnboardFinished)
  val context = LocalContext.current
  val activity = LocalContext.current as Activity

  val stateLoadingAds by mainViewModel.stateLoading.collectAsStateWithLifecycle()

  OnboardScreen(
    loadingState = stateLoadingAds,
  ) {
    viewModel.onboardIntroPassed = true
    rememberOnBroadInvoke()
  }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnboardScreen(loadingState: Boolean, modifier: Modifier = Modifier, onOnboardFinished: () -> Unit) {
  val pages = remember { OnboardPage.onboardPages }
  val pagerState = rememberPagerState(pageCount = { pages.size })
  val coroutineScope = rememberCoroutineScope()

  BackHandler {
    if (pagerState.currentPage == 0) {
      onOnboardFinished.invoke()
    } else {
      coroutineScope.launch {
        pagerState.animateScrollToPage(pagerState.currentPage - 1)
      }
    }
  }
  Column(
    modifier = modifier.fillMaxSize(),
  ) {
    Scaffold(
      modifier = Modifier.weight(1f),
      topBar = {
        TopAppBar(
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = QrCodeTheme.color.neutral5,
          ),
          title = { /*TODO*/ },
          actions = {
            Row(
              modifier = Modifier.clickable {
                onOnboardFinished.invoke()
              },
            ) {
              Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = QrLocale.strings.skip,
                color = QrCodeTheme.color.neutral3,
                style = QrCodeTheme.typo.subTitle,
              )
              Image(
                painter = painterResource(id = R.drawable.ic_double_arrow),
                contentDescription = null,
                alignment = Alignment.Center,
              )
            }
          },
        )
      },
      content = { innerPadding ->
        Column(
          modifier = Modifier
            .fillMaxSize()
            .background(QrCodeTheme.color.neutral5)
            .padding(top = innerPadding.calculateTopPadding(), start = 16.dp, end = 16.dp),
        ) {
          HorizontalPager(
            modifier = Modifier
              .wrapContentHeight()
              .fillMaxWidth(),
            state = pagerState,
          ) {
            OnboardPage(
              modifier = Modifier.fillMaxWidth(),
              page = pages[it],
            )
          }
          Spacer(modifier = Modifier.size(16.dp))
          Column(
            modifier = Modifier
              .fillMaxWidth(),
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically,
            ) {
              LineIndicator(
                pagerState = pagerState,
                count = pages.size,
              )
              Button(
                modifier = Modifier
                  .height(40.dp)
                  .wrapContentWidth(),
                onClick = {
                  if (pagerState.currentPage == pages.size - 1) {
                    onOnboardFinished.invoke()
                  } else {
                    coroutineScope.launch {
                      pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                  }
                },
                shape = QrCodeTheme.shape.roundedButton,
                border = BorderStroke(1.dp, QrCodeTheme.color.primary),
                colors = ButtonDefaults.buttonColors(
                  containerColor = QrCodeTheme.color.backgroundGreen,
                  contentColor = Color.Transparent,
                ),
              ) {
                Text(
                  modifier = Modifier.scaleBounce(),
                  text = if (pagerState.currentPage == pages.size - 1) {
                    QrLocale.strings.start
                  } else {
                    QrLocale.strings.next
                  },
                  color = QrCodeTheme.color.primary,
                  style = QrCodeTheme.typo.subTitle,
                )
              }
            }
            Spacer(modifier = Modifier.size(16.dp))
          }
        }
      },
    )
  }
}

@Composable
fun OnboardPage(page: OnboardPage, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Spacer(modifier = Modifier.size(48.dp))
    Image(
      painter = painterResource(id = page.imageResId),
      contentDescription = null,
      modifier = Modifier.size(QrCodeTheme.dimen.onboardImageSize),
    )
    Spacer(modifier = Modifier.size(64.dp))
    Text(
      text = stringResource(page.titleResId),
      color = QrCodeTheme.color.neutral1,
      style = QrCodeTheme.typo.heading,
    )
    Text(
      modifier = Modifier.padding(top = QrCodeTheme.dimen.marginVerySmall),
      text = stringResource(page.descriptionResId),
      color = QrCodeTheme.color.neutral2,
      style = QrCodeTheme.typo.body,
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LineIndicator(pagerState: PagerState, count: Int, modifier: Modifier = Modifier) {
  Canvas(modifier = modifier) {
    val spacing = 8.dp.toPx()
    val dotWidth = 8.dp.toPx()
    val dotHeight = 8.dp.toPx()
    val activeDotWidth = 24.dp.toPx()
    var x = 0f
    val y = center.y

    repeat(count) { i ->
      val posOffset = pagerState.pageOffset
      val dotOffset = posOffset % 1
      val current = posOffset.toInt()

      val factor = (dotOffset * (activeDotWidth - dotWidth))

      val calculatedWidth = when {
        i == current -> activeDotWidth - factor
        i - 1 == current || (i == 0 && posOffset > count - 1) -> dotWidth + factor
        else -> dotWidth
      }
      val color = if (i == current) Color(0xFF3BB868) else Color(0xFFE7E7E7)
      drawIndicator(x, y, calculatedWidth, dotHeight, CornerRadius(12.dp.toPx()), color)
      x += calculatedWidth + spacing
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
val PagerState.pageOffset: Float
  get() = this.currentPage + this.currentPageOffsetFraction

fun DrawScope.drawIndicator(x: Float, y: Float, width: Float, height: Float, radius: CornerRadius, color: Color) {
  val rect = RoundRect(
    x,
    y - height / 2,
    x + width,
    y + height / 2,
    radius,
  )
  val path = Path().apply { addRoundRect(rect) }
  drawPath(path = path, color = color)
}

@Preview
@Composable
private fun OnboardScreenPreview() {
  OnboardScreen(
    loadingState = false,
  ) {
  }
}
