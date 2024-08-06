package teka.android.organiks_platform_android.presentation.feature_auth.login

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.R
import teka.android.organiks_platform_android.navigation.AppScreens
import teka.android.organiks_platform_android.navigation.To_MAIN_GRAPH_ROUTE
import teka.android.organiks_platform_android.presentation.feature_auth.AuthViewModel
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.SignInViewModel
import teka.android.organiks_platform_android.ui.theme.*
import teka.android.organiks_platform_android.util.UiEvents


@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val loginViewModel:LoginViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val usernameState = authViewModel.usernameState.value
    val passwordState = authViewModel.passwordState.value
    val rememberMeState = authViewModel.rememberMeState.value
    var isPasswordOpen by remember { mutableStateOf(false) }
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState(false)
    val loginState = authViewModel.loginState.value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    //firebase login
    val signInViewModel = viewModel<SignInViewModel>()
    val state by signInViewModel.state.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            coroutineScope.launch {
                val intent = result.data ?: return@launch
                try {
                    val signInResult = authViewModel.googleAuthUiClient.signInWithIntent(intent)
                    signInViewModel.onSignInResult(signInResult)
                } catch (e: Exception) {
                    // Handle exception
                    e.printStackTrace()
                }
            }
        }
    }

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            Toast.makeText(
                authViewModel.applicationContext,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()

            navController.navigate(To_MAIN_GRAPH_ROUTE)
            signInViewModel.resetState()
        }
    }
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }




    val keyboardController = LocalSoftwareKeyboardController.current


    LaunchedEffect(key1 = true) {
        authViewModel.loginEventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
//                            actionLabel = "Click me",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
                is UiEvents.NavigateEvent -> {
                    coroutineScope.launch {
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
        Toast.makeText(context, "Login successful.", Toast.LENGTH_SHORT).show()
    }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(0.dp) ,
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

                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = GrayColor,
                                unfocusedLabelColor = Color.Gray,
                                focusedLabelColor = PrimaryColor,
                                cursorColor = Color.Black,
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
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.Black,
                                unfocusedLabelColor = Color.Gray,
                                focusedLabelColor = PrimaryColor,
                                cursorColor = Color.Black,
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
                                containerColor = PrimaryColor,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(vertical = 14.dp),
                            shape = Shapes.large,
                        ) {
                            Text(text = "Login", fontFamily = Poppins)
                        }


                        TextButton(
                            onClick = {},
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

                        //bottom section
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                thickness = 1.dp,
                                color = GrayColor
                            )
                            Text(
                                text = "Or",
                                modifier = Modifier.padding(10.dp),
                                fontSize = 20.sp,
                                fontFamily = Poppins,
                                )
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                thickness = 1.dp,
                                color = GrayColor
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        val signInIntentSender = authViewModel.googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color(android.graphics.Color.parseColor("#d2d2d2")),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.google_svg),
                                    contentDescription = "Google Logo",
                                    modifier = Modifier
                                        .size(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color(android.graphics.Color.parseColor("#d2d2d2")),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.facebook_svg),
                                    contentDescription = "Google Logo",
                                    modifier = Modifier
                                        .size(30.dp)
                                )
                            }
                        }
                    }
                }


        }

}