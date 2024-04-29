package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.schema

import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BarcodeSchema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.BaseBarcodeModel
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.Schema
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.joinToStringNotNullOrBlank
import com.qrcode.qrscanner.barcode.barcodescan.qrreader.extension.startsWithIgnoreCase
import ezvcard.Ezvcard

data class ContactInfoContent(
  val name: String? = null,
  val firstName: String? = null,
  val lastName: String? = null,
  val nickname: String? = null,
  val dateOfBirth: String? = null,
  val organization: String? = null,
  val title: String? = null,
  val email: String? = null,
  val emailType: String? = null,
  val secondaryEmail: String? = null,
  val secondaryEmailType: String? = null,
  val tertiaryEmail: String? = null,
  val tertiaryEmailType: String? = null,
  val phone: String? = null,
  val phoneType: String? = null,
  val secondaryPhone: String? = null,
  val secondaryPhoneType: String? = null,
  val tertiaryPhone: String? = null,
  val tertiaryPhoneType: String? = null,
  val address: String? = null,
  val geoUri: String? = null,
  val url: String? = null,
  val description: String? = null,
) : BaseBarcodeModel(null),
  Schema {

  companion object {
    private const val SCHEMA_PREFIX = "BEGIN:VCARD"
    private const val ADDRESS_SEPARATOR = ","

    fun parse(text: String): ContactInfoContent? {
      if (text.startsWithIgnoreCase(SCHEMA_PREFIX).not()) {
        return null
      }

      val vCard = Ezvcard.parse(text).first() ?: return null
      val firstName = vCard.structuredName?.given
      val lastName = vCard.structuredName?.family
      val nickname = vCard.nickname?.values?.firstOrNull()
      val organization = vCard.organizations?.firstOrNull()?.values?.firstOrNull()
      val title = vCard.titles?.firstOrNull()?.value
      val url = vCard.urls?.firstOrNull()?.value
      val geoUri = vCard.addresses?.firstOrNull()?.geo?.toString()
      var email: String? = null
      var emailType: String? = null
      var secondaryEmail: String? = null
      var secondaryEmailType: String? = null
      var tertiaryEmail: String? = null
      var tertiaryEmailType: String? = null
      var phone: String? = null
      var phoneType: String? = null
      var secondaryPhone: String? = null
      var secondaryPhoneType: String? = null
      var tertiaryPhone: String? = null
      var tertiaryPhoneType: String? = null
      var address: String? = null

      vCard.emails?.getOrNull(0)?.apply {
        email = value
        emailType = types.getOrNull(0)?.value
      }
      vCard.emails?.getOrNull(1)?.apply {
        secondaryEmail = value
        secondaryEmailType = types.getOrNull(0)?.value
      }
      vCard.emails?.getOrNull(2)?.apply {
        tertiaryEmail = value
        tertiaryEmailType = types.getOrNull(0)?.value
      }

      vCard.telephoneNumbers?.getOrNull(0)?.apply {
        phone = this.text
        phoneType = types?.firstOrNull()?.value
      }
      vCard.telephoneNumbers?.getOrNull(1)?.apply {
        secondaryPhone = this.text
        secondaryPhoneType = types?.firstOrNull()?.value
      }
      vCard.telephoneNumbers?.getOrNull(2)?.apply {
        tertiaryPhone = this.text
        tertiaryPhoneType = types?.firstOrNull()?.value
      }

      vCard.addresses.firstOrNull()?.apply {
        address = listOf(
          country,
          postalCode,
          region,
          locality,
          streetAddress,
        ).joinToStringNotNullOrBlank(ADDRESS_SEPARATOR)
      }

      return ContactInfoContent(
        firstName = firstName,
        lastName = lastName,
        nickname = nickname,
        organization = organization,
        title = title,
        email = email,
        emailType = emailType,
        secondaryEmail = secondaryEmail,
        secondaryEmailType = secondaryEmailType,
        tertiaryEmail = tertiaryEmail,
        tertiaryEmailType = tertiaryEmailType,
        phone = phone,
        phoneType = phoneType,
        secondaryPhone = secondaryPhone,
        secondaryPhoneType = secondaryPhoneType,
        tertiaryPhone = tertiaryPhone,
        tertiaryPhoneType = tertiaryPhoneType,
        address = address,
        geoUri = geoUri,
        url = url,
      )
    }
  }

  val contentInfo: String get() = listOfNotNull(
    name,
    phone,
    firstName,
    lastName,
    email,
    geoUri,
    address,
    url,
  ).joinToString()

  override val schema: BarcodeSchema
    get() = BarcodeSchema.TYPE_CONTACT_INFO

  override fun toFormattedText(): String = "Contacts"

  override fun toBarcodeText(): String = buildString {
    append(SCHEMA_PREFIX)
    append("\nVERSION:3.0")
    name?.let { append("\nFN:$it") }
    firstName?.let { append("\nN:$it") }
    lastName?.let { append(";$it") }
    nickname?.let { append("\nNICKNAME:$it") }
    dateOfBirth?.let { append("\nBDAY:$it") }
    organization?.let { append("\nORG:$it") }
    title?.let { append("\nTITLE:$it") }
    email?.let { append("\nEMAIL:$it") }
    secondaryEmail?.let { append(";$it") }
    tertiaryEmail?.let { append(";$it") }
    phone?.let { append("\nTEL:$it") }
    secondaryPhone?.let { append(";$it") }
    tertiaryPhone?.let { append(";$it") }
    address?.let { append("\nADR:$it") }
    geoUri?.let { append("\nGEO:$it") }
    url?.let { append("\nURL:$it") }
    description?.let { append("\nNOTE:$it") }
    append("\nEND:VCARD")
  }
}
