package com.qrcode.qrscanner.barcode.barcodescan.qrreader.domain.model.customize_qr

import androidx.compose.runtime.Stable
import io.github.alexzhirkevich.qrose.options.QrBallShape
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.circle
import io.github.alexzhirkevich.qrose.options.roundCorners
import io.github.alexzhirkevich.qrose.options.square
import javax.annotation.concurrent.Immutable
import kotlinx.collections.immutable.persistentListOf

@Immutable
@Stable
data class ShapeModel(val ball: QrBallShape, val frame: QrFrameShape) {
  companion object {
    val default = ShapeModel(QrBallShape.Default, QrFrameShape.Default)
    fun getShapeList() = persistentListOf(
      ShapeModel(QrBallShape.Default, QrFrameShape.Default),
      ShapeModel(
        QrBallShape.square(),
        QrFrameShape.roundCorners(
          corner = 0.25f,
        ),
      ),
      ShapeModel(
        QrBallShape.circle(),
        QrFrameShape.roundCorners(
          corner = 0.25f,
        ),
      ),
      ShapeModel(
        QrBallShape.roundCorners(
          radius = 0.25f,
          topLeft = false,
          bottomRight = false,
        ),
        QrFrameShape.roundCorners(
          corner = 0.25f,
          topLeft = false,
          bottomRight = false,
        ),
      ),
      ShapeModel(
        QrBallShape.roundCorners(
          radius = 0.25f,
          bottomRight = false,
        ),
        QrFrameShape.roundCorners(
          corner = 0.25f,
          bottomRight = false,
        ),
      ),
      ShapeModel(
        QrBallShape.circle(),
        QrFrameShape.roundCorners(
          corner = 0.25f,
          bottomRight = false,
          bottomLeft = false,
          topRight = false,
        ),
      ),
      ShapeModel(
        QrBallShape.circle(),
        QrFrameShape.circle(),
      ),
      ShapeModel(
        QrBallShape.roundCorners(
          radius = 0.25f,
          topRight = false,
          bottomLeft = false,
        ),
        QrFrameShape.roundCorners(
          corner = 0.25f,
          topRight = false,
          bottomLeft = false,
        ),
      ),
    )
  }
}
