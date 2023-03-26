package id.co.app.nucocore.domain.entities.view

import java.io.Serializable

data class SheetSelectorGeneral(
  val titleText: String,
  val labelActionButton: String = "Dismiss",
  val hideActionButton: Boolean = false,
  val enableActionButton: Boolean = true,
) : Serializable