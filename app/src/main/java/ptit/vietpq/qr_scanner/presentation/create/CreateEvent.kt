package ptit.vietpq.qr_scanner.presentation.create

import ptit.vietpq.qr_scanner.presentation.create_detail.CreateQrType

sealed class CreateEvent {
  data class ActionLoadAdsInter(val createType: CreateQrType) : CreateEvent()
}
