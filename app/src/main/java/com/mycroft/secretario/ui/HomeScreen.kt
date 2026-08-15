package com.mycroft.secretario.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mycroft.secretario.model.ExpenseCategory
import com.mycroft.secretario.model.FinanceTransaction
import java.text.NumberFormat
import java.util.Locale

private data class BottomDestination(
    val label: String,
    val icon: ImageVector
)

private val destinations = listOf(
    BottomDestination("Início", Icons.Default.Home),
    BottomDestination("Movimentações", Icons.Default.ReceiptLong),
    BottomDestination("Análises", Icons.Default.PieChart),
    BottomDestination("Ajustes", Icons.Default.Settings)
)

private val receivedTransactions = listOf(
    FinanceTransaction(
        title = "Luiz Gustavo",
        subtitle = "Pix recebido",
        amount = 0.0,
        date = "",
        isIncome = true
    ),
    FinanceTransaction(
        title = "Lotérica",
        subtitle = "Depósito",
        amount = 0.0,
        date = "",
        isIncome = true
    )
)

private val expenseTransactions = listOf(
    FinanceTransaction(
        title = "Compra Shopee",
        subtitle = "Secador de cabelo",
        amount = 300.0,
        date = "19 ago",
        isIncome = false
    ),
    FinanceTransaction(
        title = "Compra lanche",
        subtitle = "Lanche",
        amount = 40.0,
        date = "11 ago",
        isIncome = false
    )
)

private val expenseCategories = listOf(
    ExpenseCategory("Farmácia", 200.0, Color(0xFF4E8E62)),
    ExpenseCategory("Supermercado", 500.0, Color(0xFF3E73C4)),
    ExpenseCategory("Lanches", 140.0, Color(0xFFF2A11E)),
    ExpenseCategory("Ônibus", 30.0, Color(0xFF7266C7)),
    ExpenseCategory("Pix para terceiros", 800.0, Color(0xFF2FA7AD)),
    ExpenseCategory("Outros", 663.0, Color(0xFF9AA39D))
)

private const val TOTAL_RECEIVED = 3000.0
private const val TOTAL_EXPENSES = 2333.0

@Composable
fun SecretarioApp() {
    var selectedTab by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                destinations.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(destination.label, fontSize = 11.sp) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> HomeScreen(innerPadding)
            1 -> PlaceholderScreen(
                title = "Movimentações",
                message = "Aqui ficarão todas as entradas e saídas, com busca e filtros.",
                innerPadding = innerPadding
            )
            2 -> PlaceholderScreen(
                title = "Análises",
                message = "Aqui entrarão gráficos, evolução mensal e comparações.",
                innerPadding = innerPadding
            )
            else -> PlaceholderScreen(
                title = "Ajustes",
                message = "Aqui ficarão contas, categorias, segurança e integrações.",
                innerPadding = innerPadding
            )
        }
    }
}

@Composable
private fun HomeScreen(innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(start = 16.dp, top = 18.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { Header() }
        item { PeriodSelector() }
        item { FinancialSummary() }
        item {
            TransactionSection(
                title = "Recebido",
                total = TOTAL_RECEIVED,
                transactions = receivedTransactions,
                isIncome = true
            )
        }
        item {
            TransactionSection(
                title = "Gastos",
                total = TOTAL_EXPENSES,
                transactions = expenseTransactions,
                isIncome = false
            )
        }
        item { SpendingChartCard() }
    }
}

@Composable
private fun Header() {
    Column {
        Text(
            text = "Secretário",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Seu dinheiro, organizado.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun PeriodSelector() {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Período", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("01 ago — 23 ago", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun FinancialSummary() {
    val balance = TOTAL_RECEIVED - TOTAL_EXPENSES

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Saldo do período",
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.82f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = formatMoney(balance),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SummaryMiniValue("Recebido", TOTAL_RECEIVED)
                SummaryMiniValue("Gastos", TOTAL_EXPENSES)
            }
        }
    }
}

@Composable
private fun SummaryMiniValue(label: String, value: Double) {
    Column {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.72f),
            fontSize = 12.sp
        )
        Text(
            text = formatMoney(value),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp
        )
    }
}

@Composable
private fun TransactionSection(
    title: String,
    total: Double,
    transactions: List<FinanceTransaction>,
    isIncome: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isIncome) "Total recebido de 01-08 até 23-08" else "Total gasto no período",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = formatMoney(total),
                    color = if (isIncome) SecretarioGreen else SecretarioRed,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(12.dp))

            transactions.forEachIndexed { index, transaction ->
                TransactionRow(transaction)
                if (index != transactions.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(14.dp))
            Text(
                text = "Ver mais...",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun TransactionRow(transaction: FinanceTransaction) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(transaction.title, fontWeight = FontWeight.SemiBold)
            Text(
                transaction.subtitle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
            if (transaction.date.isNotBlank()) {
                Text(
                    transaction.date,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        if (transaction.amount > 0) {
            Text(
                text = (if (transaction.isIncome) "+ " else "− ") + formatMoney(transaction.amount),
                color = if (transaction.isIncome) SecretarioGreen else SecretarioRed,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun SpendingChartCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Onde você gastou",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Categorias do período",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(18.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                DonutChart(expenseCategories)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Gastos", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                    Text(
                        formatMoney(TOTAL_EXPENSES),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            expenseCategories.forEach { category ->
                CategoryRow(category)
                Spacer(Modifier.height(10.dp))
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = "As categorias informadas somavam R$ 1.670,00. Para fechar o total de R$ 2.333,00, R$ 663,00 aparecem como Outros.",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun DonutChart(categories: List<ExpenseCategory>) {
    val total = categories.sumOf { it.amount }

    Canvas(modifier = Modifier.size(165.dp)) {
        val strokeWidth = 28.dp.toPx()
        var startAngle = -90f

        categories.forEach { category ->
            val sweepAngle = ((category.amount / total) * 360.0).toFloat()
            drawArc(
                color = category.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
private fun CategoryRow(category: ExpenseCategory) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(11.dp)
                .background(category.color, RoundedCornerShape(50))
        )
        Spacer(Modifier.width(10.dp))
        Text(category.name, modifier = Modifier.weight(1f))
        Text(formatMoney(category.amount), fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun PlaceholderScreen(
    title: String,
    message: String,
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(
                message,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private fun formatMoney(value: Double): String {
    return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(value)
}
