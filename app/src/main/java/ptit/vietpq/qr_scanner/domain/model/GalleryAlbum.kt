package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class GalleryAlbum(
  val name: String,
  val imagePaths: ImmutableList<String>,
  val thumbNailPath: String,
) {
  companion object{
    val INITIAL = GalleryAlbum("", persistentListOf(), "")
  }
}
