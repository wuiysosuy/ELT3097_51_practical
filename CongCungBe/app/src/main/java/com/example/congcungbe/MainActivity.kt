// MainActivity.kt
package com.example.congcungbe
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.toSize


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.congcungbe.ui.theme.CongCungBeTheme
import com.example.congcungbe.ui.theme.GameViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random



enum class GameMode {
    MENU, CHON_CAP_SO, NHAP_SO, DOI_DAY_SO, PHAN_XA
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CongCungBeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    GameApp()
                }
            }
        }
    }
}

@Composable
fun GameApp() {
    var mode by remember { mutableStateOf(GameMode.MENU) }
    var total by remember { mutableStateOf(10) }
    var gridSize by remember { mutableStateOf(12) }

    when (mode) {
        GameMode.MENU -> MenuScreen(
            onStart = { m, t, g ->
                total = t
                gridSize = g
                mode = m
            },
            onReflexMode = {
                mode = GameMode.PHAN_XA
            }
        )

        GameMode.CHON_CAP_SO -> GameScreen(
            total = total,
            gridSize = gridSize,
            onBack = { mode = GameMode.MENU }
        )

        GameMode.NHAP_SO -> InputGameScreen(
            total = total,
            onBack = { mode = GameMode.MENU }
        )

        GameMode.DOI_DAY_SO -> DualRowScreen(
            total = total,
            onBack = { mode = GameMode.MENU }
        )

        GameMode.PHAN_XA -> ReflexModeScreen(
            total = total,
            onBack = { mode = GameMode.MENU }
        )
    }
}

@Composable
fun ReflexModeScreen(total: Int, onBack: () -> Unit) {
    var currentX by remember { mutableStateOf(Random.nextInt(1, total)) }
    var score by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(emptyList<Int>()) }
    var timeLeft by remember { mutableStateOf(10) }

    fun generateNewRound() {
        currentX = Random.nextInt(1, total)
        options = generateOptionsWithCorrectAnswer(total, currentX)
        timeLeft = 10
    }

    LaunchedEffect(score) {
        generateNewRound()
    }

    LaunchedEffect(timeLeft) {
        if (timeLeft > 0) {
            delay(1000)
            timeLeft--
        } else {
            message = "Hết giờ! Đáp án đúng là ${total - currentX}"
        }
    }

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Chế độ phản xạ", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Điểm: $score", fontSize = 18.sp)
        Text("Thời gian còn lại: $timeLeft giây", fontSize = 16.sp, color = if (timeLeft <= 3) Color.Red else Color.Black)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Tổng: $total", fontSize = 20.sp)
        Text("Số cho trước: $currentX", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Chọn số đúng để tổng = $total", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))

        Column {
            options.chunked(3).forEach { row ->
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    row.forEach { option ->
                        Button(
                            onClick = {
                                if (option + currentX == total) {
                                    message = "Đúng rồi!"
                                    score++
                                } else {
                                    message = "Sai rồi! Đáp án đúng là ${total - currentX}"
                                }
                                generateNewRound()
                            },
                            enabled = timeLeft > 0,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text("$option")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(message, fontSize = 16.sp, color = Color.Magenta)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Quay lại") }
    }
}

fun generateOptionsWithCorrectAnswer(total: Int, x: Int): List<Int> {
    val correct = total - x
    val options = mutableSetOf(correct)
    while (options.size < 6) {
        val rand = Random.nextInt(1, total + 5)
        if (rand != correct) options.add(rand)
    }
    return options.shuffled()
}

@Composable
fun MenuScreen(
    onStart: (GameMode, Int, Int) -> Unit,
    onReflexMode: () -> Unit
) {
    var input by remember { mutableStateOf("") }
    var gridInput by remember { mutableStateOf("12") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nhập tổng a bạn muốn:", fontSize = 20.sp)
        OutlinedTextField(value = input, onValueChange = { input = it }, label = { Text("Tổng a") })
        Spacer(modifier = Modifier.height(8.dp))
        Text("Số ô vuông muốn tạo:", fontSize = 20.sp)
        OutlinedTextField(value = gridInput, onValueChange = { gridInput = it }, label = { Text("Số nút") })
        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            Text(error, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(onClick = {
            val num = input.toIntOrNull()
            val grid = gridInput.toIntOrNull()
            if (num != null && grid != null && num in 2..20 && grid in 6..30) {
                onStart(GameMode.CHON_CAP_SO, num, grid)
            } else {
                error = "Tổng (2–20) và số ô (6–30) phải hợp lệ"
            }
        }) {
            Text("Chơi kiểu chọn cặp số ")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val num = input.toIntOrNull()
            if (num != null && num in 2..20) {
                onStart(GameMode.NHAP_SO, num, 0)
            } else {
                error = "Tổng phải từ 2 đến 20"
            }
        }) {
            Text("Chơi kiểu nhập số từng bên")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val num = input.toIntOrNull()
            if (num != null && num in 2..20) {
                onStart(GameMode.DOI_DAY_SO, num, 0)
            } else {
                error = "Tổng phải từ 2 đến 20"
            }
        }) {
            Text("Chơi kiểu 2 dãy số đối đầu")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onReflexMode) {
            Text("Chơi chế độ phản xạ")
        }
    }
}

fun generatePairedRows(total: Int, pairCount: Int = 6): Pair<List<Int>, List<Int>> {
    val pairs = mutableSetOf<Pair<Int, Int>>()
    while (pairs.size < pairCount) {
        val x = Random.nextInt(1, total)
        val y = total - x
        if (x != y && x in 1..99 && y in 1..99) {
            pairs.add(if (x < y) Pair(x, y) else Pair(y, x))
        }
    }
    val top = pairs.map { it.first }.shuffled()
    val bottom = pairs.map { it.second }.shuffled()
    return top to bottom
}

@Composable
fun DualRowScreen(total: Int, onBack: () -> Unit) {
    val (initialTop, initialBottom) = remember { generatePairedRows(total, 6) }
    var topNumbers by remember { mutableStateOf(initialTop.toMutableList()) }
    var bottomNumbers by remember { mutableStateOf(initialBottom.toMutableList()) }
    var selectedTop by remember { mutableStateOf<Int?>(null) }
    var message by remember { mutableStateOf("") }
    var score by remember { mutableStateOf(0) }

    val scrollInterval = 1000L // thời gian giữa mỗi lần cuộn (ms)

    LaunchedEffect(Unit) {
        while (true) {
            delay(scrollInterval)
            if (topNumbers.isNotEmpty()) {
                topNumbers = topNumbers.drop(1).plus(topNumbers.first()).toMutableList()
            }
            if (bottomNumbers.isNotEmpty()) {
                bottomNumbers = bottomNumbers.dropLast(1).toMutableList().apply {
                    add(0, bottomNumbers.last())
                }
            }
        }
    }


    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Tổng cần tìm: $total", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Điểm: $score", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Người chơi 1: Chọn số", fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            topNumbers.forEach { number ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(60.dp)
                        .background(if (selectedTop == number) Color.Gray else Color.Cyan)
                        .clickable { selectedTop = number }
                ) {
                    Text("$number", fontSize = 20.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Người chơi 2: Chọn số", fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            bottomNumbers.forEach { number ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Yellow)
                        .clickable {
                            if (selectedTop != null) {
                                if (selectedTop!! + number == total) {
                                    message = "Chính xác!"
                                    score++
                                    topNumbers = topNumbers.filter { it != selectedTop }.toMutableList()
                                    bottomNumbers = bottomNumbers.filter { it != number }.toMutableList()
                                } else {
                                    message = "Sai rồi!"
                                }
                                selectedTop = null
                            } else {
                                message = "Người chơi 1 chưa chọn số."
                            }
                        }
                ) {
                    Text("$number", fontSize = 20.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(message, fontSize = 18.sp, color = Color.Magenta)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Quay lại") }
    }
}


@Composable
fun GameScreen(total: Int, gridSize: Int, onBack: () -> Unit) {
    val positions = remember { mutableStateMapOf<Int, Offset>() }
    val viewModel = remember { GameViewModel(total, gridSize) }
    val numbers by viewModel.numbers.collectAsState()
    val selected by viewModel.selected.collectAsState()
    val message by viewModel.message.collectAsState()
    val score by viewModel.score.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Tổng cần tìm: $total", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Điểm: $score", fontSize = 20.sp)
        }

        Box(modifier = Modifier.fillMaxHeight(0.8f)) {

        // Removed unused variables: boxWidth, boxHeight, density
                Canvas(modifier = Modifier.fillMaxSize().zIndex(1f)) {
                    val selectedItems = selected.filter { it in positions }
                    if (selectedItems.size == 2 && selected.sum() == total && positions.containsKey(
                            selectedItems[0]
                        ) && positions.containsKey(selectedItems[1])
                    ) {
                        val p1 = positions[selectedItems[0]]!!
                        val p2 = positions[selectedItems[1]]!!

                        // Draw Pikachu-style connector
                        drawContext.canvas.nativeCanvas.apply {
                            val path = android.graphics.Path().apply {
                                moveTo(p1.x, p1.y)
                                lineTo(p1.x, p2.y)
                                lineTo(p2.x, p2.y)
                            }
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.RED
                                strokeWidth = 8f
                                style = android.graphics.Paint.Style.STROKE
                            }
                            drawPath(path, paint)
                        }
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize().zIndex(0f)
                ) {
                    items(numbers) { number ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(60.dp)
                                .onGloballyPositioned { coords ->
                                    val center = coords.positionInRoot() + Offset(
                                        x = coords.size.width / 2f,
                                        y = coords.size.height / 2f
                                    )
                                    positions[number] = center
                                }
                                .background(
                                    when {
                                        selected.size == 2 && selected.contains(number) && selected.sum() == total -> Color.Green
                                        selected.contains(number) -> Color.Gray
                                        else -> Color.Cyan
                                    }
                                )
                                .clickable { viewModel.selectNumber(number) }
                        ) {
                            Text(text = "$number", fontSize = 24.sp)
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(message, fontSize = 18.sp, color = Color.Magenta)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { viewModel.resetGame() }) { Text("Chơi lại") }
                Button(onClick = onBack) { Text("Quay lại") }
            }
        }
    }




@Composable
fun InputGameScreen(total: Int, onBack: () -> Unit) {
    var inputX by remember { mutableStateOf("") }
    var inputY by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var score by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Tổng cần tìm: $total", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputX,
            onValueChange = { inputX = it },
            label = { Text("Người chơi 1 chọn số (x)") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = inputY,
            onValueChange = { inputY = it },
            label = { Text("Người chơi 2 nhập số (y)") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val x = inputX.toIntOrNull()
            val y = inputY.toIntOrNull()
            result = when {
                x == null || y == null -> "Vui lòng nhập số hợp lệ."
                x + y == total -> {
                    score++
                    "Chính xác!"
                }

                else -> "Sai rồi! Đáp án đúng là ${total - x}"
            }
        }) {
            Text("Kiểm tra")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(result, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Điểm: $score", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Quay lại") }
    }
}


