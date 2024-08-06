package teka.android.organiks_platform_android.presentation.feature_firebase_auth.profile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import teka.android.organiks_platform_android.navigation.AUTH_GRAPH_ROUTE
import teka.android.organiks_platform_android.presentation.feature_auth.AuthViewModel
import teka.android.organiks_platform_android.presentation.feature_auth.UserState
import teka.android.organiks_platform_android.presentation.feature_firebase_auth.sign_in.UserData

@Composable
fun ProfileScreen(
    navController : NavHostController
) {
    val authViewModel: AuthViewModel = UserState.current
    val coroutineScope = rememberCoroutineScope()
    val userData: UserData? = authViewModel.googleAuthUiClient.getSignedInUser()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if(userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(
            onClick = {
                coroutineScope.launch {
//                    authViewModel.googleAuthUiClient.signOut()
                    authViewModel.firebaseUsersignOut()
                    Toast.makeText(
                        authViewModel.applicationContext,
                        "Signed out",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }) {
            Text(text = "Sign out")
        }
    }
}