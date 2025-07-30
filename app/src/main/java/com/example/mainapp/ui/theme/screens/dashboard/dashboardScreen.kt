package com.example.mainapp.ui.theme.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mainapp.R
import com.example.mainapp.navigation.ROUTE_ADDPATIENT


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    val selectedItem = remember { mutableStateOf(1) }
    Scaffold (bottomBar = {
        NavigationBar (containerColor = Color.Magenta){
            NavigationBarItem(
                selected = selectedItem.value == 0,
                onClick = {},
                icon = { Icon(Icons.Filled.Share, contentDescription = "share") },
                label = { Text("Share") },
                alwaysShowLabel = true,

                )
            NavigationBarItem(
                selected = selectedItem.value == 1,
                onClick = {},
                icon = { Icon(Icons.Filled.Home, contentDescription = "home") },
                label = { Text("Home") },
                alwaysShowLabel = true,

                )
            NavigationBarItem(
                selected = selectedItem.value == 2,
                onClick = {},
                icon = { Icon(Icons.Filled.Email, contentDescription = "email") },
                label = { Text("Email us") },
                alwaysShowLabel = true,

                )


        }
    })
    { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Image(painter = painterResource(id = R.drawable.background),
                contentDescription = "background image",
                modifier = Modifier.fillMaxWidth().height(750.dp),
                contentScale = ContentScale.FillBounds)

        }
        Column (modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally){
            TopAppBar(title = {Text(text = "Patients Log")},
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Person, contentDescription = "Person")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Logout")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Magenta,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
            Row (modifier = Modifier.wrapContentWidth()){
                Card (
                    modifier = Modifier.padding(10.dp).clickable { navController.navigate(
                        ROUTE_ADDPATIENT) },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ){
                    Box(modifier = Modifier.height(100.dp).padding(20.dp), contentAlignment = Alignment.Center) {
                        Text(text = "Add Patient",)

                    }
                }
                Spacer(modifier = Modifier.width(30.dp))
                Card (
                    modifier = Modifier.padding(20.dp).clickable {  },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ){
                    Box(modifier = Modifier.height(100.dp).padding(20.dp), contentAlignment = Alignment.Center) {
                        Text(text = "View Patient",)

                    }
                }

            }


            Row (modifier = Modifier.wrapContentWidth()) {
                Card(
                    modifier = Modifier.padding(10.dp).clickable { },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Box(
                        modifier = Modifier.height(100.dp).padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Add Patient",)

                    }

                }
                Spacer(modifier = Modifier.width(30.dp))
                Card(
                    modifier = Modifier.padding(10.dp).clickable { },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Box(
                        modifier = Modifier.height(100.dp).padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "View Patient",)

                    }
                }
            }

        }
    }
}








@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview(){
    DashboardScreen(rememberNavController())
}