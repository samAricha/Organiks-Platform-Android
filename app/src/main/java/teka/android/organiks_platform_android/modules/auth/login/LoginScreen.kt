package teka.android.organiks_platform_android.modules.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.modules.auth.AuthViewModel
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.navigation.To_MAIN_GRAPH_ROUTE

import teka.android.organiks_platform_android.ui.theme.*
import teka.android.organiks_platform_android.util.UiEvents


@Composable
fun LoginScreen(
    navController: NavController,
    ) {
    val context = LocalContext.current
    val loginViewModel:LoginViewModel = hiltViewModel()
    val authViewModel:AuthViewModel = hiltViewModel()
    Log.d("lscrn", "inside login screen")
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
    val usernameState = authViewModel.usernameState.value
    val passwordState = authViewModel.passwordState.value
    val rememberMeState = authViewModel.rememberMeState.value
    var isPasswordOpen by remember { mutableStateOf(false) }
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState(false)


    val loginState = authViewModel.loginState.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current


    LaunchedEffect(key1 = true) {
        authViewModel.loginEventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
//                            actionLabel = "Click me",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
                is UiEvents.NavigateEvent -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Successfully Logged In",
//                            actionLabel = "Click me",
                            duration = SnackbarDuration.Long
                        )
                    }
                    delay(1000)

                    navController.navigate(
                        event.route
                    )
                }
            }
        }
    }


    if (isLoggedIn){
        loginViewModel.saveOnBoardingState(completed = true)
        navController.popBackStack()
        navController.navigate(To_MAIN_GRAPH_ROUTE)
//        onClick
        Toast.makeText(context, "Login successful.", Toast.LENGTH_SHORT).show()
    }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    backgroundColor = Color.White,
                    elevation = 0.dp,
                    shape = BottomBoxShape.medium
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.verticalScroll(rememberScrollState())
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
                            text = "Sign In",
                            fontFamily = ReemKufi,
                            color = Color.Gray,
                            fontSize = 15.sp,
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            value = usernameState.text,
                            onValueChange = {
                                authViewModel.setUsername(it)
                            },
                            label = {
                                Text(text = "Email / Phone")
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
                                leadingIconColor = Color.LightGray
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType =
                                KeyboardType.Email
                            ),
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_user),
                                    contentDescription = "",
                                    modifier = Modifier.size(24.dp),
                                    tint = PrimaryColor,
                                )
                            },
                            shape = Shapes.large,
                        )

                        OutlinedTextField(
                            value = passwordState.text,
                            onValueChange = {
                                authViewModel.setPassword(it)
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
                                    modifier = Modifier.size(24.dp),
                                    tint = PrimaryColor,
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { isPasswordOpen = !isPasswordOpen }) {
                                    if (!isPasswordOpen) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_eye_open),
                                            contentDescription = "",
                                            modifier = Modifier.size(24.dp),
                                            tint = PrimaryColor,
                                        )
                                    } else {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_eye_close),
                                            contentDescription = "",
                                            modifier = Modifier.size(24.dp),
                                            tint = PrimaryColor,
                                        )
                                    }
                                }
                            },
                            shape = Shapes.large,
                        )

                        Button(
                            onClick = {
                                keyboardController?.hide()
                                authViewModel.login(username = usernameState.text, password = passwordState.text)
                            },
                            enabled = !loginState.isLoading,
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
                            Text(text = "Login", fontFamily = Poppins)

                        }


                        TextButton(
                            onClick = {},
                            contentPadding = PaddingValues(vertical = 0.dp)
                        ) {
                            Text(
                                text = "Forgot Password ?",
                                color = LightTextColor,
                                fontFamily = Poppins,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 26.dp)
                            )
                        }
                        TextButton(
                            onClick = {
                                      navController.navigate(AppScreens.Registration.route)
                            },
                            contentPadding = PaddingValues(vertical = 0.dp)
                        ) {
                            Text(
                                text = "Don't have an Account ? Sign Up",
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