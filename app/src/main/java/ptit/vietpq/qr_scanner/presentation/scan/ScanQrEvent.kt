package ptit.vietpq.qr_scanner.presentation.scan

sealed interface ScanQrEvent {
  data class OnQrScanned(val qrBarModeString: String) : ScanQrEvent
  data object OnQrScannedError : ScanQrEvent
  data class DeleteSuccess(val sizeRemain: Int) : ScanQrEvent
}
