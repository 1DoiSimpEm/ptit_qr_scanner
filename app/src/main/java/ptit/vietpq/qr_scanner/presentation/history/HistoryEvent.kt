package ptit.vietpq.qr_scanner.presentation.history

sealed class HistoryEvent {
  data class LoadActionAdsInter(val isCreated: Boolean, val result: String) : HistoryEvent()
}
