package ptit.vietpq.qr_scanner.presentation.create_result

sealed class CreateResultEvent {
  data object OnBackPressed : CreateResultEvent()
}
