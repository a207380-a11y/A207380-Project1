package com.example.a207380_caizhengxiang_encikizwanbinazmi_Project1

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ================= 屏幕 1：主页 (完美保留) =================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoSwapMainScreen(
    onNavigateToAdd: () -> Unit,
    onNavigateToSummary: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var displayMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            MyTopBar(
                currentText = inputText,
                onTextChange = { inputText = it },
                onSearchClick = {
                    displayMessage = if (inputText.isNotBlank()) "Searching for: $inputText" else "Please enter an item."
                }
            )
        },
        bottomBar = {
            // 激活底部导航栏的点击事件
            MyBottomBar(
                onExploreClick = { /* 当前已经在主页，无操作 */ },
                onForYouClick = onNavigateToSummary,
                onSellClick = onNavigateToAdd,
                onMeClick = onNavigateToProfile
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState())
        ) {
            Text("A207380 - CAI ZHENGXIANG", color = MaterialTheme.colorScheme.outline, modifier = Modifier.padding(start = 16.dp, top = 8.dp))
            if (displayMessage.isNotEmpty()) {
                Text(displayMessage, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
            }
            Text("Popular Categories", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(16.dp, 8.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                CategoryItem("Books", R.drawable.book)
                CategoryItem("Electronics", R.drawable.electronics)
                CategoryItem("Clothes", R.drawable.clothes)
                CategoryItem("Others", R.drawable.others)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Recently Added", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ProductItem("Python Book", "Like New - Swap", R.drawable.item_book, Modifier.weight(1f))
                ProductItem("Old Monitor", "Working - Free", R.drawable.item_monitor, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ProductItem("Desk Lamp", "Swap for A4 Paper", R.drawable.item_lamp, Modifier.weight(1f))
                ProductItem("T-shirt", "Size M - Free", R.drawable.item_coat, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ================= 屏幕 2：填写表单页面 (完美保留) =================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSwapScreen (viewModel: EcoSwapViewModel, onNavigateToConfirm: () -> Unit, onNavigateBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Swap Item") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, "Back") }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = uiState.itemName,
                onValueChange = { viewModel.updateItemDetails(it, uiState.itemCondition) },
                label = { Text("Item Name (e.g., Python Book)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.itemCondition,
                onValueChange = { viewModel.updateItemDetails(uiState.itemName, it) },
                label = { Text("Condition (e.g., Like New)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onNavigateToConfirm, modifier = Modifier.fillMaxWidth()) {
                Text("Preview Item")
            }
        }
    }
}

// ================= 屏幕 3：确认页面 (增加发布逻辑) =================
@Composable
fun ConfirmationScreen(viewModel: EcoSwapViewModel, onNavigateHome: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(Icons.Default.CheckCircle, contentDescription = "Success", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Item Ready for Swap!", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Item: ${uiState.itemName}", style = MaterialTheme.typography.bodyLarge)
                Text("Condition: ${uiState.itemCondition}", style = MaterialTheme.typography.bodyLarge)
                Text("Owner: A207380", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.outline)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.submitCurrentItem() // 核心：正式加入列表
                onNavigateHome()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Publish & Return Home")
        }
    }
}

// ================= 屏幕 4：ViewModel 汇总列表页 (新增) =================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryListScreen(viewModel: EcoSwapViewModel, onNavigateBack: () -> Unit) {
    val itemList by viewModel.itemList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Global Inventory") },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, "Back") } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            Text("Community Items (SDG 12)", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))

            if (itemList.isEmpty()) {
                Text("No items published yet. Go to 'Sell' to add one!", color = MaterialTheme.colorScheme.outline)
            } else {
                LazyColumn {
                    items(itemList) { item ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(item.itemName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                                Text("Condition: ${item.itemCondition}", color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ================= 屏幕 5：个人主页 (新增) =================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, "Back") } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(Icons.Default.AccountCircle, contentDescription = "Profile", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("CAI ZHENGXIANG", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Matric No: A207380", color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.height(24.dp))
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Text("EcoSwap Points Earned: 150 🌱", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

// ================= 可复用小组件 (完美保留) =================
@Composable
fun ProductItem(title: String, condition: String, imageId: Int, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier.fillMaxWidth().animateContentSize().clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(painter = painterResource(id = imageId), contentDescription = title, modifier = Modifier.fillMaxWidth().height(120.dp), contentScale = ContentScale.Fit)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = condition, style = MaterialTheme.typography.bodySmall, fontSize = 12.sp, color = MaterialTheme.colorScheme.outline)
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Owner: A207380\nLoc: UKM FTSM", style = MaterialTheme.typography.bodySmall)
                Button(onClick = { }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Text("Request Swap", fontSize = 12.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(currentText: String, onTextChange: (String) -> Unit, onSearchClick: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 4.dp) {
        Row(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.onSurface)
            TextField(
                value = currentText, onValueChange = onTextChange, placeholder = { Text(" Search EcoSwap...", fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Default.Search, null, modifier = Modifier.size(20.dp)) },
                trailingIcon = { Icon(Icons.Default.CameraAlt, null, modifier = Modifier.size(20.dp)) },
                singleLine = true,
                colors = TextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent),
                shape = RoundedCornerShape(8.dp), modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
            )
            Button(onClick = onSearchClick, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), contentPadding = PaddingValues(horizontal = 12.dp), modifier = Modifier.height(40.dp)) {
                Text("Search", fontSize = 14.sp)
            }
        }
    }
}

// 核心修改：让底部导航的图标变成可点击的跳转入口
@Composable
fun MyBottomBar(onExploreClick: () -> Unit, onForYouClick: () -> Unit, onSellClick: () -> Unit, onMeClick: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 8.dp) {
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onExploreClick() }) {
                Icon(Icons.Default.Search, contentDescription = "Explore", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(26.dp))
                Text("Explore", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onForYouClick() }) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "For You", tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(26.dp))
                Text("For You", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onSellClick() }) {
                Box(modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.error, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Add, null, tint = MaterialTheme.colorScheme.onError)
                }
                Text("Sell", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Notifications, contentDescription = "Updates", tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(26.dp))
                Text("Updates", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onMeClick() }) {
                Icon(Icons.Default.Person, contentDescription = "Me", tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(26.dp))
                Text("Me", fontSize = 10.sp, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Composable
fun CategoryItem(name: String, iconId: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), modifier = Modifier.size(56.dp)) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(id = iconId), contentDescription = name, modifier = Modifier.size(32.dp), contentScale = ContentScale.Fit)
            }
        }
        Text(text = name, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 8.dp))
    }
}