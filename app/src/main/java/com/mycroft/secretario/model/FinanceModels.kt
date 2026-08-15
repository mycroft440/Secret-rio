package com.mycroft.secretario.model

import androidx.compose.ui.graphics.Color

data class FinanceTransaction(
    val title: String,
    val subtitle: String,
    val amount: Double,
    val date: String,
    val isIncome: Boolean
)

data class ExpenseCategory(
    val name: String,
    val amount: Double,
    val color: Color
)
