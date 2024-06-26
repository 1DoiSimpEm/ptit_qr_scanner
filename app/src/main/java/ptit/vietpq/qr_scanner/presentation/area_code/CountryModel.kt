package com.qrcode.qrscanner.barcode.barcodescan.qrreader.presentation.area_code

import android.os.Parcelable
import androidx.compose.runtime.Stable
import javax.annotation.concurrent.Immutable
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

@Immutable
@Stable
@Parcelize
data class CountryModel(val code: String, val name: String) : Parcelable {
  companion object {
    val countryList = persistentListOf(
      CountryModel("+247", "Ascension Island"),
      CountryModel("+213", "Algeria"),
      CountryModel("+376", "Andorra"),
      CountryModel("+971", "United Arab Emirates"),
      CountryModel("+93", "Afghanistan"),
      CountryModel("+1", "Antigua and Barbuda"),
      CountryModel("+1", "Anguilla"),
      CountryModel("+355", "Albania"),
      CountryModel("+374", "Armenia"),
      CountryModel("+244", "Angola"),
      CountryModel("+54", "Argentina"),
      CountryModel("+1", "American Samoa"),
      CountryModel("+43", "Austria"),
      CountryModel("+61", "Australia"),
      CountryModel("+297", "Aruba"),
      CountryModel("+994", "Azerbaijan"),
      CountryModel("+387", "Bosnia and Herzegovina"),
      CountryModel("+1", "Barbados"),
      CountryModel("+880", "Bangladesh"),
      CountryModel("+32", "Belgium"),
      CountryModel("+226", "Burkina Faso"),
      CountryModel("+359", "Bulgaria"),
      CountryModel("+973", "Bahrain"),
      CountryModel("+257", "Burundi"),
      CountryModel("+229", "Benin"),
      CountryModel("+1", "Bermuda"),
      CountryModel("+673", "Brunei"),
      CountryModel("+591", "Bolivia"),
      CountryModel("+55", "Brazil"),
      CountryModel("+1", "Bahamas"),
      CountryModel("+975", "Bhutan"),
      CountryModel("+267", "Botswana"),
      CountryModel("+375", "Belarus"),
      CountryModel("+501", "Belize"),
      CountryModel("+1", "Canada"),
      CountryModel("+61", "Cocos (Keeling) Islands"),
      CountryModel("+243", "Congo (Kinshasa)"),
      CountryModel("+236", "Central African Republic"),
      CountryModel("+242", "Congo (Brazzaville)"),
      CountryModel("+41", "Switzerland"),
      CountryModel("+225", "Côte d'Ivoire"),
      CountryModel("+682", "Cook Islands"),
      CountryModel("+56", "Chile"),
      CountryModel("+237", "Cameroon"),
      CountryModel("+86", "China"),
      CountryModel("+57", "Colombia"),
      CountryModel("+506", "Costa Rica"),
      CountryModel("+53", "Cuba"),
      CountryModel("+238", "Cape Verde"),
      CountryModel("+61", "Christmas Island"),
      CountryModel("+357", "Cyprus"),
      CountryModel("+420", "Czech Republic"),
      CountryModel("+253", "Djibouti"),
      CountryModel("+45", "Denmark"),
      CountryModel("+1", "Dominican Republic"),
      CountryModel("+593", "Ecuador"),
      CountryModel("+372", "Estonia"),
      CountryModel("+20", "Egypt"),
      CountryModel("+291", "Eritrea"),
      CountryModel("+34", "Spain"),
      CountryModel("+251", "Ethiopia"),
      CountryModel("+358", "Finland"),
      CountryModel("+679", "Fiji"),
      CountryModel("+594", "French Guiana"),
      CountryModel("+500", "Falkland Islands"),
      CountryModel("+691", "Micronesia"),
      CountryModel("+298", "Faroe Islands"),
      CountryModel("+33", "France"),
      CountryModel("+241", "Gabon"),
      CountryModel("+44", "United Kingdom"),
      CountryModel("+1", "Grenada"),
      CountryModel("+995", "Georgia"),
      CountryModel("+49", "Germany"),
      CountryModel("+233", "Ghana"),
      CountryModel("+350", "Gibraltar"),
      CountryModel("+299", "Greenland"),
      CountryModel("+220", "Gambia"),
      CountryModel("+224", "Guinea"),
      CountryModel("+240", "Equatorial Guinea"),
      CountryModel("+30", "Greece"),
      CountryModel("+502", "Guatemala"),
      CountryModel("+1", "Guam"),
      CountryModel("+245", "Guinea-Bissau"),
      CountryModel("+592", "Guyana"),
      CountryModel("+852", "Hong Kong"),
      CountryModel("+504", "Honduras"),
      CountryModel("+385", "Croatia"),
      CountryModel("+509", "Haiti"),
      CountryModel("+36", "Hungary"),
      CountryModel("+62", "Indonesia"),
      CountryModel("+353", "Ireland"),
      CountryModel("+84", "Vietnam"),
    )
  }
}
