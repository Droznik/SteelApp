package com.mdrapp.de

import com.mdrapp.de.ui.util.UIText
import java.io.IOException


abstract class CustomException(val text: UIText) : Exception()

class UnauthorizedException: IOException()

class LoginErrorException : CustomException(UIText.StringResource(R.string.login_error))