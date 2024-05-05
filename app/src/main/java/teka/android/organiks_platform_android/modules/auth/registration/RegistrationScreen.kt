package teka.android.organiks_platform_android.modules.auth.registration

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.modules.auth.AuthViewModel
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.navigation.To_MAIN_GRAPH_ROUTE
import teka.android.organiks_platform_android.ui.theme.BottomBoxShape
import teka.android.organiks_platform_android.ui.theme.LightTextColor
import teka.android.organiks_platform_android.ui.theme.Poppins
import teka.android.organiks_platform_android.ui.theme.PrimaryColor
import teka.android.organiks_platform_android.ui.theme.ReemKufi
import teka.android.organiks_platform_android.ui.theme.ReemKufiBold
import teka.android.organiks_platform_android.ui.theme.Shapes

@Composable
fun RegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val localContext = LocalContext.current

    val phoneState = authViewModel.registrationPhoneState.value
    val emailState = authViewModel.registrationEmailState.value
    val passwordState = authViewModel.registrationPasswordState.value
    val passwordConfirmationState = authViewModel.registrationPasswordConfirmState.value

    var isPasswordOpen by remember { mutableStateOf(false) }
    val mContext = LocalContext.current

    val registrationViewModel : RegistrationViewModel = hiltViewModel()

    val isEmailError = registrationViewModel.formState.emailError != null
    val emailErrorMessage = registrationViewModel.formState.emailError

    val isPhoneNumberError = registrationViewModel.formState.phoneNumberError != null
    val phoneNumberErrorMessage = registrationViewModel.formState.phoneNumberError

    val isPasswordError = registrationViewModel.formState.passwordError != null
    val passwordErrorMessage = registrationViewModel.formState.passwordError

    val isPasswordConfirmationError = registrationViewModel.formState.passwordConfirmationError != null
    val passwordConfirmationErrorMessage = registrationViewModel.formState.passwordConfirmationError

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()



    // Automatically navigate when registration status changes
    LaunchedEffect(key1 = Unit) {
        val job = authViewModel.isRegistered.collect { isRegisterd ->
            if (isRegisterd) {
                navController.navigate(To_MAIN_GRAPH_ROUTE)
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = Color.White,
            elevation = 0.dp,
            shape = BottomBoxShape.medium
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

            ) {

                Image(
                    painter = painterResource(id = R.drawable.unlock2),
                    contentDescription = "Access image",
                    modifier = Modifier
                        .size(100.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "Organiks",
                    fontFamily = ReemKufiBold,
                    fontSize = 32.sp,
                )
                Text(
                    text = "Create an Account",
                    fontFamily = ReemKufi,
                    color = Color.Gray,
                    fontSize = 15.sp,
                )
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = phoneState.text,
                    onValueChange = {
                        authViewModel.setRegistrationPhone(it)
                        registrationViewModel.onEvent(MainEvent.PhoneNumberChanged(it))
                    },
                    label = {
                        Text(text = "Phone")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 10.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType =
                        KeyboardType.Phone
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        focusedLabelColor = PrimaryColor,
                        cursorColor = Color.Black,
                        leadingIconColor = Color.LightGray,
                        trailingIconColor = Color.LightGray
                    ),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_phone_24),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = PrimaryColor
                        )
                    },
                    isError = isPhoneNumberError,
                    shape = Shapes.large,
                )
                if (isPhoneNumberError) {
                    if (phoneNumberErrorMessage != null) {
                        Text(
                            text = phoneNumberErrorMessage.asString(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = emailState.text,
                    onValueChange = {
                        authViewModel.setRegistrationEmail(it)
                        registrationViewModel.onEvent(MainEvent.EmailChanged(it))
                    },
                    label = {
                        Text(text = "Email Address")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 10.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType =
                        KeyboardType.Email
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        focusedLabelColor = PrimaryColor,
                        cursorColor = Color.Black,
                        leadingIconColor = Color.LightGray,
                        trailingIconColor = Color.LightGray
                    ),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_email_24),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = PrimaryColor
                        )
                    },
                    isError = isEmailError,
                    shape = Shapes.large,
                )
                if (isEmailError) {
                    if (emailErrorMessage != null) {
                        Text(
                            text = emailErrorMessage.asString(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = passwordState.text,
                    onValueChange = {
                        authViewModel.setRegistrationPassword(it)
                        registrationViewModel.onEvent(MainEvent.PasswordChanged(it))
                    },
                    label = {
                        Text(text = "Password")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        focusedLabelColor = PrimaryColor,
                        cursorColor = Color.Black,
                        leadingIconColor = Color.LightGray,
                        trailingIconColor = Color.LightGray
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = if (!isPasswordOpen) PasswordVisualTransformation() else VisualTransformation.None,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_lock_24),
                            contentDescription = "",
                            tint = PrimaryColor,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { isPasswordOpen = !isPasswordOpen }) {
                            if (!isPasswordOpen) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_eye_open),
                                    contentDescription = "",
                                    tint = PrimaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_eye_close),
                                    contentDescription = "",
                                    tint = PrimaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    },
                    isError = isPasswordError,
                    shape = Shapes.large,
                )
                if (isPasswordError) {
                    if (passwordErrorMessage != null) {
                        Text(
                            text = passwordErrorMessage.asString(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }



                //password confirmation
                OutlinedTextField(
                    value = passwordConfirmationState.text,
                    onValueChange = {
                        authViewModel.setRegistrationConfirmPassword(it)
                        registrationViewModel.onEvent(MainEvent.PasswordConfirmationChanged(it))
                    },
                    label = {
                        Text(text = "Confirm Password")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Gray,
                        textColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        focusedLabelColor = PrimaryColor,
                        cursorColor = Color.Black,
                        leadingIconColor = Color.LightGray,
                        trailingIconColor = Color.LightGray
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = if (!isPasswordOpen) PasswordVisualTransformation() else VisualTransformation.None,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_password),
                            contentDescription = "",
                            tint = PrimaryColor,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { isPasswordOpen = !isPasswordOpen }) {
                            if (!isPasswordOpen) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_eye_open),
                                    contentDescription = "",
                                    tint = PrimaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_eye_close),
                                    contentDescription = "",
                                    tint = PrimaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    },
                    isError = isPasswordConfirmationError,
                    shape = Shapes.large,
                )
                if (isPasswordConfirmationError) {
                    if (passwordConfirmationErrorMessage != null) {
                        Text(
                            text = passwordConfirmationErrorMessage.asString(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }




                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (!isPhoneNumberError && !isEmailError && !isPasswordError && !isPasswordConfirmationError){
                            authViewModel.register(
                                phone = phoneState.text,
                                email = emailState.text,
                                password = passwordState.text,
                                passwordConfirmation = passwordConfirmationState.text
                            )
                        }else{
                            Toast.makeText(localContext,"Please fix the errors", Toast.LENGTH_SHORT).show()
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = PrimaryColor,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(vertical = 14.dp),
                    shape = Shapes.large,
                ) {
                    Text(text = "Register", fontFamily = Poppins)

                }



                TextButton(
                    onClick = {
                        Toast.makeText(mContext, "Feature coming soon", Toast.LENGTH_SHORT).show()

                    },
                    contentPadding = PaddingValues(vertical = 0.dp)
                ) {
                    Text(
                        text = "Forgot Password ?",
                        fontFamily = Poppins,
                        color = LightTextColor,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 26.dp)
                    )
                }
                TextButton(
                    onClick = { navController.navigate(route = AppScreens.Login.route) },
                    contentPadding = PaddingValues(vertical = 0.dp)
                ) {
                    Text(
                        text = "Already have an Account ? Log In",
                        color = LightTextColor,
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
