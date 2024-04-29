package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model

data class DriverLicense(
  val driverLicenseNumber: String,
  val driverFirstName: String,
  val driverMiddleName: String,
  val driverLastName: String,
  val driverGender: String,
  val driverBirthDate: String,
  val driverAddressStreet: String,
  val driverAddressCity: String,
  val driverAddressState: String,
  val driverAddressZip: String,
  val driverIssueDate: String,
  val driverExpiryDate: String?,
  val driverIssuingCountry: String,
) : BaseBarcodeModel(null),
  Schema {
  override val schema: BarcodeSchema
    get() = BarcodeSchema.OTHER

  override fun toFormattedText(): String = "Dr"

  override fun toBarcodeText(): String = ""
}
