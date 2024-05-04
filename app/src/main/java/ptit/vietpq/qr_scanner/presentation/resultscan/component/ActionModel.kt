package ptit.vietpq.qr_scanner.presentation.resultscan.component

import android.content.Context
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.persistentListOf
import ptit.vietpq.qr_scanner.R

enum class MenuOptionIndex {
  OPEN_URL,
  COPY,
  CONNECT_WIFI,
  COPY_PASSWORD_WIFI,
  ADD_CONTACT,
  CALL_PHONE,
  DIRECTION,
  SHARE,
  SEARCH_WEB,
  SEND_MAIL,
  SEND_SMS,
  SHOW_ON_MAP,
  ADD_CALENDAR_EVENT,
}

@Immutable
data class ActionOptionModel(val id: MenuOptionIndex, val image: Int, val title: String) {
  companion object {
    fun createMenuOptionModel(id: MenuOptionIndex, image: Int, title: String) = ActionOptionModel(
      id = id,
      image = image,
      title = title,
    )

    fun emailListAction(context: Context) = persistentListOf(
      ActionOptionModel(
        id = MenuOptionIndex.SEND_MAIL,
        image = R.drawable.ic_action_send_email,
        title = context.getString(R.string.send_email),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.ADD_CONTACT,
        image = R.drawable.ic_action_add_contact,
        title = context.getString(R.string.add_contact),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.COPY,
        image = R.drawable.ic_action_copy,
        title = context.getString(R.string.copy_action),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.SHARE,
        image = R.drawable.ic_action_share,
        title = context.getString(R.string.share),
      ),
    )

    fun urlBookmarkAction(context: Context) = persistentListOf(
      ActionOptionModel(
        id = MenuOptionIndex.OPEN_URL,
        image = R.drawable.ic_open_web,
        title = context.getString(R.string.open_url),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.COPY,
        image = R.drawable.ic_action_copy,
        title = context.getString(R.string.copy_action),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.SHARE,
        image = R.drawable.ic_action_share,
        title = context.getString(R.string.share),
      ),
    )

    fun wifiListAction(context: Context) = persistentListOf(
      ActionOptionModel(
        id = MenuOptionIndex.CONNECT_WIFI,
        image = R.drawable.ic_connect_wifi,
        title = context.getString(R.string.connect),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.COPY_PASSWORD_WIFI,
        image = R.drawable.ic_copy_pass,
        title = context.getString(R.string.copy_password),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.SHARE,
        image = R.drawable.ic_action_share,
        title = context.getString(R.string.share),
      ),
    )

    fun smsListAction(context: Context) = persistentListOf(
      ActionOptionModel(
        id = MenuOptionIndex.SEND_SMS,
        image = R.drawable.ic_send_sms,
        title = context.getString(R.string.send_sms),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.COPY,
        image = R.drawable.ic_action_copy,
        title = context.getString(R.string.copy_action),
      ),
    )

    fun phoneListAction(context: Context) = persistentListOf(
      ActionOptionModel(
        id = MenuOptionIndex.ADD_CONTACT,
        image = R.drawable.ic_action_add_contact,
        title = context.getString(R.string.add_contact),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.CALL_PHONE,
        image = R.drawable.ic_call_phone,
        title = context.getString(R.string.call),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.COPY,
        image = R.drawable.ic_action_copy,
        title = context.getString(R.string.copy_action),
      ),
    )

    fun textListAction(context: Context) = persistentListOf(
      ActionOptionModel(
        id = MenuOptionIndex.SEARCH_WEB,
        image = R.drawable.ic_open_web,
        title = context.getString(R.string.search_web),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.COPY,
        image = R.drawable.ic_action_copy,
        title = context.getString(R.string.copy_action),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.SHARE,
        image = R.drawable.ic_action_share,
        title = context.getString(R.string.share),
      ),
    )

    fun contactsListAction(context: Context) = persistentListOf(
      ActionOptionModel(
        id = MenuOptionIndex.ADD_CONTACT,
        image = R.drawable.ic_action_add_contact,
        title = context.getString(R.string.add_contact),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.CALL_PHONE,
        image = R.drawable.ic_call_phone,
        title = context.getString(R.string.call),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.DIRECTION,
        image = R.drawable.ic_show_on_map,
        title = context.getString(R.string.direction),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.COPY,
        image = R.drawable.ic_action_copy,
        title = context.getString(R.string.copy_action),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.SEND_MAIL,
        image = R.drawable.ic_action_send_email,
        title = context.getString(R.string.send_email),
      ),
    )

    fun geoPointListAction(context: Context) = persistentListOf(
      ActionOptionModel(
        id = MenuOptionIndex.SHOW_ON_MAP,
        image = R.drawable.ic_show_on_map,
        title = context.getString(R.string.show_on_map),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.DIRECTION,
        image = R.drawable.ic_direction,
        title = context.getString(R.string.direction),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.COPY,
        image = R.drawable.ic_action_copy,
        title = context.getString(R.string.copy_action),
      ),
    )

    fun calendarListAction(context: Context) = persistentListOf(
      ActionOptionModel(
        id = MenuOptionIndex.ADD_CALENDAR_EVENT,
        image = R.drawable.ic_add_event,
        title = context.getString(R.string.add_to_event),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.SEND_MAIL,
        image = R.drawable.ic_action_send_email,
        title = context.getString(R.string.send_email),
      ),
      ActionOptionModel(
        id = MenuOptionIndex.COPY,
        image = R.drawable.ic_action_copy,
        title = context.getString(R.string.copy_action),
      ),
    )
  }
}
