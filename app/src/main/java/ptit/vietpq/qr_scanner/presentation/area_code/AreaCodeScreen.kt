package ptit.vietpq.qr_scanner.presentation.area_code

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.area_code.CountryModel
import ptit.vietpq.qr_scanner.R
import ptit.vietpq.qr_scanner.designsystem.QrCodeTheme
import ptit.vietpq.qr_scanner.designsystem.locale.QrLocale

@Composable
internal fun AreaCodeRoute(
  onAreaSelected: (String) -> Unit,
  onBackPressed: () -> Unit,
  viewModel: AreaCodeViewModel = hiltViewModel(),
) {
  AreaCodeScreen(
    onAreaSelected = onAreaSelected,
    onBackPressed = onBackPressed,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AreaCodeScreen(
  onAreaSelected: (String) -> Unit,
  onBackPressed: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val query = rememberSaveable {
    mutableStateOf("")
  }
  val textFieldColor = TextFieldDefaults.colors(
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    cursorColor = QrCodeTheme.color.primary,
    unfocusedPlaceholderColor = QrCodeTheme.color.neutral3,
    focusedLabelColor = QrCodeTheme.color.neutral1,
    unfocusedLabelColor = QrCodeTheme.color.neutral3,
    focusedContainerColor = QrCodeTheme.color.backgroundCard,
    unfocusedContainerColor = QrCodeTheme.color.backgroundCard,
  )
  val filteredCountryList = CountryModel.countryList.filter {
    it.name.contains(query.value, ignoreCase = true) || it.code.contains(query.value, ignoreCase = true)
  }
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(QrCodeTheme.color.neutral5),
  ) {
    Column {
      Row(
        modifier = Modifier.padding(
          top = QrCodeTheme.dimen.marginDefault,
          start = QrCodeTheme.dimen.marginDefault,
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Image(
          modifier = Modifier.clickable {
            onBackPressed()
          },
          painter = painterResource(id = R.drawable.ic_back),
          contentDescription = null,
        )
        Text(
          modifier = Modifier.padding(start = QrCodeTheme.dimen.marginDefault, end = QrCodeTheme.dimen.marginDefault),
          text = QrLocale.strings.selectAreaCode,
          style = QrCodeTheme.typo.heading,
        )
      }
      TextField(
        value = query.value,
        onValueChange = { query.value = it },
        placeholder = {
          Text(
            text = QrLocale.strings.searchArea,
            style = QrCodeTheme.typo.body,
            color = QrCodeTheme.color.neutral3,
          )
        },
        modifier = Modifier
          .padding(
            top = QrCodeTheme.dimen.marginSmall,
            start = QrCodeTheme.dimen.marginDefault,
            end = QrCodeTheme.dimen.marginDefault,
            bottom = QrCodeTheme.dimen.marginPrettySmall,
          )
          .fillMaxWidth(),
        shape = QrCodeTheme.shape.roundedDefault,
        colors = textFieldColor,
        textStyle = QrCodeTheme.typo.body,
        trailingIcon = {
          Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier.padding(end = QrCodeTheme.dimen.marginDefault),
          )
        },
      )
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(QrCodeTheme.color.backgroundCard),
      ) {
        LazyColumn(
          modifier = Modifier
            .padding(
              start = QrCodeTheme.dimen.marginDefault,
              end = QrCodeTheme.dimen.marginDefault,
              top = QrCodeTheme.dimen.marginPrettySmall,
            )
            .clip(QrCodeTheme.shape.roundedDefault)
            .background(QrCodeTheme.color.neutral5),

        ) {
          items(
            count = filteredCountryList.size,
            key = {
              filteredCountryList[it].name
            },
          ) { index ->
            CountryItem(
              modifier = Modifier.animateItemPlacement(),
              countryModel = filteredCountryList[index],
              onItemClicked = {
                onAreaSelected(it)
              },
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryItem(countryModel: CountryModel, onItemClicked: (String) -> Unit, modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.clickable {
      onItemClicked(countryModel.code)
    },
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          top = QrCodeTheme.dimen.marginDefault,
          start = QrCodeTheme.dimen.marginDefault,
          end = QrCodeTheme.dimen.marginDefault,
        ),
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(
        text = countryModel.name,
        style = QrCodeTheme.typo.body,
      )
      Text(
        text = countryModel.code,
        style = QrCodeTheme.typo.body,
      )
    }
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          top = QrCodeTheme.dimen.marginDefault,
          start = QrCodeTheme.dimen.marginDefault,
          end = QrCodeTheme.dimen.marginDefault,
        )
        .height(1.dp)
        .background(QrCodeTheme.color.backgroundCard),
    )
  }
}
