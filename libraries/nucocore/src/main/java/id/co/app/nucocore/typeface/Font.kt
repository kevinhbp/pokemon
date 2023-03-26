package id.co.app.nucocore.typeface

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import id.co.app.nucocore.R

object Font {

  fun getBold(context: Context): Typeface? = ResourcesCompat.getFont(context, R.font.maisonneue_ext_bold)

  fun getDemi(context: Context): Typeface? = ResourcesCompat.getFont(context, R.font.maisonneue_ext_demi)

  fun getBook(context: Context): Typeface? = ResourcesCompat.getFont(context, R.font.maisonneue_ext_book)

}